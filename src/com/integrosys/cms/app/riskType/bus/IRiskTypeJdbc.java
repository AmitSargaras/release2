package com.integrosys.cms.app.riskType.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IRiskTypeJdbc {

	SearchResult getAllRiskType()throws RiskTypeException;
	SearchResult getAllFilteredRiskType(String code,String name)
			throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	//List<OBRiskType> getValuationByRamRatingOfCAM(long collateralId);
}
