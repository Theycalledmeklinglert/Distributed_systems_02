
package de.fhws.fiw.fds.exam02.server.database;

public class DatabaseException extends Exception
{
	public DatabaseException( )
	{
	}

	public DatabaseException( final String message )
	{
		super( message );
	}

	public DatabaseException( final String message, final Throwable cause )
	{
		super( message, cause );
	}
}
