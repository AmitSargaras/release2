/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/EBCCDocumentLocationBean.java,v 1.4 2004/04/06 09:22:38 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Implementation for the cc documentation location entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/04/06 09:22:38 $ Tag: $Name: $
 */

public abstract class EBCCDocumentLocationBean implements EntityBean, ICCDocumentLocation {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getDocLocationID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getDocLocationID" };

	/**
	 * Default Constructor
	 */
	public EBCCDocumentLocationBean() {
	}

	public abstract Long getCMPDocLocationID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPSubProfileID();

	public abstract Long getCMPPledgorID();

	public abstract String getCMPDocLocationCountry();

	public abstract String getCMPDocLocationOrgCode();

	public abstract String getIsDeletedIndStr();

	public abstract void setCMPDocLocationID(Long aCMPDocLocationID);

	public abstract void setCMPLimitProfileID(Long aCMPLimitProfileID);

	public abstract void setCMPSubProfileID(Long aCMPSubProfileID);

	public abstract void setCMPPledgorID(Long aCMPPledgorID);

	public abstract void setCMPDocLocationCountry(String aCMPDocLocationCountry);

	public abstract void setCMPDocLocationOrgCode(String aCMPDocLocationOrgCode);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	/**
	 * Get the documentation location ID
	 * @return long - the documentation location ID
	 */
	public long getDocLocationID() {
		if (getCMPDocLocationID() != null) {
			return getCMPDocLocationID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		if (getCMPLimitProfileID() != null) {
			return getCMPLimitProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getCustomerID() {
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(getDocLocationCategory())) {
			if (getCMPPledgorID() != null) {
				return getCMPPledgorID().longValue();
			}
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
		if (getCMPSubProfileID() != null) {
			return getCMPSubProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public IBookingLocation getOriginatingLocation() {
		String origCountry = getCMPDocLocationCountry();
		String origOrg = getCMPDocLocationOrgCode();

		OBBookingLocation ob = new OBBookingLocation();
		ob.setCountryCode(origCountry);
		ob.setOrganisationCode(origOrg);

		return ob;
	}

	/**
	 * Helper method to get the delete indicator
	 * @return boolean - true if it is to be deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		if ((getIsDeletedIndStr() != null) && getIsDeletedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public String getLegalName() {
		return null;
	}

	public String getLegalRef() {
		return null;
	}

	public String getCustomerType() {
		return null;
	}

	/**
	 * Set the document location ID
	 * @param aDocLocationID of long type
	 */
	public void setDocLocationID(long aDocLocationID) {
		setCMPDocLocationID(new Long(aDocLocationID));
	}

	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	/**
	 * Helper method to set delete indicator
	 * @param anIsDeletedInd - boolean
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		if (anIsDeletedInd) {
			setIsDeletedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsDeletedIndStr(ICMSConstant.FALSE_VALUE);
	}

	public void setOriginatingLocation(IBookingLocation value) {
		if (null != value) {
			setCMPDocLocationCountry(value.getCountryCode());
			setCMPDocLocationOrgCode(value.getOrganisationCode());
		}
		else {
			setCMPDocLocationCountry(null);
			setCMPDocLocationOrgCode(null);
		}
	}

	public void setCustomerID(long aCustomerID) {
		// do nothing
	}

	public void setLegalName(String aLegalName) {
		// do nothing
	}

	public void setLegalRef(String aLegalRef) {
		// do nothing
	}

	public void setCustomerType(String aCustomerType) {
		// do nothing
	}

	/**
	 * Retrieve an instance of a cc documentation
	 * @return ICCDocumentLocation - the object encapsulating the CC
	 *         documentation location
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocation getValue() throws DocumentLocationException {
		ICCDocumentLocation value = new OBCCDocumentLocation();
		AccessorUtil.copyValue(this, value, null);
		return value;
	}

	/**
	 * Set the CC documentation location object
	 * @param anICCDocumentLocation - ICCDocumentLocation
	 * @throws DocumentLocationException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICCDocumentLocation anICCDocumentLocation) throws DocumentLocationException,
			ConcurrentUpdateException {
		try {
			if (getVersionTime() != anICCDocumentLocation.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anICCDocumentLocation, this, EXCLUDE_METHOD_UPDATE);
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(anICCDocumentLocation.getDocLocationCategory())) {
				setCMPPledgorID(new Long(anICCDocumentLocation.getCustomerID()));
			}
			else {
				setCMPSubProfileID(new Long(anICCDocumentLocation.getCustomerID()));
			}
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new DocumentLocationException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of CCDocumentLocationSearchCriteria type
	 * @return int - the number of CC documentation location that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria) throws SearchDAOException {
		return DocumentLocationDAOFactory.getCCDocumentLocationDAO().getNoOfCCDocumentLocation(aCriteria);
	}

	/**
	 * To get the list of CC documentation location that satisfy the criteria
	 * @param aCritieria of CCDocumentLocationSearchCritieria type
	 * @return CCDocumentLocationSearchResult[] - the list of CC documentation
	 *         location
	 * @throws SearchDAOException
	 */
	public CCDocumentLocationSearchResult[] ejbHomeGetCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws SearchDAOException {
		return null;
		// TODO
		// return DocumentLocationDAOFactory.getCCDocumentLocationDAO().
		// getCCDocumentLocation(aCriteria);
	}

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws SearchDAOException on error
	 */
	public ICCDocumentLocation[] ejbHomeGetCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws SearchDAOException {
		return DocumentLocationDAOFactory.getCCDocumentLocationDAO().getCCDocumentLocation(anOwnerType,
				aLimitProfileID, anOwnerID);
	}

	/**
	 * Create a CC documentation location
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return Long - the CC documentation location ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICCDocumentLocation anICCDocumentLocation) throws CreateException {
		if (anICCDocumentLocation == null) {
			throw new CreateException("ICCDocumentLocation is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			DefaultLogger.debug(this, "Creating CC documentation location with ID: " + pk);
			setDocLocationID(pk);
			AccessorUtil.copyValue(anICCDocumentLocation, this, EXCLUDE_METHOD_CREATE);
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(anICCDocumentLocation.getDocLocationCategory())) {
				setCMPPledgorID(new Long(anICCDocumentLocation.getCustomerID()));
			}
			else {
				setCMPSubProfileID(new Long(anICCDocumentLocation.getCustomerID()));
			}
			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception in ejbCreate : " + ex.toString());
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 */
	public void ejbPostCreate(ICCDocumentLocation anICCDocumentLocation) throws CreateException {
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

	/**
	 * A container invokes this method when the instance is taken out of the
	 * pool of available instances to become associated with a specific EJB
	 * object. This method transitions the instance to the ready state. This
	 * method executes in an unspecified transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}

	/**
	 * Get the name of the sequence to be used for key generation
	 * @return String - the sequence to be used
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CC_DOC_LOCATION;
	}

	public abstract String getDocLocationCategory();

	public abstract String getRemarks();

	public abstract void setDocLocationCategory(String docLocationCategory);

	public abstract void setRemarks(String remarks);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long version);
}