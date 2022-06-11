package de.fhws.fiw.fds.exam02.database.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;

public class Student extends AbstractModel {

    long id;
    private String firstname;
    private String lastname;
    private String course;
    private int semester;

    long immatricNum;
    String email;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "/students/${instance.id}",
            rel = "self",
            title = "self",
            type = "application/json",
            condition = "${instance.primaryId == 0}"    // ???
    )
    private Link selfLinkPrimary;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "studyTrips/${instance.primaryId}/students/${instance.id}",
            rel = "self",
            title = "self",
            type = "application/json",
            condition = "${instance.primaryId != 0}"
    )
    private Link selfLinkSecondary;

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLinkPrimary() {
        return selfLinkPrimary;
    }

    public void setSelfLinkPrimary(Link selfLinkPrimary) {
        this.selfLinkPrimary = selfLinkPrimary;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLinkSecondary() {
        return selfLinkSecondary;
    }

    public void setSelfLinkSecondary(Link selfLinkSecondary) {
        this.selfLinkSecondary = selfLinkSecondary;
    }

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



