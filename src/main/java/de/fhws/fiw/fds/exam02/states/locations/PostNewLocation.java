package de.fhws.fiw.fds.exam02.states.locations;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.post.AbstractPostState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.NoContentResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

public class PostNewLocation extends AbstractPostState<Location>
{
	public PostNewLocation( final Builder builder )
	{
		super( builder );
	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).create( this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostStateBuilder<Location>
	{
		@Override public AbstractState build( )
		{
			return new PostNewLocation( this );
		}
	}
}
