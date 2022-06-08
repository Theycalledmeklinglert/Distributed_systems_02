package de.fhws.fiw.fds.exam02.api.states.studyTrip_students;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.models.Student;
import de.fhws.fiw.fds.exam02.database.results.NoContentResult;
import de.fhws.fiw.fds.exam02.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;

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
