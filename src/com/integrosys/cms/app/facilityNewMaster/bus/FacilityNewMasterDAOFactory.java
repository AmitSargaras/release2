/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListDAOFactory.java,v 1.7 2003/07/30 01:59:05 hltan Exp $
 */
package com.integrosys.cms.app.facilityNewMaster.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the checkList DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/07/30 01:59:05 $
 */

public class FacilityNewMasterDAOFactory {

    /**
	 * Get the checklist DAO
	 * @return IChecklistDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static IFacilityNewMasterDao getFacilityNewMasterDAO() throws SearchDAOException {
		return new FacilityNewMasterDaoImpl();
	}
	
	public static IFacilityNewMasterJdbc getFacilityNewMasterJDBC() throws SearchDAOException {
		return new FacilityNewMasterJdbcImpl();
	}


}
