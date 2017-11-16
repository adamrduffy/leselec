package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency
import org.adamrduffy.leselec.controller.model.SelectedParty
import org.adamrduffy.leselec.domain.Candidate
import org.adamrduffy.leselec.domain.Constituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.service.DistrictsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("partyController")
class PartyController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatsController.class)

    @Inject
    DistrictsService districtsService
    @Inject
    SelectedParty selectedParty
    @Inject
    SelectedConstituency selectedConstituency

    Party getParty() {
        return selectedParty.party
    }

    List<Candidate> getCandidates() {
        return selectedParty.party.candidates
    }

    boolean isCandidateElected(String candidateCode) {
        return selectedParty.party.isElected(candidateCode)
    }

    String viewConstituency(String districtName, String constituencyCode) {
        LOGGER.info(districtName + " " + constituencyCode)
        District district = districtsService.read().findResult { district -> districtName.equalsIgnoreCase(district.name) ? district : null }
        if (district != null) {
            Constituency constituency = district.constituencies.findResult { constituency -> constituencyCode.equalsIgnoreCase(constituency.code) ? constituency : null }
            if (constituency != null) {
                selectedConstituency.constituency = constituency
                return "constituency.html?faces-redirect=true"
            }

        }
        return "party.html?faces-redirect=true"
    }
}
