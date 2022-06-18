
package de.fhws.fiw.fds.exam02.server.database.inmemory;

import de.fhws.fiw.fds.exam02.server.database.models.AbstractModel;
import de.fhws.fiw.fds.exam02.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.exam02.server.database.results.NoContentResult;
import de.fhws.fiw.fds.exam02.server.database.results.SingleModelResult;
import org.apache.commons.lang.ObjectUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractInMemoryStorage<T extends AbstractModel>
{
	protected Map<Long, T> storage;

	private final AtomicLong nextId;

	protected AbstractInMemoryStorage( )
	{
		this.storage = new HashMap<>( );
		this.nextId = new AtomicLong( 1l );
	}

	public NoContentResult create(final T model )
	{
		model.setId( nextId( ) );
		this.storage.put( model.getId( ), model );
		return new NoContentResult( );
	}

	public SingleModelResult<T> readById(final long id )
	{
		if ( this.storage.containsKey( id ) )
		{
			return new SingleModelResult<>( clone( this.storage.get( id ) ) );
		}
		else
		{
			return new SingleModelResult<>( );
		}
	}

	public CollectionModelResult<T> readByPredicate( final Predicate<T> predicate )
	{
		return new CollectionModelResult( clone( filterBy( predicate ) ) );
	}

	private Collection<T> filterBy( final Predicate<T> predicate )
	{
		return this.storage.values( )
						   .stream( )
						   .filter( predicate )
						   .collect( Collectors.toList( ) );
	}

	public NoContentResult update( final T model )
	{
		this.storage.put( model.getId( ), model );
		return new NoContentResult( );
	}

	public NoContentResult delete( final long id )
	{
		this.storage.remove( id );
		return new NoContentResult( );
	}

	protected final long nextId()
	{
		return this.nextId.getAndIncrement( );
	}

	private Collection<T> clone( final Collection<T> result )
	{
		return result.stream( ).map( e -> clone( e ) ).collect( Collectors.toList( ) );
	}

	private T clone( final T result )
	{
		final T clone = ( T ) ObjectUtils.cloneIfPossible( result );
		return clone;
	}

}
