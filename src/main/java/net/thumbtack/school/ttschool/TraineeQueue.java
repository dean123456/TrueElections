package net.thumbtack.school.ttschool;

import java.util.LinkedList;
import java.util.Queue;

public class TraineeQueue {

    Queue<Trainee> queue;

    public TraineeQueue() {
        queue = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee) {
        if (trainee != null) queue.add(trainee);
    }

    public Trainee removeTrainee() throws TrainingException{
        try {
            return queue.remove();
        } catch (Exception e) {
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        }
    }

}
