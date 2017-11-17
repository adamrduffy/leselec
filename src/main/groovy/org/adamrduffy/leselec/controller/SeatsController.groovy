package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.diagram.ParliamentArchDiagram
import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.service.PartyColoursService
import org.adamrduffy.leselec.service.SeatsService

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("seatsController")
class SeatsController implements Serializable {
    @Inject
    PartyColoursService partyColoursService
    @Inject
    SeatsService seatsService

    String getParliamentArchDiagram() {
        return ParliamentArchDiagram.generate(seatsService.read().parties, partyColoursService.read())
    }

    List<Party> getParties() {
        return seatsService.read().parties
    }
}
