package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Party {
    String code
    int votes
    List<Candidate> candidates = new ArrayList<>()
    List<Candidate> elected = new ArrayList<>()
    float voteShare
    float partyQuota
    int remainderPrSeats

    int getSeats() {
        return elected.size()
    }

    float getPrSeats() {
        return Math.max(0, partyQuota - getSeats())
    }

    int getPrSeatsRoundDown() {
        return Math.floor(getPrSeats()) as int
    }

    float getPartyQuotaRemainder() {
        return partyQuota - Math.floor(partyQuota)
    }

    float getVoteShareRemainder() {
        return voteShare - Math.floor(voteShare)
    }

    int getTotalSeats() {
        return getSeats() + getPrSeatsRoundDown() + remainderPrSeats
    }

    int getVotes() {
        return candidates.sum { it.votes } as int
    }
}
