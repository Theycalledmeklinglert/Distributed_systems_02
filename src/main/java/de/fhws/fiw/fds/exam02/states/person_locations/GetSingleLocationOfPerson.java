package de.fhws.fiw.fds.exam02.states.person_locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.SingleModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class GetSingleLocationOfPerson extends AbstractGetRelationState<Location>
{
	public GetSingleLocationOfPerson( final Builder builder )
	{
		super( builder );
	}

	@Override protected SingleModelResult<Location> loadModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).readById( this.requestedId );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonLocationUri.REL_PATH_SHOW_ONLY_LINKED,
			PersonLocationRelTypes.GET_ALL_LINKED_LOCATIONS,
			getAcceptRequestHeader( ),
			this.primaryId );

		if ( isPersonLinkedToThisLocation( ) )
		{
			addLink( PersonLocationUri.REL_PATH_ID,
				PersonLocationRelTypes.UPDATE_SINGLE_LOCATION,
				getAcceptRequestHeader( ),
				this.primaryId, this.requestedId );

			addLink( PersonLocationUri.REL_PATH_ID,
				PersonLocationRelTypes.DELETE_LINK_FROM_PERSON_TO_LOCATION,
				getAcceptRequestHeader( ),
				this.primaryId, this.requestedId );
		}
		else
		{
			addLink( PersonLocationUri.REL_PATH_ID,
				PersonLocationRelTypes.CREATE_LINK_FROM_PERSON_TO_LOCATION,
				getAcceptRequestHeader( ),
				this.primaryId, this.requestedId );
		}
	}

	private boolean isPersonLinkedToThisLocation( )
	{
		return !DaoFactory.getInstance( )
						  .getPersonLocationDao( )
						  .readById( this.primaryId, this.requestedId )
						  .isEmpty( );
	}

	public static class Builder extends AbstractGetRelationStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSingleLocationOfPerson( this );
		}
	}
}
