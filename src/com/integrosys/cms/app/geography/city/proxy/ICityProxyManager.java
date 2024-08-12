package com.integrosys.cms.app.geography.city.proxy;

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
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 15-04-2011
	 */

public interface ICityProxyManager {
	
	public boolean isCityCodeUnique(String cityCode);
	
	public boolean isCityNameUnique(String cityName,long stateId);
	
	public SearchResult listCity(String type, String text) throws NoSuchGeographyException;

//	public SearchResult listRegion() throws NoSuchGeographyException;

	public boolean checkInActiveStates(ICity city);
	
	public List getRegionList(long stateId) throws NoSuchGeographyException;
	
	public List getStateList(long stateId) throws NoSuchGeographyException;
	
	public List getCityList(long stateId) throws NoSuchGeographyException;
	
	public List getCountryList(long countryId) throws NoSuchGeographyException;
	
	public ICityTrxValue makerCreateCity(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue, ICity anCity) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public ICityTrxValue getCityById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public ICity createCity(ICity city) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	 
	public ICity updateCity(ICity city) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICityTrxValue makerUpdateCity(ITrxContext anITrxContext, ICityTrxValue anICCCityTrxValue, ICity anICCCity)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue makerEditRejectedCity(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue, ICity anCity) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue getCityTrxValue(long aCityId) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue makerDeleteCity(ITrxContext anITrxContext, ICityTrxValue anICCCityTrxValue, ICity anICCCity)throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public ICityTrxValue makerActivateCity(ITrxContext anITrxContext, ICityTrxValue anICCCityTrxValue, ICity anICCCity)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue getCityByTrxID(String aTrxID) throws NoSuchGeographyException,TransactionException,CommandProcessingException;
	
	public ICityTrxValue checkerApproveCity(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue checkerRejectCity(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue makerCloseRejectedCity(ITrxContext anITrxContext, ICityTrxValue anISystemBankTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue makerSaveCity(ITrxContext anITrxContext, ICity anICCity)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue makerUpdateSaveCreateCity(ITrxContext anITrxContext,ICityTrxValue anICCCityTrxValue,ICity anICCCity)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public ICityTrxValue makerUpdateSaveUpdateCity(ITrxContext anITrxContext,ICityTrxValue anICCCityTrxValue,ICity anICCCity)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	/*	Methods for File Upload */
		
	public boolean isPrevFileUploadPending() throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue makerInsertMapperCity(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public int insertCity(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue getInsertFileByTrxID(String aTrxID) throws NoSuchGeographyException,TransactionException,CommandProcessingException;
	
	public List getAllStage(String searchBy, String login) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue checkerApproveInsertCity(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public List getFileMasterList(String searchBy) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICity insertActualCity(String sysId) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICityTrxValue checkerCreateCity(ITrxContext anITrxContext,ICity anICCCity, String refStage)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue checkerRejectInsertCity(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICityTrxValue makerInsertCloseRejectedCity(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;  //A shiv 170811

	//Added by Anil
	public List getCityByCountryCode(String countryCode) throws NoSuchGeographyException;
}
