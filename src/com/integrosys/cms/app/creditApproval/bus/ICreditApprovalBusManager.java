
package com.integrosys.cms.app.creditApproval.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * <p>
 * Interface of business manager for Credit Approval
 * 
 * <p>
 * <b>NOTE</b> All calling to persistence storage should come through here, be
 * it JDBC, ORM, or even Entity Bean.
 * 

 * @author govind.sahu
 * @since 2011/04/08
 */
public interface ICreditApprovalBusManager {
	
	
	
	
	/**
	 * get CreditApproval List
	 * @return The Credit Approval having the CreditApproval or <code>null</code>.
	 * @throws CreditApprovalException on errors.
	 */
	public List getCreditApprovalList() throws CreditApprovalException;
	
	
	/**
	 * get CreditApproval boolean
	 * @return The list feed entry having the CreditApproval List 
	 * @throws CreditApprovalException on errors.
	 */
	public boolean getCheckCreditApprovalUniquecode(String appCode) throws CreditApprovalException;
	
	
	/**
	 * Gets all the entries in the CreditApproval.
	 * @param id Identifies the Credit Approval.
	 * @return The containing all the entries.
	 * @throws CreditApprovalException when there are errors in getting the
	 *         entries.
	 */
	public ICreditApproval updateStatusCreditApproval(ICreditApproval actual) throws CreditApprovalException;
	
	
	
	/**
	 * Gets all the entries in the Credit Approval.
	 * @param id Identifies the Credit Approval.
	 * @return The containing all the entries.
	 * @throws CreditApprovalException when there are errors in getting the
	 *         entries.
	 */
	public ICreditApproval getCreditApprovalEntry(long id) throws CreditApprovalException;

	
	/**
	 * Gets all the entries in the Credit Approval.
	 * @param  Identifies the Credit Approval.
	 * @return The containing all the entries.
	 * @throws CreditApprovalException when there are errors in getting the
	 *         entries.
	 */
	public ICreditApproval getCreditApprovalEntry(String id) throws CreditApprovalException;


	/**
	 * Creates the  Credit Approval with all the entries.
	 * @param The CreditApproval to be created.
	 * @return The created Credit Approval.
	 * @throws CreditApprovalException when there are errors in creating the
	 *         group.
	 */
	public ICreditApproval createCreditApproval(ICreditApproval creditApproval) throws CreditApprovalException;

	

	public ICreditApproval updateToWorkingCopy(ICreditApproval workingCopy, ICreditApproval creditApprovalCopy)
	throws CreditApprovalException;
	
	public List getAllCreditApproval(String searchBy,String searchText) throws CreditApprovalException,TrxParameterException,TransactionException;

	
	boolean isPrevFileUploadPending() throws CreditApprovalException;
	int insertCreditApproval(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList, long countryId)throws CreditApprovalException;
	IFileMapperId insertCreditApproval(IFileMapperId fileId, ICreditApprovalTrxValue idxTrxValue)throws CreditApprovalException;
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws CreditApprovalException;
	IFileMapperId getInsertFileById(long id) throws CreditApprovalException,TrxParameterException,TransactionException;
	List getAllStageCreditApproval(String searchBy, String login)throws CreditApprovalException,TrxParameterException,TransactionException;
	List getFileMasterList(String searchBy)throws CreditApprovalException,TrxParameterException,TransactionException;
	ICreditApproval insertActualCreditApproval(String sysId)throws CreditApprovalException;
	ICreditApproval insertCreditApproval(ICreditApproval creditApproval)throws CreditApprovalException;
	
	public long getCountryIdForCountry(String countryName);
	
	public boolean isRegionCodeVaild(String regionCode,long countryId);
	
	public boolean isCreditApprovalNameUnique(String creditApprovalName);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public List getRegionList(String countryCode);	
	
	public boolean isCreditEmployeeIdUnique(String employeeId);
}
