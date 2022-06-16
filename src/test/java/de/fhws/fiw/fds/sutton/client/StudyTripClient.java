package de.fhws.fiw.fds.sutton.client;

import com.owlike.genson.GenericType;
import de.fhws.fiw.fds.sutton.models.StudyTrip;

import java.io.IOException;
import java.util.List;

public class StudyTripClient
{
    private static final String BASE_URL = "http://localhost:8080/exam02/api/studyTrips";

    private final GenericWebClient<StudyTrip> client;

    public StudyTripClient(final String userName, final String password )
    {
        this.client = new GenericWebClient<>( userName, password );
    }

    public WebApiResponse create(final StudyTrip studyTrip ) throws IOException
    {
        return this.client.sendPostRequest( BASE_URL, studyTrip );
    }

    public WebApiResponse loadSingleById(final long id ) throws IOException
    {
        final String theUrl = String.format( "%s/%d", BASE_URL, id );
        return this.client.sendGetSingleRequest( theUrl, StudyTrip.class );
    }

    public WebApiResponse loadSingleByUrl(final String url ) throws IOException
    {
        return this.client.sendGetSingleRequest( url, StudyTrip.class );
    }

    public WebApiResponse loadSinglePerson(final StudyTrip studyTrip ) throws IOException
    {
        final String theUrl = String.format( "%s/%d", BASE_URL, studyTrip.getId( ) );
        return this.client.sendGetSingleRequest( theUrl, StudyTrip.class );
    }

    public WebApiResponse loadAllPersons( ) throws IOException
    {
        return loadAllPersonsByParameters( "", "" );
    }

    public WebApiResponse loadAllPersonsByParameters(
            final String firstName,
            final String lastName
    )
            throws IOException
    {
        final String theUrl = String.format(
                "%s?firstname=%s&lastname=%s",
                BASE_URL,
                firstName,
                lastName
        );

        return this.client.sendGetCollectionRequest( theUrl, new GenericType<List<StudyTrip>>( )
        {
        } );
    }

    public WebApiResponse update(final StudyTrip studyTrip ) throws IOException
    {
        final String theUrl = String.format( "%s/%d", BASE_URL, studyTrip.getId( ) );
        return this.client.sendPutRequest( theUrl, studyTrip );
    }

    public WebApiResponse delete(final StudyTrip studyTrip ) throws IOException
    {
        final String theUrl = String.format( "%s/%d", BASE_URL, studyTrip.getId( ) );
        return this.client.sendDeleteRequest( theUrl );
    }
}