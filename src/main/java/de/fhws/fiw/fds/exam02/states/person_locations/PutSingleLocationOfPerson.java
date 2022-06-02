package de.fhws.fiw.fds.exam02.states.person_locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.NoContentResult;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.SingleModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class PutSingleLocationOfPerson extends AbstractPutRelationState<Location>
{
	public PutSingleLocationOfPerson( final Builder builder )
	{
		super( builder );
	}

	@Override protected SingleModelResult<Location> loadModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).readById( this.requestedId );
	}

	@Override protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getPersonLocationDao( ).update( this.primaryId, this.modelToUpdate );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonLocationUri.REL_PATH_ID,
			PersonLocationRelTypes.GET_SINGLE_LOCATION,
			getAcceptRequestHeader( ),
			this.primaryId, this.requestedId );
	}

	public static class Builder extends AbstractPutRelationStateBuilder<Location>
	{
		@Override public AbstractState build( )
		{
			return new PutSingleLocationOfPerson( this );
		}
	}
}
