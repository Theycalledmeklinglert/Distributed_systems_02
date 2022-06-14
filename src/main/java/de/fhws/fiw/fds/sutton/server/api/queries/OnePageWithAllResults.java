package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.exam02.server.database.models.AbstractModel;

public class OnePageWithAllResults<T extends AbstractModel> extends PagingBehaviorUsingOffsetSize<T>
{
	public OnePageWithAllResults( )
	{
		super( 0, Integer.MAX_VALUE );
	}

	@Override protected int getDefaultMaxPageSize( )
	{
		return Integer.MAX_VALUE;
	}
}
