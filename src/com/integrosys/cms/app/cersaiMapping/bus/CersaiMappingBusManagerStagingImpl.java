package com.integrosys.cms.app.cersaiMapping.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class CersaiMappingBusManagerStagingImpl extends AbstractCersaiMappingBusManager {

	@Override
	public String getCersaiMappingName() {
		// TODO Auto-generated method stub
		return ICersaiMappingDao.STAGE_CERSAI_MAPPING_NAME;
	}

	@Override
	public SearchResult getAllCersaiMapping()
			throws CersaiMappingException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getCersaiMappingJdbc().getAllCersaiMapping();
	}

	@Override
	public SearchResult getAllCersaiMapping(String mastername)
			throws CersaiMappingException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getCersaiMappingJdbc().getAllCersaiMapping(mastername);
	}
	
}
