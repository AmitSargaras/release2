package com.integrosys.cms.app.cersaiMapping.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface ICersaiMappingBusManager {

	SearchResult getAllCersaiMapping()throws CersaiMappingException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	SearchResult getAllCersaiMapping(String mastername)throws CersaiMappingException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	ICersaiMapping createCersaiMapping(ICersaiMapping stagingCersaiMapping);

	ICersaiMapping getCersaiMappingById(long stagingPK) throws CersaiMappingException, TrxParameterException, TransactionException;

	ICersaiMapping updateCersaiMapping(ICersaiMapping item) throws CersaiMappingException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
