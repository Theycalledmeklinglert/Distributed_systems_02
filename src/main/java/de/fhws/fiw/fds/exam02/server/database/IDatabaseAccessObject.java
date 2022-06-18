

package de.fhws.fiw.fds.exam02.server.database;

import de.fhws.fiw.fds.exam02.server.database.models.AbstractModel;
import de.fhws.fiw.fds.exam02.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.exam02.server.database.results.NoContentResult;
import de.fhws.fiw.fds.exam02.server.database.results.SingleModelResult;

import java.util.function.Predicate;

public interface IDatabaseAccessObject<T extends AbstractModel>
{
	NoContentResult create(final T model );

	SingleModelResult<T> readById(final long id );

	CollectionModelResult<T> readByPredicate( final Predicate<T> predicate );

	NoContentResult update( final T model );

	NoContentResult delete( final long id );
}
