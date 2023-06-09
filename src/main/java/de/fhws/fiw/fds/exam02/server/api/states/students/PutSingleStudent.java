package de.fhws.fiw.fds.exam02.server.api.states.students;


import de.fhws.fiw.fds.exam02.server.database.DaoFactory;
import de.fhws.fiw.fds.exam02.server.database.models.Student;
import de.fhws.fiw.fds.exam02.server.database.results.NoContentResult;
import de.fhws.fiw.fds.exam02.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;

public class PutSingleStudent extends AbstractPutState<Student>
{
	public PutSingleStudent(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).readById( this.modelToUpdate.getId( ) );
	}

	@Override protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).update( this.modelToUpdate );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudentUri.REL_PATH_ID, StudentRelTypes.GET_SINGLE_STUDENT, getAcceptRequestHeader( ),
			this.modelToUpdate.getId( ) );
	}

	public static class Builder extends AbstractPutState.AbstractPutStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new PutSingleStudent( this );
		}
	}
}
