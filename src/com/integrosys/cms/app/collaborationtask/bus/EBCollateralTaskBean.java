/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCollateralTaskBean.java,v 1.12 2006/08/30 12:37:02 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

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
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;

/**
 * Implementation for the collateral collaboration task entity bean
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/08/30 12:37:02 $ Tag: $Name: $
 */

public abstract class EBCollateralTaskBean implements EntityBean, ICollateralTask {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getTaskID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getTaskID" };

	/**
	 * Default Constructor
	 */
	public EBCollateralTaskBean() {
	}

	public abstract Long getCMPTaskID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPCollateralID();

	public abstract String getIsDeletedIndStr();

	public abstract Long getCmsLeSubProfileID();

	public abstract String getCMPCustomerCategory();

	public abstract void setCMPTaskID(Long aCMPTaskID);

	public abstract void setCMPLimitProfileID(Long aCMPLimitProfileID);

	public abstract void setCMPCollateralID(Long aCMPCollateralID);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setCmsLeSubProfileID(Long cmsLeSubProfileID);

	public abstract void setCMPCustomerCategory(String cMPCustomerCategory);

	/**
	 * Get the task ID
	 * @return long - the task ID
	 */
	public long getTaskID() {
		if (getCMPTaskID() != null) {
			return getCMPTaskID().longValue();
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

	/**
	 * Not implemented
	 */
	public ILimit[] getLimitList() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public String[] getLimitRefList() {
		return null;
	}

	/**
	 * Get the collateral ID
	 * @return long - the collateral ID
	 */
	public long getCollateralID() {
		if (getCMPCollateralID() != null) {
			return getCMPCollateralID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the collateral reference
	 * @return String - the collateral reference
	 */
	public String getCollateralRef() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public ICollateralType getCollateralType() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public ICollateralSubType getCollateralSubType() {
		return null;
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

	public long getLeSubProfileID() {
		if (getCmsLeSubProfileID() != null) {
			return getCmsLeSubProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getCustomerCategory() {
		return getCMPCustomerCategory();
	}

	/**
	 * Set the task ID
	 * @param aTaskID of long type
	 */
	public void setTaskID(long aTaskID) {
		setCMPTaskID(new Long(aTaskID));
	}

	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	/**
	 * Not implemented
	 */
	public void setLimitList(ILimit[] aLimitList) {
		// do nothing
	}

	public void setCollateralID(long aCollateralID) {
		setCMPCollateralID(new Long(aCollateralID));
	}

	/**
	 * Not implemented
	 */
	public void setCollateralRef(String aCollateralRef) {
		// do nothing
	}

	/**
	 * Not implemented
	 */
	public void setCollateralType(ICollateralType anICollateralType) {
		// do nothing
	}

	/**
	 * Not implemented
	 */
	public void setCollateralSubType(ICollateralSubType anICollateralSubType) {
		// do nothing
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

	public void setLeSubProfileID(long leSubProfileID) {
		// setCmsLeSubProfileID(new Long(leSubProfileID));
		setCmsLeSubProfileID(null);
	}

	public void setCustomerCategory(String customerCategory) {
		String categoryCode = customerCategory;
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(categoryCode)) {
			categoryCode = ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER;
		}
		if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(categoryCode)) {
			categoryCode = ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER;
		}

		setCMPCustomerCategory(categoryCode);
	}

	public ICoBorrowerLimit[] getCoBorrowerLimitList() {
		return null; // do nothing
	}

	public void setCoBorrowerLimitList(ICoBorrowerLimit[] coBorrowerLimitList) {
		// do nothing
	}

	/**
	 * Retrieve an instance of a collateral task
	 * @return ICollateralTask - the object encapsulating the collateral task
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTask getValue() throws CollaborationTaskException {
		ICollateralTask value = new OBCollateralTask();
		AccessorUtil.copyValue(this, value, null);
		return value;
	}

	/**
	 * Set the collateral task object
	 * @param anICollateralTask - ICollateralTask
	 * @throws CollaborationTaskException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICollateralTask anICollateralTask) throws CollaborationTaskException,
			ConcurrentUpdateException {
		try {
			if (getVersionTime() != anICollateralTask.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anICollateralTask, this, EXCLUDE_METHOD_UPDATE);
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CollaborationTaskException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of CollateralTaskSearchCriteria type
	 * @return int - the number of collateral task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfCollateralTask(CollateralTaskSearchCriteria aCriteria) throws SearchDAOException {
		return CollateralTaskDAOFactory.getCollateralTaskDAO().getNoOfCollateralTask(aCriteria);
	}

	/**
	 * To get the list of collateral task that satisfy the criteria
	 * @param aCritieria of CollateralTaskSearchCritieria type
	 * @return CollateralTaskSearchResult[] - the list of collateral task
	 * @throws SearchDAOException
	 */
	public CollateralTaskSearchResult[] ejbHomeGetCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws SearchDAOException {
		return CollateralTaskDAOFactory.getCollateralTaskDAO().getCollateralTask(aCriteria);
	}

	/**
	 * Create a collateral task
	 * @param anICollateralTask of ICollateralTask type
	 * @return Long - the collateral task ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICollateralTask anICollateralTask) throws CreateException {
		if (anICollateralTask == null) {
			throw new CreateException("ICollateralTask is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			DefaultLogger.debug(this, "Creating collateral task with ID: " + pk);
			setTaskID(pk);
			AccessorUtil.copyValue(anICollateralTask, this, EXCLUDE_METHOD_CREATE);
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
	 * @param anICollateralTask of ICollateralTask type
	 */
	public void ejbPostCreate(ICollateralTask anICollateralTask) throws CreateException {
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
		return ICMSConstant.SEQUENCE_COLLATERAL_TASK;
	}

	public abstract String getCollateralLocation();

	public abstract String getRemarks();

	public abstract String getSecurityOrganisation();

	public abstract void setCollateralLocation(String collateralLocation);

	public abstract void setRemarks(String remarks);

	public abstract void setSecurityOrganisation(String securityOrganisation);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long version);
}