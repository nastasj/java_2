package reqres.in;

public class User {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    private String name;
    private String job;

    public int getId(int id) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return Objects.equals(name, user.name) && Objects.equals(job, user.job);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, job);
//    }

}
