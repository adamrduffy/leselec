package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedParty
import org.adamrduffy.leselec.diagram.ParliamentArchDiagram
import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.service.PartyColoursService
import org.adamrduffy.leselec.service.SeatsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("seatsController")
class SeatsController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatsController.class)

    @Inject
    PartyColoursService partyColoursService
    @Inject
    SeatsService seatsService

    @Inject
    SelectedParty selectedParty

    String viewPartyDetails(String partyCode) {
        LOGGER.info(partyCode + " selected")
        def party = seatsService.read().parties.findResult { party -> partyCode.equalsIgnoreCase(party.code) ? party : null }
        if (party == null) {
            return "index.html?faces-redirect=true"
        } else {
            selectedParty.party = Party.fromJson(party)
            return "party.html?faces-redirect=true"
        }
    }

    String getParliamentArchDiagram() {
        return ParliamentArchDiagram.generate(seatsService.read().parties, partyColoursService.read())
    }

    List<Party> getParties() {
        return seatsService.read().parties
    }
}
