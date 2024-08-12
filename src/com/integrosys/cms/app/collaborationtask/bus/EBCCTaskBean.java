/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCCTaskBean.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
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
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Implementation for the cc collaboration task entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */

public abstract class EBCCTaskBean implements EntityBean, ICCTask {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getTaskID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getTaskID" };

	/**
	 * Default Constructor
	 */
	public EBCCTaskBean() {
	}

	public abstract Long getCMPTaskID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPSubProfileID();

	public abstract Long getCMPPledgorID();

	public abstract String getIsDeletedIndStr();

	public abstract void setCMPTaskID(Long aCMPTaskID);

	public abstract void setCMPLimitProfileID(Long aCMPLimitProfileID);

	public abstract void setCMPSubProfileID(Long aCMPSubProfileID);

	public abstract void setCMPPledgorID(Long aCMPPledgorID);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

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

	public long getCustomerID() {
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(getCustomerCategory())) {
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
	 * Set the task ID
	 * @param aTaskID of long type
	 */
	public void setTaskID(long aTaskID) {
		setCMPTaskID(new Long(aTaskID));
	}

	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	public void setCustomerID(long aCustomerID) {
		/*
		 * if (ICMSConstant.CHECKLIST_PLEDGER.equals(getCustomerCategory())) {
		 * DefaultLogger.debug(this, "IN Set Pledger!!!"); setCMPPledgorID(new
		 * Long(aCustomerID)); } else { setCMPSubProfileID(new
		 * Long(aCustomerID)); }
		 */
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
	 * Retrieve an instance of a cc task
	 * @return ICCTask - the object encapsulating the CC task
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTask getValue() throws CollaborationTaskException {
		ICCTask value = new OBCCTask();
		AccessorUtil.copyValue(this, value, null);
		return value;
	}

	/**
	 * Set the CC task object
	 * @param anICCTask - ICCTask
	 * @throws CollaborationTaskException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICCTask anICCTask) throws CollaborationTaskException, ConcurrentUpdateException {
		try {
			if (getVersionTime() != anICCTask.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anICCTask, this, EXCLUDE_METHOD_UPDATE);
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(anICCTask.getCustomerCategory())) {
				setCMPPledgorID(new Long(anICCTask.getCustomerID()));
			}
			else {
				setCMPSubProfileID(new Long(anICCTask.getCustomerID()));
			}
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
	 * @param aCriteria of CCTaskSearchCriteria type
	 * @return int - the number of CC task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException {
		return CCTaskDAOFactory.getCCTaskDAO().getNoOfCCTask(aCriteria);
	}

	/**
	 * To get the list of CC task that satisfy the criteria
	 * @param aCritieria of CCTaskSearchCritieria type
	 * @return CCTaskSearchResult[] - the list of CC task
	 * @throws SearchDAOException
	 */
	public CCTaskSearchResult[] ejbHomeGetCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException {
		DefaultLogger.debug(this, "IN HEREE !!!");
		return CCTaskDAOFactory.getCCTaskDAO().getCCTask(aCriteria);
	}

	/**
	 * Create a CC task
	 * @param anICCTask of ICCTask type
	 * @return Long - the CC task ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICCTask anICCTask) throws CreateException {
		if (anICCTask == null) {
			throw new CreateException("ICCTask is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			DefaultLogger.debug(this, "Creating CC task with ID: " + pk);
			setTaskID(pk);
			AccessorUtil.copyValue(anICCTask, this, EXCLUDE_METHOD_CREATE);
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(anICCTask.getCustomerCategory())) {
				setCMPPledgorID(new Long(anICCTask.getCustomerID()));
			}
			else {
				setCMPSubProfileID(new Long(anICCTask.getCustomerID()));
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
	 * @param anICCTask of ICCTask type
	 */
	public void ejbPostCreate(ICCTask anICCTask) throws CreateException {
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
		return ICMSConstant.SEQUENCE_CC_TASK;
	}

	public abstract String getCustomerCategory();

	public abstract String getDomicileCountry();

	public abstract String getOrgCode();

	public abstract String getRemarks();

	public abstract void setCustomerCategory(String customerCategory);

	public abstract void setDomicileCountry(String domicileCountry);

	public abstract void setOrgCode(String anOrgCode);

	public abstract void setRemarks(String remarks);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long version);
}