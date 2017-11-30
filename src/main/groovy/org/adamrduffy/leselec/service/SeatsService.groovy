package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.domain.District
import org.adamrduffy.parly.Constituency
import org.adamrduffy.parly.MixedMemberProportionalRepresentation
import org.adamrduffy.parly.Party
import org.adamrduffy.parly.Seats
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named
class SeatsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatsService.class)

    static final TOTAL_SEATS = 120

    @Inject
    DistrictsService districtsService

    Seats read() {
        LOGGER.debug("reading districts")
        List<District> districts = districtsService.findAll()
        List<Constituency> constituencies = districts.constituencies.flatten() as List<Constituency>
        LOGGER.debug("determining elected candidates")
        MixedMemberProportionalRepresentation.determineElectedCandidate(constituencies)
        LOGGER.debug("determining constituencies with by-elections")
        int byElections = MixedMemberProportionalRepresentation.countByElections(constituencies)
        LOGGER.debug("determining parties")
        def parties = MixedMemberProportionalRepresentation.determineParties(constituencies)
        LOGGER.debug("calculating seat allocation")
        return MixedMemberProportionalRepresentation.calculateSeats(parties.values() as List<Party>, byElections, TOTAL_SEATS)
    }
}
