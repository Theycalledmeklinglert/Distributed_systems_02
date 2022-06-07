package de.fhws.fiw.fds.exam02.services;


import de.fhws.fiw.fds.exam02.states.locations.*;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingPage;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.sutton.server.models.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "students" )
public class StudentService extends AbstractService
{
	@GET
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getAllStudents(@DefaultValue( "1" ) @QueryParam( PagingBehaviorUsingPage.QUERY_PARAM_PAGE) final int pageNumber)
	{
		return new GetAllStudents.Builder( )
			.setQuery( new GetAllStudents.AllStudents( )
					.setPagingBehavior( new PagingBehaviorUsingPage( pageNumber ) ) )
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
	public Response getSingleStudent( @PathParam( "id" ) final long id )
	{
		return new GetSingleStudent.Builder( )
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
	public Response createSingleStudent(final Student studentModel )
	{
		return new PostNewStudent.Builder( )
			.setModelToCreate( studentModel )
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
	public Response updateSingleStudent( @PathParam( "id" ) final long id, final Student studentModel )
	{
		return new PutSingleStudent.Builder( )
			.setRequestedId( id )
			.setModelToUpdate( studentModel )
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
	public Response deleteSingleStudent(@PathParam( "id" ) final long id )
	{
		return new DeleteSingleStudent.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}
}
