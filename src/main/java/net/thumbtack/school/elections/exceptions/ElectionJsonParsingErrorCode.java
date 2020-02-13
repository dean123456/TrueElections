package net.thumbtack.school.elections.exceptions;

public enum ElectionJsonParsingErrorCode {

    WRONG_FIRSTNAME("Не верное имя"),
    WRONG_LASTNAME("Не верная фамилия"),
    WRONG_PATRONYMIC("Не верное отчество"),
    WRONG_STREET("Нет такой улицы"),
    WRONG_HOUSE("Не верно указан номер дома"),
    WRONG_APARTMENTS("Не верно указан номер квартиры"),
    WRONG_LOGIN("Такой логин не подойдёт"),
    WRONG_PASSWORD("Такой пароль не подойдёт"),
    WRONG_TOKEN("Не верный token"),
    WRONG_PROPOSAL("Не верно указано предложение"),
    WRONG_RATING("Оценка не может быть меньше 1 и больше 5");

    String errorString;

    ElectionJsonParsingErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
