package de.fhws.fiw.fds.exam02.states.studyTrip_students;


import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.fiw.fds.sutton.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.Student;

public class DeleteSingleStudentOfStudyTrip extends AbstractDeleteRelationState<Student>
{
	public DeleteSingleStudentOfStudyTrip(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripStudentDao( ).readById( this.primaryId, this.modelIdToDelete );
	}

	@Override protected NoContentResult deleteModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripStudentDao( ).deleteRelation( this.primaryId, this.modelIdToDelete );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripStudentUri.REL_PATH,
			StudyTripStudentRelTypes.GET_ALL_LINKED_STUDENTS,
			getAcceptRequestHeader( ),
			this.primaryId );
	}

	public static class Builder extends AbstractDeleteRelationStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new DeleteSingleStudentOfStudyTrip( this );
		}
	}
}
