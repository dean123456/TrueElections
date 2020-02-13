package net.thumbtack.school.ttschool;

public enum TrainingErrorCode {

    TRAINEE_WRONG_FIRSTNAME("Не верно указано имя"),
    TRAINEE_WRONG_LASTNAME("Не верно указана фамилия"),
    TRAINEE_WRONG_RATING("Не верно указана оценка"),
    GROUP_WRONG_NAME("Недопустимое название группы"),
    GROUP_WRONG_ROOM("Недопустимое название аудитории"),
    TRAINEE_NOT_FOUND("Нет такого студента"),
    SCHOOL_WRONG_NAME("Недопустимое название школы"),
    DUPLICATE_GROUP_NAME("Группа с таким именем уже существует"),
    GROUP_NOT_FOUND("Нет такой группы"),
    DUPLICATE_TRAINEE("Такой студент уже есть"),
    EMPTY_TRAINEE_QUEUE("В очереди никого нет");


    private String errorString;

    TrainingErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }

}
