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

package de.fhws.fiw.fds.sutton.server.api.states.post;

import de.fhws.fiw.fds.exam02.database.models.AbstractModel;
import de.fhws.fiw.fds.exam02.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public abstract class AbstractPostState<T extends AbstractModel> extends AbstractState
{
	protected T modelToStore;

	protected NoContentResult resultAfterSave;

	protected AbstractPostState( final AbstractPostStateBuilder<T> builder )
	{
		super( builder );
		this.modelToStore = builder.modelToCreate;
	}

	@Override
	protected Response buildInternal( )
	{
		configureState( );

		authorizeRequest( );

		if ( this.modelToStore.getId( ) != 0 )

		{

			return Response.status( Response.Status.BAD_REQUEST ).build( );
		}

		this.resultAfterSave = saveModel( );

		if ( this.resultAfterSave.hasError( ) )
		{
			return Response.serverError( )
						   .build( );
		}

		return createResponse( );
	}

	protected abstract void authorizeRequest( );

	protected abstract NoContentResult saveModel( );

	protected Response createResponse( )
	{
		defineLocationLink( );

		defineTransitionLinks( );

		return this.responseBuilder.build( );
	}

	/**
	 * This method is used to define all transition links based on the idea of a REST system as
	 * a finite state machine.
	 */
	protected abstract void defineTransitionLinks( );

	protected void defineLocationLink( )
	{
		final UriBuilder builder = this.uriInfo.getAbsolutePathBuilder( );
		final URI location = builder.path( Long.toString( this.modelToStore.getId( ) ) )
									.build( );
		this.responseBuilder.status( Response.Status.CREATED );
		this.responseBuilder.location( location );
	}

	public static abstract class AbstractPostStateBuilder<T extends AbstractModel>
		extends AbstractState.AbstractStateBuilder
	{
		protected T modelToCreate;

		public AbstractPostStateBuilder setModelToCreate( final T modelToCreate )
		{
			this.modelToCreate = modelToCreate;
			return this;
		}
	}
}
