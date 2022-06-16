package de.fhws.fiw.fds.sutton.client;

import de.fhws.fiw.fds.sutton.models.StudyTrip;
import okhttp3.Headers;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class WebApiResponsePrevVersion
{
	private Collection<StudyTrip> responseData;

	private final int lastStatusCode;

	private Headers headers; // headers.values("Link")

	public WebApiResponsePrevVersion(final int lastStatusCode )
	{
		this( Collections.EMPTY_LIST, lastStatusCode );
	}

	public WebApiResponsePrevVersion(final StudyTrip responseData, final int lastStatusCode )
	{
		this( Optional.of( responseData ), lastStatusCode );
	}

	public WebApiResponsePrevVersion(final Optional<StudyTrip> responseData, final int lastStatusCode )
	{
		this( convertToList( responseData ), lastStatusCode );
	}

	public WebApiResponsePrevVersion(final Collection<StudyTrip> responseData, final int lastStatusCode )
	{
		this.responseData = responseData;
		this.lastStatusCode = lastStatusCode;
	}

	public WebApiResponsePrevVersion(final Headers headers, final int lastStatusCode) {
		this.headers = headers;
		this.lastStatusCode = lastStatusCode;
	}

	public WebApiResponsePrevVersion(final Collection<StudyTrip> responseData, final int lastStatusCode, final Headers headers )
	{
		this.responseData = responseData;
		this.lastStatusCode = lastStatusCode;
		this.headers = headers;
	}

	public Collection<StudyTrip> getResponseData( )
	{
		return responseData;
	}

	public int getLastStatusCode( )
	{
		return lastStatusCode;
	}

	private static Collection<StudyTrip> convertToList(final Optional<StudyTrip> project )
	{
		return project.isPresent( ) ? Collections.singletonList( project.get( ) ) : Collections.emptyList( );
	}

	public Headers getHeaders() {
		return this.headers;
	}

	/*
	public long getIdFromHeaderString()
	{
		return Long.parseLong(this.header.substring(this.header.lastIndexOf("/") + 1));
	}
	*/
}
