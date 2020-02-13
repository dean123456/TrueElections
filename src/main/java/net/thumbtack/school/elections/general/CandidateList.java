package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.Objects;

public class CandidateList implements Serializable {

    private static final long serialVersionUID = 50L;

    private int id, agree;
    private String firstName, lastName, patronymic;
    private Programme programme;

    private CandidateList(int id, String firstName, String lastName, String patronymic, int agree) {
        this(id, firstName, lastName, patronymic, agree, new Programme());
    }

    private CandidateList(int id, String firstName, String lastName, String patronymic, int agree, Programme programme) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.agree = agree;
        this.programme = programme;
    }

    private CandidateList(String firstName, String lastName, String patronymic, int agree) {
        this(0, firstName, lastName, patronymic, agree, new Programme());
    }

    public int getId() {
        return id;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public Programme getProgramme() {
        return programme;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidateList)) return false;
        CandidateList that = (CandidateList) o;
        return getId() == that.getId() &&
                getAgree() == that.getAgree() &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getProgramme(), that.getProgramme());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAgree(), getFirstName(), getLastName(), getPatronymic(), getProgramme());
    }
}
