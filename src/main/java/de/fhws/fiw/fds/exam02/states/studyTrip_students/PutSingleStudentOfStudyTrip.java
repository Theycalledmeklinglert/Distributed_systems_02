package de.fhws.fiw.fds.exam02.states.studyTrip_students;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.Student;

public class PutSingleStudentOfStudyTrip extends AbstractPutRelationState<Student>
{
	public PutSingleStudentOfStudyTrip(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).readById( this.requestedId );
	}

	@Override protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripStudentDao().update( this.primaryId, this.modelToUpdate );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripStudentUri.REL_PATH_ID,
			StudyTripStudentRelTypes.GET_SINGLE_STUDENT,
			getAcceptRequestHeader( ),
			this.primaryId, this.requestedId );
	}

	public static class Builder extends AbstractPutRelationStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new PutSingleStudentOfStudyTrip( this );
		}
	}
}
