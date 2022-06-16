package de.fhws.fiw.fds.sutton.client;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.exam02.server.database.models.AbstractModel;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GenericWebClient<T extends AbstractModel>
{
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse( "application/json" );

    private final OkHttpClient client;

    private final Genson genson;

    public GenericWebClient( )
    {
        this( "", "" );
    }

    public GenericWebClient( final String userName, final String password )
    {
        this.client = new OkHttpClient.Builder( )
                .addInterceptor( new BasicAuthInterceptor( userName, password ) )
                .build( );
        this.genson = new Genson( );
    }

    public WebApiResponse sendDispatcherRequest( final String url ) throws IOException
    {
        final Request request = new Request.Builder( )
                .url( url )
                .get( )
                .build( );

        final Response response = this.client.newCall( request ).execute( );

        return new WebApiResponse(response.code(), response.headers());
    }

    public WebApiResponse<T> sendGetSingleRequest(final String url,
                                                             final Class<T> clazz ) throws IOException
    {
        final Request request = new Request.Builder( )
                .url( url )
                .get( )
                .build( );

        final Response response = this.client.newCall( request ).execute( );
        final int statusCodeOfLastRequest = response.code( );

        if ( statusCodeOfLastRequest == 200 )
        {
            return new WebApiResponse<>( deserialize( response, clazz ),
                    response.code( ), response.headers() );
        }
        else
        {
            return new WebApiResponse( statusCodeOfLastRequest, response.headers() );
        }
    }

    public WebApiResponse<T> sendGetCollectionRequest(final String url,
                                                                 final GenericType<List<T>> genericType )
            throws IOException
    {
        final Request request = new Request.Builder( )
                .url( url )
                .get( )
                .build( );

        final Response response = this.client.newCall( request ).execute( );
        final int statusCodeOfLastRequest = response.code( );

        if ( statusCodeOfLastRequest == 200 )
        {
            return new WebApiResponse<>( deserializeToCollection( response, genericType ),
                    response.code( ), response.headers() );
        }
        else
        {
            return new WebApiResponse( statusCodeOfLastRequest, response.headers() );
        }
    }

    public WebApiResponse sendPostRequest(final String url, final T object )
            throws IOException
    {
        final RequestBody body = RequestBody.create( MEDIA_TYPE_JSON, serialize( object ) );

        final Request request = new Request.Builder( )
                .url( url )
                .post( body )
                .build( );

        final Response response = this.client.newCall( request ).execute( );
        final int statusCodeOfLastRequest = response.code( );

        if ( statusCodeOfLastRequest == 201 )
        {
            return new WebApiResponse<T>( 201, response.headers());
        }
        else
        {
            return new WebApiResponse( statusCodeOfLastRequest, response.headers() );
        }
    }

    public WebApiResponse<T> sendPutRequest(final String url, final T object )
            throws IOException
    {
        final RequestBody body = RequestBody.create( MEDIA_TYPE_JSON, serialize( object ) );

        final Request request = new Request.Builder( )
                .url( url )
                .put( body )
                .build( );

        final Response response = this.client.newCall( request ).execute( );

        return new WebApiResponse<>( response.code( ), response.headers() );
    }

    public WebApiResponse<T> sendDeleteRequest(final String url ) throws IOException
    {
        final Request request = new Request.Builder( )
                .url( url )
                .delete( )
                .build( );

        final Response response = this.client.newCall( request ).execute( );

        return new WebApiResponse<>( response.code( ), response.headers() );
    }

    private String serialize( final T object )
    {
        return this.genson.serialize( object );
    }

    private List<T> deserializeToCollection( final Response response, final GenericType<List<T>> genericType )
            throws IOException
    {
        final String data = response.body( ).string( );
        return genson.deserialize( data, genericType );
    }

    private Optional<T> deserialize( final Response response, final Class<T> clazz ) throws IOException
    {
        final String data = response.body( ).string( );
        return Optional.ofNullable( genson.deserialize( data, clazz ) );
    }
}
