package com.integrosys.cms.ui.geography.state;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.AbstractStateBusManager;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.geography.state.proxy.IStateBusManager;

public class StateBusManagerImpl extends AbstractStateBusManager implements IStateBusManager{

	private IStateDAO stateDAO;

	public IStateDAO getStateDAO() {
		return stateDAO;
	}

	public void setStateDAO(IStateDAO stateDAO) {
		this.stateDAO = stateDAO;
	}
	
	public String getStateName(){
		return IStateDAO.ACTUAL_ENTITY_NAME_STATE;
	}
	
	public IState createState(IState country)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		return getStateDAO().createState(country);
	}

	public IState deleteState(IState country)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		return getStateDAO().deleteState(country);
	}

	public IState getStateById(long id) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		return getStateDAO().getStateById(id);
	}

	public SearchResult listState(String type, String text)	throws NoSuchGeographyException {
		return getStateDAO().listState(type,text);
	}

	public IState updateState(IState State)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		return getStateDAO().updateState(State);
	}

	public IState updateToWorkingCopy(IState workingCopy, IState imageCopy)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		IState updated;
		try {
			workingCopy.setStateName(imageCopy.getStateName());
			workingCopy.setStateCode(imageCopy.getStateCode());			
			workingCopy.setRegionId(imageCopy.getRegionId());
			updated = updateState(workingCopy);
		} catch (NoSuchGeographyException e) {
			throw new NoSuchGeographyException(
					"Error while Copying copy to main file");
		}
		return updateState(updated);
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

	public IState makerUpdateSaveCreateState(IState state)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		return getStateDAO().createState(state);
	}

	public IState makerUpdateSaveUpdateState(IState state)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		return getStateDAO().updateState(state);
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

	public IRegion getRegionByRegionCode(String regionCode) {
		return getStateDAO().getRegionByRegionCode(regionCode);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getStateDAO().deleteTransaction(obFileMapperMaster);		
	}
}
