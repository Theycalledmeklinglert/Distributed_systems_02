package de.fhws.fiw.fds.exam02.states.locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.NoContentResult;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.SingleModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.persons.PersonRelTypes;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.persons.PersonUri;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class DeleteSingleLocation extends AbstractDeleteState<Location>
{
	public DeleteSingleLocation( final Builder builder )
	{
		super( builder );
	}

	@Override protected SingleModelResult<Location> loadModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).readById( this.modelIdToDelete );
	}

	@Override protected NoContentResult deleteModel( )
	{
		DaoFactory.getInstance( ).getPersonLocationDao( ).deleteRelationsToSecondary( this.modelIdToDelete );
		return DaoFactory.getInstance( ).getPersonDao( ).delete( this.modelIdToDelete );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonUri.REL_PATH, PersonRelTypes.GET_ALL_PERSONS, getAcceptRequestHeader( ) );
	}

	public static class Builder extends AbstractDeleteStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new DeleteSingleLocation( this );
		}
	}
}
