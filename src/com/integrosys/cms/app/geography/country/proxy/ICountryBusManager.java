package com.integrosys.cms.app.geography.country.proxy;

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
import com.integrosys.cms.app.geography.country.bus.CountryException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;

public interface ICountryBusManager {
	
	public boolean isCountryCodeUnique(String countryCode);
	
	public boolean isCountryNameUnique(String countryName);
	
	public boolean checkActiveRegion(ICountry country);
	
	public SearchResult getCountryList(String type, String text) throws NoSuchGeographyException;

	public ICountry deleteCountry(ICountry country) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountry getCountryById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountry updateCountry(ICountry country) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICountry updateToWorkingCopy(ICountry workingCopy, ICountry imageCopy) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICountry createCountry(ICountry country)throws NoSuchGeographyException, TrxParameterException, TransactionException;

	public ICountry makerUpdateSaveCreateCountry(ICountry anICCCountry)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public ICountry makerUpdateSaveUpdateCountry(ICountry anICCCountry)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	/*	Methods for File Upload */
	
	boolean isPrevFileUploadPending() throws CountryException;
	
	int insertCountry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws CountryException;
	
	IFileMapperId insertCountry(IFileMapperId fileId, ICountryTrxValue idxTrxValue)throws CountryException;
	
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws CountryException;
	
	IFileMapperId getInsertFileById(long id) throws CountryException,TrxParameterException,TransactionException;
	
	List getAllStageCountry(String searchBy, String login)throws CountryException,TrxParameterException,TransactionException;
	
	List getFileMasterList(String searchBy)throws CountryException,TrxParameterException,TransactionException;
	
	ICountry insertActualCountry(String sysId)throws CountryException;
	
	ICountry insertCountry(ICountry holiday)throws CountryException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
