package com.integrosys.cms.app.systemBank.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;


/**
 *@author $Author: Abhijit R $
 * Abstract System Bank Bus manager 
 */
public abstract class AbstractSystemBankBusManager implements ISystemBankBusManager {

	private ISystemBankDao systemBankDao;

	private ISystemBankJdbc systemBankJdbc;

	public ISystemBankDao getSystemBankDao() {
		return systemBankDao;
	}

	public void setSystemBankDao(ISystemBankDao systemBankDao) {
		this.systemBankDao = systemBankDao;
	}

	public ISystemBankJdbc getSystemBankJdbc() {
		return systemBankJdbc;
	}

	public void setSystemBankJdbc(ISystemBankJdbc systemBankJdbc) {
		this.systemBankJdbc = systemBankJdbc;
	}
	public abstract String getSystemBankName();
		

	/**
	  * @return Particular System Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public ISystemBank getSystemBankById(long id) throws SystemBankException,TrxParameterException,TransactionException  {
		if(id!=0){
		return  getSystemBankDao().getSystemBank(getSystemBankName(),new Long(id));
		}else{
			throw new SystemBankException("ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return SystemBank Object after create
	 * 
	 */
	
	public ISystemBank createSystemBank(ISystemBank systemBank)throws SystemBankException {
		if(!(systemBank==null)){
		return getSystemBankDao().createSystemBank(getSystemBankName(), systemBank);
		}else{
			throw new SystemBankException("ERROR- System Bank object   is null. ");
		}
	}
	/**
	 @return SystemBank Object after update
	 * 
	 */
	public ISystemBank updateSystemBank(ISystemBank item) throws SystemBankException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		try {
			return getSystemBankDao().updateSystemBank(getSystemBankName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new SystemBankException("Current SystemBank [" + item + "] was updated before by ["
					+ item.getSystemBankCode() + "] at [" + item.getSystemBankName() + "]");
		}
		
	}
	
	/**
	 * @return List of all authorized System Bank
	 */
	
	
	
	public List getAllSystemBank() {
		 return getSystemBankDao().getAllSystemBank();
	}

}