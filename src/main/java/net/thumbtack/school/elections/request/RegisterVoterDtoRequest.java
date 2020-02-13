package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class RegisterVoterDtoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName, lastName, patronymic, street, login, password, house;

    private int apartment;

    public RegisterVoterDtoRequest(String firstName, String lastName,
                                   String street, String house,
                                   String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.house = house;
        this.login = login;
        this.password = password;
    }

    public RegisterVoterDtoRequest(String firstName, String lastName, String patronymic,
                                   String street, String house,
                                   String login, String password) {
        this(firstName, lastName, street, house, login, password);
        this.patronymic = patronymic;
    }

    public RegisterVoterDtoRequest(String firstName, String lastName,
                                   String street, String house, int apartment,
                                   String login, String password) {
        this(firstName, lastName, street, house, login, password);
        this.apartment = apartment;
    }

    public RegisterVoterDtoRequest(String firstName, String lastName, String patronymic,
                                   String street, String house, int apartment,
                                   String login, String password) {
        this(firstName, lastName, patronymic, street, house, login, password);
        this.apartment = apartment;
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

    public String getHouse() {
        return house;
    }

    public int getApartment() {
        return apartment;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public RegisterVoterDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkName(firstName, ElectionJsonParsingErrorCode.WRONG_FIRSTNAME);
        ElectionJsonParsingException.checkLastName(lastName, ElectionJsonParsingErrorCode.WRONG_LASTNAME);
        if (patronymic != null)
            ElectionJsonParsingException.checkPatronymic(patronymic, ElectionJsonParsingErrorCode.WRONG_PATRONYMIC);
        ElectionJsonParsingException.checkStreet(street, ElectionJsonParsingErrorCode.WRONG_STREET);
        ElectionJsonParsingException.checkHouseNumber(house, ElectionJsonParsingErrorCode.WRONG_HOUSE);
        if (apartment < 0)
            throw new ElectionJsonParsingException(ElectionJsonParsingErrorCode.WRONG_APARTMENTS);
        ElectionJsonParsingException.checkLogin(login, ElectionJsonParsingErrorCode.WRONG_LOGIN);
        ElectionJsonParsingException.checkPassword(password, ElectionJsonParsingErrorCode.WRONG_PASSWORD);
        return this;

    }

}
