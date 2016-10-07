package ru.kadyrov.sync.data.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kadyrov.sync.data.db.api.DepartmentService;
import ru.kadyrov.sync.data.domain.NaturalKey;
import ru.kadyrov.sync.data.transform.api.DepartmentTransformer;

import java.io.File;
import java.util.Map;

public class ImportAction extends ActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ImportAction.class);

    private final DepartmentService service;
    private final DepartmentTransformer transformer;

    public ImportAction(ApplicationContext context) {
        service = context.service();
        transformer = context.transformer();
    }

    @Override
    public void action(String fileName) {
        try {
            Map<NaturalKey, String> department = transformer.fromFile(new File(fileName));
            service.save(department);
        } catch (Exception e) {
            logger.error("Cannot save data: " + e.getMessage());
        }

    }
}
