package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.Objects;

public class Vote implements Serializable {

    private static final long serialVersionUID = 56L;

    private int id;
    private int voterId;
    private int candidateId;

    public Vote(int id, int voterId, int candidateId) {
        this.id = id;
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    public int getId() {
        return id;
    }

    public int getVoterId() {
        return voterId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return getId() == vote.getId() &&
                getVoterId() == vote.getVoterId() &&
                getCandidateId() == vote.getCandidateId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVoterId(), getCandidateId());
    }
}
