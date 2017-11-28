package org.adamrduffy.leselec.process

import groovy.json.JsonSlurper
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.json.JsonFile
import org.adamrduffy.parly.Constituency
import org.adamrduffy.parly.MixedMemberProportionalRepresentation
import org.adamrduffy.parly.Party

class ProcessVotesFile {
    static final TOTAL_SEATS = 120

    static List<District> loadDistricts(File file) {
        return (List<District>) new JsonSlurper().parse(file)
    }

    static void main(String[] args) {
        def districts = loadDistricts(new File("votes.json"))
        List<Constituency> constituencies = districts.constituencies.flatten() as List<Constituency>
        MixedMemberProportionalRepresentation.determineElectedCandidate(constituencies)
        int byElections = MixedMemberProportionalRepresentation.countByElections(constituencies)
        def parties = MixedMemberProportionalRepresentation.determineParties(constituencies)
        JsonFile.save(MixedMemberProportionalRepresentation.calculateSeats(parties.values() as List<Party>, byElections, TOTAL_SEATS), "seats.json")
    }
}
