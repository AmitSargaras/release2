package com.integrosys.cms.app.otherbank.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/** 
 * Defines methods for operation on other bank
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 17:27:01 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public interface IOtherBankDAO {
	
	public final static String ACTUAL_ENTITY_NAME = "actualOtherBank";
	
	public final static String STAGING_ENTITY_NAME = "stagingOtherBank";
	
    static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	
	static final String CMS_FILE_MAPPER_ID ="fileMapper";
	
	public SearchResult getOtherBankList(String bankCode,String bankName) throws OtherBankException;
	public List<OBOtherBank> getOtherBankList(String bankCode,String bankName,String branchName,String branchCode) throws OtherBankException;
	public SearchResult  getInsurerList() throws OtherBankException;
	
	
	
	public List getCountryList();
	
	public List getRegionList(String countryId);
	
	public List getStateList(String regionId);
	
	public List getCityList(String stateId);
	
	public SearchResult getOtherBranchList(String branchCode,String branchName,String state,String city,long id) throws OtherBranchException ;

	public SearchResult getOtherBank() throws OtherBankException;
	
	public boolean checkOtherBranchById(long id) throws OtherBankException;
	
	public IOtherBank getOtherBankById(long id) throws OtherBankException ;
	 
	public IOtherBank updateOtherBank(IOtherBank OtherBank) throws OtherBankException;
	
	public IOtherBank deleteOtherBank(IOtherBank OtherBank) throws OtherBankException;
	
	public IOtherBank createOtherBank(IOtherBank OtherBank) throws OtherBankException;
	
	IOtherBank getOtherBank(String entityName, Serializable key)
		throws OtherBankException;

	IOtherBank updateOtherBank(String entityName, IOtherBank item)
		throws OtherBankException;

	IOtherBank createOtherBank(String entityName, IOtherBank systemBank)
		throws OtherBankException;
	
//**********************UPLOAD********************************
	
	int insertOtherBank(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);

	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws OtherBankException;
	
	IOtherBank insertOtherBank(String entityName, IOtherBank otherBank)	throws OtherBankException;
	
	IFileMapperId insertOtherBank(String entityName, IFileMapperId fileId, IOtherBankTrxValue trxValue)	throws OtherBankException;

	IFileMapperId getInsertFileList(String entityName, Serializable key)throws OtherBankException;
	
	public List getAllStageOtherBank(String searchBy, String login)throws OtherBankException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy);
	
	IOtherBank insertActualOtherBank(String sysId)	throws OtherBankException;
	
	boolean isUniqueCode(String branchCode) throws OtherBankException;
	
	boolean isUniqueName(String branchName) throws OtherBankException;
	
	public boolean isPrevFileUploadPending();
	//******************* Methods defined for Guarantee Security ******************
	
	public String getCityName(String cityId);
	public String getStateName(String stateId);
	public String getRegionName(String regionId);
	public String getCountryName(String countryId);
	
	public IOtherBank getOtherBankByCode(String bankCode) throws OtherBankException ;

	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws OtherBankException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	//by Rajib for PDC
	public IOtherBank getOtherBankListForPDC(String bankCode);
	
	public SearchResult getInsurerNameFromCode(String insurerName)throws  OtherBankException;
	public SearchResult getOtherBankList() throws OtherBankException;

}
