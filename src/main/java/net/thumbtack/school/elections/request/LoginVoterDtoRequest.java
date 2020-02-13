package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class LoginVoterDtoRequest implements Serializable {

    private static final long serialVersionUID = 4L;

    private String login;
    private String password;

    public LoginVoterDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public LoginVoterDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkLogin(login, ElectionJsonParsingErrorCode.WRONG_LOGIN);
        ElectionJsonParsingException.checkPassword(password, ElectionJsonParsingErrorCode.WRONG_PASSWORD);
        return this;
    }
}
