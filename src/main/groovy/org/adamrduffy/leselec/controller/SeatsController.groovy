package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.diagram.ParliamentArchDiagram
import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.domain.PartyColour
import org.adamrduffy.leselec.domain.Seats
import org.adamrduffy.leselec.json.JsonFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.faces.context.FacesContext
import javax.inject.Named

@ApplicationScoped
@Named("seatsController")
class SeatsController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatsController.class)

    @Value("classpath:party.colours.json")
    Resource partyColoursFile
    @Value("classpath:seats.json")
    Resource seatsFile

    private List<PartyColour> partyColours
    private Seats seats

    String viewPartyDetails(String partyCode) {
        LOGGER.info(partyCode + " selected")
        return "party.html"
    }

    String getParliamentArchDiagram() {
        return ParliamentArchDiagram.generate(seats.parties, partyColours)
    }

    List<Party> getParties() {
        return seats.parties
    }

    @PostConstruct
    void postConstruct() {
        LOGGER.info("parsing json files and creating objects")
        partyColours = JsonFile.load(partyColoursFile.file)
        seats = JsonFile.load(seatsFile.file)
    }
}
