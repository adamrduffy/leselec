package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency
import org.adamrduffy.leselec.domain.Constituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.service.DistrictsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("partyController")
class PartyController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartyController.class)

    @Inject
    DistrictsService districtsService
    @Inject
    SelectedConstituency selectedConstituency

    String viewDistrict(String districtName) {
        LOGGER.info(districtName)
        District district = districtsService.read().find { district -> districtName.equalsIgnoreCase(district.name) }
        if (district != null) {
            selectedConstituency.district = district
            return "district.html?faces-redirect=true"
        }
        return "party.html?faces-redirect=true"
    }

    String viewConstituency(String districtName, String constituencyCode) {
        LOGGER.info(districtName + " " + constituencyCode)
        District district = districtsService.read().find { district -> districtName.equalsIgnoreCase(district.name) }
        if (district != null) {
            selectedConstituency.district = district
            Constituency constituency = district.constituencies.find { constituency -> constituencyCode.equalsIgnoreCase(constituency.code) }
            if (constituency != null) {
                selectedConstituency.constituency = constituency
                return "constituency.html?faces-redirect=true"
            }

        }
        return "party.html?faces-redirect=true"
    }
}
