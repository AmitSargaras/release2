package com.integrosys.cms.app.valuationAgency.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;

public class ValuationAgencyBusManagerImpl extends AbstractValuationAgencyBusManager implements IValuationAgencyBusManager {
	
	IValuationAgencyDao valuationDao;	

	public IValuationAgencyDao getValuationDao() {
		return valuationDao;
	}

	public void setValuationDao(IValuationAgencyDao valuationDao) {
		this.valuationDao = valuationDao;
	}

	public List getAllValuationAgency() {
		return getValuationDao().getAllValuationAgency();
//		return getValuationJdbc().getAllValuationAgency();

	}
	
	public List getFilteredValuationAgency(String code,String name) {
		return getValuationDao().getFilteredValuationAgency(code,name);
//		return getValuationJdbc().getAllValuationAgency();

	}

	public String getValuationAgencyName() {
		return IValuationAgencyDao.ACTUAL_VALUATION_AGENCY_NAME;
	}

	public IValuationAgency updateToWorkingCopy(IValuationAgency workingCopy,
			IValuationAgency imageCopy) throws ValuationAgencyException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		IValuationAgency updated;
		try {
			workingCopy.setAddress(imageCopy.getAddress());
			workingCopy.setCityTown(imageCopy.getCityTown());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setState(imageCopy.getState());
			workingCopy.setRegion(imageCopy.getRegion());
			workingCopy.setMasterId(imageCopy.getMasterId());
			workingCopy.setCountry(imageCopy.getCountry());
			workingCopy.setValuationAgencyCode(imageCopy
					.getValuationAgencyCode());
			workingCopy.setValuationAgencyName(imageCopy
					.getValuationAgencyName());
			workingCopy.setStatus(imageCopy.getStatus());
			updated = updateValuationAgency(workingCopy);
			return updateValuationAgency(updated);
		} catch (Exception e) {
			throw new ValuationAgencyException(
					"Error while Copying copy to main file");
		}

	}
	
	public boolean isVACodeUnique(String vaCode){
		 return getValuationDao().isVACodeUnique(vaCode);
	 }
	
	public List getCountryList(long countryId) throws ValuationAgencyException {
		return getValuationDao().getCountryList(countryId);
	}

	public List getCityList(long stateId) throws ValuationAgencyException {
		return getValuationDao().getCityList(stateId);
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ValuationAgencyException, TrxParameterException,TransactionException {
		getValuationDao().deleteTransaction(obFileMapperMaster);			
	}

	public boolean isValuationNameUnique(String valuationName) throws ValuationAgencyException, TrxParameterException,TransactionException {
		return getValuationDao().isValuationNameUnique(valuationName);
	}

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws ValuationAgencyException,TrxParameterException, TransactionException {
		return getValuationDao().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
}
