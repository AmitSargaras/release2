package com.integrosys.cms.app.contentManager.factory;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.service.ContentManagerService;

public class ContentManagerFactory {
	
	private static ContentManagerService contentManagerService = null;
	
	private static void intializeContentManager() throws ContentManagerInitializationException {
		try {
			Object object = PropertyManager.getValue("contentManager");
			if (object == null) {
				throw new ContentManagerInitializationException("Error Initalizing Content Manager. Unable to read property contentManager");
			}
			String contentManagerClass = (String) object;
			Class serviceClass = Class.forName(contentManagerClass);
			contentManagerService = (ContentManagerService) serviceClass.newInstance();
		} catch (Exception e) {
			throw new ContentManagerInitializationException("Error Initalizing Content Manager. Unable to instantiate contentManager");
		}
	}
	
	public static ContentManagerService getContentManagerService() throws ContentManagerInitializationException {
		if (contentManagerService == null) {
			intializeContentManager();
		}
		return contentManagerService;
	}
}
