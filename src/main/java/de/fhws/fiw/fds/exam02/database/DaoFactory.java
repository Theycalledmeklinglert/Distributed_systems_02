/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.exam02.database;

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
