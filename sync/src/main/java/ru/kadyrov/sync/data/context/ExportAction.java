package ru.kadyrov.sync.data.context;

import ru.kadyrov.sync.data.db.api.DepartmentService;
import ru.kadyrov.sync.data.domain.NaturalKey;
import ru.kadyrov.sync.data.files.FileUtils;
import ru.kadyrov.sync.data.transform.DepartmentTransformer;

import java.nio.ByteBuffer;
import java.util.Map;

public class ExportAction extends ActionHandler {

    private final DepartmentService service;
    private final DepartmentTransformer transformer;

    public ExportAction(ApplicationContext context) {
        service = context.service();
        transformer = context.transformer();
    }

    @Override
    public void action(String fileName) {
        try {
            Map<NaturalKey, String> department = service.loadAll();
            ByteBuffer byteBuffer = transformer.toFile(department);
            FileUtils.writeToFile(byteBuffer.array(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
