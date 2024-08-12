package com.integrosys.cms.app.geography.state.proxy;

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
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IStateProxyManager {
	
	public boolean isStateCodeUnique(String stateCode);
	
	public boolean isStateNameUnique(String stateName,long countryId);
	
	public SearchResult listState(String type, String text) throws NoSuchGeographyException;

	public boolean checkActiveCities(IState state);
	
	public boolean checkInActiveRegions(IState state);
	
	public List getStateList(long stateId) throws NoSuchGeographyException;
	
	public List getCountryList(long countryId) throws NoSuchGeographyException;
	
	public List getRegionList(long countryId) throws NoSuchGeographyException;
	
	public IStateTrxValue makerCreateState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue, IState anState) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public IStateTrxValue getStateById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public IState createState(IState state) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	 
	public IState updateState(IState state) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IStateTrxValue makerUpdateState(ITrxContext anITrxContext, IStateTrxValue anICCStateTrxValue, IState anICCState)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue makerEditRejectedState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue, IState anState) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue getStateTrxValue(long aStateId) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue makerDeleteState(ITrxContext anITrxContext, IStateTrxValue anICCStateTrxValue, IState anICCState)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue makerActivateState(ITrxContext anITrxContext, IStateTrxValue anICCStateTrxValue, IState anICCState)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue getStateByTrxID(String aTrxID) throws NoSuchGeographyException,TransactionException,CommandProcessingException;
	
	public IStateTrxValue checkerApproveState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue checkerRejectState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue makerCloseRejectedState(ITrxContext anITrxContext, IStateTrxValue anISystemBankTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue makerUpdateSaveCreateState(ITrxContext anITrxContext,IStateTrxValue anICCStateTrxValue,IState anICState)throws NoSuchGeographyException, TrxParameterException,TransactionException;
	
	public IStateTrxValue makerUpdateSaveUpdateState(ITrxContext anITrxContext,IStateTrxValue anICCCityTrxValue,IState anICState)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IStateTrxValue makerSaveState(ITrxContext anITrxContext, IState anICState)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	/*	Methods for File Upload */
		
	public boolean isPrevFileUploadPending() throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue makerInsertMapperState(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public int insertState(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue getInsertFileByTrxID(String aTrxID) throws NoSuchGeographyException,TransactionException,CommandProcessingException;
	
	public List getAllStage(String searchBy, String login) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue checkerApproveInsertState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public List getFileMasterList(String searchBy) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IState insertActualState(String sysId) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IStateTrxValue checkerCreateState(ITrxContext anITrxContext,IState anICCState, String refStage)throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue checkerRejectInsertState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IStateTrxValue makerInsertCloseRejectedState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public IRegion getRegionByRegionCode(String regionCode);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
