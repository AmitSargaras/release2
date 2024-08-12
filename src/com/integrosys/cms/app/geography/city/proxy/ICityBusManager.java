package com.integrosys.cms.app.geography.city.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface ICityBusManager {
	
	public boolean isCityCodeUnique(String cityCode);
	
	public boolean isCityNameUnique(String cityName,long stateId);
	
	public SearchResult listCity(String type, String text) throws NoSuchGeographyException;

	public boolean checkInActiveStates(ICity city);
	
	public List getCountryList(long countryId) throws NoSuchGeographyException;
	
	public List getRegionList(long stateId) throws NoSuchGeographyException;
	
	public List getStateList(long stateId) throws NoSuchGeographyException;
	
	public List getCityList(long stateId) throws NoSuchGeographyException;
	
	
	public ICity getCityById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICity updateCity(ICity city) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICity updateToWorkingCopy(ICity workingCopy, ICity imageCopy) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public ICity createCity(ICity city)throws NoSuchGeographyException, TrxParameterException, TransactionException;
	
	public ICity deleteCity(ICity city) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICity makerUpdateSaveCreateCity(ICity anICCCity)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public ICity makerUpdateSaveUpdateCity(ICity anICCCity)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	/*	Methods for File Upload */
	
	boolean isPrevFileUploadPending() throws NoSuchGeographyException;
	
	int insertCity(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws NoSuchGeographyException;
	
	IFileMapperId insertCity(IFileMapperId fileId, ICityTrxValue idxTrxValue)throws NoSuchGeographyException;
	
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws NoSuchGeographyException;
	
	IFileMapperId getInsertFileById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	List getAllStageCity(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException;

	ICity insertActualCity(String sysId)throws NoSuchGeographyException;
	
	ICity insertCity(ICity holiday)throws NoSuchGeographyException;
	
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException; //A shiv 170811
	
	//Added by Anil
	public List getCityByCountryCode(String countryCode) throws NoSuchGeographyException;

}
