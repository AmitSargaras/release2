package com.integrosys.cms.app.cersaiMapping.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

public interface ICersaiMappingProxyManager {


	//ICersaiMappingTrxValue makerSaveCersaiMapping(OBTrxContext ctx, ICersaiMapping cersaiMapping) throws CersaiMappingException, TrxParameterException, TransactionException;
	
	
	public SearchResult getAllActualCersaiMapping() throws CersaiMappingException,TrxParameterException,TransactionException;
	
	public SearchResult getAllActualCersaiMapping(String mastername) throws CersaiMappingException,TrxParameterException,TransactionException;
	
	public ICersaiMappingTrxValue makerCreateCersaiMapping(ITrxContext anITrxContext, ICersaiMapping anICCCersaiMapping)throws CersaiMappingException,TrxParameterException,TransactionException;
	
	public ICersaiMappingTrxValue getCersaiMappingByTrxID(String aTrxID) throws CersaiMappingException,TransactionException,CommandProcessingException;
	
	public ICersaiMappingTrxValue checkerApproveCersaiMapping(ITrxContext anITrxContext, ICersaiMappingTrxValue anICersaiMappingTrxValue) throws CersaiMappingException,TrxParameterException,TransactionException;
	
	public boolean isCersaiCodeUnique(String cersaiCode);
	
	public ICersaiMappingTrxValue makerUpdateSaveUpdateCersaiMapping(ITrxContext anITrxContext, ICersaiMappingTrxValue anICCCersaiMappingTrxValue, ICersaiMapping anICCCersaiMapping)
			throws CersaiMappingException,TrxParameterException,TransactionException;
	
	public ICersaiMappingTrxValue makerSaveCersaiMapping(ITrxContext anITrxContext, ICersaiMapping anICCCersaiMapping)throws CersaiMappingException,TrxParameterException,TransactionException;
	//added by santosh
	public ICersaiMappingTrxValue getCersaiMappingTrxValue(long cersaiCode)
			throws CersaiMappingException,TransactionException,CommandProcessingException;

	public ICersaiMappingTrxValue makerUpdateCersaiMapping(OBTrxContext ctx, ICersaiMappingTrxValue trxValueIn,
			OBCersaiMapping cersaiMapping)throws CersaiMappingException,TrxParameterException,TransactionException;
	
	public ICersaiMappingTrxValue checkerRejectCersaiMapping(ITrxContext ctx, ICersaiMappingTrxValue trxValueIn)
			throws CersaiMappingException,TrxParameterException,TransactionException;

	public ICersaiMappingTrxValue makerEditRejectedCersaiMapping(ITrxContext ctx, ICersaiMappingTrxValue trxValueIn,ICersaiMapping anICCCersaiMapping)
			throws CersaiMappingException,TrxParameterException,TransactionException;

	public ICersaiMappingTrxValue makerCloseRejectedCersaiMapping(ITrxContext ctx, ICersaiMappingTrxValue trxValueIn)
			throws CersaiMappingException,TrxParameterException,TransactionException;

	public ICersaiMappingTrxValue makerUpdateSaveCreateCersaiMapping(ITrxContext ctx, ICersaiMappingTrxValue trxValueIn,ICersaiMapping anICCCersaiMapping)
			throws CersaiMappingException,TrxParameterException,TransactionException;
	
	public SearchResult getAllFilteredActualCersaiMapping(String code,String name)
			throws CersaiMappingException,TrxParameterException,TransactionException;
}
