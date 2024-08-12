package com.integrosys.cms.app.insurancecoverage.bus;

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
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/** 
 * Defines methods for operation on Insurance Coverage
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * Tag : $Name$
 */

public interface IInsuranceCoverageDAO {
	
	public final static String ACTUAL_ENTITY_NAME = "actualInsuranceCoverage";
	
	public final static String STAGING_ENTITY_NAME = "stagingInsuranceCoverage";
	
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	
	public SearchResult getInsuranceCoverageList(String icCode,String companyName) throws InsuranceCoverageException;
	
	public ArrayList getInsuranceDetailList(Long colId,String compnent) throws InsuranceCoverageException;
	
	public SearchResult getInsuranceCoverageDtlsList(long id) throws InsuranceCoverageDtlsException;
	
	public boolean isICCodeUnique(String rmCode);

	public SearchResult getInsuranceCoverage() throws InsuranceCoverageException;
	
	public IInsuranceCoverage getInsuranceCoverageById(long id) throws InsuranceCoverageException ;
	 
	public IInsuranceCoverage updateInsuranceCoverage(IInsuranceCoverage relationshipMgr) throws InsuranceCoverageException;
	
	public IInsuranceCoverage deleteInsuranceCoverage(IInsuranceCoverage relationshipMgr) throws InsuranceCoverageException;
	
	public IInsuranceCoverage createInsuranceCoverage(IInsuranceCoverage relationshipMgr) throws InsuranceCoverageException;
	
	IInsuranceCoverage getInsuranceCoverage(String entityName, Serializable key)
		throws InsuranceCoverageException;

	IInsuranceCoverage updateInsuranceCoverage(String entityName, IInsuranceCoverage item)
		throws InsuranceCoverageException;

	IInsuranceCoverage createInsuranceCoverage(String entityName, IInsuranceCoverage insuranceCoverage)
		throws InsuranceCoverageException;
	
	public boolean isCompanyNameUnique(String companyName);
	
	IFileMapperId insertInsuranceCoverage(String entityName, IFileMapperId fileId, IInsuranceCoverageTrxValue trxValue)
	throws InsuranceCoverageException;
	int insertInsuranceCoverage(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws InsuranceCoverageException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws InsuranceCoverageException;
	IInsuranceCoverage insertActualInsuranceCoverage(String sysId)
	throws InsuranceCoverageException;
	IInsuranceCoverage insertInsuranceCoverage(String entityName, IInsuranceCoverage holiday)
	throws InsuranceCoverageException;
	List getAllStageInsuranceCoverage (String searchBy, String login)throws InsuranceCoverageException;
	List getFileMasterList(String searchBy)throws InsuranceCoverageException;
	boolean isPrevFileUploadPending() throws InsuranceCoverageException;

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public List getInsuranceCoverageList() throws InsuranceCoverageException;
	public String getInsuranceCompanyName(String icCode) throws InsuranceCoverageException;
}
