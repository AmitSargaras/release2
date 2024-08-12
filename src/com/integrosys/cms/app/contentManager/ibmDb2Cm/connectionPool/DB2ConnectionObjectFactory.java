package com.integrosys.cms.app.contentManager.ibmDb2Cm.connectionPool;

import org.apache.commons.pool.PoolableObjectFactory;

import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.contentManager.ibmDb2Cm.utilities.ContentManagerHelper;

public class DB2ConnectionObjectFactory implements PoolableObjectFactory {

	public void activateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	public void destroyObject(Object arg0) throws Exception {
		if (arg0 instanceof DKDatastoreICM) {
			DKDatastoreICM connection = (DKDatastoreICM)arg0;
			DefaultLogger.debug(this, "Connection Object Destroyed : " + connection);
	        connection.disconnect();
	        connection.destroy();
		}
	}

	public Object makeObject() throws Exception {
		DefaultLogger.debug(this,"makeObject method getting Created : ");
		// Creating Object Factory
		Object o =  ContentManagerHelper.initializeReadOnlyDatastore();
		DefaultLogger.debug(this,"Connection Object Created : " + o);
		return o;
	}

	public void passivateObject(Object arg0) throws Exception {
		
	}

	public boolean validateObject(Object arg0) {
		return false;
	}

}
