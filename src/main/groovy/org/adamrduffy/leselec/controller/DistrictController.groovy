package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.service.DistrictsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.faces.event.AbortProcessingException
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("districtController")
class DistrictController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistrictController.class)

    @Inject
    DistrictsService districtsService

    @Inject
    SelectedConstituency selectedConstituency

    void viewDistrict(String districtName) {
        LOGGER.info(districtName)
        District district = districtsService.read().find { district -> districtName.equalsIgnoreCase(district.name) }
        if (district == null) {
            throw new AbortProcessingException("unable to determine district for " + districtName)
        }
        selectedConstituency.district = district
    }
}
