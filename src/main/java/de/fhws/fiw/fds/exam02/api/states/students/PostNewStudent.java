package de.fhws.fiw.fds.exam02.api.states.students;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.models.Student;
import de.fhws.fiw.fds.exam02.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;


public class PostNewStudent extends AbstractPostState<Student>
{
	public PostNewStudent(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).create( this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostState.AbstractPostStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new PostNewStudent( this );
		}
	}
}
