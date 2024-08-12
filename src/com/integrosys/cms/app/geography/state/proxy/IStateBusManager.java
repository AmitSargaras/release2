package com.integrosys.cms.app.geography.state.proxy;

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
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IStateBusManager {
	
	public boolean isStateCodeUnique(String stateCode);
	
	public boolean isStateNameUnique(String stateName,long countryId);
	
	public boolean checkActiveCities(IState state);
	
	public boolean checkInActiveRegions(IState state);
	
	public SearchResult listState(String type, String text) throws NoSuchGeographyException;
	
	public List getStateList(long stateId) throws NoSuchGeographyException;
	
	public List getCountryList(long stateId) throws NoSuchGeographyException;
	
	public List getRegionList(long countryId) throws NoSuchGeographyException;
	
	public IState getStateById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IState updateState(IState state) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IState updateToWorkingCopy(IState workingCopy, IState imageCopy) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IState createState(IState state)throws NoSuchGeographyException, TrxParameterException, TransactionException;
	
	public IState deleteState(IState state) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IState makerUpdateSaveCreateState(IState anICCState)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public IState makerUpdateSaveUpdateState(IState anICCState)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	/*	Methods for File Upload */
	
	boolean isPrevFileUploadPending() throws NoSuchGeographyException;
	
	int insertState(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws NoSuchGeographyException;
	
	IFileMapperId insertState(IFileMapperId fileId, IStateTrxValue idxTrxValue)throws NoSuchGeographyException;
	
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws NoSuchGeographyException;
	
	IFileMapperId getInsertFileById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	List getAllStageState(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IState insertActualState(String sysId)throws NoSuchGeographyException;
	
	IState insertState(IState holiday)throws NoSuchGeographyException;
	
	public IRegion getRegionByRegionCode(String regionCode);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
