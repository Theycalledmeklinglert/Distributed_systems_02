
package de.fhws.fiw.fds.sutton.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class Hyperlinks
{
	public static void addLink( final UriInfo uriInfo,
		final Response.ResponseBuilder responseBuilder,
		final String path,
		final String relationType,
		final String mediaType,
		final Object... params )
	{
		final UriBuilder builder = uriInfo.getAbsolutePathBuilder( );
		builder.replacePath( beforeQuestionMark( path ) );
		builder.replaceQuery( afterQuestionMark( path ) );
		String uriTemplate = builder.toTemplate( );

		for ( final Object p : params )
		{
			uriTemplate = replaceFirstTemplate( uriTemplate, p );
		}

		responseBuilder.header( "Link", linkHeader( uriTemplate, relationType, mediaType ) );
	}

	private static String beforeQuestionMark( final String path )
	{
		if ( path.contains( "?" ) )
		{
			return path.substring( 0, path.indexOf( "?" ) );
		}
		else
		{
			return path;
		}
	}

	private static String afterQuestionMark( final String path )
	{
		if ( path.contains( "?" ) )
		{
			return path.substring( path.indexOf( "?" ) + 1 );
		}
		else
		{
			return "";
		}
	}

	public static String replaceFirstTemplate( final String uri, final Object value )
	{
		return uri.replaceFirst( "\\{id\\}", value.toString( ) );
	}

	public static String linkHeader( final String uri, final String rel, final String mediaType )
	{
		final StringBuilder sb = new StringBuilder( );
		sb.append( '<' ).append( uri ).append( ">;" );
		sb.append( "rel" ).append( "=\"" ).append( rel ).append( "\"" );
		if ( mediaType != null && !mediaType.isEmpty( ) )
		{
			sb.append( ";" );
			sb.append( "type" ).append( "=\"" ).append( mediaType ).append( "\"" );
		}

		return sb.toString( );
	}
}
