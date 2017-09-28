package org.adamrduffy.leselec.process

import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.domain.Seats
import org.adamrduffy.leselec.json.JsonFile

class ProcessVotesFile {
    static Seats calculateSeats(List<Party> parties) {
        float nationalQuota = (parties.sum { it.votes }) / 120
        parties.sort(new Comparator<Party>() {
            @Override
            int compare(Party o1, Party o2) {
                return o1.code <=> o2.code
            }
        })
        parties.each { party ->
            party.partyQuota = party.votes / nationalQuota
            println "$party.code Seats: $party.seats + $party.prSeatsRoundDown"
        }
        int seatsAllocated = parties.sum { it.getTotalSeats() } as int
        println "Total Seats Allocated: $seatsAllocated"
        return new Seats(parties: parties, nationalQuota: nationalQuota as float)
    }

    static Map<String, Party> findWinners(List<District> districts) {
        Map<String, Party> parties = new HashMap<>()
        districts.each { district ->
            district.constituencies.each { constituency ->
                constituency.candidates.each { candidate ->
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

    static void main(String[] args) {
        def results = JsonFile.load("votes.json")
        def parties = findWinners(results as List<District>)
        JsonFile.save(calculateSeats(new ArrayList<Party>(parties.values())), "seats.json")
    }
}
