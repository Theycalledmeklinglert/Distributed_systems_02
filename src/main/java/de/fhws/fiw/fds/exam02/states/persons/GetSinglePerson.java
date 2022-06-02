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

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.get.AbstractGetState;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.SingleModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.person_locations.PersonLocationRelTypes;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.person_locations.PersonLocationUri;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Person;

public class GetSinglePerson extends AbstractGetState<Person>
{
	public GetSinglePerson( final AbstractGetStateBuilder builder )
	{
		super( builder );
	}

	@Override protected SingleModelResult<Person> loadModel( )
	{
		return DaoFactory.getInstance( ).getPersonDao( ).readById( this.requestedId );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonUri.REL_PATH_ID, PersonRelTypes.UPDATE_SINGLE_PERSON, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( PersonUri.REL_PATH_ID, PersonRelTypes.DELETE_SINGLE_PERSON, getAcceptRequestHeader( ),
			this.requestedId );
		addLink( PersonLocationUri.REL_PATH, PersonLocationRelTypes.CREATE_LOCATION, getAcceptRequestHeader( ),
			this.requestedId );
	}

	public static class Builder extends AbstractGetStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSinglePerson( this );
		}
	}
}
