
package de.fhws.fiw.fds.exam02.server.database.results;

public abstract class AbstractResult
{
	protected boolean hasError;

	protected int errorCode;

	protected String errorMessage;

	protected AbstractResult( )
	{
		this.hasError = false;
	}

	public abstract boolean isEmpty( );

	public final boolean hasError( )
	{
		return this.hasError;
	}

	public final void setError( )
	{
		this.hasError = true;
	}

	public final void setError( final int errorCode, final String errorMessage )
	{
		this.hasError = true;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
