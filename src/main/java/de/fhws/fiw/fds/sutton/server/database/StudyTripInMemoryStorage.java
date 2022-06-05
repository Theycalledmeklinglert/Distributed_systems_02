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

package de.fhws.fiw.fds.sutton.server.database;


import de.fhws.fiw.fds.sutton.server.models.StudyTrip;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;

public class StudyTripInMemoryStorage extends AbstractInMemoryStorage<StudyTrip> implements StudyTripDao
{
	public StudyTripInMemoryStorage( )
	{
		super( );
		populateData( );
	}

	  private void populateData( )
	{
		long id = 0;
		create( new StudyTrip(id, "Test Trip", LocalDate.of(2000, 01, 01), LocalDate.of(2001, 01, 01), "FHWS", "Wuerzburg", "Germany", new HashSet<Long>()));
	}




}
