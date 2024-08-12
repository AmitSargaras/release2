package com.integrosys.base.uiinfra.common;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class StartupDeleteSession {

	private SessionJdbcDao sessionJdbcDao;

	public void setSessionJdbcDao(SessionJdbcDao sessionJdbcDao) {
		this.sessionJdbcDao = sessionJdbcDao;
	}

	public SessionJdbcDao getSessionJdbcDao() {
		return sessionJdbcDao;
	}

	/**
	 * Method to delete from Session table in DB
	 */
	public void clearSession() {

		String clustering = PropertyManager.getValue("clustering.enabled", "false");
		System.out.println("###############################################################################");
		System.out.println("##### [StartupDeleteSession] Clustering flag set to: " + clustering);

		if (clustering.equals("false")) {
			int num = getSessionJdbcDao().clearSession();

			System.out.println("##### [StartupDeleteSession] NO of session records deleted :" + num);
		}

	}
}