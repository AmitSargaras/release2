package com.integrosys.cms.app.excludedfacility.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

public class ExcludedFacilityDAOFactory {

	/**
	 * Get the checklist DAO
	 * @return IChecklistDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static IExcludedFacilityDao getExcludedFacilityDAO() throws SearchDAOException {
		return new ExcludedFacilityDaoImpl();
	}
	
	public static IExcludedFacilityJdbc getExcludedFacilityJDBC() throws SearchDAOException {
		return new ExcludedFacilityJdbcImpl();
	}
}
