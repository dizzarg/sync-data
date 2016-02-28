package ru.kadyrov.sync.data.db.api;

import ru.kadyrov.sync.data.db.api.exception.DAOException;
import ru.kadyrov.sync.data.domain.NaturalKey;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;


public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ConnectionHolder connectionHolder;

    public DepartmentService(DatabaseContext context) {
        this.departmentRepository = context.repository();
        this.connectionHolder = context.connectionHolder();
    }

    public Map<NaturalKey, String> loadAll() throws DAOException {
        try (Connection conn = connectionHolder.open(true)) {
            return departmentRepository.loadAll(conn);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void save(Map<NaturalKey, String> department) throws DAOException {
        try (Connection conn = connectionHolder.open(false)) {
            try {
                departmentRepository.deleteAll(conn);
                for (NaturalKey naturalKey : department.keySet()) {
                    departmentRepository.save(conn, naturalKey, department.get(naturalKey));
                }
                conn.commit();
            } catch (SQLException e){
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

}
