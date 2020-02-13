package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.Objects;

public class Election implements Serializable {

    private static final long serialVersionUID = 27L;

    private boolean started;
    private boolean finished;

    public Election() {
        this(false, false);
    }

    public Election(Boolean started, Boolean finished) {
        this.started = started;
        this.finished = finished;
    }

    public void start() {
        this.started = true;
    }

    public void stop() {
        this.started = false;
        this.finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Election)) return false;
        Election election = (Election) o;
        return isStarted() == election.isStarted() &&
                isFinished() == election.isFinished();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isStarted(), isFinished());
    }
}
