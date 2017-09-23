package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Party {
    String code
    int votes
    List<Candidate> candidates = new ArrayList<>()
    List<Candidate> elected = new ArrayList<>()

    int getSeats() {
        return elected.size()
    }

    int getVotes() {
        return candidates.sum { it.votes }
    }
}
