package com.integrosys.cms.app.cersaiMapping.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingBusManager;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;

public class CersaiMappingBusManagerImpl extends AbstractCersaiMappingBusManager implements ICersaiMappingBusManager  {
	
	@Override
	public String getCersaiMappingName() {
		return ICersaiMappingDao.ACTUAL_CERSAI_MAPPING_NAME;
	}

	@Override
	public SearchResult getAllCersaiMapping()
			throws CersaiMappingException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getCersaiMappingJdbc().getAllCersaiMapping();
	}
	
	public SearchResult getAllCersaiMapping(String mastername)
			throws CersaiMappingException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getCersaiMappingJdbc().getAllCersaiMapping(mastername);
	}

}
