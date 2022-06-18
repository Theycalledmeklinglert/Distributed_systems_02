
package de.fhws.fiw.fds.exam02.server.api.states.studyTrips;

import de.fhws.fiw.fds.exam02.server.api.states.studyTrip_students.StudyTripStudentRelTypes;
import de.fhws.fiw.fds.exam02.server.api.states.studyTrip_students.StudyTripStudentUri;
import de.fhws.fiw.fds.exam02.server.database.DaoFactory;
import de.fhws.fiw.fds.exam02.server.database.models.StudyTrip;
import de.fhws.fiw.fds.exam02.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;

public class GetSingleStudyTrip extends AbstractGetState<StudyTrip>
{
	public GetSingleStudyTrip(final AbstractGetStateBuilder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {

	}

	@Override protected SingleModelResult<StudyTrip> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao().readById( this.requestedId );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripUri.PATH_ELEMENT, StudyTripRelTypes.GET_ALL_STUDYTRIPS, getAcceptRequestHeader( ),
				this.requestedId );
		addLink( StudyTripUri.REL_PATH_ID, StudyTripRelTypes.UPDATE_SINGLE_STUDYTRIP, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( StudyTripUri.REL_PATH_ID, StudyTripRelTypes.DELETE_SINGLE_STUDYTRIP, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( StudyTripStudentUri.REL_PATH, StudyTripStudentRelTypes.CREATE_STUDENT, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( StudyTripStudentUri.REL_PATH, StudyTripStudentRelTypes.GET_ALL_LINKED_STUDENTS, getAcceptRequestHeader( ),
				this.requestedId );
	}

	public static class Builder extends AbstractGetStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSingleStudyTrip( this );
		}
	}
}
