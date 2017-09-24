package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Party {
    String code
    int votes
    List<Candidate> candidates = new ArrayList<>()
    List<Candidate> elected = new ArrayList<>()
    float partyQuota

    int getSeats() {
        return elected.size()
    }

    float getPrSeats() {
        return Math.max(0, partyQuota - getSeats())
    }

    int getTotalSeats() {
        return Math.max(getSeats(), (int) Math.floor(getPrSeats()))
    }

    int getVotes() {
        return candidates.sum { it.votes }
    }
}
