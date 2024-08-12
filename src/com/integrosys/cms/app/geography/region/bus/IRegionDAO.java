package com.integrosys.cms.app.geography.region.bus;

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
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IRegionDAO {
	
	public final static String ACTUAL_ENTITY_NAME_REGION = "actualRegion";
	
	public final static String STAGING_REGION_ENTITY_NAME = "stagingRegion";
	
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	public boolean isRegionCodeUnique(String regionCode);
	
	public boolean isRegionNameUnique(String regionName,long countryId);
	
	public boolean checkActiveState(IRegion region);
	
	public boolean checkInActiveCountries(IRegion region);
	
	public IRegion createRegion(String entityName,IRegion geography) throws NoSuchGeographyException;
		
//	public SearchResult listRegion(String type, String text) throws NoSuchGeographyException;
	
	public List getCountryList(long countryId) throws NoSuchGeographyException;

	public SearchResult listRegion(String type,String text) throws NoSuchGeographyException;
	
	public IRegion deleteRegion(IRegion country) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IRegion getRegionById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IRegion getRegion(String entity,long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IRegion updateRegion(IRegion region) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IRegion createRegion(IRegion region)throws NoSuchGeographyException;
	
	public IRegion getRegionByRegionCode(String regionCode) ;
	
	/*	Methods for File Upload */
	
	IFileMapperId insertRegion(String entityName, IFileMapperId fileId, IRegionTrxValue trxValue) throws NoSuchGeographyException;
	
	int insertRegion(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws NoSuchGeographyException;
	
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws NoSuchGeographyException;
	
	IRegion insertActualRegion(String sysId) throws NoSuchGeographyException;
	
	IRegion insertRegion(String entityName, IRegion holiday) throws NoSuchGeographyException;
	
	List getAllStageRegion (String searchBy, String login) throws NoSuchGeographyException;
	
	List getFileMasterList(String searchBy)throws NoSuchGeographyException;
	
	boolean isPrevFileUploadPending() throws NoSuchGeographyException;

	public ICountry getCountryByCountryCode(String countryCode);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public long getRegionByRegionId(String regionCode)throws Exception;
}
