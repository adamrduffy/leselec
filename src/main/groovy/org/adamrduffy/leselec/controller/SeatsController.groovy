package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedParty
import org.adamrduffy.leselec.diagram.ParliamentArchDiagram
import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.domain.PartyColour
import org.adamrduffy.leselec.domain.Seats
import org.adamrduffy.leselec.json.JsonFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("seatsController")
class SeatsController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatsController.class)

    @Inject
    SelectedParty selectedParty

    private List<PartyColour> partyColours
    private Seats seats

    String viewPartyDetails(String partyCode) {
        LOGGER.info(partyCode + " selected")
        selectedParty.party = Party.fromJson(seats.parties.findResult { party -> partyCode.equalsIgnoreCase(party.code) ? party : null })
        return "party.html?faces-redirect=true"
    }

    String getParliamentArchDiagram() {
        return ParliamentArchDiagram.generate(seats.parties, partyColours)
    }

    List<Party> getParties() {
        return seats.parties
    }

    @PostConstruct
    void postConstruct() {
        LOGGER.info("reading json files")
        ClassPathResource partyColoursJson = new ClassPathResource("party.colours.json")
        ClassPathResource seatsJson = new ClassPathResource("seats.json")
        LOGGER.info("parsing json files and creating objects")
        partyColours = JsonFile.load(partyColoursJson.inputStream)
        seats = JsonFile.load(seatsJson.inputStream)
    }
}
