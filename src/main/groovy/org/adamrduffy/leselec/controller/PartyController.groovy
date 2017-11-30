package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency
import org.adamrduffy.leselec.controller.model.SelectedParty
import org.adamrduffy.parly.Party
import org.adamrduffy.leselec.service.DistrictsService
import org.adamrduffy.leselec.service.ElectionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.faces.event.AbortProcessingException
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("partyController")
class PartyController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartyController.class)

    @Inject
    DistrictsService districtsService
    @Inject
    ElectionService seatsService

    @Inject
    SelectedConstituency selectedConstituency
    @Inject
    SelectedParty selectedParty

    void readPartyDetails(String partyCode) {
        LOGGER.info(partyCode + " selected")
        def party = seatsService.read().parties.findResult { party -> partyCode.equalsIgnoreCase(party.code) ? party : null }
        if (party == null) {
            throw new AbortProcessingException("unable to determine party for " + partyCode)
        }
        selectedParty.party = fromJson(party)
    }

    static Party fromJson(json) {
        return new Party(code: json.code, votes: json.votes, candidates: json.candidates,
                voteShare: json.voteShare, partyQuota: json.partyQuota,
                remainderPrSeats: json.remainderPrSeats)
    }
}
