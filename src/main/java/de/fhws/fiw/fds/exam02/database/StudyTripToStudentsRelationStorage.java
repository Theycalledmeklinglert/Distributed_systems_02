package de.fhws.fiw.fds.exam02.database;

import de.fhws.fiw.fds.exam02.database.inmemory.AbstractInMemoryRelationStorage;
import de.fhws.fiw.fds.exam02.database.models.Student;

public class StudyTripToStudentsRelationStorage extends AbstractInMemoryRelationStorage<Student>
	implements StudyTripStudentDao
{
	public StudyTripToStudentsRelationStorage( )
	{
		super( );
		this.storage.put( 1l, 1l );
	}

	protected IDatabaseAccessObject<Student> getSecondaryStorage( )
	{
		return DaoFactory.getInstance( ).getStudentDao( );
	}
}
