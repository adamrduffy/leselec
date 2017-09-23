package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class District {
    String name
    String url
    int resultCount
    List<Result> results
}
