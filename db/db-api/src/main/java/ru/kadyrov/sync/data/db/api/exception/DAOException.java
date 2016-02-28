package ru.kadyrov.sync.data.db.api.exception;

/**
 * Database exception
 */
public class DAOException extends Exception {
    public DAOException(Exception e) {
        super(e);
    }
}
