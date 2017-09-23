package org.adamrduffy.leselec.process

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.domain.Party

class ProcessResults {
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
                parties.get(elected.party).elected.add(elected)
            }
        }
        return parties
    }

    static Object load(String filePath) {
        return new JsonSlurper().parseText(new File(filePath).text)
    }

    static save(Object content, String filePath) {
        new File(filePath).write(new JsonBuilder(content).toPrettyString())
    }

    static void main(String[] args) {
        def results = load("results.json")
        def parties = findWinners(results as List<District>)
        save(parties, "parties.json")
    }
}
