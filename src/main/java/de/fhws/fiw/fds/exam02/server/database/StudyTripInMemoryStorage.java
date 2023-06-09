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

package de.fhws.fiw.fds.exam02.server.database;


import de.fhws.fiw.fds.exam02.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.exam02.server.database.models.StudyTrip;
import de.fhws.fiw.fds.exam02.server.database.results.NoContentResult;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static de.fhws.fiw.fds.exam02.server.database.models.ValidityCheck.checkStudyTrip;


public class StudyTripInMemoryStorage extends AbstractInMemoryStorage<StudyTrip> implements StudyTripDao
{
	public StudyTripInMemoryStorage( )
	{
		super( );
	//	populateData( );
	}

	  private void populateData( )
	{
		long id = 0;
	}

	 @Override
	public NoContentResult create(final StudyTrip studyTrip )
	{
		if(!checkStudyTrip(studyTrip)) throw new WebApplicationException(Response.status(422).build());
		studyTrip.setId( nextId( ) );
		this.storage.put( studyTrip.getId( ), studyTrip );
		return new NoContentResult( );
	}





}
