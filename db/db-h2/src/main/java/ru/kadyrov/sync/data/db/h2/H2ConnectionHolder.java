package ru.kadyrov.sync.data.db.h2;

import ru.kadyrov.sync.data.db.api.ConnectionContext;
import ru.kadyrov.sync.data.db.api.ConnectionHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionHolder implements ConnectionHolder {

    private final ConnectionContext context;

    public H2ConnectionHolder(ConnectionContext context) {
        this.context = context;
    }

    @Override
    public Connection open(boolean autoCommit) throws SQLException {
        Connection connection = DriverManager.getConnection(context.url(), context.user(), context.password());
        connection.setAutoCommit(autoCommit);
        return connection;
    }
}
