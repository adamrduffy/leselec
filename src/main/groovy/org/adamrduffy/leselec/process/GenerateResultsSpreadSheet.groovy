package org.adamrduffy.leselec.process

import org.adamrduffy.leselec.domain.Constituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.json.JsonFile

class GenerateResultsSpreadSheet {
    static void generateSpreadSheet(List<District> districts) {
        List<Constituency> constituencies = new ArrayList<>()
        Set<String> partyCodes = new TreeSet<>()
        districts.each { district ->
            district.constituencies.each { constituency ->
                def c = constituency as Constituency
                c.candidates.each { candidate ->
                    partyCodes.add(candidate.party)
                }
                constituencies.add(c)
            }
        }
        constituencies.sort(new Comparator<Constituency>() {
            @Override
            int compare(Constituency o1, Constituency o2) {
                return o1.code <=> o2.code
            }
        })

        partyCodes.each { partyCode ->
            print "," + partyCode
        }
        println ""
        constituencies.each { constituency ->
            Map<String,Integer> votes = new HashMap<>()
            constituency.candidates.each { candidate ->
                votes.put(candidate.party, candidate.votes)
            }
            print constituency.code
            partyCodes.each { partyCode ->
                print ","
                if (votes.containsKey(partyCode)) {
                    print votes.get(partyCode)
                } else {
                    print ""
                }
            }
            println ""
        }
    }

    static void main(String[] args) {
        def results = JsonFile.load("results.json")
        generateSpreadSheet(results as List<District>)
    }
}
