package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency
import org.adamrduffy.parly.Constituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.service.DistrictsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.faces.event.AbortProcessingException
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("constituencyController")
class ConstituencyController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstituencyController.class)

    @Inject
    DistrictsService districtsService

    @Inject
    SelectedConstituency selectedConstituency

    void viewConstituency(String districtName, String constituencyCode) {
        LOGGER.info(districtName + " " + constituencyCode)
        District district = districtsService.read().find { district -> districtName.equalsIgnoreCase(district.name) }
        if (district == null) {
            throw new AbortProcessingException("unable to determine district for " + districtName)
        } else {
            selectedConstituency.district = district
            Constituency constituency = district.constituencies.find { constituency -> constituencyCode.equalsIgnoreCase(constituency.code) }
            if (constituency == null) {
                throw new AbortProcessingException("unable to determine constituency for " + constituencyCode)
            } else {
                selectedConstituency.constituency = constituency
            }
        }
    }

}
