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

    boolean isElected(String candidateCode) {
        return elected.find { e -> candidateCode.equalsIgnoreCase( e.code ) } != null
    }

    static Party fromJson(json) {
        return new Party(code: json.code, votes: json.votes, candidates: json.candidates,
                elected: json.elected, voteShare: json.voteShare, partyQuota: json.partyQuota,
                remainderPrSeats: json.remainderPrSeats)
    }
}
