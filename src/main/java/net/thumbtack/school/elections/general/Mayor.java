package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.Objects;

public class Mayor implements Serializable {

    private static final long serialVersionUID = 56L;

    private String fullName;

    public Mayor(Voter voter) {
        fullName = voter.getFullName();
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mayor)) return false;
        Mayor mayor = (Mayor) o;
        return Objects.equals(getFullName(), mayor.getFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName());
    }
}
