package com.integrosys.cms.app.insurancecoveragedtls.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/** 
 * Defines methods for operation on Insurance Coverage Details
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * Tag : $Name$
 */

public interface IInsuranceCoverageDtlsDAO {
	
	public final static String ACTUAL_ENTITY_NAME = "actualInsuranceCoverageDtls";
	
	public final static String STAGING_ENTITY_NAME = "stagingInsuranceCoverageDtls";
	
	public SearchResult getInsuranceCoverageDtlsList() throws InsuranceCoverageDtlsException;
	
	public boolean isICCodeUnique(String rmCode);

	public SearchResult getInsuranceCoverageDtls() throws InsuranceCoverageDtlsException;
	
	public IInsuranceCoverageDtls getInsuranceCoverageDtlsById(long id) throws InsuranceCoverageDtlsException ;
	 
	public IInsuranceCoverageDtls updateInsuranceCoverageDtls(IInsuranceCoverageDtls relationshipMgr) throws InsuranceCoverageDtlsException;
	
	public IInsuranceCoverageDtls deleteInsuranceCoverageDtls(IInsuranceCoverageDtls relationshipMgr) throws InsuranceCoverageDtlsException;
	
	public IInsuranceCoverageDtls createInsuranceCoverageDtls(IInsuranceCoverageDtls relationshipMgr) throws InsuranceCoverageDtlsException;
	
	IInsuranceCoverageDtls getInsuranceCoverageDtls(String entityName, Serializable key)
		throws InsuranceCoverageDtlsException;

	IInsuranceCoverageDtls updateInsuranceCoverageDtls(String entityName, IInsuranceCoverageDtls item)
		throws InsuranceCoverageDtlsException;

	IInsuranceCoverageDtls createInsuranceCoverageDtls(String entityName, IInsuranceCoverageDtls insuranceCoverage)
		throws InsuranceCoverageDtlsException;

}
