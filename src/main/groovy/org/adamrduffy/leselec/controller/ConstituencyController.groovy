package org.adamrduffy.leselec.controller

import org.adamrduffy.leselec.controller.model.SelectedConstituency

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("constituencyController")
class ConstituencyController implements Serializable {
    @Inject
    SelectedConstituency selectedConstituency


}
