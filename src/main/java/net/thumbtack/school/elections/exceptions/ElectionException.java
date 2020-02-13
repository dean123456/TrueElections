package net.thumbtack.school.elections.exceptions;

public class ElectionException extends Exception {

    private String error;

    public ElectionException(ElectionErrorCode electionErrorCode) {
        super(electionErrorCode.getErrorString());
        error = electionErrorCode.getErrorString();
    }

    public String getError() {
        return error;
    }

}
