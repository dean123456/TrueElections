package net.thumbtack.school.ttschool;

public class TrainingException extends Exception {

    TrainingErrorCode trainingErrorCode;

    TrainingException(TrainingErrorCode trainingErrorCode) {
        super(trainingErrorCode.getErrorString());
        this.trainingErrorCode = trainingErrorCode;
    }

    public TrainingErrorCode getErrorCode() {
        return trainingErrorCode;
    }

    public static void checkString(String s, TrainingErrorCode trainingErrorCode) throws TrainingException {
        if (s == null || s.length() == 0)
            throw new TrainingException(trainingErrorCode);
    }
}
