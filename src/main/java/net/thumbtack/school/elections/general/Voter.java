package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.*;

public class Voter implements Serializable {

    private static final long serialVersionUID = 54L;

    private String firstName, lastName, patronymic, street, login, password, token, house;

    private int id, apartment;

    private List<Proposal> proposalList;

    private Voter(int id, String firstName, String lastName,
                  String patronymic, String street,
                  String house, int apartments, String login,
                  String password, String token, List<Proposal> proposalList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.street = street;
        this.house = house;
        this.apartment = apartments;
        this.login = login;
        this.password = password;
        this.token = token;
        this.proposalList = proposalList;
    }

    public Voter(String firstName, String lastName,
                 String patronymic, String street,
                 String house, int apartments, String login,
                 String password) {
        this(0, firstName, lastName, patronymic, street,
                house, apartments, login, password, UUID.randomUUID().toString(), new ArrayList<>());
    }

    private Voter(int id, String firstName, String lastName,
                  String patronymic, String street,
                  String house, int apartments, String login,
                  String password, String token) {
        this(id, firstName, lastName, patronymic, street,
                house, apartments, login, password, token, new ArrayList<>());
    }

    private Voter(int id, String firstName, String lastName,
                  String patronymic) {
        this(id, firstName, lastName, patronymic, null,
                null, 0, null, null, null, new ArrayList<>());
    }

    public Voter(int id, String firstName, String lastName,
                 String patronymic, List<Proposal> proposalList) {
        this(id, firstName, lastName, patronymic, null,
                null, 0, null, null, null, proposalList);
    }

    public Voter() {
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

    public String getStreet() {
        return street;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getHouse() {
        return house;
    }

    public int getId() {
        return id;
    }

    public int getApartment() {
        return apartment;
    }

    public List<Proposal> getProposalList() {
        return proposalList;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullName() {
        if (patronymic != null) {
            return firstName + " " + patronymic + " " + lastName;
        }
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voter)) return false;
        Voter voter = (Voter) o;
        return getId() == voter.getId() &&
                getApartment() == voter.getApartment() &&
                Objects.equals(getFirstName(), voter.getFirstName()) &&
                Objects.equals(getLastName(), voter.getLastName()) &&
                Objects.equals(getPatronymic(), voter.getPatronymic()) &&
                Objects.equals(getStreet(), voter.getStreet()) &&
                Objects.equals(getLogin(), voter.getLogin()) &&
                Objects.equals(getPassword(), voter.getPassword()) &&
                Objects.equals(getToken(), voter.getToken()) &&
                Objects.equals(getHouse(), voter.getHouse()) &&
                Objects.equals(getProposalList(), voter.getProposalList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getPatronymic(), getStreet(), getLogin(), getPassword(), getToken(), getHouse(), getId(), getApartment(), getProposalList());
    }
}
