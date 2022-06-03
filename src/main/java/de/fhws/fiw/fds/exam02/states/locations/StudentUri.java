package de.fhws.fiw.fds.exam02.states.locations;


import de.fhws.fiw.fds.exam02.Start;

public interface StudentUri
{
	String PATH_ELEMENT = "students";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_ID = REL_PATH + "/{id}";
}
