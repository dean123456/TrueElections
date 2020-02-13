package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Programme implements Serializable {

    private static final long serialVersionUID = 51L;

    private int id;
    private List<Proposal> programmeProposalList;

    private Programme(int id, List<Proposal> programmeProposalList) {
        this.id = id;
        this.programmeProposalList = programmeProposalList;
    }

    public Programme() {
        this(0, new ArrayList<>());
    }

    public Programme(int id) {
        this(id, new ArrayList<>());
    }

    public Programme(List<Proposal> programmeProposalList) {
        this(0, programmeProposalList);
    }

    public int getId() {
        return id;
    }

    public List<Proposal> getProgrammeProposalList() {
        return programmeProposalList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Programme)) return false;
        Programme programme = (Programme) o;
        return getId() == programme.getId() &&
                Objects.equals(getProgrammeProposalList(), programme.getProgrammeProposalList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProgrammeProposalList());
    }
}
