package com.integrosys.cms.app.goodsMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;

public interface IGoodsMasterBusManager {

	SearchResult getAllGoodsMaster()throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	IGoodsMaster createGoodsMaster(IGoodsMaster goodsMaster)throws GoodsMasterException;
	IGoodsMaster getGoodsMasterById(long id) throws GoodsMasterException,TrxParameterException,TransactionException;
	IGoodsMaster updateGoodsMaster(IGoodsMaster item) throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public boolean isGoodsCodeUnique(String goodsCode);
	IGoodsMaster updateToWorkingCopy(IGoodsMaster workingCopy, IGoodsMaster imageCopy) throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	SearchResult getAllFilteredGoodsMaster(String code,String name)throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
