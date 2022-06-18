
package de.fhws.fiw.fds.exam02.server.database.results;

public class NoContentResult extends AbstractResult
{
	public NoContentResult( )
	{
		super( );
	}

	@Override
	public boolean isEmpty( )
	{
		return true;
	}

}
