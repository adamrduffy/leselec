package org.adamrduffy.leselec.controller.model

import groovy.transform.Canonical
import org.adamrduffy.parly.Party

import javax.enterprise.context.SessionScoped
import javax.inject.Named

@SessionScoped
@Named
@Canonical
class SelectedParty implements Serializable {
    Party party
}
