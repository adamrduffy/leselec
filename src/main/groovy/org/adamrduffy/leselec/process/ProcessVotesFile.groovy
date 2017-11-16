package org.adamrduffy.leselec.process

import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.domain.Seats
import org.adamrduffy.leselec.json.JsonFile

class ProcessVotesFile {
    static final TOTAL_SEATS = 120

    static Seats calculateSeats(List<Party> parties, int byelections) {
        int nationalTotalVotes = parties.sum { it.votes } as int
        float nationalQuota = nationalTotalVotes / (TOTAL_SEATS - byelections)
        println "$nationalTotalVotes $nationalQuota"
        parties.sort(new PartyCodeComparator())
        parties.each { party ->
            party.partyQuota = party.votes / nationalQuota
            party.voteShare = (party.votes / nationalTotalVotes) * 100
            println "$party.code Seats: $party.seats + $party.prSeatsRoundDown ($party.votes $party.partyQuota % $party.voteShare %)"
        }
        int seatsAllocated = parties.sum { it.getTotalSeats() } as int
        println "Total Seats Allocated: $seatsAllocated"
        int remaining = TOTAL_SEATS - seatsAllocated - byelections
        println "Allocating Remaining $remaining on Highest Remainder"
        parties.sort(new PartyPrRemainderComparator())
        parties.take(remaining).each { party ->
            println "$party.code PR Remainder $party.partyQuotaRemainder"
            party.remainderPrSeats += 1
        }
        parties.sort(new PartySeatComparator())
        parties.each { party ->
            println "$party.code Seats: $party.totalSeats"
        }
        return new Seats(parties: parties, nationalQuota: nationalQuota as float)
    }

    static Map<String, Party> findWinners(List<District> districts) {
        Map<String, Party> parties = new HashMap<>()
        districts.each { district ->
            district.constituencies.each { constituency ->
                constituency.candidates.each { candidate ->
                    candidate.districtName = district.name
                    candidate.constituencyCode = constituency.code
                    candidate.constituencyName = constituency.name

                    Party party = parties.containsKey(candidate.party) ? parties.get(candidate.party) : new Party(code: candidate.party)
                    party.candidates.add(candidate)
                    parties.put(party.code, party)
                }
                def elected = constituency.candidates.max { it.votes }
                if (!constituency.byelection) {
                    parties.get(elected.party).elected.add(elected)
                }
            }
        }
        return parties
    }

    static int countByElections(List<District> districts) {
        return districts.constituencies.flatten().sum { it.byelection ? 1 : 0 } as int
    }

    static class PartyCodeComparator implements Comparator<Party> {
        @Override
        int compare(Party o1, Party o2) {
            return o1.code <=> o2.code
        }
    }

    static class PartyPrRemainderComparator implements Comparator<Party> {
        @Override
        int compare(Party o1, Party o2) {
            return o2.partyQuotaRemainder <=> o1.partyQuotaRemainder
        }
    }

    static class PartySeatComparator implements Comparator<Party> {
        @Override
        int compare(Party o1, Party o2) {
            return o2.totalSeats <=> o1.totalSeats
        }
    }

    static void main(String[] args) {
        def results = JsonFile.load("votes.json")
        def parties = findWinners(results as List<District>)
        int byelections = countByElections(results as List<District>)
        JsonFile.save(calculateSeats(new ArrayList<Party>(parties.values()), byelections), "seats.json")
    }
}
