package ru.kadyrov.sync.data.domain;

public class NaturalKey {

    private final String code;
    private final String job;

    public NaturalKey(String code, String job) {
        this.code = code;
        this.job = job;
    }

    public String getCode() {
        return code;
    }

    public String getJob() {
        return job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NaturalKey that = (NaturalKey) o;

        if (!code.equals(that.code)) return false;
        return job.equals(that.job);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + job.hashCode();
        return result;
    }
}
