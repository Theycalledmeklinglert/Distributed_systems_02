package de.fhws.fiw.fds.sutton.client;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.sutton.models.Student;
import de.fhws.fiw.fds.sutton.models.StudyTrip;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class WebApiClient {
    private final Genson genson;
    private final GenericWebClient client;
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse( "application/json" );
    private static final String URL = "http://localhost:8080/exam02/api";

    private static WebApiResponse lastResponse;

    public WebApiClient()
    {
        this.client = new GenericWebClient<StudyTrip>("","");
        this.genson = new Genson();
    }

    public WebApiResponse callDispatcher(  ) throws IOException
    {
        lastResponse = client.sendDispatcherRequest(URL);
        return lastResponse;
    }


    public WebApiResponse getStudyTripsByAttributes(final String name, final String firstDate, final String lastDate, final String city, final String country, final int pageNumber ) throws IOException
    {
        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"getAllStudyTrips\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/studyTrips" + "?name=" + name + "&firstDate="+ firstDate + "&lastDate=" + lastDate + "&city=" + city + "&country=" + country + "&page=" + pageNumber;
        lastResponse = client.sendGetCollectionRequest(theUrl, new GenericType<List<StudyTrip>>() {
        });
        // TODO: MUSS UEBERALL AM ENDE STEHEN

        return lastResponse;
    }

    public WebApiResponse getSingleStudyTripByID(final long id ) throws IOException
    {
        final String theUrl = URL + "/studyTrips" + "/" + id;
        lastResponse = client.sendGetSingleRequest(theUrl, StudyTrip.class);
        // TODO: MUSS UEBERALL AM ENDE STEHEN

        return lastResponse;
    }



    public WebApiResponse postStudyTrip(StudyTrip studyTrip) throws IOException
        {
            if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"createStudyTrip\"")) // TODO: Check if this works
            {
                System.out.println("Failed");
                return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
            }

            String theUrl = URL + "/studyTrips";
            lastResponse = client.sendPostRequest(theUrl, studyTrip);

            // TODO: MUSS UEBERALL AM ENDE STEHEN
            return lastResponse;
        }

    public WebApiResponse updateStudyTrip(final long id, StudyTrip studyTrip) throws IOException {

        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"updateStudyTrip\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/studyTrips" + "/" + id;
        lastResponse = client.sendPutRequest(theUrl, studyTrip);
        return lastResponse;
    }


    public WebApiResponse deleteStudyTripById(final long id) throws IOException {

            if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"deleteStudyTrip\"")) // TODO: Check if this works
            {
                return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
            }

            final String theUrl = URL + "/studyTrips" + "/" + id;
            lastResponse = client.sendDeleteRequest(theUrl);

            return lastResponse;

    }

    public WebApiResponse getStudentsOfStudyTrip(final StudyTrip studyTrip, final int pageNumber ) throws IOException
    {
        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"getAllStudentsOfStudyTrip\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = studyTrip.getStudents().toString() + "?page=" + pageNumber;
        lastResponse = client.sendGetCollectionRequest(theUrl, new GenericType<List<Student>>() {
        });
        // TODO: MUSS UEBERALL AM ENDE STEHEN

        return lastResponse;
    }


    public WebApiResponse getSingleStudentOfStudyTripByID(final long studyTripID, final long studentID ) throws IOException
    {

        final String theUrl = URL + "/studyTrips" + "/" + studyTripID + "/" + studentID;
        lastResponse = client.sendGetSingleRequest(theUrl, Student.class);
        // TODO: MUSS UEBERALL AM ENDE STEHEN

        return lastResponse;
    }

    public WebApiResponse postStudentToStudyTripByID(final long studyTripID, final long studentID) throws IOException {
        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"createStudentOfStudyTrip\""))
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        ArrayList<Student> list = (ArrayList<Student>) getSingleStudentByID(studentID).getResponseData().stream().collect(Collectors.toList());
        Student student = list.get(0);
        final String theUrl = URL + "/studyTrips" + "/" + studyTripID;
        lastResponse = client.sendPostRequest(theUrl, student);

        return lastResponse;
    }


    public WebApiResponse postStudentToStudyTrip(final long studyTripID, final Student student) throws IOException {
        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"createStudentOfStudyTrip\""))
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/studyTrips" + "/" + studyTripID;
        lastResponse = client.sendPostRequest(theUrl, student);

        return lastResponse;

    }

    public WebApiResponse updateStudentOfStudyTrip(final int studyTripID, final Student student) throws IOException {
        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"updateStudentsOfStudyTrip\""))
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/studyTrips" + "/" + studyTripID;
        lastResponse = client.sendPutRequest(theUrl, student);

        return lastResponse;

    }

    public WebApiResponse deleteStudentOfStudyTripById(final long studyTripID, final long studentID) throws IOException {

        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"unlinkStudyTripToStudent\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/studyTrips" + "/" + studyTripID + "/" + studentID;
        lastResponse = client.sendDeleteRequest(theUrl);

        return lastResponse;

    }



    public WebApiResponse getAllStudents(final int pageNumber ) throws IOException
    {
        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"getAllStudents\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/students" + "?page=" + pageNumber;
        lastResponse = client.sendGetCollectionRequest(theUrl, new GenericType<List<Student>>() {
        });
        // TODO: MUSS UEBERALL AM ENDE STEHEN

        return lastResponse;
    }

    public WebApiResponse getSingleStudentByID(final long id ) throws IOException
    {
        final String theUrl = URL + "/students" + "/" + id;
        lastResponse = client.sendGetSingleRequest(theUrl, Student.class);
        // TODO: MUSS UEBERALL AM ENDE STEHEN

        return lastResponse;
    }

    public WebApiResponse postStudent(final Student student) throws IOException
    {
        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"createStudent\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        String theUrl = URL + "/students";
        lastResponse = client.sendPostRequest(theUrl, student);

        // TODO: MUSS UEBERALL AM ENDE STEHEN
        return lastResponse;
    }

    public WebApiResponse updateStudent(final long id, Student student) throws IOException {

        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"updateStudent\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/students" + "/" + id;
        lastResponse = client.sendPutRequest(theUrl, student);
        return lastResponse;
    }


    public WebApiResponse deleteStudentByID(final long id) throws IOException {

        if(!lastResponse.getHeaders().values("Link").toString().contains("rel=\"deleteStudent\"")) // TODO: Check if this works
        {
            return new WebApiResponse(403); // TODO: Probably wrong status code but I don't know which one would fit better
        }

        final String theUrl = URL + "/students" + "/" + id;
        lastResponse = client.sendDeleteRequest(theUrl);

        return lastResponse;

    }



}




