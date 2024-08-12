package com.integrosys.cms.app.geography.country.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.CountryException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface ICountryProxyManager {

	/**Maker Checker Methods for Master Geography
	 */	

	public boolean isCountryCodeUnique(String countryCode);
	
	public boolean isCountryNameUnique(String countryName);
	
	public boolean checkActiveRegion(ICountry country);
	
	public SearchResult listCountry(String type, String text) throws NoSuchGeographyException;
	
	public ICountryTrxValue makerCreateCountry(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue, ICountry anCountry) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public ICountryTrxValue getCountryById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public ICountry createCountry(ICountry Country) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	 
	public ICountry updateCountry(ICountry Country) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICountry deleteCountry(ICountry Country) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerUpdateCountry(ITrxContext anITrxContext, ICountryTrxValue anICCCountryTrxValue, ICountry anICCCountry)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerEditRejectedCountry(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue, ICountry anCountry) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue getCountryTrxValue(long aCountryId) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerDeleteCountry(ITrxContext anITrxContext, ICountryTrxValue anICCCountryTrxValue, ICountry anICCCountry)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerActivateCountry(ITrxContext anITrxContext, ICountryTrxValue anICCCountryTrxValue, ICountry anICCCountry)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue getCountryByTrxID(String aTrxID) throws NoSuchGeographyException,TransactionException,CommandProcessingException;
	
	public ICountryTrxValue checkerApproveCountry(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue checkerRejectCountry(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerCloseRejectedCountry(ITrxContext anITrxContext, ICountryTrxValue anISystemBankTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerUpdateSaveCreateCountry(ITrxContext anITrxContext,ICountryTrxValue anICCCountryTrxValue,ICountry anICCCountry)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerUpdateSaveUpdateCountry(ITrxContext anITrxContext,ICountryTrxValue anICCCountryTrxValue,ICountry anICCCountry)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerSaveCountry(ITrxContext anITrxContext, ICountry anICCountry)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	/*	Methods for File Upload */
	
	public boolean isPrevFileUploadPending() throws CountryException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerInsertMapperCountry(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws CountryException,TrxParameterException,TransactionException;
	
	public int insertCountry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws CountryException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue getInsertFileByTrxID(String aTrxID) throws CountryException,TransactionException,CommandProcessingException;
	
	public List getAllStage(String searchBy, String login) throws CountryException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue checkerApproveInsertCountry(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue) throws CountryException,TrxParameterException,TransactionException;
	
	public List getFileMasterList(String searchBy) throws CountryException,TrxParameterException,TransactionException;
	
	public ICountry insertActualCountry(String sysId) throws CountryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICountryTrxValue checkerCreateCountry(ITrxContext anITrxContext,ICountry anICCCountry, String refStage)throws CountryException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue checkerRejectInsertCountry(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue) throws CountryException,TrxParameterException,TransactionException;
	
	public ICountryTrxValue makerInsertCloseRejectedCountry(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue) throws CountryException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
}
