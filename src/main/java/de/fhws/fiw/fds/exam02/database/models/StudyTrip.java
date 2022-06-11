package de.fhws.fiw.fds.exam02.database.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonDateTimeConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.time.LocalDate;

public class StudyTrip extends AbstractModel implements Serializable, Cloneable {
    long id;
    String name;
    LocalDate firstDate;
    LocalDate lastDate;
    String partnerUni;
    String city;
    String country;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "/studyTrips/${instance.id}",
            rel = "self",
            title = "self",
            type = "application/json" )
    private Link selfLink;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "studyTrips/${instance.id}/students",
            rel = "getStudentsOfStudyTrip",
            title = "student",
            type = "application/json"
    )
    private Link students;
   // private Set<Long> studentIds;
    @JsonConverter( JsonServerLinkConverter.class )
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonConverter( JsonServerLinkConverter.class )
    public Link getStudents() {
        return students;
    }

    public void setStudents(Link students) {
        this.students = students;
    }


    //@Override
    public long getId() {
        return id;
    }

  //  @Override
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
                ", selfLink=" + selfLink +
                ", students=" + students +
                '}';
    }

    public StudyTrip()
    {
    }

    public StudyTrip(String name, LocalDate firstDate, LocalDate lastDate, String partnerUni, String city, String country) {
        this.name = name;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.partnerUni = partnerUni;
        this.city = city;
        this.country = country;
    }

    public StudyTrip(long id, String name, LocalDate firstDate, LocalDate lastDate, String partnerUni, String city, String country) {
        this.id = id;
        this.name = name;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.partnerUni = partnerUni;
        this.city = city;
        this.country = country;
    }
}
