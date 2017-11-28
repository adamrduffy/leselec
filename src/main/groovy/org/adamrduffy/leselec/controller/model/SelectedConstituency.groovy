package org.adamrduffy.leselec.controller.model

import groovy.transform.Canonical
import org.adamrduffy.parly.Constituency
import org.adamrduffy.leselec.domain.District

import javax.enterprise.context.SessionScoped
import javax.inject.Named

@SessionScoped
@Named
@Canonical
class SelectedConstituency implements Serializable {
    District district
    Constituency constituency
}
