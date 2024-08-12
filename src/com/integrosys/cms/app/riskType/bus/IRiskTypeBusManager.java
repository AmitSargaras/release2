package com.integrosys.cms.app.riskType.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;

public interface IRiskTypeBusManager {

	SearchResult getAllRiskType()throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	IRiskType createRiskType(IRiskType valuationAmountAndRating)throws RiskTypeException;
	IRiskType getRiskTypeById(long id) throws RiskTypeException,TrxParameterException,TransactionException;
	IRiskType updateRiskType(IRiskType item) throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	//public boolean isProductCodeUnique(String productCode);
	IRiskType updateToWorkingCopy(IRiskType workingCopy, IRiskType imageCopy) throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	SearchResult getAllFilteredRiskType(String code,String name)throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	IRiskType deleteToWorkingCopy(IRiskType workingCopy, IRiskType imageCopy) throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
