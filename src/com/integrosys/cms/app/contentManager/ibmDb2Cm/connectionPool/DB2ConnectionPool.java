package com.integrosys.cms.app.contentManager.ibmDb2Cm.connectionPool;
 
import org.apache.commons.pool.impl.GenericObjectPool;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;


public class DB2ConnectionPool extends GenericObjectPool {

	private static DB2ConnectionPool pool;
	
	// Initialize the Content Manager Pool
	private DB2ConnectionPool(DB2ConnectionObjectFactory arg0) {
		super(arg0);
	    this.setMaxIdle(Integer.valueOf(PropertyManager.getValue("maxIdle")).intValue());
	    this.setMaxActive(Integer.valueOf(PropertyManager.getValue("maxActive")).intValue()); 
	    this.setTimeBetweenEvictionRunsMillis(Integer.valueOf(PropertyManager.getValue("timeBetweenEvictionRunsMillis")).intValue());
	    this.setMinEvictableIdleTimeMillis(Integer.valueOf(PropertyManager.getValue("minEvictableIdleTimeMillis")).intValue());
	    this.setMaxWait(Integer.valueOf(PropertyManager.getValue("maxWait")).intValue());
	}

	// Retrieve an object from the DB2 Content Manager ConnectionPool
	public Object borrowObject() throws Exception {
		DefaultLogger.debug(this,"Borrowing Connection from CMPool.");
		Object o = super.borrowObject();
		DefaultLogger.debug(this,"Retrieved Connection from CMPool. Active Connections -- > " + this.getNumActive() +" Idle Connections -- > " + this.getNumIdle());
		return o;
	}
	
	// Return an object to the DB2 Content Manager ConnectionPool
	public void returnObject(Object arg0) throws Exception {
		super.returnObject(arg0);
		DefaultLogger.debug(this,"Retrieved Connection from CMPool. Active Connections -- > " + this.getNumActive() +" Idle Connections -- > " + this.getNumIdle());
	}
	
	public static DB2ConnectionPool getConnectionPoolInstance() {
		if (pool == null) {
			pool = new DB2ConnectionPool(new DB2ConnectionObjectFactory());
		}
		return pool;
	}
	
}
