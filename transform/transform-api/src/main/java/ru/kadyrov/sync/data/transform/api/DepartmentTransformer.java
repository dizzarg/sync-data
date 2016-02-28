package ru.kadyrov.sync.data.transform.api;

import ru.kadyrov.sync.data.domain.DuplicateException;
import ru.kadyrov.sync.data.domain.NaturalKey;
import ru.kadyrov.sync.data.transform.api.exception.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

public interface DepartmentTransformer {
    Map<NaturalKey, String> fromFile(File var1) throws ParseException, DuplicateException;

    ByteBuffer toFile(Map<NaturalKey, String> department) throws IOException;
}
