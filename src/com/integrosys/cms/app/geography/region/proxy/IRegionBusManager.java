package com.integrosys.cms.app.geography.region.proxy;

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
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IRegionBusManager {
	
	public boolean isRegionCodeUnique(String regionCode);
	
	public boolean isRegionNameUnique(String regionName,long countryId);
	
//	public SearchResult getRegionList(String type, String text) throws NoSuchGeographyException;
	
	public boolean checkActiveState(IRegion region);
	
	public boolean checkInActiveCountries(IRegion region);
	
	public SearchResult getRegionList(String type,String text) throws NoSuchGeographyException;
	
	public List getCountryList(long countryId) throws NoSuchGeographyException;
	
	public IRegion getRegionById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegion updateRegion(IRegion region) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IRegion updateToWorkingCopy(IRegion workingCopy, IRegion imageCopy) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IRegion createRegion(IRegion region)throws NoSuchGeographyException, TrxParameterException, TransactionException;
	
	public IRegion deleteRegion(IRegion region) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegion makerUpdateSaveCreateRegion(IRegion anICCRegion)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public IRegion makerUpdateSaveUpdateRegion(IRegion anICCRegion)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	/*	Methods for File Upload */
	
	boolean isPrevFileUploadPending() throws NoSuchGeographyException;
	
	int insertRegion(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws NoSuchGeographyException;
	
	IFileMapperId insertRegion(IFileMapperId fileId, IRegionTrxValue idxTrxValue)throws NoSuchGeographyException;
	
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws NoSuchGeographyException;
	
	IFileMapperId getInsertFileById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	List getAllStageRegion(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IRegion insertActualRegion(String sysId)throws NoSuchGeographyException;
	
	IRegion insertRegion(IRegion holiday)throws NoSuchGeographyException;
	
	public ICountry getCountryByCountryCode(String countryCode);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
