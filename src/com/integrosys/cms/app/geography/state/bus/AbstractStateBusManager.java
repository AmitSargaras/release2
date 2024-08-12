package com.integrosys.cms.app.geography.state.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.proxy.IStateBusManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */
public abstract class AbstractStateBusManager implements IStateBusManager{
	
	private IStateDAO stateDAO;
	
	public IStateDAO getStateDAO() {
		return stateDAO;
	}

	public void setStateDAO(IStateDAO stateDAO) {
		this.stateDAO = stateDAO;
	}

	/**
	 * Gets Entity Name Region 
	 * @return
	 */
	public abstract String getStateName();
	
	public IState getStateById(long id) throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getStateDAO().getState(getStateName(), id);
	}
	
	public IState getState(String entity,long id)
		throws NoSuchGeographyException, TrxParameterException,
		TransactionException {
	return getStateDAO().getState(getStateName(), id);
	}

	public IState createState(IState state)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		return getStateDAO().createState(getStateName(),state);
	}
	
	public IState updateState(IState state)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
	return getStateDAO().updateState(state);
	}
	
	public List getStateList(long stateId) throws NoSuchGeographyException {
		return getStateDAO().getStateList(stateId);
	}
	
	public List getCountryList(long stateId) throws NoSuchGeographyException {
		return getStateDAO().getCountryList(stateId);
	}
	
	public List getRegionList(long countryId) throws NoSuchGeographyException {
		return getStateDAO().getRegionList(countryId);
	}
	
	public SearchResult listState(String type, String text)throws NoSuchGeographyException {
		return getStateDAO().listState(type, text);
	}
	
	public IState makerUpdateSaveCreateState(IState city)throws NoSuchGeographyException, TrxParameterException,TransactionException{
		return getStateDAO().createState(getStateName(),city);
	}
		
	public IState makerUpdateSaveUpdateState(IState anICCState)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException {
		return getStateDAO().updateState(anICCState);
	}
	

    /**
	 * This method returns exception as staging country can never be working copy
	 */

    public IState updateToWorkingCopy(IState workingCopy, IState imageCopy)throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/**
	 * @return the Country details
	 */
	public IState deleteState(IState state) throws NoSuchGeographyException , TrxParameterException, TransactionException{
		return getStateDAO().deleteState(state);	
	}
	
	public boolean checkActiveCities(IState state) {
		return getStateDAO().checkActiveCities(state);
	}
	
	public boolean checkInActiveRegions(IState state) {
		return getStateDAO().checkInActiveRegions(state);
	}
	
	public boolean isStateCodeUnique(String stateCode) {
		return getStateDAO().isStateCodeUnique(stateCode);
	}
	
	public boolean isStateNameUnique(String stateName,long countryId) {
		return getStateDAO().isStateNameUnique(stateName,countryId);
	}
	
//############################ File Upload ##########################
	
	public boolean isPrevFileUploadPending()
	throws NoSuchGeographyException {
		try {
			return getStateDAO().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}

	public int insertState(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws NoSuchGeographyException {
		try {
			return getStateDAO().insertState(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertState(
			IFileMapperId fileId, IStateTrxValue trxValue)
			throws NoSuchGeographyException {
		if (!(fileId == null)) {
			return getStateDAO().insertState(getStateName(), fileId, trxValue);
		} else {
			throw new NoSuchGeographyException(
					"ERROR- State object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws NoSuchGeographyException {
		if (!(fileId == null)) {
			return getStateDAO().createFileId(getStateName(), fileId);
		} else {
			throw new NoSuchGeographyException(
					"ERROR- State object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getStateDAO().getInsertFileList(
					getStateName(), new Long(id));
		} else {
			throw new NoSuchGeographyException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageState(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException {

		return getStateDAO().getAllStageState(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException {
		return getStateDAO().getFileMasterList(searchBy);
	}
	
	
	public IState insertActualState(String sysId) throws NoSuchGeographyException {
		try {
			return getStateDAO().insertActualState(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	
	public IState insertState(IState state)throws NoSuchGeographyException {
		if (!(state == null)) {
			return getStateDAO().insertState("actualState", state);
		} else {
			throw new NoSuchGeographyException(
					"ERROR-  Relationshp Manager object is null. ");
		}
	}
	
	public IRegion getRegionByRegionCode(String regionCode) {
		return getStateDAO().getRegionByRegionCode(regionCode);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getStateDAO().deleteTransaction(obFileMapperMaster);		
	}
}
