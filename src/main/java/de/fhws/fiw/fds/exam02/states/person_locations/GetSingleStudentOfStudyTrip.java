package de.fhws.fiw.fds.exam02.states.person_locations;


import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.Student;

public class GetSingleStudentOfStudyTrip extends AbstractGetRelationState<Student>
{
	public GetSingleStudentOfStudyTrip(final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).readById( this.requestedId );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripStudentUri.REL_PATH_SHOW_ONLY_LINKED,
			StudyTripStudentRelTypes.GET_ALL_LINKED_STUDENTS,
			getAcceptRequestHeader( ),
			this.primaryId );

		if ( isStudyTripLinkedToThisStudent( ) )
		{
			addLink( StudyTripStudentUri.REL_PATH_ID,
				StudyTripStudentRelTypes.UPDATE_SINGLE_STUDENT,
				getAcceptRequestHeader( ),
				this.primaryId, this.requestedId );

			addLink( StudyTripStudentUri.REL_PATH_ID,
				StudyTripStudentRelTypes.DELETE_LINK_FROM_STUDYTRIP_TO_STUDENT,
				getAcceptRequestHeader( ),
				this.primaryId, this.requestedId );
		}
		else
		{
			addLink( StudyTripStudentUri.REL_PATH_ID,
				StudyTripStudentRelTypes.CREATE_LINK_FROM_STUDYTRIP_TO_STUDENT,
				getAcceptRequestHeader( ),
				this.primaryId, this.requestedId );
		}
	}

	private boolean isStudyTripLinkedToThisStudent( )
	{
		return !DaoFactory.getInstance( )
						  .getStudyTripStudentDao( )
						  .readById( this.primaryId, this.requestedId )
						  .isEmpty( );
	}

	public static class Builder extends AbstractGetRelationStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSingleStudentOfStudyTrip( this );
		}
	}
}
