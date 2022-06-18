
package de.fhws.fiw.fds.exam02.server.api.states.studyTrips;

import de.fhws.fiw.fds.exam02.server.database.DaoFactory;
import de.fhws.fiw.fds.exam02.server.database.models.StudyTrip;
import de.fhws.fiw.fds.exam02.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;


public class PostNewStudyTrip extends AbstractPostState<StudyTrip>
{
	public PostNewStudyTrip(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao( ).create( this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostStateBuilder<StudyTrip>
	{
		@Override public AbstractState build( )
		{
			return new PostNewStudyTrip( this );
		}
	}
}
