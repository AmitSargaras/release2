package com.integrosys.cms.app.geography.city.bus;

import java.io.Serializable;
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
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface ICityDAO {
	
	public final static String ACTUAL_ENTITY_NAME_CITY = "actualCity";
	
	public final static String STAGING_CITY_ENTITY_NAME = "stagingCity";

	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	public boolean isCityCodeUnique(String cityCode);
	
	public boolean isCityNameUnique(String cityName,long stateId);
	
	public boolean checkInActiveStates(ICity city);
	
	public ICity createCity(String entityName,ICity city) throws NoSuchGeographyException;
		
	public SearchResult listCity(String type, String text) throws NoSuchGeographyException;
	
	public List getCountryList(long countryId) throws NoSuchGeographyException;
	
	public List getRegionList(long stateId) throws NoSuchGeographyException;
	
	public List getStateList(long stateId) throws NoSuchGeographyException;
	
	public List getCityList(long stateId) throws NoSuchGeographyException;

	public ICity deleteCity(ICity city) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	ICity getCityById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	ICity getCity(String entity,long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	ICity updateCity(ICity city) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	ICity createCity(ICity city)throws NoSuchGeographyException;
	
//	public ICityTrxValue makerUpdateSaveCreateCity(ICity anICCCity)throws NoSuchGeographyException, TrxParameterException,TransactionException;
//	
//	public ICityTrxValue makerUpdateSaveUpdateCity(ICity anICCCity)throws NoSuchGeographyException, TrxParameterException,TransactionException;

	/*	Methods for File Upload */
	
	public ICity getCityByCityCode(String cityCode);
	
	IFileMapperId insertCity(String entityName, IFileMapperId fileId, ICityTrxValue trxValue) throws NoSuchGeographyException;
	
	int insertCity(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws NoSuchGeographyException;
	
	IFileMapperId getInsertFileList(String entityName, Serializable key) throws NoSuchGeographyException;
	
	ICity insertActualCity(String sysId) throws NoSuchGeographyException;
	
	ICity insertCity(String entityName, ICity holiday) throws NoSuchGeographyException;
	
	List getAllStageCity (String searchBy, String login) throws NoSuchGeographyException;
	
	List getFileMasterList(String searchBy) throws NoSuchGeographyException;
	
	boolean isPrevFileUploadPending() throws NoSuchGeographyException;	
	
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException;  //A shiv 170811

	//Added By Anil
	List getCityByCountryCode(String countryCode) throws NoSuchGeographyException;
	
	public long getCityByCityId(String cityCode)throws Exception;
}
