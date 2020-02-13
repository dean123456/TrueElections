package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.Objects;

public class Rating implements Serializable {

    private static final long serialVersionUID = 53L;

    private int id;
    private int voter_id;
    private int rating;

    private Rating(int id, int voter_id, int rating) {
        this.id = id;
        this.voter_id = voter_id;
        this.rating = rating;
    }

    private Rating(){}

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getVoter_id() {
        return voter_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating1 = (Rating) o;
        return getId() == rating1.getId() &&
                getVoter_id() == rating1.getVoter_id() &&
                getRating() == rating1.getRating();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVoter_id(), getRating());
    }
}
