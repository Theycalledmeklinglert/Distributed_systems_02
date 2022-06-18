

package de.fhws.fiw.fds.exam02.server.api.states.studyTrips;


import de.fhws.fiw.fds.exam02.server.database.DaoFactory;
import de.fhws.fiw.fds.exam02.server.database.models.StudyTrip;
import de.fhws.fiw.fds.exam02.server.database.results.NoContentResult;
import de.fhws.fiw.fds.exam02.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;

public class PutSingleStudyTrip extends AbstractPutState<StudyTrip>
{
	public PutSingleStudyTrip(final Builder builder )
	{
		super( builder );		// ????
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override protected SingleModelResult<StudyTrip> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao( ).readById( this.modelToUpdate.getId( ) );
	}

	@Override protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao( ).update( this.modelToUpdate );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( StudyTripUri.REL_PATH_ID, StudyTripRelTypes.GET_SINGLE_STUDYTRIP, getAcceptRequestHeader( ),
			this.modelToUpdate.getId( ) );
	}

	public static class Builder extends AbstractPutStateBuilder<StudyTrip>
	{
		@Override public AbstractState build( )
		{
			return new PutSingleStudyTrip( this );
		}
	}
}
