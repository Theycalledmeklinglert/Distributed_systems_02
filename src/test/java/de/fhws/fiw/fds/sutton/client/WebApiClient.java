package de.fhws.fiw.fds.sutton.client;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.sutton.models.Student;
import de.fhws.fiw.fds.sutton.models.StudyTrip;
import okhttp3.*;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.*;

import static de.fhws.fiw.fds.sutton.client.LinkToMapParser.parseLinks;


public class WebApiClient {
    private final Genson genson;
    private final OkHttpClient client;
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse( "application/json" );
    private static final String URL = "http://localhost:8080/exam02/api";

    public static Map<String, String> lastResponseHyperLinks = new HashMap<>();

    public WebApiClient()
    {
        this.client = new OkHttpClient();
        this.genson = new Genson();
    }

    public WebApiClient( final String userName, final String password )
    {
        this.client = new OkHttpClient.Builder( )
                .addInterceptor( new BasicAuthInterceptor( userName, password ) )
                .build( );
        this.genson = new Genson( );
    }


    public Response callDispatcher(  ) throws IOException
    {
        final Request request = new Request.Builder( )
                .url( URL )
                .get( )
                .build( );

        final Response response = this.client.newCall( request ).execute( );

        lastResponseHyperLinks = parseLinks(response.headers("Link"));
        return response;
    }

    public Map<Response, ArrayList<StudyTrip>> getStudyTripsByAttributes(final String name, final String firstDate, final String lastDate, final String city, final String country, final int pageNumber ) throws IOException
    {
        final String theUrl = URL + "/studyTrips" + "?name=" + name + "&firstDate="+ firstDate + "&lastDate=" + lastDate + "&city=" + city + "&country=" + country + "&page=" + pageNumber;

        if(lastResponseHyperLinks.get("getAllStudyTrips") == null && !lastResponseHyperLinks.values().contains(theUrl))
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        Response response = sendGetRequest(theUrl);

        if(response.code() != 200)
        {
            throw new WebApplicationException(response.code());
        }
        ArrayList<StudyTrip> trips = genson.deserialize(response.body().string(), new GenericType<ArrayList<StudyTrip>>() {});
        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, ArrayList<StudyTrip>> res = new HashMap<>();
        res.put(response, trips);
        return res;
    }

    public Map<Response, StudyTrip> getSingleStudyTripByID(final long id ) throws IOException
    {
        final String theUrl = URL + "/studyTrips" + "/" + id;

        Response response = sendGetRequest(theUrl);

        if(response.code() != 200)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, StudyTrip> res = new HashMap<>();
        res.put(response, genson.deserialize(response.body().string(), StudyTrip.class));
        return res;
    }

    public Map<Response, StudyTrip> postStudyTrip(StudyTrip studyTrip) throws IOException
        {
            if(lastResponseHyperLinks.get("createStudyTrip") == null)
            {
                throw new WebApplicationException("Missing corresponding Hyperlink of last request");
            }

            String theUrl = URL + "/studyTrips";
            Response response = sendPostRequest(theUrl, genson.serialize(studyTrip));

            if(response.code() != 201)
            {
                throw new WebApplicationException(response.code());
            }

            lastResponseHyperLinks = parseLinks(response.headers("Link"));

            HashMap<Response, StudyTrip> res = new HashMap<>();
            res.put(response, null);
            return res;
        }


    public Map<Response, StudyTrip> updateStudyTrip(final long id, StudyTrip studyTrip) throws IOException {

        if(lastResponseHyperLinks.get("updateStudyTrip") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        final String theUrl = URL + "/studyTrips" + "/" + id;
        Response response = sendPutRequest(theUrl, genson.serialize(studyTrip));

        if(response.code() != 204)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, StudyTrip> res = new HashMap<>();
        res.put(response, null);
        return res;
    }


    public Map<Response, StudyTrip> deleteStudyTripById(final long id) throws IOException {

        if(lastResponseHyperLinks.get("deleteStudyTrip") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }
        final String theUrl = URL + "/studyTrips" + "/" + id;
        Response response = sendDeleteRequest(theUrl);

        if(response.code() != 204)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, StudyTrip> res = new HashMap<>();
        res.put(response, null);
        return res;
    }


    public Map<Response, ArrayList<Student>> getStudentsOfStudyTrip(final StudyTrip studyTrip, final int pageNumber ) throws IOException
    {
        final String theUrl = studyTrip.getStudents().toString() + "?page=" + pageNumber;

        if(lastResponseHyperLinks.get("getAllStudentsOfStudyTrip") == null && lastResponseHyperLinks.get("self") != theUrl && !lastResponseHyperLinks.values().contains(theUrl))
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        Response response = sendGetRequest(theUrl);

        if(response.code() != 200)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, ArrayList<Student>> res = new HashMap<>();
        res.put(response, genson.deserialize(response.body().string(), new GenericType<ArrayList<Student>>() {}));
        return res;
    }


    public Map<Response, Student> getSingleStudentOfStudyTripByID(final long studyTripID, final long studentID ) throws IOException
    {
        final String theUrl = URL + "/studyTrips" + "/" + studyTripID + "/students" + "/" + studentID;

        Response response = sendGetRequest(theUrl);

        if(response.code() != 200)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, genson.deserialize(response.body().string(), Student.class));
        return res;
    }

