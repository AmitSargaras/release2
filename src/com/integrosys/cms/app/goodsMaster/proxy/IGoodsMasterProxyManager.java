package com.integrosys.cms.app.goodsMaster.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

public interface IGoodsMasterProxyManager {

	public SearchResult getAllActualGoodsMaster() throws GoodsMasterException,TrxParameterException,TransactionException;
	
	public IGoodsMasterTrxValue makerCreateGoodsMaster(ITrxContext anITrxContext, IGoodsMaster anICCGoodsMaster)throws GoodsMasterException,TrxParameterException,TransactionException;
	
	public IGoodsMasterTrxValue getGoodsMasterByTrxID(String aTrxID) throws GoodsMasterException,TransactionException,CommandProcessingException;
	
	public IGoodsMasterTrxValue checkerApproveGoodsMaster(ITrxContext anITrxContext, IGoodsMasterTrxValue anIGoodsMasterTrxValue) throws GoodsMasterException,TrxParameterException,TransactionException;
	
	public boolean isGoodsCodeUnique(String goodsCode);
	
	public IGoodsMasterTrxValue makerUpdateSaveUpdateGoodsMaster(ITrxContext anITrxContext, IGoodsMasterTrxValue anICCGoodsMasterTrxValue, IGoodsMaster anICCGoodsMaster)
			throws GoodsMasterException,TrxParameterException,TransactionException;
	
	public IGoodsMasterTrxValue makerSaveGoodsMaster(ITrxContext anITrxContext, IGoodsMaster anICCGoodsMaster)throws GoodsMasterException,TrxParameterException,TransactionException;
	//added by santosh
	public IGoodsMasterTrxValue getGoodsMasterTrxValue(String goodsCode)
			throws GoodsMasterException,TransactionException,CommandProcessingException;

	public IGoodsMasterTrxValue makerUpdateGoodsMaster(OBTrxContext ctx, IGoodsMasterTrxValue trxValueIn,
			OBGoodsMaster goodsMaster)throws GoodsMasterException,TrxParameterException,TransactionException;
	
	public IGoodsMasterTrxValue checkerRejectGoodsMaster(ITrxContext ctx, IGoodsMasterTrxValue trxValueIn)
			throws GoodsMasterException,TrxParameterException,TransactionException;

	public IGoodsMasterTrxValue makerEditRejectedGoodsMaster(ITrxContext ctx, IGoodsMasterTrxValue trxValueIn,IGoodsMaster anICCGoodsMaster)
			throws GoodsMasterException,TrxParameterException,TransactionException;

	public IGoodsMasterTrxValue makerCloseRejectedGoodsMaster(ITrxContext ctx, IGoodsMasterTrxValue trxValueIn)
			throws GoodsMasterException,TrxParameterException,TransactionException;

	public IGoodsMasterTrxValue makerUpdateSaveCreateGoodsMaster(ITrxContext ctx, IGoodsMasterTrxValue trxValueIn,IGoodsMaster anICCGoodsMaster)
			throws GoodsMasterException,TrxParameterException,TransactionException;
	
	public IGoodsMasterTrxValue makerCloseDraftGoodsMaster(ITrxContext ctx, IGoodsMasterTrxValue trxValueIn)
			throws GoodsMasterException,TrxParameterException,TransactionException;
	
	public SearchResult getAllFilteredActualGoodsMaster(String code,String name)
			throws GoodsMasterException,TrxParameterException,TransactionException;

}
