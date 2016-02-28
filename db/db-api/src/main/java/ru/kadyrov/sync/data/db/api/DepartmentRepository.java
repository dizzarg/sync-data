package ru.kadyrov.sync.data.db.api;

import ru.kadyrov.sync.data.domain.NaturalKey;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface DepartmentRepository {

    Map<NaturalKey, String> loadAll(Connection connection) throws SQLException;

    void save(Connection conn, NaturalKey key, String description) throws SQLException;

    void deleteAll(Connection conn) throws SQLException;

}
