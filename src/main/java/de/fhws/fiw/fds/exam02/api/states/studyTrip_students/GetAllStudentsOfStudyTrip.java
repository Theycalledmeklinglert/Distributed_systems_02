package de.fhws.fiw.fds.exam02.api.states.studyTrip_students;


import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.DatabaseException;
import de.fhws.fiw.fds.exam02.database.StudyTripStudentDao;
import de.fhws.fiw.fds.exam02.database.models.Student;
import de.fhws.fiw.fds.exam02.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.function.Predicate;

public class GetAllStudentsOfStudyTrip extends AbstractGetCollectionRelationState<Student>
{
	public GetAllStudentsOfStudyTrip(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( new GenericEntity<Collection<Student>>( this.result.getResult( ) )
		{
		} );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripStudentUri.REL_PATH,
			StudyTripStudentRelTypes.CREATE_STUDENT,
			getAcceptRequestHeader( ),
			this.primaryId );

		if ( this.query.isShowAll( ) )
		{
			addLink( StudyTripStudentUri.REL_PATH_SHOW_ONLY_LINKED,
				StudyTripStudentRelTypes.GET_ALL_LINKED_STUDENTS,
				getAcceptRequestHeader( ),
				this.primaryId );
		}
		else
		{
			addLink( StudyTripStudentUri.REL_PATH_SHOW_ALL,
				StudyTripStudentRelTypes.GET_ALL_STUDENTS,
				getAcceptRequestHeader( ),
				this.primaryId );
		}
	}

	public static class AllStudents extends AbstractRelationQuery<Student>
	{
		private final StudyTripStudentDao storage;

		public AllStudents( final long primaryId, final boolean showAll )
		{
			super( primaryId, showAll );
			this.storage = DaoFactory.getInstance( ).getStudyTripStudentDao( );
		}

		@Override protected CollectionModelResult<Student> doExecuteQuery( ) throws DatabaseException
		{
			if ( showAll )
			{
				return storage.readAllByPredicate( this.primaryId, all( ) );
			}
			else
			{
				return this.storage.readByPredicate( this.primaryId, all( ) );
			}
		}
	}

	public static class FilterStudentsByName extends AbstractRelationQuery<Student>
	{
		private final StudyTripStudentDao storage;
		private final String firstName;
		private final String lastName;

		public FilterStudentsByName(final long primaryId, final boolean showAll, final String firstName, final String lastName )
		{
			super( primaryId, showAll );
			this.firstName = firstName;
			this.lastName = lastName;
			this.storage = DaoFactory.getInstance( ).getStudyTripStudentDao( );
		}

		@Override protected CollectionModelResult<Student> doExecuteQuery( ) throws DatabaseException
		{
			if ( showAll )
			{
				return this.storage.readAllByPredicate( this.primaryId, byFirstAndLastName( ) );
			}
			else
			{
				return this.storage.readByPredicate( this.primaryId, byFirstAndLastName( ) );
			}
		}

		protected Predicate<Student> byFirstAndLastName( )
		{
			return l -> StringUtils.isEmpty( firstName ) || StringUtils.isEmpty( lastName ) || l.getFirstname( ).equalsIgnoreCase( firstName )
						|| l.getLastname().equalsIgnoreCase( lastName );
		}
	}

	public static class Builder extends AbstractGetCollectionRelationState.AbstractGetCollectionRelationStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new GetAllStudentsOfStudyTrip( this );
		}
	}
}
