package net.thumbtack.school.ttschool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TraineeMap {
    Map<Trainee, String> map;

    public TraineeMap() {
        map = new HashMap<>();
    }

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (map.containsKey(trainee)) throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        map.put(trainee, institute);
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        checkTraineeIsNotExist(trainee);
        map.replace(trainee, institute);
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        checkTraineeIsNotExist(trainee);
        map.remove(trainee);
    }

    public int getTraineesCount() {
        return map.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        checkTraineeIsNotExist(trainee);
        return map.get(trainee);
    }

    public Set<Trainee> getAllTrainees() {
        return map.keySet();
    }

    public Set<String> getAllInstitutes() {
        return new TreeSet<>(map.values());
    }

    public boolean isAnyFromInstitute(String institute) {
        return map.containsValue(institute);
    }

    private void checkTraineeIsNotExist(Trainee trainee) throws TrainingException{
        if (!map.containsKey(trainee)) throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }
}
