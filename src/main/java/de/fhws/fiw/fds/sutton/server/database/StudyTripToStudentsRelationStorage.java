package de.fhws.fiw.fds.sutton.server.database;

import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryRelationStorage;
import de.fhws.fiw.fds.sutton.server.models.Student;

public class StudyTripToStudentsRelationStorage extends AbstractInMemoryRelationStorage<Student>
	implements StudyTripStudentDao
{
	public StudyTripToStudentsRelationStorage( )
	{
		super( );
		this.storage.put( 1l, 1l );
	}

	@Override protected IDatabaseAccessObject<Student> getSecondaryStorage( )
	{
		return DaoFactory.getInstance( ).getStudentDao( );
	}
}
