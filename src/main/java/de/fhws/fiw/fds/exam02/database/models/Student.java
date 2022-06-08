package de.fhws.fiw.fds.exam02.database.models;

public class Student extends AbstractModel {

    long id;
    private String firstname;
    private String lastname;
    private String course;
    private int semester;

    long immatricNum; // muss unique sein
    String email;

    public Student() {
    }

    public Student(long id, String firstname, String lastname, String course, int semester, long immatricNum, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.course = course;
        this.semester = semester;
        this.immatricNum = immatricNum;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }


    public long getImmatricNum() {
        return immatricNum;
    }

    public void setImmatricNum(long immatricNum) {
        this.immatricNum = immatricNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}



