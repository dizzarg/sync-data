package ru.kadyrov.sync.data.db.h2;

import ru.kadyrov.sync.data.db.api.DepartmentRepository;
import ru.kadyrov.sync.data.domain.NaturalKey;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class H2DepartmentRepository implements DepartmentRepository {

    private static final String FIND_ALL = "SELECT DEP_CODE, DEP_JOB, DESCRIPTION FROM DEPARTMENTS;";
    private static final String CREATE = "INSERT INTO DEPARTMENTS(DEP_CODE, DEP_JOB, DESCRIPTION) VALUES (?, ?, ?);";
    private static final String DELETE_ALL = "DELETE FROM DEPARTMENTS";

    @Override
    public Map<NaturalKey, String> loadAll(Connection connection) throws SQLException {
        try (Statement stat = connection.createStatement()) {
            stat.executeQuery(FIND_ALL);
            ResultSet resultSet = stat.executeQuery(FIND_ALL);
            Map<NaturalKey, String> result = new HashMap<>();
            while (resultSet.next()){
                String depCode = resultSet.getString("DEP_CODE");
                String depJob = resultSet.getString("DEP_JOB");
                String description = resultSet.getString("DESCRIPTION");
                NaturalKey naturalKey = new NaturalKey(depCode, depJob);
                result.put(naturalKey, description);
            }
            return result;
        }
    }

    @Override
    public void save(Connection conn, NaturalKey key, String description) throws SQLException {
        try (PreparedStatement stat = conn.prepareStatement(CREATE)) {
            stat.setString(1, key.getCode());
            stat.setString(2, key.getJob());
            stat.setString(3, description);
            stat.executeUpdate();
        }
    }

    @Override
    public void deleteAll(Connection conn) throws SQLException {
        try (Statement stat = conn.createStatement()) {
            stat.execute(DELETE_ALL);
        }
    }

}
