package com.integrosys.cms.app.productMaster.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

public interface IProductMasterProxyManager {

	public SearchResult getAllActualProductMaster() throws ProductMasterException,TrxParameterException,TransactionException;
	
	public IProductMasterTrxValue makerCreateProductMaster(ITrxContext anITrxContext, IProductMaster anICCProductMaster)throws ProductMasterException,TrxParameterException,TransactionException;
	
	public IProductMasterTrxValue getProductMasterByTrxID(String aTrxID) throws ProductMasterException,TransactionException,CommandProcessingException;
	
	public IProductMasterTrxValue checkerApproveProductMaster(ITrxContext anITrxContext, IProductMasterTrxValue anIProductMasterTrxValue) throws ProductMasterException,TrxParameterException,TransactionException;
	
	public boolean isProductCodeUnique(String productCode);
	
	public IProductMasterTrxValue makerUpdateSaveUpdateProductMaster(ITrxContext anITrxContext, IProductMasterTrxValue anICCProductMasterTrxValue, IProductMaster anICCProductMaster)
			throws ProductMasterException,TrxParameterException,TransactionException;
	
	public IProductMasterTrxValue makerSaveProductMaster(ITrxContext anITrxContext, IProductMaster anICCProductMaster)throws ProductMasterException,TrxParameterException,TransactionException;
	//added by santosh
	public IProductMasterTrxValue getProductMasterTrxValue(long productCode)
			throws ProductMasterException,TransactionException,CommandProcessingException;

	public IProductMasterTrxValue makerUpdateProductMaster(OBTrxContext ctx, IProductMasterTrxValue trxValueIn,
			OBProductMaster productMaster)throws ProductMasterException,TrxParameterException,TransactionException;
	
	public IProductMasterTrxValue checkerRejectProductMaster(ITrxContext ctx, IProductMasterTrxValue trxValueIn)
			throws ProductMasterException,TrxParameterException,TransactionException;

	public IProductMasterTrxValue makerEditRejectedProductMaster(ITrxContext ctx, IProductMasterTrxValue trxValueIn,IProductMaster anICCProductMaster)
			throws ProductMasterException,TrxParameterException,TransactionException;

	public IProductMasterTrxValue makerCloseRejectedProductMaster(ITrxContext ctx, IProductMasterTrxValue trxValueIn)
			throws ProductMasterException,TrxParameterException,TransactionException;

	public IProductMasterTrxValue makerUpdateSaveCreateProductMaster(ITrxContext ctx, IProductMasterTrxValue trxValueIn,IProductMaster anICCProductMaster)
			throws ProductMasterException,TrxParameterException,TransactionException;
	
	public IProductMasterTrxValue makerCloseDraftProductMaster(ITrxContext ctx, IProductMasterTrxValue trxValueIn)
			throws ProductMasterException,TrxParameterException,TransactionException;
	
	public SearchResult getAllFilteredActualProductMaster(String code,String name)
			throws ProductMasterException,TrxParameterException,TransactionException;
}
