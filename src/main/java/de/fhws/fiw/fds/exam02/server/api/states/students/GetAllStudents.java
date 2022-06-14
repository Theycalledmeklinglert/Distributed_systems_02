package de.fhws.fiw.fds.exam02.server.api.states.students;


import de.fhws.fiw.fds.exam02.server.database.DaoFactory;
import de.fhws.fiw.fds.exam02.server.database.DatabaseException;
import de.fhws.fiw.fds.exam02.server.database.models.Student;
import de.fhws.fiw.fds.exam02.server.database.models.StudyTrip;
import de.fhws.fiw.fds.exam02.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;

import javax.ws.rs.core.GenericEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GetAllStudents extends AbstractGetCollectionState<Student>
{
	public GetAllStudents(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	protected void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( new GenericEntity<Collection<Student>>( this.result.getResult( ) )
		{
		} );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudentUri.REL_PATH, StudentRelTypes.CREATE_STUDENT, getAcceptRequestHeader( ) );
	}

	public static class AllStudents extends AbstractQuery<Student>
	{
		@Override protected CollectionModelResult<Student> doExecuteQuery( ) throws DatabaseException
		{
			CollectionModelResult<Student> result = DaoFactory.getInstance( ).getStudentDao( ).readByPredicate( all( ) );

			ArrayList<Student> newList = (ArrayList<Student>) result.getResult();
			Collections.sort(newList, Comparator.comparing(Student::getLastname).thenComparing(Student::getFirstname));
			result.setResult(newList);

			return result;
		}
	}

	public static class Builder extends AbstractGetCollectionState.AbstractGetCollectionStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new GetAllStudents( this );
		}
	}
}
