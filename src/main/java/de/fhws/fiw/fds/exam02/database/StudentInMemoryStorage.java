package de.fhws.fiw.fds.exam02.database;


import de.fhws.fiw.fds.exam02.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.exam02.database.models.Student;
import de.fhws.fiw.fds.exam02.database.results.NoContentResult;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

import static de.fhws.fiw.fds.exam02.database.models.ValidityCheck.checkStudent;

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
