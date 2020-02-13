package net.thumbtack.school.elections.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;

public class ElectionsUtils {
    private static SqlSessionFactory sqlSessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(ElectionsUtils.class);

    public static boolean initSqlSessionFactory() {
        try (Reader reader = Resources.getResourceAsReader("elections-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error loading elections-config.xml", e);
            return false;
        }
    }

    public static boolean closeSqlSessionFactory() {
        if (sqlSessionFactory != null) {
            sqlSessionFactory = null;
            return true;
        }
        return false;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static SqlSession getSession() {
        return ElectionsUtils.getSqlSessionFactory().openSession();
    }
}
