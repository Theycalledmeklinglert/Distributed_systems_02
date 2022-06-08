package de.fhws.fiw.fds.exam02.api.states.students;

import de.fhws.fiw.fds.exam02.api.states.studyTrips.StudyTripRelTypes;
import de.fhws.fiw.fds.exam02.api.states.studyTrips.StudyTripUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.models.Student;
import de.fhws.fiw.fds.exam02.database.results.NoContentResult;
import de.fhws.fiw.fds.exam02.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;

public class DeleteSingleStudent extends AbstractDeleteState<Student>
{
	public DeleteSingleStudent(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).readById( this.modelIdToDelete );
	}

	@Override protected NoContentResult deleteModel( )
	{
		DaoFactory.getInstance( ).getStudyTripStudentDao( ).deleteRelationsToSecondary( this.modelIdToDelete );
		return DaoFactory.getInstance( ).getStudyTripDao( ).delete( this.modelIdToDelete );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripUri.REL_PATH, StudyTripRelTypes.GET_ALL_STUDYTRIPS, getAcceptRequestHeader( ) );
	}

	public static class Builder extends AbstractDeleteStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new DeleteSingleStudent( this );
		}
	}
}
