package de.fhws.fiw.fds.exam02.states.locations;

import de.fhws.pvs.unit10.slides.suttondemo.Start;

public interface LocationUri
{
	String PATH_ELEMENT = "locations";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_ID = REL_PATH + "/{id}";
}
