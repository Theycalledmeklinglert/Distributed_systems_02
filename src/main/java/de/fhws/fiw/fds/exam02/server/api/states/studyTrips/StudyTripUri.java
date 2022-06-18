
package de.fhws.fiw.fds.exam02.server.api.states.studyTrips;


import de.fhws.fiw.fds.exam02.server.Start;

public interface StudyTripUri
{
	String PATH_ELEMENT = "studyTrips";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_ID = REL_PATH + "/{id}";
}
