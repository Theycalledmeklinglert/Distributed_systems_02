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

package de.fhws.fiw.fds.exam02.states.persons;

import de.fhws.fiw.fds.exam02.states.person_locations.StudyTripStudentUri;
import de.fhws.fiw.fds.exam02.states.person_locations.StudyTripStudentRelTypes;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.StudyTrip;

public class GetSingleStudyTrip extends AbstractGetState<StudyTrip>
{
	public GetSingleStudyTrip(final AbstractGetStateBuilder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {

	}

	@Override protected SingleModelResult<StudyTrip> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao().readById( this.requestedId );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripUri.REL_PATH_ID, StudyTripRelTypes.UPDATE_SINGLE_STUDYTRIP, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( StudyTripUri.REL_PATH_ID, StudyTripRelTypes.DELETE_SINGLE_STUDYTRIP, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( StudyTripStudentUri.REL_PATH, StudyTripStudentRelTypes.CREATE_STUDENT, getAcceptRequestHeader( ),
			this.requestedId );
	}

	public static class Builder extends AbstractGetStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSingleStudyTrip( this );
		}
	}
}
