/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.collateralNewMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * @author  Abhijit R. 
 */
public interface ICollateralNewMasterJdbc {
	SearchResult getAllCollateralNewMaster (String searchBy,String searchText)throws CollateralNewMasterException;
	SearchResult getAllCollateralNewMaster()throws CollateralNewMasterException;
	SearchResult getFilteredCollateral(String collateralCode,String collateralDescription,String collateralMainType,String collateralSubType)throws CollateralNewMasterException;
	SearchResult getAllCollateralNewMasterBySecSubType(String secSubType)throws CollateralNewMasterException;
	List getAllCollateralNewMasterSearch(String login)throws CollateralNewMasterException;
	ICollateralNewMaster listCollateralNewMaster(long branchCode)throws CollateralNewMasterException;
	

}
