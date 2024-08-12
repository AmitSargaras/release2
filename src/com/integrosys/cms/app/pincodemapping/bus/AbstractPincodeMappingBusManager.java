package com.integrosys.cms.app.pincodemapping.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractPincodeMappingBusManager implements IPincodeMappingBusManager {

	abstract public String getPincodeMappingName();

	private IPincodeMappingDao pincodeMappingDao;

	private IPincodeMappingJdbc pincodeMappingJdbc;

	public IPincodeMappingDao getPincodeMappingDao() {
		return pincodeMappingDao;
	}

	public void setPincodeMappingDao(IPincodeMappingDao pincodeMappingDao) {
		this.pincodeMappingDao = pincodeMappingDao;
	}

	public IPincodeMappingJdbc getPincodeMappingJdbc() {
		return pincodeMappingJdbc;
	}

	public void setPincodeMappingJdbc(IPincodeMappingJdbc pincodeMappingJdbc) {
		this.pincodeMappingJdbc = pincodeMappingJdbc;
	}

	/**
	 * @return PincodeMapping Object after create
	 * 
	 */

	public IPincodeMapping createPincodeMapping(IPincodeMapping pincodeMapping) throws PincodeMappingException {
		if (!(pincodeMapping == null)) {

			return getPincodeMappingDao().createPincodeMapping(getPincodeMappingName(), pincodeMapping);
		} else {
			throw new PincodeMappingException("ERROR- Pincode Mapping object   is null. ");
		}
	}

	/**
	 * @return PincodeMapping Object after update
	 * 
	 */
	public IPincodeMapping updatePincodeMapping(IPincodeMapping item)
			throws PincodeMappingException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		try {
			return getPincodeMappingDao().updatePincodeMapping(getPincodeMappingName(),item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new PincodeMappingException(
					"Current PincodeMapping [" + item + "] was updated before by [" + item.getPincode() + "]");
		}

	}

	public IPincodeMapping deletePincodeMapping(IPincodeMapping item)
			throws PincodeMappingException, TrxParameterException,
			TransactionException {
		try {
			return getPincodeMappingDao().deletePincodeMapping(getPincodeMappingName(),
					item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new PincodeMappingException("current incodeMapping ");
		}
	}
	
	public IPincodeMapping getPincodeMappingById(long id)
			throws PincodeMappingException, TrxParameterException, TransactionException {
		if (id != 0) {
			return getPincodeMappingDao().getPincodeMapping(getPincodeMappingName(), new Long(id));
		} else {
			throw new PincodeMappingException("ERROR-- Key for Object Retrival is null.");
		}
	}
	public boolean isStateIdUnique(String stateId,String pincode){
		return getPincodeMappingDao().isStateIdUnique(stateId,pincode);
	 }
	
	/**
	 * @return the ArrayList
	 */
	public List getStateList() throws PincodeMappingException{
		return (List)getPincodeMappingDao().getStateList();
	}
}
