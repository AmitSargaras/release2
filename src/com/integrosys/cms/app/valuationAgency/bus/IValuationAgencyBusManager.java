package com.integrosys.cms.app.valuationAgency.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;

public interface IValuationAgencyBusManager {

	List getAllValuationAgency();
	
	List getFilteredValuationAgency(String code,String name);

	IValuationAgency createValuationAgency(IValuationAgency valuationAgency)
			throws ValuationAgencyException;

	IValuationAgency getValuationAgencyById(long id)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException;

	IValuationAgency updateValuationAgency(IValuationAgency item)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException;

	IValuationAgency updateToWorkingCopy(IValuationAgency workingCopy,
			IValuationAgency imageCopy) throws ValuationAgencyException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException;

	IValuationAgency disableValuationAgency(IValuationAgency item)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException;

	IValuationAgency enableValuationAgency(IValuationAgency item)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException;
	
	public boolean isVACodeUnique(String rmCode);
	
	public List getCountryList(long countryId) throws ValuationAgencyException;
	public List getCityList(long stateId) throws ValuationAgencyException;
	
	//**********************FOR UPLOAD********************************
	boolean isPrevFileUploadPending() throws ValuationAgencyException;
	
	int insertValuationAgency(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws ValuationAgencyException;
	
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws ValuationAgencyException;
	
	IValuationAgency insertValuationAgency(IValuationAgency valuationAgency)throws ValuationAgencyException;
	
	IFileMapperId insertValuationAgency(IFileMapperId fileId, IValuationAgencyTrxValue idxTrxValue)throws ValuationAgencyException;
	
	
	IFileMapperId getInsertFileById(long id) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	List getAllStageValuationAgency(String searchBy, String login)throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	List getFileMasterList(String searchBy)throws ValuationAgencyException,TrxParameterException,TransactionException;
	IValuationAgency insertActualValuationAgency(String sysId)throws ValuationAgencyException;
	
	boolean isUniqueCode(String branchCode) throws ValuationAgencyException,TrxParameterException,TransactionException;

	public boolean isValuationNameUnique(String valuationName) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	//	********** Addedy by Dattatray Thorat for Property - Commercial Security
	
	public String getValuationAgencyName(String companyId);
}
