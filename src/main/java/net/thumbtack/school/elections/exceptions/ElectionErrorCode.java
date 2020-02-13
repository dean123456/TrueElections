package net.thumbtack.school.elections.exceptions;

public enum ElectionErrorCode {
    DUPLICATE_LOGIN("Избиратель с таким логином уже существует"),
    VOTER_NOT_FOUND("Избиратель не найден"),
    WRONG_LOGIN_OR_PASSWORD("Неверный логин или пароль"),
    EMPTY_VOTER_LIST("Нет ни одного зарегистрированного избирателя"),
    DUPLICATE_CANDIDATE("Кандидат уже назначен"),
    CANDIDATE_NOT_FOUND("Нет такого кандидата"),
    EMPTY_CANDIDATE_LIST("Нет ни одного кандидата"),
    EMPTY_PROPOSAL_LIST("Нет ни одного предложения"),
    DUPLICATE_PROPOSAL("Такое предложение уже есть"),
    DUPLICATE_PROPOSAL_IN_PROGRAMME("Такое предложение уже есть в программе"),
    CANT_EDIT_OWN_RATING("Нельзя изменить оценку своего предложения"),
    CANT_REMOVE_OWN_RATING("Нельзя удалить оценку своего предложения"),
    CANT_REMOVE_OWN_PROPOSAL("Нельзя удалить своё предложение"),
    NO_RATING("Вы не оценивали это предложение"),
    DISAGREE("Сначала дайте согласие на выдвижение"),
    ELECTIONS_STARTED_OR_FINISHED("Слишком поздно. Выборы уже начались или закончились"),
    ELECTIONS_NOT_STARTED_OR_FINISHED("Выборы ещё не начались или уже закончились"),
    ELECTIONS_NOT_FINISHED("Выборы ещё не закончились или не начинались вовсе"),
    ALREADY_VOTED("Вы уже проголосовали"),
    CANT_VOTE_TO_YOURSELF("Вы не можете голосовать за себя"),
    NOT_LOGIN("Сначала войдите под своим логином и поролем"),
    WRONG_POSITION("Выбрана не существующая позиция");

    String errorString;

    ElectionErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }

}
