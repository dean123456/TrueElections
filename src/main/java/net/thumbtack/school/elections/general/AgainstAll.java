package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.Objects;

public class AgainstAll implements Serializable {

    private static final long serialVersionUID = 56L;

    private int id;
    private int voterId;

    public AgainstAll(int id, int voterId) {
        this.id = id;
        this.voterId = voterId;
    }

    public int getId() {
        return id;
    }

    public int getVoterId() {
        return voterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgainstAll)) return false;
        AgainstAll that = (AgainstAll) o;
        return getId() == that.getId() &&
                getVoterId() == that.getVoterId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVoterId());
    }
}
