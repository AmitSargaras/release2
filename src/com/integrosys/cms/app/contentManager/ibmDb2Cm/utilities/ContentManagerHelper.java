package com.integrosys.cms.app.contentManager.ibmDb2Cm.utilities;

import com.ibm.mm.sdk.common.DKException;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;

/**
 * This class contains the utilit/helper methods for IBM DB2 Content Manager.
 * Currently supports IBM DB2 Content Manager version 8.42 and 8.43.
 */
public class ContentManagerHelper {
	public static DKDatastoreICM initializeReadOnlyDatastore() throws ContentManagerInitializationException {
		DKDatastoreICM dsICM = null;
		try {
			DefaultLogger.debug("ContentManagerHelper","----- calling initializeReadOnlyDatastore() --------");
			// Load configuration file
			dsICM = new DKDatastoreICM(PropertyManager.getValue("configFile"));
			DefaultLogger.debug("ContentManagerHelper","dsICM Path ------- " + dsICM);
			// Load connection details
			String schema = PropertyManager.getValue("dms.default.database");
			// Test Connection (Optional)
			String userName =  PropertyManager.getValue("dms.readOnly1.userName");
			String userPassword =  PropertyManager.getValue("dms.readOnly1.password");
			
			DefaultLogger.debug("ContentManagerHelper","Connecting to  ------- " + userName + "/" + userPassword + "@" + schema);
			dsICM.connect(schema,userName,userPassword,""); 
			DefaultLogger.debug("ContentManagerHelper","Connected to datastore (Database '"+dsICM.datastoreName()+"', UserName '"+dsICM.userName()+"').");
	        if (dsICM == null) {
	        	throw new ContentManagerInitializationException("Error connecting to content manager datasource : Credentials Failed.");
	        }
		} 
		catch (DKException e) {
			e.printStackTrace();
			throw new ContentManagerInitializationException("Error connecting to content manager datasource.");
		} 
		catch (ContentManagerInitializationException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ContentManagerInitializationException("Error connecting to content manager datasource.");
		}
		return dsICM;
	}
	public static DKDatastoreICM initializeDatastore(String userName, String userPassword) throws ContentManagerInitializationException {
		DKDatastoreICM dsICM = null;
		try {
			// Load configuration file
			dsICM = new DKDatastoreICM(PropertyManager.getValue("configFile"));
			// Load connection details
			String schema = PropertyManager.getValue("dms.default.database");
			// Test Connection (Optional)
			dsICM.connect(schema,userName,userPassword,""); 
			DefaultLogger.debug("ContentManagerHelper","Connected to datastore (Database '"+dsICM.datastoreName()+"', UserName '"+dsICM.userName()+"').");
	        if (dsICM == null) {
	        	throw new ContentManagerInitializationException("Error connecting to content manager datasource : Credentials Failed.");
	        }
		} 
		catch (DKException e) {
			e.printStackTrace();
			throw new ContentManagerInitializationException("Error connecting to content manager datasource.");
		} 
		catch (ContentManagerInitializationException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ContentManagerInitializationException("Error connecting to content manager datasource.");
		}
		return dsICM;
	}
}
