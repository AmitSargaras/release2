package com.integrosys.cms.app.insurancecoverage.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
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
public interface IInsuranceCoverageBusManager {
	
	public SearchResult getInsuranceCoverageList(String icCode,String companyName) throws InsuranceCoverageException;
	
	public SearchResult getInsuranceCoverageDtlsList(long id) throws InsuranceCoverageDtlsException;
	
	public boolean isICCodeUnique(String rmCode);
	
	public IInsuranceCoverage deleteInsuranceCoverage(IInsuranceCoverage relationshipMgr) throws InsuranceCoverageException,TrxParameterException,TransactionException;
	
	IInsuranceCoverage getInsuranceCoverageById(long id) throws InsuranceCoverageException,TrxParameterException,TransactionException;
	
	IInsuranceCoverage updateInsuranceCoverage(IInsuranceCoverage item) throws InsuranceCoverageException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IInsuranceCoverage updateToWorkingCopy(IInsuranceCoverage workingCopy, IInsuranceCoverage imageCopy) throws InsuranceCoverageException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IInsuranceCoverage createInsuranceCoverage(IInsuranceCoverage systemBank)throws InsuranceCoverageException;
	
	public boolean isCompanyNameUnique(String companyName);
	
	boolean isPrevFileUploadPending() throws InsuranceCoverageException;
	int insertInsuranceCoverage(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws InsuranceCoverageException;
	IFileMapperId insertInsuranceCoverage(IFileMapperId fileId, IInsuranceCoverageTrxValue idxTrxValue)throws InsuranceCoverageException;
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws InsuranceCoverageException;
	IFileMapperId getInsertFileById(long id) throws InsuranceCoverageException,TrxParameterException,TransactionException;
	List getAllStageInsuranceCoverage(String searchBy, String login)throws InsuranceCoverageException,TrxParameterException,TransactionException;
	List getFileMasterList(String searchBy)throws InsuranceCoverageException,TrxParameterException,TransactionException;
	IInsuranceCoverage insertActualInsuranceCoverage(String sysId)throws InsuranceCoverageException;
	IInsuranceCoverage insertInsuranceCoverage(IInsuranceCoverage holiday)throws InsuranceCoverageException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
