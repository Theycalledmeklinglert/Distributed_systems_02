package de.fhws.fiw.fds.exam02.server.api;

import java.util.Collection;
import java.util.stream.Collectors;

public class Pagination
{
	private static final int MAX_PAGE_SIZE = 10;

	public static <T> Collection<T> page( final Collection<T> result, final int pageNumber )
	{
		return result;
	}

	public static <T> Collection<T> page( final Collection<T> result, final int offset, final int size )
	{
		final long skip = Math.max( offset, 0 );
		final long limit = Math.min( Math.max( size, 1 ), MAX_PAGE_SIZE );

		return result.stream( )
				.skip( skip )
				.limit( limit )
				.collect( Collectors.toList( ) );
	}}
