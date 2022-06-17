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

package de.fhws.fiw.fds.exam02.server.api.states.studyTrips;

import de.fhws.fiw.fds.exam02.server.database.DaoFactory;
import de.fhws.fiw.fds.exam02.server.database.DatabaseException;
import de.fhws.fiw.fds.exam02.server.database.models.StudyTrip;
import de.fhws.fiw.fds.exam02.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.GenericEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
			this.name = name.toLowerCase();
			if (!StringUtils.isEmpty(firstDate))
			{
				this.firstDate = LocalDate.parse(firstDate);
			}
			else this.firstDate = LocalDate.parse("0000-01-01");;
			if(!StringUtils.isEmpty(lastDate))
			{
				this.lastDate = LocalDate.parse(lastDate);
			}
			else this.lastDate = LocalDate.parse("9999-12-31");
			this.city = city.toLowerCase();
			this.country = country.toLowerCase();
		}

		@Override protected CollectionModelResult<StudyTrip> doExecuteQuery( ) throws DatabaseException
		{
			CollectionModelResult<StudyTrip> result = DaoFactory.getInstance( ).getStudyTripDao( ).readByPredicate( byNameAndStartAndEndDateAndCityAndCountry());

			ArrayList<StudyTrip> newList = (ArrayList<StudyTrip>) result.getResult();
			Collections.sort(newList, Comparator.comparing(StudyTrip::getFirstDate));
			result.setResult(newList);

			return result;
		}

		private Predicate<StudyTrip> byNameAndStartAndEndDateAndCityAndCountry()
		{
			return t -> matchName(t) && matchCity(t) && matchCountry(t) && matchDates(t); // &&matchStartDate(t)
		}

		private boolean matchName( final StudyTrip trip )
		{
			return StringUtils.isEmpty( name ) || trip.getName( ).toLowerCase().contains( name );
		}

		private boolean matchDates(final StudyTrip trip )
		{
			if(firstDate.isEqual(trip.getFirstDate()) || lastDate.isEqual(trip.getLastDate()) || firstDate.isEqual (trip.getLastDate()) || lastDate.isEqual(trip.getFirstDate()))
			{
				return true;
			}
			else
			{
				return (lastDate.isAfter(trip.getLastDate()) && firstDate.isBefore(trip.getFirstDate())) || (lastDate.isAfter(trip.getFirstDate()) && lastDate.isBefore(trip.getLastDate())) || (firstDate.isAfter(trip.getFirstDate()) && firstDate.isBefore(trip.getLastDate()));
			}
		}


		private boolean matchCity( final StudyTrip trip )
		{
			return StringUtils.isEmpty( city ) || trip.getCity().toLowerCase().contains( city );
		}

		private boolean matchCountry( final StudyTrip trip)
		{
			return StringUtils.isEmpty( country ) || trip.getCountry().toLowerCase().contains( country );
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
