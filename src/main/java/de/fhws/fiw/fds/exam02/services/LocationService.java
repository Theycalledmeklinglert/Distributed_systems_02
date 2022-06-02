package de.fhws.fiw.fds.exam02.services;

import de.fhws.pvs.unit10.slides.sutton.server.api.services.AbstractService;
import de.fhws.pvs.unit10.slides.suttondemo.api.states.locations.*;
import de.fhws.pvs.unit10.slides.suttondemo.models.Location;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "locations" )
public class LocationService extends AbstractService
{
	@GET
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getAllLocations( )
	{
		return new GetAllLocations.Builder( )
			.setQuery( new GetAllLocations.AllLocations( ) )
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
	public Response getSingleLocation( @PathParam( "id" ) final long id )
	{
		return new GetSingleLocation.Builder( )
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
	public Response createSingleLocation( final Location locationModel )
	{
		return new PostNewLocation.Builder( )
			.setModelToCreate( locationModel )
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
	public Response updateSingleLocation( @PathParam( "id" ) final long id, final Location locationModel )
	{
		return new PutSingleLocation.Builder( )
			.setRequestedId( id )
			.setModelToUpdate( locationModel )
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
	public Response deleteSingleLocation( @PathParam( "id" ) final long id )
	{
		return new DeleteSingleLocation.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}
}
