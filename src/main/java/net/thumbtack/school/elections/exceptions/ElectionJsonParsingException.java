package net.thumbtack.school.elections.exceptions;

public class ElectionJsonParsingException extends Exception {

    private String error;
    private static final int MAX_RATING = 5;
    private static final int MIN_RATING = 1;

    public ElectionJsonParsingException(ElectionJsonParsingErrorCode electionJsonParsingErrorCode) {
        super(electionJsonParsingErrorCode.getErrorString());
        error = electionJsonParsingErrorCode.getErrorString();
    }

    public String getError() {
        return error;
    }

    public static void checkName(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() == 0 || !s.matches("^[А-Я][а-я]+"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkLastName(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() == 0 || !s.matches("^[А-Я][а-я-]+"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkPatronymic(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s.length() == 0 || !s.matches("^[А-Я][а-я]+"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkStreet(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() == 0 || !s.matches("^[\\\\p{Punct}]?([А-Яа-я0-9]+[- ]?)+"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkHouseNumber(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() == 0 || !s.matches("^[\\d]+[A-Za-zА-Яа-я]*")) {
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
        }
    }

    public static void checkLogin(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() < 5 || s.length() > 20 || !s.matches("[\\w]+"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkPassword(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() < 5 || s.length() > 20 || !s.matches("[\\w!@#$%^&*()\";:?~.,]*[A-Z]+[\\w!@#$%^&*()\";:?~.,]*"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkToken(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() != 36 || !s.matches("[\\w]{8}[\\-][\\w]{4}[\\-][\\w]{4}[\\-][\\w]{4}[\\-][\\w]{12}"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkProposal(String s, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (s == null || s.length() == 0 || !s.matches("([А-Яа-я0-9]*[- !.:;?,]*)+"))
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
    }

    public static void checkRating(int rating, ElectionJsonParsingErrorCode electionJsonParsingErrorCode) throws ElectionJsonParsingException {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new ElectionJsonParsingException(electionJsonParsingErrorCode);
        }
    }

}
