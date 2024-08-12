package com.integrosys.cms.app.insurancecoveragedtls.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 *@author $Author: Dattatray Thorat $
 * Insurance Coverage Details Bus manager 
 */

public interface IInsuranceCoverageDtlsBusManager {
	
	public SearchResult getInsuranceCoverageDtlsList() throws InsuranceCoverageDtlsException;
	
	public boolean isICCodeUnique(String rmCode);
	
	public IInsuranceCoverageDtls deleteInsuranceCoverageDtls(IInsuranceCoverageDtls relationshipMgr) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException;
	
	IInsuranceCoverageDtls getInsuranceCoverageDtlsById(long id) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException;
	
	IInsuranceCoverageDtls updateInsuranceCoverageDtls(IInsuranceCoverageDtls item) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IInsuranceCoverageDtls updateToWorkingCopy(IInsuranceCoverageDtls workingCopy, IInsuranceCoverageDtls imageCopy) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IInsuranceCoverageDtls createInsuranceCoverageDtls(IInsuranceCoverageDtls systemBank)throws InsuranceCoverageDtlsException;
}
