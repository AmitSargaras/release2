package com.integrosys.cms.app.checklist.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 15, 2005 Time: 5:52:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EBDocumentshareBean implements EntityBean, IShareDoc {

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getDocShareId", "getDocShareIdRef",
			"getLeID", "getLeName", "getSecurityDtlId", "getSecurityType", "getSecuritySubType" };

	private static long LONG_INVALID_VALUE = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	protected EntityContext _context = null;


    // *************Override Inteface Methods (Java 1.4 requirement) **************

    public abstract void setDocShareIdRef(long docShareId);
    public abstract long getProfileId();
    public abstract void setProfileId(long profileId);
    public abstract long getSubProfileId();
    public abstract void setSubProfileId(long subProfileId);
    public abstract long getPledgorDtlId();
    public abstract void setPledgorDtlId(long pledgorDtlId);
    public abstract long getCollateralId();
    public abstract void setCollateralId(long collateralId);
    public abstract long getCheckListId();
    public abstract void setCheckListId(long checkListId);
    public abstract String getDetails();
    public abstract void setDetails(String details);

    // ************** Abstract methods ************
	public abstract void setCMPDocShareId(Long aDocShareId);

	public abstract Long getCMPDocShareId();

	/*
	 * public abstract void setIsDeletedIndStr(String anIsDeletedIndStr); public
	 * abstract String getIsDeletedIndStr();
	 */

	public abstract Long getCMPCheckListItemID();

	public abstract void setCMPCheckListItemID(Long aCheckListItemID);

	public abstract void setStatusStr(String shareStatusStr);

	public abstract String getStatusStr();

	public abstract long getDocShareIdRef();

	/**
	 * Default Constructor
	 */
	public EBDocumentshareBean() {
	}

	public Long ejbCreate(Long checkListItemID, IShareDoc iShareDoc) throws CreateException {

		if (iShareDoc == null) {
			throw new CreateException("ShareDoc is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));

			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>>>>>######### EBDocumentshareBean.ejbCreate.PK " +
			// pk);
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>>>>>######### EBDocumentshareBean.ejbCreate.checkListItemID "
			// + checkListItemID);

			IShareDoc shareDoc = iShareDoc;
			shareDoc.setStatus(ICMSConstant.STATE_ACTIVE);
			setDocShareId(pk);
			// setIsDeletedInd(shareDoc.getIsDeletedInd());

			if ((LONG_INVALID_VALUE == shareDoc.getDocShareIdRef()) || (shareDoc.getDocShareIdRef() == 0)) {
				setDocShareIdRef(pk);
			}
			else {
				setDocShareIdRef(shareDoc.getDocShareIdRef());
			}

			AccessorUtil.copyValue(shareDoc, this, EXCLUDE_METHOD_CREATE);
			return new Long(pk);
		}
		catch (Exception ex) {
			Debug("Exception at  EBDocumentshareBean:ejbCreate:");
			_context.setRollbackOnly();
			throw new CreateException("Exception at  EBDocumentshareBean:ejbCreate: " + ex.toString());
		}
	}

	public void ejbPostCreate(Long checkListItemID, IShareDoc IShareDoc) throws CreateException {
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

	public IShareDoc getValue() {
		OBShareDoc value = new OBShareDoc();
		AccessorUtil.copyValue(this, value);
		value.setCheckListItemID(getCheckListItemID());
		return value;
	}

	public void setValue(IShareDoc iShareDoc) {
		AccessorUtil.copyValue(iShareDoc, this, EXCLUDE_METHOD_CREATE);
	}

	public long getDocShareId() {
		if (getCMPDocShareId() != null) {
			return getCMPDocShareId().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setDocShareId(long aShareId) {
		setCMPDocShareId(new Long(aShareId));
	}

	/*
	 * public boolean getIsDeletedInd() { if (getIsDeletedIndStr() != null &&
	 * getIsDeletedIndStr().equals(ICMSConstant.TRUE_VALUE)) { return true; }
	 * return false; }
	 * 
	 * public void setIsDeletedInd(boolean anIsDeletedInd) { if (anIsDeletedInd)
	 * { setIsDeletedIndStr(ICMSConstant.TRUE_VALUE); return; }
	 * setIsDeletedIndStr(ICMSConstant.FALSE_VALUE); }
	 */

	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CMS_CHECKLIST_ITEM_SHARE;
	}

	/*
	 * //replaced by findByCheckListID public List
	 * ejbHomeGetShareDocumentItemId(long aCheckListId) throws
	 * SearchDAOException { return
	 * CheckListDAOFactory.getCheckListDAO().getShareDocumentItemId
	 * (aCheckListId); }
	 */

	public long getCheckListItemID() {
		if (getCMPCheckListItemID() != null) {
			return getCMPCheckListItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setCheckListItemID(long aCheckListItemID) {
		// do nothing (When a cmp-field and a cmr-field (relationship) are
		// mapped to the same column,
		// the setXXX method for the cmp-field may not be called. The cmp-field
		// is read-only.)
	}

	public String getItemStatus() {
		return null;
	}

	public void setItemStatus(String anItemStatus) {
		// nothing
	}

	public String getDeleteCheckListId() {
		return null;
	}

	public void setDeleteCheckListId(String deleteCheckListId) {
		// nothing
	}

	public String getExistingChkListId() {
		return null;
	}

	public void setExistingChkListId(String existingChkListId) {
		//
	}

	public String getStatus() {
		return getStatusStr();
	}

	public void setStatus(String shareStatus) {
		setStatusStr(shareStatus);
	}

	public String getLeID() {
		return null;
	}

	public void setLeID(String leID) {
	}

	public String getLeName() {
		return null;
	}

	public void setLeName(String leName) {
	}

	public String getSecurityDtlId() {
		return null;
	}

	public void setSecurityDtlId(String securityDtlId) {
	}

	public String getSecurityType() {
		return null;
	}

	public void setSecurityType(String securityType) {
	}

	public String getSecuritySubType() {
		return null;
	}

	public void setSecuritySubType(String securitySubType) {
	}

	private void Debug(String msg) {
		// DefaultLogger.debug(this, msg);
		// System.out.println(" EBDocumentshareBean " + msg);
	}
}
