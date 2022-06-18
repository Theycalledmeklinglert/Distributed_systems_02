
package de.fhws.fiw.fds.exam02.server.database.models;

import com.owlike.genson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable, Cloneable
{
	protected long id;

	private long primaryId;

	public long getId( )
	{
		return this.id;
	}

	public void setId( final long id )
	{
		this.id = id;
	}

	@JsonIgnore
	public long getPrimaryId( )
	{
		return primaryId;
	}

	@JsonIgnore
	public void setPrimaryId( final long primaryId )
	{
		this.primaryId = primaryId;
	}

	@Override public Object clone( ) throws CloneNotSupportedException
	{
		return super.clone( );
	}
}
