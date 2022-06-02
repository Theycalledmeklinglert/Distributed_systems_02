package de.fhws.fiw.fds.exam02.states.person_locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.NoContentResult;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.SingleModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class DeleteSingleLocationOfPerson extends AbstractDeleteRelationState<Location>
{
	public DeleteSingleLocationOfPerson( final Builder builder )
	{
		super( builder );
	}

	@Override protected SingleModelResult<Location> loadModel( )
	{
		return DaoFactory.getInstance( ).getPersonLocationDao( ).readById( this.primaryId, this.modelIdToDelete );
	}

	@Override protected NoContentResult deleteModel( )
	{
		return DaoFactory.getInstance( ).getPersonLocationDao( ).deleteRelation( this.primaryId, this.modelIdToDelete );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonLocationUri.REL_PATH,
			PersonLocationRelTypes.GET_ALL_LINKED_LOCATIONS,
			getAcceptRequestHeader( ),
			this.primaryId );
	}

	public static class Builder extends AbstractDeleteRelationStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new DeleteSingleLocationOfPerson( this );
		}
	}
}
