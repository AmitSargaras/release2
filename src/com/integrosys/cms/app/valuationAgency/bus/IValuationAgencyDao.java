package com.integrosys.cms.app.valuationAgency.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;

public interface IValuationAgencyDao {

	static final String ACTUAL_VALUATION_AGENCY_NAME = "actualOBValuationAgency";

	static final String STAGE_VALUATION_AGENCY_NAME = "stageOBValuationAgency";
	
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	
	static final String CMS_FILE_MAPPER_ID ="fileMapper";

	IValuationAgency getValuationAgency(String entityName, Serializable key)
			throws ValuationAgencyException;

	IValuationAgency updateValuationAgency(String entityName,
			IValuationAgency item) throws ValuationAgencyException;

	IValuationAgency createValuationAgency(String entityName,
			IValuationAgency valuationAgency) throws ValuationAgencyException;

	IValuationAgency disableValuationAgency(String entityName,
			IValuationAgency item);

	IValuationAgency enableValuationAgency(String entityName,
			IValuationAgency item);
	
	public boolean isVACodeUnique(String rmCode);
	
	public List getCountryList(long countryId) throws ValuationAgencyException;
	public List getCityList(long stateId) throws ValuationAgencyException;
	
	public List getAllValuationAgency() throws ValuationAgencyException;
	
	public List getFilteredValuationAgency(String code,String name) throws ValuationAgencyException;
	//**********************UPLOAD********************************
	
	int insertValuationAgency(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);

	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws ValuationAgencyException;
	
	IValuationAgency insertValuationAgency(String entityName, IValuationAgency valuationAgency)	throws ValuationAgencyException;
	
	IFileMapperId insertValuationAgency(String entityName, IFileMapperId fileId, IValuationAgencyTrxValue trxValue)	throws ValuationAgencyException;

	IFileMapperId getInsertFileList(String entityName, Serializable key)throws ValuationAgencyException;
	
	public List getAllStageValuationAgency(String searchBy, String login)throws ValuationAgencyException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy);
	
	IValuationAgency insertActualValuationAgency(String sysId)	throws ValuationAgencyException;
	
	boolean isUniqueCode(String branchCode) throws ValuationAgencyException;
	
	public boolean isPrevFileUploadPending();

	public boolean isValuationNameUnique(String valuationName) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
//********** Addedy by Dattatray Thorat for Property - Commercial Security
	
	public String getValuationAgencyName(String companyId);

	}
