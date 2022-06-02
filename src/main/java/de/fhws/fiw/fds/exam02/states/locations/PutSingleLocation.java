package de.fhws.fiw.fds.exam02.states.locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.put.AbstractPutState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.NoContentResult;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.SingleModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class PutSingleLocation extends AbstractPutState<Location>
{
	public PutSingleLocation( final Builder builder )
	{
		super( builder );
	}

	@Override protected SingleModelResult<Location> loadModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).readById( this.modelToUpdate.getId( ) );
	}

	@Override protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).update( this.modelToUpdate );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( LocationUri.REL_PATH_ID, LocationRelTypes.GET_SINGLE_LOCATION, getAcceptRequestHeader( ),
			this.modelToUpdate.getId( ) );
	}

	public static class Builder extends AbstractPutStateBuilder<Location>
	{
		@Override public AbstractState build( )
		{
			return new PutSingleLocation( this );
		}
	}
}
