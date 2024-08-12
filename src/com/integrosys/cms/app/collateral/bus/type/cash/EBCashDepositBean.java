/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/EBCashDepositBean.java,v 1.2 2003/08/20 05:54:22 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.common.BatchResourceFactory;

/**
 * Entity bean implementation for cash deposit.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/20 05:54:22 $ Tag: $Name: $
 */
public abstract class EBCashDepositBean implements ICashDeposit, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the equity. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getCashDepositID", "getRefID" };

	/**
	 * Get cash deposit id.
	 * 
	 * @return long
	 */
	/*public long getCashDepositID() {
		return getEBCashDepositID().longValue();
	}*/
	
	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get the legal ID
	 * 
	 * @return long
	 */
	public long getCashDepositID() {
		if (null != getEBCashDepositID()) {
			return getEBCashDepositID().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}	

	
	public ILienMethod[] getLien() {
		return null;
	}
	

	// Setters
	/**
	 * Set the LEID
	 * 
	 * @param value is of type long
	 */
	

	
	public void setLien(ILienMethod[] value) {
		// do nothing
	}
	

	// ********** Abstract Methods *************
	// Getters
	

	

	

	
	
/*********************************************************8/

	/**
	 * Set cash deposit id.
	 * 
	 * @param cashDepositID of type long
	 */
	public void setCashDepositID(long cashDepositID) {
		setEBCashDepositID(new Long(cashDepositID));
	}

	public boolean getOwnBank() {
		if (getIsOwnBankStr()!=null && getIsOwnBankStr().equals(ICMSConstant.TRUE_VALUE)) return true;
		return false;
	}
	
	public void setOwnBank(boolean ownBank) {
		if (ownBank) setIsOwnBankStr(ICMSConstant.TRUE_VALUE);
		else setIsOwnBankStr(ICMSConstant.FALSE_VALUE);
	}
	/**
	 * Get deposit amount.
	 * 
	 * @return Amount
	 */
	public Amount getDepositAmount() {
		return new Amount(getEBDepositAmount(), getDepositCcyCode());
	}

	/**
	 * Set deposit amount.
	 * 
	 * @param depositAmount of type Amount
	 */
	public void setDepositAmount(Amount depositAmount) {
		if (depositAmount != null) {
			setEBDepositAmount(depositAmount.getAmountAsDouble());
		}
	}
	
	public boolean getHasValidated() {
		return ICMSConstant.TRUE_VALUE.equals(getEBHasValidated());
	}
	
	public void setHasValidated(boolean hasValidate) {
		setEBHasValidated(hasValidate?ICMSConstant.TRUE_VALUE:ICMSConstant.FALSE_VALUE);
	}
	/**/
	public String getFlag(){
		return getEBFlag();
	}
	
	public void setFlag(String flag){
		setEBFlag(flag);
	}
	
	public String getMaker_id() {
		return getEBMaker_id();
}

    public void setMaker_id(String maker_id) {
		setEBMaker_id(maker_id);
}
    public String getChecker_id() {
		return getEBChecker_id();
}

    public void setChecker_id(String checker_id) {
		setEBChecker_id(checker_id);
}
    public Date getMaker_date() {
		return getEBMaker_date();
}

    public void setMaker_date(Date maker_date) {
		setEBMaker_date(maker_date);
}
    public Date getChecker_date() {
		return getEBChecker_date();
}
    
    public String getSearchFlag() {
		return getEBSearchFlag();
}

    public void setSearchFlag(String searchFlag) {
		setEBSearchFlag(searchFlag);
    }
    
    
    
    public void setChecker_date(Date checker_date) {
		setEBChecker_date(checker_date);
}
    
    public abstract String getEBFlag();
    
    public abstract void setEBFlag(String flag);
    
    public abstract String getEBSearchFlag();
    
    public abstract void setEBSearchFlag(String searchFlag);      
    
    public abstract String getEBMaker_id();

	public abstract void setEBMaker_id(String eBMaker_id);
	
	public abstract String getEBChecker_id();

	public abstract void setEBChecker_id(String eBChecker_id);
	
	public abstract Date getEBMaker_date();

	public abstract void setEBMaker_date(Date maker_date);
	
	public abstract Date getEBChecker_date();

	public abstract void setEBChecker_date(Date checker_date);

	
	
	
	
	
	/**/

	public abstract Long getEBCashDepositID();

	public abstract void setEBCashDepositID(Long eBCashDepositID);

	public abstract double getEBDepositAmount();

	public abstract void setEBDepositAmount(double eBDepositAmount);

	public abstract void setStatus(String status);
	
	public abstract String getEBHasValidated();
	
	public abstract void setEBHasValidated(String eBHasValidate);
	
	public abstract String getHoldStatus();

	public abstract void setHoldStatus(String holdStatus);
	
    public abstract Collection getCMRLien();
	
	public abstract void setCMRLien(Collection value);//cashDepositeIDCMR
	

	/**
	 * Get cash deposit
	 * 
	 * @return ICashDeposit
	 * @throws CollateralException 
	 */
	public ICashDeposit getValue() throws CollateralException {
		try {
			OBCashDeposit ob = new OBCashDeposit();
			AccessorUtil.copyValue(this, ob);
			ob.setLien(retrieveLien());
			return ob;
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CollateralException) {
				throw (CollateralException) e;
			}
			else {
				throw new CollateralException("Caught Exception: " + e.toString());
			}
		}
	}

	private ILienMethod[] retrieveLien() throws CollateralException {
		try {
			Collection c = getCMRLien();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLienLocal local = (EBLienLocal) i.next();
					ILienMethod ob = local.getValue();
					aList.add(ob);
				}

				return (ILienMethod[]) aList.toArray(new ILienMethod[0]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CollateralException) {
				throw (CollateralException) e;
			}
			else {
				throw new CollateralException("Caught Exception: " + e.toString());
			}
		}
	}
	
	private void deleteLien(List deleteList) throws CollateralException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRLien();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBLienLocal local = (EBLienLocal) i.next();
				c.remove(local);
				local.remove();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CollateralException) {
				throw (CollateralException) e;
			}
			else {
				throw new CollateralException("Caught Exception: " + e.toString());
			}
		}
	}
	private void createLien(List createList,long lienId) throws CollateralException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRLien();
		Iterator i = createList.iterator();
		try {
			EBLienLocalHome home = getEBLocalHomeLien();
			while (i.hasNext()) {
				ILienMethod ob = (ILienMethod) i.next();
				if(ob!=null){
				DefaultLogger.debug(this, "Creating Lien ID: " + ob.getLienID());
				String serverType = (new BatchResourceFactory()).getAppServerType();
				DefaultLogger.debug(this, "=======Application server Type is (Cash Deposit)======= : " + serverType);
				if(serverType.equals(ICMSConstant.WEBSPHERE))
				{
					ob.setCashDepositID(lienId);
				}
				EBLienLocal local = home.create(ob);
				c.add(local);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CollateralException) {
				throw (CollateralException) e;
			}
			else {
				throw new CollateralException("Caught Exception: " + e.toString());
			}
		}
	}
	protected EBLienLocalHome getEBLocalHomeLien() throws CollateralException {
		EBLienLocalHome home = (EBLienLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIEN_LOCAL_JNDI, EBLienLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CollateralException("EBLienLocalHome is null!");
		}
	}
	
	private void updateLien(ILienMethod[] addr,long LEID) throws CollateralException {
		try {
			Collection c = getCMRLien();

			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteLien(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				List leinLst = Arrays.asList(addr);
				// create new records
				createLien(leinLst,LEID);
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBLienLocal local = (EBLienLocal) i.next();

					long lienID = local.getLienID();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						ILienMethod newOB = addr[j];

						if (newOB.getLienID() == lienID) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				for (int j = 0; j < addr.length; j++) {
					i = c.iterator();
					ILienMethod newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBLienLocal local = (EBLienLocal) i.next();
						long id = local.getLienID();

						if (newOB.getLienID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteLien(deleteList);
				createLien(createList,LEID);
			}
		}
		catch (CollateralException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CollateralException("Caught Exception: " + e.toString());
		}
	}
	
	private void updateDependants(ICashDeposit value) throws CollateralException {
		
		updateLien(value.getLien(),value.getCashDepositID());
		
	}
	/**
	 * Set cash deposit.
	 * 
	 * @param deposit of type ICashDeposit
	 */
	public void setValue(ICashDeposit deposit) {
		AccessorUtil.copyValue(deposit, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param deposit of type ICashDeposit
	 * @return primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICashDeposit deposit) throws CreateException {
		try {
			String depositID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CASH_DEPOSIT, true);
			AccessorUtil.copyValue(deposit, this, EXCLUDE_METHOD);
			this.setEBCashDepositID(new Long(depositID));
			if (deposit.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getCashDepositID());
				
			}
			else {
				// else maintain this reference id.
				setRefID(deposit.getRefID());
				
			}
			
			
			
			
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param deposit of type ICashDeposit
	 */
	public void ejbPostCreate(ICashDeposit deposit) throws CreateException{
		try {
			updateLien(deposit.getLien(), deposit.getCashDepositID());
		} catch (CollateralException e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

    public abstract String getDepositReceiptNo();

    public abstract void setDepositReceiptNo(String depositReceiptNo);

    public abstract Date getDepositMaturityDate();

    public abstract void setDepositMaturityDate(Date depositMaturityDate);

    public abstract String getDepositCcyCode();

    public abstract void setDepositCcyCode(String depositCcyCode);

    public abstract long getRefID();

    public abstract void setRefID(long refID);

    public abstract String getStatus();

    public abstract String getDepositRefNo();

    public abstract void setDepositRefNo(String depositRefNo);

    public abstract double getFdrRate();

    public abstract void setFdrRate(double fdrRate);

    public abstract Date getIssueDate();

    public abstract void setIssueDate(Date issueDate);

    public abstract String getThirdPartyBank();

    public abstract void setThirdPartyBank(String thirdPartyBank);

    public abstract String getAccountTypeNum();

    public abstract void setAccountTypeNum(String accountTypeNum);

    public abstract String getAccountTypeValue();

    public abstract void setAccountTypeValue(String accountTypeValue);
    
    public abstract int getTenure();
    
    public abstract void setTenure(int tenure);
    
    public abstract String getTenureUOM();
    
    public abstract void setTenureUOM(String tenureUOM);
    
    public abstract String getIsOwnBankStr();
    
    public abstract void setIsOwnBankStr(String isOwnBankStr);
    
    public abstract String getGroupAccountNumber();
    
    public abstract void setGroupAccountNumber(String groupAccountNumber);

    //Andy Wong, 3 July 2009: collateral exists flag used for Stp inquiry, not persist to DB
    public String getCollateralExists(){return null;}

    public void setCollateralExists(String collateralExists){}
    
    public abstract Date getVerificationDate();

    public abstract void setVerificationDate(Date verificationDate);
    
   /* public abstract double getFdLienPercentage();

    public abstract void setFdLienPercentage(double fdLienPercentage);*/
    
    public abstract double getDepositeInterestRate();

    public abstract void setDepositeInterestRate(double depositeInterestRate);
    
    public abstract String getDepositorName();

    public abstract void setDepositorName(String depositorName);
    
    
    
    public abstract String getSystemId();

    public abstract void setSystemId(String systemId); 
    
    public abstract String getSystemName();

    public abstract void setSystemName(String systemName);
    
    public abstract String getCustomerId();

    public abstract void setCustomerId(String customerId);
    
    public abstract String getFinwareId();

    public abstract void setFinwareId(String finwareId);
    
    public abstract String getActive();

    public abstract void setActive(String active);

}