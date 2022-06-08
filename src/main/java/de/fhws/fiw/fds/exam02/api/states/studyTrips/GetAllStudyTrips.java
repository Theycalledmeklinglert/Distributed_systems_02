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

package de.fhws.fiw.fds.exam02.api.states.studyTrips;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.DatabaseException;
import de.fhws.fiw.fds.exam02.database.models.StudyTrip;
import de.fhws.fiw.fds.exam02.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.GenericEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.function.Predicate;

public class GetAllStudyTrips extends AbstractGetCollectionState<StudyTrip>
{
	public GetAllStudyTrips(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	protected void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( new GenericEntity<Collection<StudyTrip>>( this.result.getResult( ) )
		{
		} );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripUri.REL_PATH, StudyTripRelTypes.CREATE_STUDYTRIP, getAcceptRequestHeader( ) );
	}

	public static class AllPersons extends AbstractQuery<StudyTrip>
	{
		@Override protected CollectionModelResult<StudyTrip> doExecuteQuery( ) throws DatabaseException
		{
			return DaoFactory.getInstance( ).getStudyTripDao( ).readByPredicate( all( ) );
		}
	}

	public static class ByNameAndStartAndEndDateAndCityAndCountry extends AbstractQuery<StudyTrip>
	{
		protected String name;

		protected LocalDate firstDate;

		protected LocalDate lastDate;

		protected String city;

		protected String country;

		public ByNameAndStartAndEndDateAndCityAndCountry(String name, String firstDate, String lastDate, String city, String country) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			this.name = name;
			if(!StringUtils.isEmpty(firstDate)) this.firstDate = LocalDate.parse(firstDate, formatter);
			if(!StringUtils.isEmpty(lastDate)) this.lastDate = LocalDate.parse(lastDate, formatter);
			this.city = city;
			this.country = country;
		}

		@Override protected CollectionModelResult<StudyTrip> doExecuteQuery( ) throws DatabaseException
		{
			return DaoFactory.getInstance( ).getStudyTripDao( ).readByPredicate( byNameAndStartAndEndDateAndCityAndCountry());
		}

		private Predicate<StudyTrip> byNameAndStartAndEndDateAndCityAndCountry()
		{
			return t -> matchName(t) && matchStartAndEndDate(t) && matchCity(t) && matchCountry(t);
		}

		private boolean matchName( final StudyTrip trip )
		{
			return StringUtils.isEmpty( name ) || trip.getName( ).contains( name );
		}

		private boolean matchStartAndEndDate( final StudyTrip trip )
		{
			return matchStartDate(trip) || matchEndDate(trip);
		}

		private boolean matchStartDate( final StudyTrip trip )
		{
			return firstDate == null || trip.getFirstDate().isBefore(firstDate) || trip.getFirstDate().isEqual(firstDate);
		}

		private boolean matchEndDate( final StudyTrip trip )
		{
			return lastDate == null || trip.getLastDate().isAfter(lastDate) || trip.getLastDate().isEqual(lastDate);
		}

		private boolean matchCity( final StudyTrip trip )
		{
			return StringUtils.isEmpty( city ) || trip.getCity().contains( city );
		}

		private boolean matchCountry( final StudyTrip trip)
		{
			return StringUtils.isEmpty( country ) || trip.getCountry().contains( country );
		}

	}

	public static class Builder extends AbstractGetCollectionStateBuilder<StudyTrip>
	{
		@Override public AbstractState build( )
		{
			return new GetAllStudyTrips( this );
		}
	}
}
