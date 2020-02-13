package net.thumbtack.school.ttschool;

import java.util.*;

public class School {

    private String name;
    private int year;
    private Set<Group> groups;

    public School(String name, int year) throws TrainingException {
        setName(name);
        this.year = year;
        groups = new TreeSet<>((g1, g2) -> g1.getName().compareTo(g2.getName()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TrainingException {
        TrainingException.checkString(name, TrainingErrorCode.SCHOOL_WRONG_NAME);
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) throws TrainingException {
        if (containsGroup(group)) throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
        groups.add(group);
    }

    public void removeGroup(Group group) throws TrainingException {
        if (!containsGroup(group)) throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        groups.remove(group);
    }

    public void removeGroup(String name) throws TrainingException {
        boolean isRemove = false;
        for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext(); ) {
            Group g = iterator.next();
            if (g.getName().compareTo(name) == 0) {
                iterator.remove();
                isRemove = true;
            }
        }
        if (!isRemove) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public boolean containsGroup(Group group) {
        return groups.contains(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return year == school.year &&
                Objects.equals(name, school.name) &&
                Objects.equals(groups, school.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, groups);
    }
}
