package org.adamrduffy.leselec.controller.model

import groovy.transform.Canonical
import org.adamrduffy.parly.Candidate

import javax.enterprise.context.SessionScoped
import javax.inject.Named

@SessionScoped
@Named
@Canonical
class SelectedCandidate implements Serializable {
    Candidate candidate
}
