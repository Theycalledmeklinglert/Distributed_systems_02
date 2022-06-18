

package de.fhws.fiw.fds.sutton.utils;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import javax.ws.rs.core.Link;

public class JsonServerLinkConverter implements Converter<Link>
{
	public JsonServerLinkConverter( )
	{
	}

	@Override public void serialize( final Link link, final ObjectWriter objectWriter, final Context context )
		throws Exception
	{
		objectWriter.writeName( link.getTitle( ) );
		objectWriter.beginObject( );
		objectWriter.writeString( "href", this.replaceCharacters( link.getUri( ).toASCIIString( ) ) );
		objectWriter.writeString( "rel", link.getRel( ) );
		if ( link.getType( ) != null && !link.getType( ).isEmpty( ) )
		{
			objectWriter.writeString( "type", link.getType( ) );
		}

		objectWriter.endObject( );
	}

	@Override public Link deserialize( final ObjectReader objectReader, final Context context ) throws Exception
	{
		Link returnValue = null;
		objectReader.beginObject( );

		while ( objectReader.hasNext( ) )
		{
			objectReader.next( );
			if ( "href".equals( objectReader.name( ) ) )
			{
				final String link = objectReader.valueAsString( );
				returnValue = Link.fromUri( link ).build( new Object[ 0 ] );
			}
		}

		objectReader.endObject( );
		return returnValue;
	}

	private String replaceCharacters( final String body )
	{
		return body.replace( "%3F", "?" ).replaceAll( "%7B", "{" ).replaceAll( "%7D", "}" );
	}
}
