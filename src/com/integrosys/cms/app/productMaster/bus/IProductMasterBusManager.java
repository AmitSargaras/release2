package com.integrosys.cms.app.productMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;

public interface IProductMasterBusManager {

	SearchResult getAllProductMaster()throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	IProductMaster createProductMaster(IProductMaster productMaster)throws ProductMasterException;
	IProductMaster getProductMasterById(long id) throws ProductMasterException,TrxParameterException,TransactionException;
	IProductMaster updateProductMaster(IProductMaster item) throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public boolean isProductCodeUnique(String productCode);
	IProductMaster updateToWorkingCopy(IProductMaster workingCopy, IProductMaster imageCopy) throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	SearchResult getAllFilteredProductMaster(String code,String name)throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
