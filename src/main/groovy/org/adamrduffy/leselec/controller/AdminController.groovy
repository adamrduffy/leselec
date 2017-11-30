package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedCandidate
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.service.DistrictsService
import org.adamrduffy.parly.Candidate
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
    SelectedCandidate selectedCandidate

    List<District> getDistricts() {
        return districtsService.findAll()
    }

    void selectCandidate(Candidate candidate) {
        LOGGER.debug "selected $candidate.code $candidate.name"
        selectedCandidate.candidate = candidate
    }
}
