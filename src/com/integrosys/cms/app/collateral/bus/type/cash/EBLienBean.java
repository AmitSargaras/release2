/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBContactBean.java,v 1.8 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.EBCollateralSubTypeLocal;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for Contact Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBLienBean implements EntityBean, ILienMethod {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CASH_FIXED_DEPOSIT_LIEN;

	private static final String[] EXCLUDE_METHOD = new String[] { "getCashDepositID", "getLienId" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBLienBean() {
	}
	
	



	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getLienID() {
		if (null != getEBlienID()) {
			return getEBlienID().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	public long getCashDepositID() {
		if (null != getCashDepositIdFK()) {
			return getCashDepositIdFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setCashDepositID(long value) {
		setCashDepositIdFK(new Long(value));
	}
	
	public abstract Long getCashDepositIdFK();
	
	public abstract void setCashDepositIdFK(Long value);
	/**
	 * Get Legal ID
	 * 
	 * @return long
	 */
	

	/**
	 * Set the contact ID
	 * 
	 * @param value is of type long
	 */
	public void setLienID(long value) {
		setEBlienID(new Long(value));
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	
	// ************** Abstract methods ************
	public abstract Long getEBlienID();

	public abstract void setEBlienID(Long eBLienID);

	// ************* Non-persistent methods End ***********
	
	/**
	 * Create a Contact Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 * @return Long the contact ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ILienMethod value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILienMethod is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));			
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setLienID(pk);			
			if (value.getActualLienId() == 1 || value.getActualLienId() == 0) {
				setActualLienId(pk);				
			}
			else {
				// else maintain this reference id.
				setActualLienId(value.getActualLienId());
				
			}
			return new Long(pk);	
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 */
	public void ejbPostCreate(ILienMethod value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public ILienMethod getValue() {
		OBLien value = new OBLien();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(ILienMethod value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	
	


	

	/*public abstract Long getCashDepositId();//CASH_DEPOSIT_ID

	public abstract void setCashDepositId(Long cashDepositId);
*/
	
	public abstract long getDepositReferenceNumber();//DEPOSIT_REFERENCE_NUMBER

	public abstract void setDepositReferenceNumber(long depositReferenceNumber);
	
	public abstract String getLienNumber();//LIEN_NUMBER

	public abstract void setLienNumber(String lienNumber);

	public abstract double getLienAmount();//LIEN_AMOUNT

	public abstract void setLienAmount(double lienAmount);
	
	public abstract long getActualLienId();//ACTUAL_LIEN_AMOUNT

	public abstract void setActualLienId(long actualLienId);
	
	public abstract String getStatus(); //STATUS
		
	public abstract void setStatus(String status);
	
	public abstract String getSerialNo();//SERIAL_NO

	public abstract void setSerialNo(String serialNo);
	
	public abstract String getBaselSerial();//BASEL_SERIAL

	public abstract void setBaselSerial(String baselSerial);
	
	public abstract String getRemark();//REMARK

	public abstract void setRemark(String remark);
	
	public abstract String getFacilityName();
	public abstract void setFacilityName(String facilityName);
	public abstract String getFacilityId();
	public abstract void setFacilityId(String facilityId);
	public abstract String getLcnNo();
	public abstract void setLcnNo(String lcnNo);
	
	/*public abstract Collection getCashDepositIDCMR();
	
	public abstract void setCashDepositIDCMR(Collection cashDepositIDCMR);//cashDepositeIDCMR
*/
	

}