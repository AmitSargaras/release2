package com.integrosys.cms.app.riskType.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeException;
import com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

public interface IRiskTypeProxyManager {

	public SearchResult getAllActualRiskType() throws RiskTypeException,TrxParameterException,TransactionException;
	
	public IRiskTypeTrxValue makerCreateRiskType(ITrxContext anITrxContext, IRiskType anICCRiskType)throws RiskTypeException,TrxParameterException,TransactionException;
	
	public IRiskTypeTrxValue getRiskTypeByTrxID(String aTrxID) throws RiskTypeException,TransactionException,CommandProcessingException;
	
	public IRiskTypeTrxValue checkerApproveRiskType(ITrxContext anITrxContext, IRiskTypeTrxValue anIRiskTypeTrxValue) throws RiskTypeException,TrxParameterException,TransactionException;
	
	//public boolean isProductCodeUnique(String productCode);
	
	public IRiskTypeTrxValue makerUpdateSaveUpdateRiskType(ITrxContext anITrxContext, IRiskTypeTrxValue anICCRiskTypeTrxValue, IRiskType anICCRiskType)
			throws RiskTypeException,TrxParameterException,TransactionException;
	
	public IRiskTypeTrxValue makerSaveRiskType(ITrxContext anITrxContext, IRiskType anICCRiskType)throws RiskTypeException,TrxParameterException,TransactionException;
	
	public IRiskTypeTrxValue getRiskTypeTrxValue(long productCode)
			throws RiskTypeException,TransactionException,CommandProcessingException;

	public IRiskTypeTrxValue makerUpdateRiskType(OBTrxContext ctx, IRiskTypeTrxValue trxValueIn,
			OBRiskType riskType)throws RiskTypeException,TrxParameterException,TransactionException;
	
	public IRiskTypeTrxValue checkerRejectRiskType(ITrxContext ctx, IRiskTypeTrxValue trxValueIn)
			throws RiskTypeException,TrxParameterException,TransactionException;

	public IRiskTypeTrxValue makerEditRejectedRiskType(ITrxContext ctx, IRiskTypeTrxValue trxValueIn,IRiskType anICCRiskType)
			throws RiskTypeException,TrxParameterException,TransactionException;

	public IRiskTypeTrxValue makerCloseRejectedRiskType(ITrxContext ctx, IRiskTypeTrxValue trxValueIn)
			throws RiskTypeException,TrxParameterException,TransactionException;

	public IRiskTypeTrxValue makerUpdateSaveCreateRiskType(ITrxContext ctx, IRiskTypeTrxValue trxValueIn,IRiskType anICCRiskType)
			throws RiskTypeException,TrxParameterException,TransactionException;
	
	public IRiskTypeTrxValue makerCloseDraftRiskType(ITrxContext ctx, IRiskTypeTrxValue trxValueIn)
			throws RiskTypeException,TrxParameterException,TransactionException;
	
	public SearchResult getAllFilteredActualRiskType(String code,String name)
			throws RiskTypeException,TrxParameterException,TransactionException;

	public IRiskTypeTrxValue makerDeleteRiskType(ITrxContext ctx, IRiskTypeTrxValue trxValueIn,IRiskType anICCRiskType)
			throws RiskTypeException,TrxParameterException,TransactionException;
}
