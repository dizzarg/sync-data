package ru.kadyrov.sync.data.db.api;

public interface DatabaseContext {
   ConnectionHolder connectionHolder();
   DepartmentRepository repository();
}
