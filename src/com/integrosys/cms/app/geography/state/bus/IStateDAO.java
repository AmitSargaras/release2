package com.integrosys.cms.app.geography.state.bus;

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
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IStateDAO {
	
	public final static String ACTUAL_ENTITY_NAME_STATE = "actualState";
	
	public final static String STAGING_STATE_ENTITY_NAME = "stagingState";
	
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	public boolean isStateCodeUnique(String stateCode);
	
	public boolean isStateNameUnique(String stateName,long countryId);
	
	public boolean checkActiveCities(IState state);
	
	public boolean checkInActiveRegions(IState state);
	
	public IState createState(String entityName,IState state) throws NoSuchGeographyException;
		
	public SearchResult listState(String type, String text) throws NoSuchGeographyException;
	
	public List getCountryList(long stateId) throws NoSuchGeographyException;
	
	public List getStateList(long stateId) throws NoSuchGeographyException;

	public List getRegionList(long countryId) throws NoSuchGeographyException;
	
	public IState deleteState(IState state) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IState getStateById(long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IState getState(String entity,long id) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	IState updateState(IState state) throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IState createState(IState state)throws NoSuchGeographyException;
	
	IState getStateByStateCode(String stateCode);
	
	/*	Methods for File Upload */
	
	IFileMapperId insertState(String entityName, IFileMapperId fileId, IStateTrxValue trxValue) throws NoSuchGeographyException;
	
	int insertState(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws NoSuchGeographyException;
	
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws NoSuchGeographyException;
	
	IState insertActualState(String sysId) throws NoSuchGeographyException;
	
	IState insertState(String entityName, IState holiday) throws NoSuchGeographyException;
	
	List getAllStageState (String searchBy, String login) throws NoSuchGeographyException;
	
	List getFileMasterList(String searchBy) throws NoSuchGeographyException;
	
	boolean isPrevFileUploadPending() throws NoSuchGeographyException;

	public IRegion getRegionByRegionCode(String regionCode);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public long getStateByStateId(String stateCode)throws Exception;
}
