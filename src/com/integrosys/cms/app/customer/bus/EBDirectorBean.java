/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBContactBean.java,v 1.8 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

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
public abstract class EBDirectorBean implements EntityBean, IDirector {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CONTACT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getDirectorID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBDirectorBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getDirectorID() {
		if (null != getDirectorPK()) {
			return getDirectorPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	public long getLEID() {
		if (null != getLEFK()) {
			return getLEFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setLEID(long value) {
		setLEFK(new Long(value));
	}
	
	public abstract Long getLEFK();
	
	public abstract void setLEFK(Long value);
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
	public void setDirectorID(long value) {
		setDirectorPK(new Long(value));
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
	public abstract Long getDirectorPK();

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
	public abstract void setDirectorPK(Long value);

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
	public Long ejbCreate(IDirector value) throws CreateException {
		if (null == value) {
			throw new CreateException("IDirector is null!");
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
			setDirectorID(pk);

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
	public void ejbPostCreate(IDirector value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public IDirector getValue() {
		OBDirector value = new OBDirector();
		AccessorUtil.copyValue(this, value);
		return  value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(IDirector value) {
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

	
	public abstract  String getRelatedType() ;

	public abstract  void setRelatedType(String relatedType) ;

	public abstract  String getRelationship();

	public abstract  void setRelationship(String relationship);

	public abstract  String getDirectorEmail();

	public abstract  void setDirectorEmail(String directorEmail);

	public abstract  String getDirectorFax();

	public abstract  void setDirectorFax(String directorFax);

	public abstract  String getDirectorTelNo();

	public abstract  void setDirectorTelNo(String directorTelNo) ;

	public abstract  String getDirectorCountry();

	public abstract  void setDirectorCountry(String directorCountry) ;

	public abstract  String getDirectorState() ;

	public abstract  void setDirectorState(String directorState) ;

	public abstract  String getDirectorCity() ;

	public abstract  void setDirectorCity(String directorCity) ;

	public abstract  String getDirectorRegion();

	public abstract  void setDirectorRegion(String directorRegion); 

	public abstract  String getDirectorPostCode() ;

	public abstract  void setDirectorPostCode(String directorPostCode) ;

	public abstract  String getDirectorAddress3();

	public abstract  void setDirectorAddress3(String directorAddress3) ;

	public abstract  String getDirectorAddress2() ;

	public abstract  void setDirectorAddress2(String directorAddress2) ;

	public abstract  String getDirectorAddress1() ;

	public abstract  void setDirectorAddress1(String directorAddress1) ;

	public abstract  String getPercentageOfControl() ;

	public abstract  void setPercentageOfControl(String percentageOfControl) ;

	public abstract  String getFullName() ;

	public abstract  void setFullName(String fullName);

	public abstract  String getNamePrefix() ;

	public abstract  void setNamePrefix(String namePrefix);

	public abstract  String getBusinessEntityName() ;

	public abstract  void setBusinessEntityName(String businessEntityName); 

	public abstract  String getDirectorPan();

	public abstract  void setDirectorPan(String directorPan);
	
	public abstract  String getDirectorAadhar();

	public abstract  void setDirectorAadhar(String directorAadhar);

	public abstract  String getRelatedDUNSNo() ;

	public abstract  void setRelatedDUNSNo(String relatedDUNSNo);

	public abstract  String getDinNo() ;

	public abstract  void setDinNo(String dinNo);

	public abstract  String getDirectorName() ;

	public abstract  void setDirectorName(String directorName);
	
	public abstract String getDirStdCodeTelNo();

	public abstract void setDirStdCodeTelNo(String dirStdCodeTelNo); 
	
	public abstract String getDirStdCodeTelex();

	public abstract void setDirStdCodeTelex(String dirStdCodeTelex) ;

}