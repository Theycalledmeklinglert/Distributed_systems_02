package de.fhws.fiw.fds.exam02.states.person_locations;


import de.fhws.fiw.fds.exam02.Start;

public interface StudyTripStudentUri
{
	String SHOW_ALL_PARAMETER = "showAll";
	String PATH_ELEMENT = "studyTrips/{id}/students";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_SHOW_ALL = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=true";
	String REL_PATH_SHOW_ONLY_LINKED =
		Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=false";
	String REL_PATH_ID = REL_PATH + "/{id}";
}
