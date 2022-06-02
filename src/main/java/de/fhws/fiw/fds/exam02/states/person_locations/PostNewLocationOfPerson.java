package de.fhws.fiw.fds.exam02.states.person_locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.NoContentResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class PostNewLocationOfPerson extends AbstractPostRelationState<Location>
{
	public PostNewLocationOfPerson( final Builder builder )
	{
		super( builder );
	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getPersonLocationDao( ).create( this.primaryId, this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostRelationStateBuilder<Location>
	{
		@Override public AbstractState build( )
		{
			return new PostNewLocationOfPerson( this );
		}
	}
}
