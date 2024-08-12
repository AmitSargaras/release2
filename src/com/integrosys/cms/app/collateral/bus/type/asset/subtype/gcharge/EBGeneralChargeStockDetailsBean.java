/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBContactBean.java,v 1.8 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for Contact Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBGeneralChargeStockDetailsBean implements EntityBean, IGeneralChargeStockDetails{
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_GENERAL_CHARGE_STOCK_DETAILS;

	private static final String[] EXCLUDE_METHOD = new String[] { "getGeneralChargeStockDetailsID", "getGeneralChargeDetailsID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBGeneralChargeStockDetailsBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getGeneralChargeStockDetailsID() {
		if (null != getGeneralChargeStockDetailsPK()) {
			return getGeneralChargeStockDetailsPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	public long getGeneralChargeDetailsID() {
		if (null != getGeneralChargeDetailsIDFK()) {
			return getGeneralChargeDetailsIDFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setGeneralChargeDetailsID(long value) {
		setGeneralChargeDetailsIDFK(new Long(value));
	}
	
	public abstract Long getGeneralChargeDetailsIDFK();
	
	public abstract void setGeneralChargeDetailsIDFK(Long value);
	/**
	 * Get Legal ID
	 * 
	 * @return long
	 */
	

	/**
	 * Set the GeneralChargeStockDetails ID
	 * 
	 * @param value is of type long
	 */
	public void setGeneralChargeStockDetailsID(long value) {
		setGeneralChargeStockDetailsPK(new Long(value));
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	
	// ************** Abstract methods ************
	/**
	 * Get the contact PK
	 * 
	 * @return Long
	 */
	public abstract Long getGeneralChargeStockDetailsPK();

	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	

	/**
	 * Set the contact PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setGeneralChargeStockDetailsPK(Long value);

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */


	// *****************************************************
	/**
	 * Create a Contact Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 * @return Long the contact ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IGeneralChargeStockDetails value) throws CreateException {
		if (null == value) {
			throw new CreateException("IGeneralChargeStockDetails is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getContactID() ==
			 * com.integrosys.cms.
			 * app.common.constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getContactID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); //to be set by cmr
			setGeneralChargeStockDetailsID(pk);
			
			
			if (value.getCmsRefId() == ICMSConstant.LONG_MIN_VALUE ) {
				setCmsRefId(pk);
			}
			else {
				// else maintain this reference id.
				setCmsRefId(value.getCmsRefId());
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
	public void ejbPostCreate(IGeneralChargeStockDetails value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public IGeneralChargeStockDetails getValue() {
		OBGeneralChargeStockDetails value = new OBGeneralChargeStockDetails();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(IGeneralChargeStockDetails value) {
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

	
//	=========== Adding abstract methods Anil
	
	public abstract long getLocationId() ;
	public abstract void setLocationId(long locationId) ;
	
	public abstract String getLocationDetail() ;
	public abstract void setLocationDetail(String locationDetail) ;
	
	public abstract String getStockType();
	public abstract void setStockType(String stockType);
	
	public abstract String getMarginType() ;
	public abstract void setMarginType(String marginType);
	
	public abstract String getComponent();
	public abstract void setComponent(String component);
	
	public abstract String getComponentAmount();
	public abstract void setComponentAmount(String componentAmount) ;
	
	public abstract String getMargin();
	public abstract void setMargin(String margin);
	
	public abstract String getLonable();
	public abstract void setLonable(String lonable);
	
	public abstract String getHasInsurance();
	public abstract void setHasInsurance(String hasInsurance);
	
	public abstract String getInsuranceCompanyName();
	public abstract void setInsuranceCompanyName(String insuranceCompanyName) ;
	
	public abstract String getInsuranceCompanyCategory();
	public abstract void setInsuranceCompanyCategory(String insuranceCompanyCategory);
	
	public abstract String getInsuredAmount();
	public abstract void setInsuredAmount(String insuredAmount);
	
	public abstract Date getEffectiveDateOfInsurance();
	public abstract void setEffectiveDateOfInsurance(Date effectiveDateOfInsurance);
	
	public abstract Date getExpiryDate();
	public abstract void setExpiryDate(Date expiryDate);
	
	public abstract String getInsuranceDescription();
	public abstract void setInsuranceDescription(String insuranceDescription);
	
	public abstract String getInsurancePolicyNo();
	public abstract void setInsurancePolicyNo(String insurancePolicyNo) ;
	
	public abstract String getInsuranceCoverNote();
	public abstract void setInsuranceCoverNote(String insuranceCoverNote);
	
	public abstract String getTotalPolicyAmount();
	public abstract void setTotalPolicyAmount(String totalPolicyAmount) ;
	
	public abstract Date getInsuranceRecivedDate() ;
	public abstract void setInsuranceRecivedDate(Date insuranceRecivedDate) ;
	
	public abstract String getInsuranceDefaulted();
	public abstract void setInsuranceDefaulted(String insuranceDefaulted);
	
	public abstract String getInsurancePremium();
	public abstract void setInsurancePremium(String insurancePremium);

	//Start Santosh
	public abstract String getApplicableForDp();
	public abstract void setApplicableForDp(String applicableForDp);
	//End Santosh
	
	public String getInsuranceCurrency() {
		return null;
	}
	public void setInsuranceCurrency(String insuranceCurrency) {
	}
	
	public abstract String getStockComponentCat();
	public abstract void setStockComponentCat(String stockComponentCat);
	
	
	public abstract long getCmsRefId();
	public abstract void setCmsRefId(long cmsRefId);
	

}