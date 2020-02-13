package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.database.DataBase;

public interface CommonDao {

    void clear();

    void insertAll(DataBase database);
}
