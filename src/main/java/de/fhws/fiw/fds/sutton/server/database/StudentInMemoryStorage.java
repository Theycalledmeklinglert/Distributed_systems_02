package de.fhws.fiw.fds.sutton.server.database;


import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.models.Student;
import de.fhws.fiw.fds.sutton.server.models.StudyTrip;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static de.fhws.fiw.fds.sutton.server.models.ValidityCheck.checkStudent;

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
	public Set<Long> getImmatricNums()
	{
		HashSet<Long> immatricNums = new HashSet<>();
		this.storage.values().stream().forEach(s -> immatricNums.add(s.getImmatricNum()));
		return immatricNums;
	}

	@Override
	public NoContentResult create(final Student student )
	{
		if(!checkStudent(student)) throw new WebApplicationException(Response.status(422).build());
		student.setId( nextId( ) );
		this.storage.put( student.getId( ), student );
		return new NoContentResult( );
	}
}
