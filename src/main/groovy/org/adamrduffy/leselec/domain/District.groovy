package org.adamrduffy.leselec.domain

import groovy.transform.Canonical
import org.adamrduffy.parly.Constituency

@Canonical
class District {
    String name
    String url
    int resultCount
    List<Result> results
    List<Constituency> constituencies
}
