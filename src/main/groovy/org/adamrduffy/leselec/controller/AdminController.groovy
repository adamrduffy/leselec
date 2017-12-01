package org.adamrduffy.leselec.controller

import net.bootsfaces.utils.FacesMessages
import org.adamrduffy.leselec.controller.model.SelectedDistrictAndConstituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.service.DistrictsService
import org.adamrduffy.parly.Constituency
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("adminController")
class AdminController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class)

    @Inject
    DistrictsService districtsService
    @Inject
    SelectedDistrictAndConstituency selectedDistrictAndConstituency

    List<District> getDistricts() {
        return districtsService.findAll()
    }

    List<Constituency> getConstituencies() {
        return selectedDistrictAndConstituency.district != null ? selectedDistrictAndConstituency.district.constituencies : null
    }

    void selectDistrict(District district) {
        LOGGER.debug "selecting district $district.name"
        selectedDistrictAndConstituency.district = district
        selectedDistrictAndConstituency.constituency = null
    }

    void selectConstituency(Constituency constituency) {
        LOGGER.debug "selecting constituency $constituency.code $constituency.name"
        selectedDistrictAndConstituency.constituency = constituency
    }

    void checkOnlyOneCandidateElected(Constituency constituency) {
        LOGGER.debug "selecting constituency $constituency.code $constituency.name"
        if (constituency.candidates.findAll { c -> c.elected }.size() > 1) {
            FacesMessages.error("Only a single candidate may be elected in a constituency")
        }
    }
}
