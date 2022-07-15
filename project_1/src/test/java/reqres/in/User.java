package reqres.in;

import java.util.Objects;

public class User {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(job, user.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, job);
    }

    public String getName() {
        return name;
    }

    public User withName(String name) {
        this.name = name;
        return this;
    }

    public String getJob() {
        return job;
    }

    public User withJob(String job) {
        this.job = job;
        return this;
    }

    private String name;
    private String job;



}
