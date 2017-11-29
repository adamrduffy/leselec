package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency
import org.adamrduffy.leselec.service.ConstituencyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("constituencyController")
class ConstituencyController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstituencyController.class)

    @Inject
    ConstituencyService constituencyService

    @Inject
    SelectedConstituency selectedConstituency

    void viewConstituency(String constituencyCode) {
        LOGGER.info("constituency " + constituencyCode)
        selectedConstituency.constituency = constituencyService.find(constituencyCode)
    }

}
