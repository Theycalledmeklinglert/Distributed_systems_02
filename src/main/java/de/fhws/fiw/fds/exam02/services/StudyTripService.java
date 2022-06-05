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

import de.fhws.fiw.fds.exam02.states.person_locations.*;
import de.fhws.fiw.fds.exam02.states.persons.*;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.sutton.server.models.Student;
import de.fhws.fiw.fds.sutton.server.models.StudyTrip;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "studyTrips" )
public class StudyTripService extends AbstractService
{
	@GET
	@Produces( {MediaType.APPLICATION_JSON} )
	public Response getAllStudyTrips(
		@DefaultValue( "" ) @QueryParam( "name" ) final String name,
		@DefaultValue( "" ) @QueryParam( "startDate" ) final String startDate,
		@DefaultValue( "" ) @QueryParam( "endDate" ) final String endDate,
		@DefaultValue( "" ) @QueryParam( "city" ) final String city,
		@DefaultValue( "" ) @QueryParam( "country" ) final String countrty
		)
	{
		return new GetAllStudyTrips.Builder( )
			.setQuery( new GetAllStudyTrips.ByNameAndStartAndEndDateAndCityAndCountry( name, startDate, endDate, city, countrty ) )
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
	public Response getSingleStudyTrip( @PathParam( "id" ) final long id )
	{
		return new GetSingleStudyTrip.Builder( )
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
	public Response createSingleStudyTrip(final StudyTrip studyTripModel )
	{
		return new PostNewStudyTrip.Builder( )
			.setModelToCreate( studyTripModel )
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
	public Response updateSingleStudyTrip (@PathParam( "id" ) final long id, final StudyTrip studyTripModel )
	{
		return new PutSingleStudyTrip.Builder( )
			.setRequestedId( id )
			.setModelToUpdate( studyTripModel )
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
	public Response deleteSingleStudyTrip(@PathParam( "id" ) final long id )
	{
		return new DeleteSingleStudyTrip.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{studyTripId: \\d+}/students" )
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getStudentsOfStudyTrip(@PathParam( "studyTripId" ) final long studyTripId,
										   @DefaultValue( "" ) @QueryParam( "firstName" ) final String firstName,
										   @DefaultValue( "" ) @QueryParam( "lastName" ) final String lastName,
										   @DefaultValue( "false" ) @QueryParam( "showAll" ) final boolean showAll )
	{
		return new GetAllStudentsOfStudyTrip.Builder( )
			.setParentId( studyTripId )
			.setQuery( new GetAllStudentsOfStudyTrip.FilterStudentsByName( studyTripId, showAll, firstName, lastName ) )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{studyTripId: \\d+}/students/{studentId: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getLocationByIdOfPerson( @PathParam( "studyTripId" ) final long studyTripId,
		@PathParam( "studentId" ) final long studentId )
	{
		return new GetSingleStudentOfStudyTrip.Builder( )
			.setParentId( studyTripId )
			.setRequestedId( studentId )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@POST
	@Path( "{studyTripId: \\d+}/students" )
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response createNewStudentOfStudyTrip(@PathParam( "studyTripId" ) final long studyTripId, final Student student )
	{
		return new PostNewStudentOfStudyTrip.Builder( )
			.setParentId( studyTripId )
			.setModelToCreate( student )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@PUT
	@Path( "{studyTripId: \\d+}/students/{studentId: \\d+}" )
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response updateNewLocationOfPerson( @PathParam( "studyTripId" ) final long studyTripId,
		@PathParam( "studentId" ) final long studentId, final Student student )
	{
		return new PutSingleStudentOfStudyTrip.Builder( )
			.setParentId( studyTripId )
			.setRequestedId( studentId )
			.setModelToUpdate( student )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@DELETE
	@Path( "{studyTripId: \\d+}/students/{studentId: \\d+}" )
	public Response deleteLocationOfPerson( @PathParam( "studyTripId" ) final long studyTripId,
		@PathParam( "studentId" ) final long studentId )
	{
		return new DeleteSingleStudentOfStudyTrip.Builder( )
			.setParentId( studyTripId )
			.setRequestedId( studentId )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}
}
