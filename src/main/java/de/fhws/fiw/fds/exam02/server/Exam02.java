
package de.fhws.fiw.fds.exam02.server;

import de.fhws.fiw.fds.exam02.server.api.services.DispatcherService;
import de.fhws.fiw.fds.exam02.server.api.services.StudentService;
import de.fhws.fiw.fds.exam02.server.api.services.StudyTripService;
import de.fhws.fiw.fds.sutton.server.api.AbstractApplication;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath( "api" )
public class Exam02 extends AbstractApplication
{
	@Override protected Set<Class<?>> getServiceClasses( )
	{
		final Set<Class<?>> returnValue = new HashSet<>( );



		/* TODO: Add your service classes here */
		returnValue.add( DispatcherService.class );
		returnValue.add( StudentService.class );
		returnValue.add( StudyTripService.class );

		return returnValue;

	}
}
