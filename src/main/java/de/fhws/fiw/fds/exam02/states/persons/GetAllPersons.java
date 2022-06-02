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

import de.fhws.pvs.unit10.slides.sutton.server.api.queries.AbstractQuery;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.AbstractState;
import de.fhws.pvs.unit10.slides.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.pvs.unit10.slides.sutton.server.database.DatabaseException;
import de.fhws.pvs.unit10.slides.sutton.server.database.results.CollectionModelResult;
import de.fhws.pvs.unit10.slides.suttondemo.database.DaoFactory;
import de.fhws.pvs.unit10.slides.suttondemo.models.Person;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.function.Predicate;

public class GetAllPersons extends AbstractGetCollectionState<Person>
{
	public GetAllPersons( final Builder builder )
	{
		super( builder );
	}

	protected void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( new GenericEntity<Collection<Person>>( this.result.getResult( ) )
		{
		} );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( PersonUri.REL_PATH, PersonRelTypes.CREATE_PERSON, getAcceptRequestHeader( ) );
	}

	public static class AllPersons extends AbstractQuery<Person>
	{
		@Override protected CollectionModelResult<Person> doExecuteQuery( ) throws DatabaseException
		{
			return DaoFactory.getInstance( ).getPersonDao( ).readByPredicate( all( ) );
		}
	}

	public static class ByFirstAndLastName extends AbstractQuery<Person>
	{
		protected String firstName;

		protected String lastName;

		public ByFirstAndLastName( final String firstName, final String lastName )
		{
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override protected CollectionModelResult<Person> doExecuteQuery( ) throws DatabaseException
		{
			return DaoFactory.getInstance( ).getPersonDao( ).readByPredicate( byFirstNameAndLastName( ) );
		}

		protected Predicate<Person> byFirstNameAndLastName( )
		{
			return p -> matchFirstName( p ) && matchLastName( p );
		}

		private boolean matchFirstName( final Person person )
		{
			return StringUtils.isEmpty( firstName ) || person.getFirstName( ).equals( firstName );
		}

		private boolean matchLastName( final Person person )
		{
			return StringUtils.isEmpty( lastName ) || person.getLastName( ).equals( lastName );
		}
	}

	public static class Builder extends AbstractGetCollectionStateBuilder<Person>
	{
		@Override public AbstractState build( )
		{
			return new GetAllPersons( this );
		}
	}
}