    public Map<Response, Student> postStudentToStudyTrip(final long studyTripID, final Student student) throws IOException {
        if(lastResponseHyperLinks.get("createStudentOfStudyTrip") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        Map<Response, Student> map = (HashMap<Response, Student>) getSingleStudentByID(student.getId());
        Optional<Student> studentToLink = map.values().stream().findFirst();

        if(!studentToLink.isPresent())
        {
            map = postStudent(student);
            studentToLink = Optional.of(map.values().stream().findFirst()).get();
        }

        final String theUrl = URL + "/studyTrips" + "/" + studyTripID + "/students";

        Response response = sendPostRequest(theUrl, genson.serialize(studentToLink));

        if(response.code() != 201)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, null);
        return res;
    }

    public Map<Response, Student> updateStudentOfStudyTrip(final int studyTripID, final Student student) throws IOException {
        if(lastResponseHyperLinks.get("updateStudentsOfStudyTrip") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        final String theUrl = URL + "/studyTrips" + "/" + studyTripID + "/students" + "/" + student.getId();
        Response response = sendPutRequest(theUrl,genson.serialize(student));

        if(response.code() != 204)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, null);
        return res;

    }

    public Map<Response, Student> deleteStudentOfStudyTripById(final long studyTripID, final long studentID) throws IOException {

        if(lastResponseHyperLinks.get("unlinkStudyTripToStudent") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        final String theUrl = URL + "/studyTrips" + "/" + studyTripID + "/students/" + studentID;
        Response response = sendDeleteRequest(theUrl);

        if(response.code() != 204)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, null);
        return res;
        }


    public Map<Response, ArrayList<Student>> getAllStudents(final int pageNumber ) throws IOException
    {
        final String theUrl = URL + "/students" + "?page=" + pageNumber;

        if(lastResponseHyperLinks.get("getAllStudents") == null && lastResponseHyperLinks.get("self") != theUrl && !lastResponseHyperLinks.values().contains(theUrl))
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        Response response = sendGetRequest(theUrl);

        if(response.code() != 200)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, ArrayList<Student>> res = new HashMap<>();
        res.put(response, genson.deserialize(response.body().string(), new GenericType<ArrayList<Student>>() {}));
        return res;
    }

    public Map<Response, Student> getSingleStudentByID(final long id ) throws IOException
    {
        final String theUrl = URL + "/students" + "/" + id;

        Response response = sendGetRequest(theUrl);

        if(response.code() != 200)
        {
            throw new WebApplicationException(response.code());
        }

        Student student = genson.deserialize(response.body().string(), Student.class);
        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, genson.deserialize(response.body().string(), Student.class));
        return res;
    }


    public Map<Response, Student> postStudent(final Student student) throws IOException
    {
        if(lastResponseHyperLinks.get("createStudent") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        String theUrl = URL + "/students";
        Response response = sendPostRequest(theUrl, genson.serialize(student));
        System.out.println(genson.serialize(student));

        if(response.code() != 201)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, null);
        return res;
        }

    public Map<Response, Student> updateStudent(final long id, Student student) throws IOException {

        if(lastResponseHyperLinks.get("updateStudent") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        final String theUrl = URL + "/students" + "/" + id;

        Response response = sendPutRequest(theUrl, genson.serialize(student));

        if(response.code() != 204)
        {
            throw new WebApplicationException(response.code());
        }

        lastResponseHyperLinks = parseLinks(response.headers("Link"));


        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, null);
        return res;
    }


    public Map<Response, Student> deleteStudentByID(final long id) throws IOException {

        if(lastResponseHyperLinks.get("deleteStudent") == null)
        {
            throw new WebApplicationException("Missing corresponding Hyperlink of last request");
        }

        final String theUrl = URL + "/students" + "/" + id;
        Response response = sendDeleteRequest(theUrl);

        if(response.code() != 204)
        {
            throw new WebApplicationException(response.code());
        }
        lastResponseHyperLinks = parseLinks(response.headers("Link"));

        HashMap<Response, Student> res = new HashMap<>();
        res.put(response, null);
        return res;
    }



    private Response sendGetRequest(final String url) throws IOException
    {
        final Request request = new Request.Builder( )
                .url( url )
                .get( )
                .build( );

        final Response response = this.client.newCall( request ).execute( );

        if (response.code() == 200) return response;
        else throw new WebApplicationException();
    }

    private Response sendPostRequest(final String url, String json )
            throws IOException
    {
        final RequestBody body = RequestBody.create( MEDIA_TYPE_JSON, json);

        final Request request = new Request.Builder( )
                .url( url )
                .post( body )
                .build( );

       return this.client.newCall( request ).execute( );
    }

    public Response sendPutRequest(final String url, String json ) throws IOException
    {
        final RequestBody body = RequestBody.create( MEDIA_TYPE_JSON, json );

        final Request request = new Request.Builder( )
                .url( url )
                .put( body )
                .build( );

        return this.client.newCall( request ).execute( );
    }

    public Response sendDeleteRequest(final String url ) throws IOException
    {
        final Request request = new Request.Builder( )
                .url( url )
                .delete( )
                .build( );

        return this.client.newCall( request ).execute( );
    }





}




