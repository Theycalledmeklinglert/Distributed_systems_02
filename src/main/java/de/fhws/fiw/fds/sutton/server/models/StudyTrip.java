package de.fhws.fiw.fds.sutton.server.models;

import com.owlike.genson.annotation.JsonConverter;
import com.owlike.genson.annotation.JsonDateFormat;
import de.fhws.fiw.fds.sutton.client.Link;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonDateTimeConverter;
import org.glassfish.jersey.linking.InjectLink;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StudyTrip extends AbstractModel {
    long id;
    String name;
    LocalDate firstDate;
    LocalDate lastDate;
    String partnerUni;
    String city;
    String country;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonConverter(JsonDateTimeConverter.class)
    public LocalDate getFirstDate() {
        return firstDate;
    }
    @JsonConverter(JsonDateTimeConverter.class)

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }
    @JsonConverter(JsonDateTimeConverter.class)

    public LocalDate getLastDate() {
        return lastDate;
    }
    @JsonConverter(JsonDateTimeConverter.class)
    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public String getPartnerUni() {
        return partnerUni;
    }

    public void setPartnerUni(String partnerUni) {
        this.partnerUni = partnerUni;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Link getStudents() {
        return students;
    }

    public void setStudents(Link students) {
        this.students = students;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }

    @Override
    public String toString() {
        return "StudyTrip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstDate=" + firstDate +
                ", lastDate=" + lastDate +
                ", partnerUni='" + partnerUni + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", students=" + students +
                ", studentIds=" + studentIds +
                '}';
    }

    @InjectLink(
           style = InjectLink.Style.ABSOLUTE,
            value = "studyTrip/{instance.id}/students",
            rel = "getStudentsOfTrip",
            title = "student", // ???
            type = "application/json"
    )

    private Link students;
    private Set<Long> studentIds;

    public StudyTrip()
    {
        this.studentIds = new HashSet<>();
    }

    public StudyTrip(long id, String name, LocalDate firstDate, LocalDate lastDate, String partnerUni, String city, String country, Set<Long> studentIds) {
        this.id = id;
        this.name = name;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.partnerUni = partnerUni;
        this.city = city;
        this.country = country;
        this.studentIds = studentIds;
    }
}
