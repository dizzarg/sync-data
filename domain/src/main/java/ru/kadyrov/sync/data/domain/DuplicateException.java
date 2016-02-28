package ru.kadyrov.sync.data.domain;

public class DuplicateException extends Exception {

    private static final String format = "Department by DepCode: %s and DepJob: %s exists in %s";

    public DuplicateException(NaturalKey naturalKey, String source) {
        super(String.format(format, naturalKey.getCode(), naturalKey.getJob(), source));
    }
}
