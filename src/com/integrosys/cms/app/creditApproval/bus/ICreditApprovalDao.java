package com.integrosys.cms.app.creditApproval.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;

/**
 * interface of data access object for CreditApproval
 * 
 * @author Govind Sahu
 * @since 1.0
 */
public interface ICreditApprovalDao {

	/**
	 * entity name for OBCreditApproval stored in actual table
	 */
	public static final String ACTUAL_CREDIT_APPROVAL_ENTITY_NAME = "actualCreditApproval";


	/**
	 * entity name for OBCreditApproval stored in staging table
	 */
	public static final String STAGE_CREDIT_APPROVAL_ENTITY_NAME = "stageCreditApproval";

	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	/**
	 * Retrieve CreditApproval list
	 * 
	 * @return instance of CreditApproval list, else <code>null</code> will be
	 *         returned.
	 */
	public List getCreditApprovalList();
	
	/**
	 * Retrieve CreditApproval boolean
	 * 
	 * @return instance of CreditApproval boolean
	 *         returned.
	 */
	public boolean getCheckCreditApprovalUniquecode(String EntityName,String appCode);
	
	
	/**
	 * Retrieve CreditApproval using primary key provided
	 * 
	 * @param entityName the persistence entity name of CreditApproval feed entry, which
	 *        can be actual or staging name.
	 * @param key the primary key of CreditApproval feed entry, usually is
	 *        {@link java.lang.Long} type.
	 * @return the CreditApproval with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public ICreditApproval getCreditApprovalEntryByPrimaryKey(String entityName, Long key);

	/**
	 * Create CreditApproval into persistent storage
	 * 
	 * @param entityName the persistence entity name of CreditApproval feed entry, which
	 *        can be actual or staging name.
	 * @param aCreditApproval CreditApproval instance to be created
	 * @return created instance of CreditApproval feed entry.
	 */
	public ICreditApproval createCreditApproval(String entityName, ICreditApproval aCreditApproval);

	/**
	 * Update CreditApproval into persistent storage
	 * 
	 * @param entityName the persistence entity name of CreditApproval feed entry, which
	 *        can be actual or staging name.
	 * @param aCreditApproval CreditApproval instance to be updated
	 * @return updated instance of CreditApproval feed entry.
	 */
	public ICreditApproval updateCreditApproval(String entityName, ICreditApproval aCreditApproval);


	/**
	 * Retrieve CreditApproval feed group using group type code provided
	 * 
	 * @param entityName the persistence entity name of CreditApproval feed group, which
	 *        can be actual or staging name.
	 * @param groupType group type of the feed, normally is <code>CreditApproval</code>
	 * @return instance of CreditApproval feed group, else <code>null</code> will be
	 *         returned.
	 */
	public ICreditApproval getCreditApprovalEntryByEntryType(String entityName, String Key);

	/**
	 * Retrieve CreditApproval using CreditApprovalEntry obj
	 * 
	 * @param entityName the persistence entity name of CreditApproval feed entry, which
	 *        can be actual or staging name.
	 * @param CreditApproval feed entry.
	 * @return instance of CreditApproval feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public ICreditApproval updateStatusCreditApproval(String entityName, ICreditApproval obj);

	
	/**
	 * @return List of all authorized Credit Approval according to Search Criteria provided.
	 * 
	 */
	public List getAllCreditApproval(String EntityName, String searchTxtApprovalCode, String searchTxtApprovalName);
	
	
	boolean isPrevFileUploadPending() throws CreditApprovalException;
	List getAllStageCreditApproval (String searchBy, String login)throws CreditApprovalException;
	List getFileMasterList(String searchBy)throws CreditApprovalException;
	//ICreditApproval createCreditApproval(String entityName, ICreditApproval creditApproval)throws CreditApprovalException;
	IFileMapperId insertCreditApproval(String entityName, IFileMapperId fileId, ICreditApprovalTrxValue trxValue)
	throws CreditApprovalException;
	int insertCreditApproval(IFileMapperMaster fileMapperMaster, String userName, ArrayList result, long countryId);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws CreditApprovalException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws CreditApprovalException;
	ICreditApproval insertActualCreditApproval(String sysId)
	throws CreditApprovalException;
	ICreditApproval insertCreditApproval(String entityName, ICreditApproval creditApproval)
	throws CreditApprovalException;
	
	public long getCountryIdForCountry(String countryName);
	
	public boolean isRegionCodeVaild(String regionCode,long countryId);
	
	public boolean isCreditApprovalNameUnique(String creditApprovalName);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public List getRegionList(String countryCode);
	public String getCPSIdByApprovalCode(String entityName,String appCode);
	
	
	public boolean isCreditEmployeeIdUnique(String employeeId);
}
