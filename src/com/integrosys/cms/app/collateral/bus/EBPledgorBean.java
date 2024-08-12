/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBPledgorBean.java,v 1.3 2003/11/05 03:42:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for pledgor entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/11/05 03:42:05 $ Tag: $Name: $
 */
public abstract class EBPledgorBean implements IPledgor, EntityBean {

	private static final long serialVersionUID = -4020297951924199573L;

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the pledgor. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getPledgorID" };

	/**
	 * Get pledgor ID, the primary key in CMS.
	 * 
	 * @return long
	 */
	public long getPledgorID() {
		return getEBPledgorID().longValue();
	}

	/**
	 * Set pledgor ID, the primary key in CMS.
	 * 
	 * @param pledgorID is of type long
	 */
	public void setPledgorID(long pledgorID) {
		setEBPledgorID(new Long(pledgorID));
	}

	/**
	 * Get pledgor instruction booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation() {
		OBBookingLocation bl = new OBBookingLocation();
		bl.setCountryCode(getBLCountry());
		bl.setOrganisationCode(getBLOrganisation());
		return bl;
	}

	/**
	 * Set pledgor instruction booking location.
	 * 
	 * @param bookingLocation of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation bookingLocation) {
		if (bookingLocation != null) {
			setBLCountry(bookingLocation.getCountryCode());
			setBLOrganisation(bookingLocation.getOrganisationCode());
		}
	}

	public abstract Long getEBPledgorID();

	public abstract void setEBPledgorID(Long eBPledgorID);

	public abstract String getBLCountry();

	public abstract void setBLCountry(String bLCountry);

	public abstract String getBLOrganisation();

	public abstract void setBLOrganisation(String bLOrganisation);

	public abstract String getPlgIdNumText();

	public abstract void setPlgIdNumText(String plgIdNumText);

	public abstract String getLegalID();

	public abstract void setLegalID(String value);

	/**
	 * Get pledgor credit grades.
	 * 
	 * @return IPledgorCreditGrade[]
	 */
	public IPledgorCreditGrade[] getCreditGrades() {
		return null;
	}

	/**
	 * Set pledgor credit grades.
	 * 
	 * @param creditGrades of type IPledgorCreditGrade[]
	 */
	public void setCreditGrades(IPledgorCreditGrade[] creditGrades) {
	}

	/**
	 * Get the pledgor of this entity.
	 * 
	 * @return a pledgor
	 */
	public IPledgor getValue() {
		OBPledgor pledgor = new OBPledgor();
		AccessorUtil.copyValue(this, pledgor);
		return pledgor;
	}

	/**
	 * Set the pledgor.
	 * 
	 * @param pledgor of type IPledgor
	 */
	public void setValue(IPledgor pledgor) {
		AccessorUtil.copyValue(pledgor, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param pledgor of type IPledgor
	 * @return pledgor primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IPledgor pledgor) throws CreateException {
		try {
			String pledgorID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_PLEDGOR, true);
			AccessorUtil.copyValue(pledgor, this);
			setEBPledgorID(new Long(pledgorID));
			DefaultLogger.debug(this, "Creating Pledgor with ID: " + pledgorID);

			if (pledgor.getSysGenPledgorID() == ICMSConstant.LONG_INVALID_VALUE) {
				setSysGenPledgorID(Long.parseLong(pledgorID));
			}
			return null;
		}
		catch (Exception e) {
			CreateException ce = new CreateException("Failed to create pledgor, pledgor cif [" + pledgor.getLegalID()
					+ "]");
			ce.initCause(e);
			throw ce;
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param pledgor of type IPledgor
	 */
	public void ejbPostCreate(IPledgor pledgor) {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
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

	public abstract long getSysGenPledgorID();

	public abstract void setSysGenPledgorID(long sysGenPledgorID);

	public abstract String getPledgorName();

	public abstract void setPledgorName(String pledgorName);

	public abstract String getPledgorRelshipID();

	public abstract void setPledgorRelshipID(String pledgorRelshipID);

	public abstract String getPledgorRelship();

	public abstract void setPledgorRelship(String pledgorRelship);

	public abstract long getLegalTypeID();

	public abstract void setLegalTypeID(long legalTypeID);

	public abstract String getLegalType();

	public abstract void setLegalType(String legalType);

	public abstract String getDomicileCountry();

	public abstract void setDomicileCountry(String domicileCountry);

	public abstract String getSegmentCode();

	public abstract void setSegmentCode(String segmentCode);

	public abstract String getPledgorStatus();

	public abstract void setPledgorStatus(String pledgorStatus);

	public abstract String getPlgIdCountry();

	public abstract void setPlgIdCountry(String plgIdCountry);

	public abstract String getPlgIdTypeCode();

	public abstract void setPlgIdTypeCode(String plgIdTypeCode);

	public abstract String getPlgIdType();

	public abstract void setPlgIdType(String plgIdType);

	public abstract String getSourceId();

	public abstract void setSourceId(String sourceId);

	public abstract String getLegalIDSourceCode();

	public abstract void setLegalIDSourceCode(String value);

	public abstract String getLegalIDSource();

	public abstract void setLegalIDSource(String value);
}