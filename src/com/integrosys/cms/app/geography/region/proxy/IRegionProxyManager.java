package com.integrosys.cms.app.geography.region.proxy;

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
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IRegionProxyManager {

	public boolean isRegionCodeUnique(String regionCode);
	
	public boolean isRegionNameUnique(String regionName,long countryId);
	
//	public SearchResult listRegion(String type, String text) throws NoSuchGeographyException;

	public boolean checkActiveState(IRegion region);
	
	public boolean checkInActiveCountries(IRegion region);
	
	public SearchResult listRegion(String type,String text) throws NoSuchGeographyException;
	
	public List getCountryList(long countryId) throws NoSuchGeographyException;
	
	public IRegionTrxValue makerCreateRegion(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue, IRegion anRegion) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public IRegionTrxValue getRegionById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public IRegion createRegion(IRegion region) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	 
	public IRegion updateRegion(IRegion region) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IRegionTrxValue makerUpdateRegion(ITrxContext anITrxContext, IRegionTrxValue anICCRegionTrxValue, IRegion anICCRegion)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerEditRejectedRegion(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue, IRegion anRegion) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue getRegionTrxValue(long aRegionId) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerDeleteRegion(ITrxContext anITrxContext, IRegionTrxValue anICCRegionTrxValue, IRegion anICCRegion)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerActivateRegion(ITrxContext anITrxContext, IRegionTrxValue anICCRegionTrxValue, IRegion anICCRegion)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue getRegionByTrxID(String aTrxID) throws NoSuchGeographyException,TransactionException,CommandProcessingException;
	
	public IRegionTrxValue checkerApproveRegion(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue checkerRejectRegion(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerCloseRejectedRegion(ITrxContext anITrxContext, IRegionTrxValue anISystemBankTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerUpdateSaveCreateRegion(ITrxContext anITrxContext,IRegionTrxValue anICCRegionTrxValue,IRegion anICCRegion)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerUpdateSaveUpdateRegion(ITrxContext anITrxContext,IRegionTrxValue anICCRegionTrxValue,IRegion anICCRegion)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerSaveRegion(ITrxContext anITrxContext, IRegion anICRegion)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	/*	Methods for File Upload */
	
	public boolean isPrevFileUploadPending() throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerInsertMapperRegion(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public int insertRegion(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue getInsertFileByTrxID(String aTrxID) throws NoSuchGeographyException,TransactionException,CommandProcessingException;
	
	public List getAllStage(String searchBy, String login) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue checkerApproveInsertRegion(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public List getFileMasterList(String searchBy) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegion insertActualRegion(String sysId) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IRegionTrxValue checkerCreateRegion(ITrxContext anITrxContext,IRegion anICCRegion, String refStage)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue checkerRejectInsertRegion(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegionTrxValue makerInsertCloseRejectedRegion(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public ICountry getCountryByCountryCode(String countryCode);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
}
