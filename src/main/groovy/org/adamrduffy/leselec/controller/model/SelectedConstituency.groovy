package org.adamrduffy.leselec.controller.model

import groovy.transform.Canonical
import org.adamrduffy.leselec.domain.Constituency

import javax.enterprise.context.SessionScoped
import javax.inject.Named

@SessionScoped
@Named
@Canonical
class SelectedConstituency implements Serializable {
    Constituency constituency
}
