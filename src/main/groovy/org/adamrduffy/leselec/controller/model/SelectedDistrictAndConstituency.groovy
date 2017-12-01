package org.adamrduffy.leselec.controller.model

import groovy.transform.Canonical
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.parly.Constituency

import javax.enterprise.context.SessionScoped
import javax.inject.Named

@SessionScoped
@Named
@Canonical
class SelectedDistrictAndConstituency implements Serializable {
    District district
    Constituency constituency
}
