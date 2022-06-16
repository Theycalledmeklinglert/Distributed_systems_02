package de.fhws.fiw.fds.sutton.client;

import de.fhws.fiw.fds.exam02.server.database.models.AbstractModel;
import okhttp3.Headers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class WebApiResponse<T extends AbstractModel>
{
    private final Collection<T> responseData;

    private final int lastStatusCode;

    private Headers headers; // headers.values("Link")


    public WebApiResponse(Collection<T> responseData, int lastStatusCode, Headers headers) {
        this.responseData = responseData;
        this.lastStatusCode = lastStatusCode;
        this.headers = headers;
    }

    public WebApiResponse(final Optional<T> responseData, int lastStatusCode, Headers headers) {
        this.responseData = convertToList(responseData);       // Todo: Test if this cast works
        this.lastStatusCode = lastStatusCode;
        this.headers = headers;
    }


    public WebApiResponse( final int lastStatusCode )
    {
        this( Collections.EMPTY_LIST, lastStatusCode );
    }

    public WebApiResponse( final int lastStatusCode, final Headers headers )
    {
        this( Collections.EMPTY_LIST, lastStatusCode );
        this.headers = headers;
    }

    public WebApiResponse( final T responseData, final int lastStatusCode )
    {
        this( Optional.of( responseData ), lastStatusCode );
    }

    public WebApiResponse( final Optional<T> responseData, final int lastStatusCode )
    {
        this( convertToList( responseData ), lastStatusCode );
    }

    public WebApiResponse( final Collection<T> responseData, final int lastStatusCode )
    {
        this.responseData = responseData;
        this.lastStatusCode = lastStatusCode;
    }

    public Collection<T> getResponseData( )
    {
        return responseData;
    }

    public Optional<T> getFirstResponse( )
    {
        return this.responseData.stream( ).findFirst( );
    }

    public int getLastStatusCode( )
    {
        return lastStatusCode;
    }

    public Headers getHeaders( )
    {
        return headers;
    }

    private static <T> Collection<T> convertToList( final Optional<T> object )
    {
        return object.isPresent( ) ? Collections.singletonList( object.get( ) ) : Collections.emptyList( );
    }
}