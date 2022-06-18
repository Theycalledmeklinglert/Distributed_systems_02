
package de.fhws.fiw.fds.exam02.server.api.states;

import de.fhws.fiw.fds.exam02.server.api.states.students.StudentRelTypes;
import de.fhws.fiw.fds.exam02.server.api.states.students.StudentUri;
import de.fhws.fiw.fds.exam02.server.api.states.studyTrips.StudyTripRelTypes;
import de.fhws.fiw.fds.exam02.server.api.states.studyTrips.StudyTripUri;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;

import javax.ws.rs.core.MediaType;

public class DispatcherState extends AbstractGetDispatcherState
{
	public DispatcherState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripUri.REL_PATH, StudyTripRelTypes.GET_ALL_STUDYTRIPS, MediaType.APPLICATION_JSON );
		addLink( StudentUri.REL_PATH, StudentRelTypes.GET_ALL_STUDENT, MediaType.APPLICATION_JSON );

	}

	public static class Builder extends AbstractDispatcherStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new DispatcherState( this );
		}
	}
}
