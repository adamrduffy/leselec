package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.domain.PartyColour
import org.adamrduffy.leselec.service.PartyColoursService
import org.adamrduffy.leselec.service.SeatsService
import org.adamrduffy.parly.ParliamentArchDiagram
import org.adamrduffy.parly.Parliamentarian
import org.apache.commons.lang3.StringUtils

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
        List<Party> parties = seatsService.read().parties
        List<PartyColour> partyColours = partyColoursService.read()

        List<Parliamentarian> parliamentarians = new LinkedList<>()
        parties.each { party ->
            def partyColour = partyColours.find { partyColour -> StringUtils.equalsIgnoreCase(party.code, partyColour.code) }
            party.elected.each { delegate ->
                parliamentarians.add(new Parliamentarian(name: delegate.name, party: party.code, partyColour: partyColour?.colour))
            }
            for (int seat = party.elected.size(); seat < party.totalSeats; seat++) {
                parliamentarians.add(new Parliamentarian(party: party.code, partyColour: partyColour?.colour))
            }
        }

        def seatLabel = { p -> p == null || p.party == null ? "Vacant" : (p.name == null ? "PR - $p.party" : "$p.name - $p.party") }
        return ParliamentArchDiagram.generate(parliamentarians, 120, [vacantSeatStrokeColour: '#808080'], seatLabel)
    }

    List<Party> getParties() {
        return seatsService.read().parties
    }
}
