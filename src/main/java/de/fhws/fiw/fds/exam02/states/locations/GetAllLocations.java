package de.fhws.fiw.fds.exam02.states.locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.queries.AbstractQuery;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.pvs.unit10.slides.sutton.server.database.DatabaseException;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.CollectionModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;

public class GetAllLocations extends AbstractGetCollectionState<Location>
{
	public GetAllLocations( final Builder builder )
	{
		super( builder );
	}

	protected void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( new GenericEntity<Collection<Location>>( this.result.getResult( ) )
		{
		} );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( LocationUri.REL_PATH, LocationRelTypes.CREATE_LOCATION, getAcceptRequestHeader( ) );
	}

	public static class AllLocations extends AbstractQuery<Location>
	{
		@Override protected CollectionModelResult<Location> doExecuteQuery( ) throws DatabaseException
		{
			return DaoFactory.getInstance( ).getLocationDao( ).readByPredicate( all( ) );
		}
	}

	public static class Builder extends AbstractGetCollectionStateBuilder<Location>
	{
		@Override public AbstractState build( )
		{
			return new GetAllLocations( this );
		}
	}
}
