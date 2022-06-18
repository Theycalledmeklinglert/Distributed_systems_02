
package de.fhws.fiw.fds.exam02.server.database.results;

import de.fhws.fiw.fds.exam02.server.database.models.AbstractModel;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionModelResult<T extends AbstractModel> extends AbstractResult
{
	protected Collection<T> result;

	protected int totalNumberOfResult;

	public CollectionModelResult( )
	{
		this.result = new LinkedList<>( );
		this.totalNumberOfResult = 0;
	}

	public CollectionModelResult( final Collection<T> result )
	{
		this.result = result != null ? result : new LinkedList<>( );
		this.totalNumberOfResult = result.size( );
	}

	@Override
	public boolean isEmpty( )
	{
		return this.result.isEmpty( );
	}

	public Collection<T> getResult( )
	{
		return this.result;
	}

	public int getTotalNumberOfResult( )
	{
		return this.totalNumberOfResult;
	}

	public void setTotalNumberOfResult( final int totalNumberOfResult )
	{
		this.totalNumberOfResult = totalNumberOfResult;
	}

	public void setResult( Collection<T> result )
	{
		this.result = result;
	}

}
