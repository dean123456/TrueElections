package net.thumbtack.school.ttschool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Group {

    private String name;
    private String room;
    private List<Trainee> trainees;

    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        trainees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TrainingException {
        TrainingException.checkString(name, TrainingErrorCode.GROUP_WRONG_NAME);
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) throws TrainingException {
        TrainingException.checkString(room, TrainingErrorCode.GROUP_WRONG_ROOM);
        this.room = room;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void addTrainee(Trainee trainee) {
        if (trainee != null) trainees.add(trainee);
    }

    public void removeTrainee(Trainee trainee) throws TrainingException {
        if (!trainees.contains(trainee))
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        trainees.remove(trainee);
    }

    public void removeTrainee(int index) throws TrainingException {
        try {
            trainees.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        for (Trainee trainee : trainees) {
            if (trainee.getFirstName().equals(firstName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
        for (Trainee trainee : trainees) {
            if (trainee.getFullName().equals(fullName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant() {
        Collections.sort(trainees, (t1, t2) -> t1.getFirstName().compareTo(t2.getFirstName()));
    }

    public void sortTraineeListByRatingDescendant() {
        Collections.sort(trainees, (t1, t2) -> t2.getRating() - t1.getRating());
    }

    public void reverseTraineeList() {
        Collections.reverse(trainees);
    }

    public void rotateTraineeList(int positions) {
        Collections.rotate(trainees, positions);
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        List<Trainee> result = new ArrayList<>();
        if (!trainees.isEmpty()) {
            sortTraineeListByRatingDescendant();
            for (Trainee trainee : trainees) {
                if (trainee.getRating() == trainees.get(0).getRating()) {
                    result.add(trainee);
                }
            }
        }
        if (result.isEmpty()) throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        return result;
    }

    public boolean hasDuplicates() {
        for (Trainee trainee : trainees) {
            if (Collections.frequency(trainees, trainee) > 1) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) &&
                Objects.equals(room, group.room) &&
                Objects.equals(trainees, group.trainees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, trainees);
    }
}
