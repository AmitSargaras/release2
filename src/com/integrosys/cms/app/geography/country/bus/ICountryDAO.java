package com.integrosys.cms.app.geography.country.bus;

import java.io.Serializable;
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
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;

public interface ICountryDAO {
	
	public final static String ACTUAL_ENTITY_NAME_COUNTRY = "actualCountry";
	
	public final static String STAGING_COUNTRY_ENTITY_NAME = "stagingCountry";
	
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

//	public IGeography getGeographyByIndex(long id) throws NoSuchGeographyException;
	
	public boolean isCountryCodeUnique(String countryCode);
	
	public boolean isCountryNameUnique(String countryName);

	public boolean checkActiveRegion(ICountry country);
	
	public ICountry createCountry(String entityName,ICountry geography) throws NoSuchGeographyException;
		
	public SearchResult listCountry(String type, String text) throws NoSuchGeographyException;

	public ICountry deleteCountry(ICountry country) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	ICountry getCountryById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	ICountry getCountry(String entity,long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	ICountry updateCountry(ICountry country) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
//	ICountry updateToWorkingCopy(ICountry workingCopy, ICountry imageCopy) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	ICountry createCountry(ICountry country)throws NoSuchGeographyException;
			
	/*	Methods for File Upload */
	
	public ICountry getCountryByCountryCode(String countryCode);
	
	IFileMapperId insertCountry(String entityName, IFileMapperId fileId, ICountryTrxValue trxValue) throws CountryException;
	
	int insertCountry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws CountryException;
	
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws CountryException;
	
	ICountry insertActualCountry(String sysId) throws CountryException;
	
	ICountry insertCountry(String entityName, ICountry holiday) throws CountryException;
	
	List getAllStageCountry (String searchBy, String login)throws CountryException;
	
	List getFileMasterList(String searchBy)throws CountryException;
	
	boolean isPrevFileUploadPending() throws CountryException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public long getCountryByCountryId(String countryCode)throws Exception;

}
