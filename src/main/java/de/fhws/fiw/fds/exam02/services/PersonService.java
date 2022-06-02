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

package de.fhws.fiw.fds.exam02.services;

import de.fhws.fiw.fds.exam02.states.persons.GetAllPersons;
import de.fhws.fiw.fds.exam02.states.persons.GetSinglePerson;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "studyTrips" )
public class PersonService extends AbstractService
{
	@GET
	@Produces( {MediaType.APPLICATION_JSON} )
	public Response getAllStudyTrips(
		@DefaultValue( "" ) @QueryParam( "firstname" ) final String firstName,
		@DefaultValue( "" ) @QueryParam( "lastname" ) final String lastName
	)
	{
		return new GetAllPersons.Builder( )
			.setQuery( new GetAllPersons.ByFirstAndLastName( firstName, lastName ) )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getSinglePerson( @PathParam( "id" ) final long id )
	{
		return new GetSinglePerson.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@POST
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response createSinglePerson( final Person personModel )
	{
		return new PostNewPerson.Builder( )
			.setModelToCreate( personModel )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@PUT
	@Path( "{id: \\d+}" )
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response updateSinglePerson( @PathParam( "id" ) final long id, final Person personModel )
	{
		return new PutSinglePerson.Builder( )
			.setRequestedId( id )
			.setModelToUpdate( personModel )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@DELETE
	@Path( "{id: \\d+}" )
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response deleteSinglePerson( @PathParam( "id" ) final long id )
	{
		return new DeleteSinglePerson.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{personId: \\d+}/locations" )
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getLocationsOfPerson( @PathParam( "personId" ) final long personId,
		@DefaultValue( "" ) @QueryParam( "cityname" ) final String cityName,
		@DefaultValue( "false" ) @QueryParam( "showAll" ) final boolean showAll )
	{
		return new GetAllLocationsOfPerson.Builder( )
			.setParentId( personId )
			.setQuery( new GetAllLocationsOfPerson.FilterLocationsByName( personId, showAll, cityName ) )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{personId: \\d+}/locations/{locationId: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getLocationByIdOfPerson( @PathParam( "personId" ) final long personId,
		@PathParam( "locationId" ) final long locationId )
	{
		return new GetSingleLocationOfPerson.Builder( )
			.setParentId( personId )
			.setRequestedId( locationId )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@POST
	@Path( "{personId: \\d+}/locations" )
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response createNewLocationOfPerson( @PathParam( "personId" ) final long personId, final Location location )
	{
		return new PostNewLocationOfPerson.Builder( )
			.setParentId( personId )
			.setModelToCreate( location )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@PUT
	@Path( "{personId: \\d+}/locations/{locationId: \\d+}" )
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response updateNewLocationOfPerson( @PathParam( "personId" ) final long personId,
		@PathParam( "locationId" ) final long locationId, final Location location )
	{
		return new PutSingleLocationOfPerson.Builder( )
			.setParentId( personId )
			.setRequestedId( locationId )
			.setModelToUpdate( location )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@DELETE
	@Path( "{personId: \\d+}/locations/{locationId: \\d+}" )
	public Response deleteLocationOfPerson( @PathParam( "personId" ) final long personId,
		@PathParam( "locationId" ) final long locationId )
	{
		return new DeleteSingleLocationOfPerson.Builder( )
			.setParentId( personId )
			.setRequestedId( locationId )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}
}
