
package de.fhws.fiw.fds.exam02.server.database;

public class DaoFactory
{
	private static DaoFactory INSTANCE;

	public static final DaoFactory getInstance( )
	{
		if ( INSTANCE == null )
		{
			INSTANCE = new DaoFactory( );
		}

		return INSTANCE;
	}

	private final StudyTripDao studyTripDao;

	private final StudentDao studentDao;

	private final StudyTripStudentDao studyTripStudentDao;

	private DaoFactory( )
	{
		this.studyTripDao = new StudyTripInMemoryStorage( );
		this.studentDao = new StudentInMemoryStorage( );
		this.studyTripStudentDao = new StudyTripToStudentsRelationStorage( );
	}

	public StudyTripDao getStudyTripDao( )
	{
		return this.studyTripDao;
	}

	public StudentDao getStudentDao( )
	{
		return this.studentDao;
	}

	public StudyTripStudentDao getStudyTripStudentDao( )
	{
		return studyTripStudentDao;
	}
}
