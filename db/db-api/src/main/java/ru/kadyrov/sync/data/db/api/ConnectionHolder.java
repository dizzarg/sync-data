package ru.kadyrov.sync.data.db.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionHolder {

    Connection open(boolean autoCommit) throws SQLException;
}
