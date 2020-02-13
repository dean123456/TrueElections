package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.Objects;

public class Candidate implements Serializable {

    private static final long serialVersionUID = 55L;

    private int id;
    private boolean agree;
    private int voter_id;
    private Programme programme;

    private Candidate(int id, int voter_id, boolean agree, Programme programme) {
        this.id = id;
        this.voter_id = voter_id;
        this.agree = agree;
        this.programme = programme;
    }

    public Candidate(int voter_id) {
        this(0, voter_id, false, new Programme());
    }

    public Candidate(int id, int voter_id, boolean agree) {
        this(id, voter_id, agree, new Programme());
    }

    public Candidate() {
    }

    public int getId() {
        return id;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public Programme getProgramme() {
        return programme;
    }

    public int getVoter_id() {
        return voter_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidate)) return false;
        Candidate candidate = (Candidate) o;
        return getId() == candidate.getId() &&
                isAgree() == candidate.isAgree() &&
                getVoter_id() == candidate.getVoter_id() &&
                Objects.equals(getProgramme(), candidate.getProgramme());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isAgree(), getVoter_id(), getProgramme());
    }

    public String getNotice() {
        if (!this.isAgree()) return "Вашу кандидату выставили на пост мэра. Необходимо дать своё согласие.";
        return "";
    }
}
