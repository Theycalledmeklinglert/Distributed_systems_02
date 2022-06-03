package de.fhws.fiw.fds.exam02.states.person_locations;


import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.fiw.fds.sutton.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.models.Student;

public class PostNewStudentOfStudyTrip extends AbstractPostRelationState<Student>
{
	public PostNewStudentOfStudyTrip(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripStudentDao().create( this.primaryId, this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostRelationStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new PostNewStudentOfStudyTrip( this );
		}
	}
}
