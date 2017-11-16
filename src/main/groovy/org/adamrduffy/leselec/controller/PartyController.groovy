package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedParty
import org.adamrduffy.leselec.domain.Candidate

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("partyController")
class PartyController implements Serializable {
    @Inject
    SelectedParty selectedParty

    List<Candidate> getCandidates() {
        return selectedParty.party.candidates
    }

    boolean isCandidateElected(String candidateCode) {
        return selectedParty.party.isElected(candidateCode)
    }
}
