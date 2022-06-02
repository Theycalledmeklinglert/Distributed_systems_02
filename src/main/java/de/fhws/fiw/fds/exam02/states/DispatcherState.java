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

package de.fhws.fiw.fds.exam02.states;

import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.get.AbstractGetDispatcherState;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.persons.PersonRelTypes;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.persons.PersonUri;

import javax.ws.rs.core.MediaType;

public class DispatcherState extends AbstractGetDispatcherState
{
	public DispatcherState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonUri.REL_PATH, PersonRelTypes.CREATE_PERSON, MediaType.APPLICATION_JSON );
		addLink( PersonUri.REL_PATH, PersonRelTypes.GET_ALL_PERSONS, MediaType.APPLICATION_JSON );
	}

	public static class Builder extends AbstractDispatcherStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new DispatcherState( this );
		}
	}
}
