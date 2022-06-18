

package de.fhws.fiw.fds.exam02.server.database.results;

import de.fhws.fiw.fds.exam02.server.database.models.AbstractModel;

public class SingleModelResult<T extends AbstractModel> extends AbstractResult
{
	protected T result;

	protected boolean found;

	public SingleModelResult( )
	{
		this.found = false;
	}

	public SingleModelResult( final T result )
	{
		this.result = result;
		this.found = result != null;
	}

	public T getResult( )
	{
		return this.result;
	}

	@Override
	public boolean isEmpty( )
	{
		return !this.found;
	}
}
