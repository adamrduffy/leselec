package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.service.DistrictsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
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

    District getDistrict(String constituencyCode) {
        return districtsService.findForConstituency(constituencyCode)
    }

    void viewDistrict(String districtName) {
        LOGGER.info(districtName)
        selectedConstituency.district = districtsService.find(districtName)
    }
}
