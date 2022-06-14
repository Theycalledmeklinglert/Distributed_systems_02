package de.fhws.fiw.fds.exam02.server.api.states.students;

import de.fhws.fiw.fds.exam02.server.api.states.studyTrips.StudyTripUri;
import de.fhws.fiw.fds.exam02.server.database.DaoFactory;
import de.fhws.fiw.fds.exam02.server.database.models.Student;
import de.fhws.fiw.fds.exam02.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;

public class GetSingleStudent extends AbstractGetState<Student>
{
	public GetSingleStudent(final Builder builder )
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

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudentUri.REL_PATH_ID, StudentRelTypes.UPDATE_SINGLE_STUDENT, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( StudyTripUri.REL_PATH_ID, StudentRelTypes.DELETE_SINGLE_STUDENT, getAcceptRequestHeader( ),
			this.requestedId );
	}

	public static class Builder extends AbstractGetState.AbstractGetStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSingleStudent( this );
		}
	}
}
