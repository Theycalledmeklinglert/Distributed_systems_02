package de.fhws.fiw.fds.exam02.states.person_locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import de.fhws.pvs.unit10.slides.sutton.server.database.DatabaseException;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.CollectionModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.database.PersonLocationDao;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.function.Predicate;

public class GetAllLocationsOfPerson extends AbstractGetCollectionRelationState<Location>
{
	public GetAllLocationsOfPerson( final Builder builder )
	{
		super( builder );
	}

	@Override protected void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( new GenericEntity<Collection<Location>>( this.result.getResult( ) )
		{
		} );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonLocationUri.REL_PATH,
			PersonLocationRelTypes.CREATE_LOCATION,
			getAcceptRequestHeader( ),
			this.primaryId );

		if ( this.query.isShowAll( ) )
		{
			addLink( PersonLocationUri.REL_PATH_SHOW_ONLY_LINKED,
				PersonLocationRelTypes.GET_ALL_LINKED_LOCATIONS,
				getAcceptRequestHeader( ),
				this.primaryId );
		}
		else
		{
			addLink( PersonLocationUri.REL_PATH_SHOW_ALL,
				PersonLocationRelTypes.GET_ALL_LOCATIONS,
				getAcceptRequestHeader( ),
				this.primaryId );
		}
	}

	public static class AllLocations extends AbstractRelationQuery<Location>
	{
		private final PersonLocationDao storage;

		public AllLocations( final long primaryId, final boolean showAll )
		{
			super( primaryId, showAll );
			this.storage = DaoFactory.getInstance( ).getPersonLocationDao( );
		}

		@Override protected CollectionModelResult<Location> doExecuteQuery( ) throws DatabaseException
		{
			if ( showAll )
			{
				return storage.readAllByPredicate( this.primaryId, all( ) );
			}
			else
			{
				return this.storage.readByPredicate( this.primaryId, all( ) );
			}
		}
	}

	public static class FilterLocationsByName extends AbstractRelationQuery<Location>
	{
		private final PersonLocationDao storage;
		private final String cityName;

		public FilterLocationsByName( final long primaryId, final boolean showAll, final String cityName )
		{
			super( primaryId, showAll );
			this.cityName = cityName;
			this.storage = DaoFactory.getInstance( ).getPersonLocationDao( );
		}

		@Override protected CollectionModelResult<Location> doExecuteQuery( ) throws DatabaseException
		{
			if ( showAll )
			{
				return this.storage.readAllByPredicate( this.primaryId, byCityName( ) );
			}
			else
			{
				return this.storage.readByPredicate( this.primaryId, byCityName( ) );
			}
		}

		protected Predicate<Location> byCityName( )
		{
			return l -> StringUtils.isEmpty( cityName ) || l.getCityName( ).equalsIgnoreCase( cityName );
		}
	}

	public static class Builder extends AbstractGetCollectionRelationStateBuilder<Location>
	{
		@Override public AbstractState build( )
		{
			return new GetAllLocationsOfPerson( this );
		}
	}
}
