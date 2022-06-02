package de.fhws.fiw.fds.exam02.states.locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.get.AbstractGetState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.SingleModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.persons.PersonUri;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class GetSingleLocation extends AbstractGetState<Location>
{
	public GetSingleLocation( final Builder builder )
	{
		super( builder );
	}

	@Override protected SingleModelResult<Location> loadModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).readById( this.requestedId );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( LocationUri.REL_PATH_ID, LocationRelTypes.UPDATE_SINGLE_LOCATION, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( PersonUri.REL_PATH_ID, LocationRelTypes.DELETE_SINGLE_LOCATION, getAcceptRequestHeader( ),
			this.requestedId );
	}

	public static class Builder extends AbstractGetStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSingleLocation( this );
		}
	}
}
