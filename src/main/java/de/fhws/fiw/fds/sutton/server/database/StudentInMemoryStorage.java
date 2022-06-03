package de.fhws.fiw.fds.sutton.server.database;


import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.models.Student;

import java.time.LocalDate;

public class StudentInMemoryStorage extends AbstractInMemoryStorage<Student> implements StudentDao
{
	public StudentInMemoryStorage( )
	{
		super( );
		// populateData( );
	}

	/* private void populateData( )
	{
		create( new Location( "London", 0d, 0d, LocalDate.of( 1960, 2, 9 ) ) );
	}

	 */
}
