/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDocBean.java,v 1.35 2006/08/30 12:36:20 jzhai Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;

/**
 * Implementation for the custodian doc entity bean
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.35 $
 * @since $Date: 2006/08/30 12:36:20 $ Tag: $Name: $
 */

public abstract class EBCustodianDocBean implements EntityBean, ICustodianDoc {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	protected static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CUSTODIAN_DOC;

	protected static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCustodianDocID" };

	protected static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCustodianDocID" };

	private static final String LIST_DELIMITER = ",";

	private static final int MAX_LIST_SIZE = 4000;

	/**
	 * Default Constructor
	 */
	public EBCustodianDocBean() {
	}

	// getter cmp fields
	public abstract Long getCMPCustodianDocID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPCheckListID();

	public abstract Long getCMPPledgorID();

	public abstract Long getCMPSubProfileID();

	public abstract Long getCMPCollateralID();

	public abstract String getCMPDocType();

	public abstract String getCMPDocSubType();

	public abstract Long getCMPVersionTime();

	public abstract Date getCMPLastUpdateDate();

	public abstract String getCMPUpdatedCheckListItemRefList();// CR-107

	// CR34
	public abstract String getCMPReversalRemarks();

	public abstract Long getCMPRevRemarksUpdatedBy();

	public abstract Long getCMPReversalID();

	public abstract String getCMPReversalRmkUpdatedUserInfo();

	// setter cmp fields
	public abstract void setCMPCustodianDocID(Long aCustodianDocID);

	public abstract void setCMPLimitProfileID(Long aLimitProfileID);

	public abstract void setCMPCheckListID(Long aCheckListID);

	public abstract void setCMPPledgorID(Long aPledgorID);

	public abstract void setCMPSubProfileID(Long aSubProfileID);

	public abstract void setCMPCollateralID(Long aCollateralID);

	public abstract void setCMPDocType(String aDocType);

	public abstract void setCMPDocSubType(String aDocSubType);

	public abstract void setCMPVersionTime(Long aVersionTime);

	public abstract void setCMPLastUpdateDate(Date aLastUpdateDate);

	public abstract void setCMPUpdatedCheckListItemRefList(String checkListItemRefStr);// CR
																						// -
																						// 107

	// CR34
	public abstract void setCMPReversalRemarks(String reversalRemarks);

	public abstract void setCMPRevRemarksUpdatedBy(Long revRemarksUpdatedBy);

	public abstract void setCMPReversalID(Long reversalID);

	public abstract void setCMPReversalRmkUpdatedUserInfo(String userInfo);

	// cmr fields
	public abstract Collection getCustodianDocItemCMR();

	public abstract void setCustodianDocItemCMR(Collection coll);

	// CR34
	public String getReversalRemarks() {
		return getCMPReversalRemarks();
	}

	public long getRevRemarksUpdatedBy() {
		if (getCMPRevRemarksUpdatedBy() != null) {
			return getCMPRevRemarksUpdatedBy().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getReversalID() {
		if (getCMPReversalID() != null) {
			return getCMPReversalID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getReversalRmkUpdatedUserInfo() {
		return getCMPReversalRmkUpdatedUserInfo();
	}

	public void setReversalRemarks(String reversalRemarks) {
		setCMPReversalRemarks(reversalRemarks);
	}

	public void setRevRemarksUpdatedBy(long updatedBy) {
		setCMPRevRemarksUpdatedBy(new Long(updatedBy));
	}

	public void setReversalID(long reversalID) {
		setCMPReversalID(new Long(reversalID));
	}

	public void setReversalRmkUpdatedUserInfo(String userInfo) {
		setCMPReversalRmkUpdatedUserInfo(userInfo);
	}

	public long getCustodianDocID() {
		if (getCMPCustodianDocID() != null) {
			return getCMPCustodianDocID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getLimitProfileID() {
		if (getCMPLimitProfileID() != null) {
			return getCMPLimitProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getCheckListID() {
		if (getCMPCheckListID() != null) {
			return getCMPCheckListID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getPledgorID() {
		if (getCMPPledgorID() != null) {
			return getCMPPledgorID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getSubProfileID() {
		if (getCMPSubProfileID() != null) {
			return getCMPSubProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getDocType() {
		if (getCMPDocType() != null) {
			return getCMPDocType();
		}
		return "";
	}

	public String getDocSubType() {
		if (getCMPDocSubType() != null) {
			return getCMPDocSubType();
		}
		return null;
	}

	public long getVersionTime() {
		if (getCMPVersionTime() != null) {
			return getCMPVersionTime().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public Date getLastUpdateDate() {
		if (getCMPLastUpdateDate() != null) {
			return getCMPLastUpdateDate();
		}
		return null;
	}

	// new getter methods implemented from ICustodianDoc
	public ICustodianDocItem getCustodianDocItem(int index) {
		return null;
	}

	public ArrayList getCustodianDocItems() {
		return null;
	}

	/**
	 * This method retrieves String of ChecklistItemRefList from DB and tokenzes
	 * it. It adds all tokens into ArrayList object
	 * 
	 * @return checkListItemRefList contains List of CheckListItemRef
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public ArrayList getUpdatedCheckListItemRefArrayList() // CR-107
	{
		String chekListItemRefStr = getCMPUpdatedCheckListItemRefList();
		StringTokenizer st = null;
		ArrayList checkListItemRefList = new ArrayList();
		if (chekListItemRefStr != null) {
			st = new StringTokenizer(chekListItemRefStr, LIST_DELIMITER);

			while (st.hasMoreTokens()) {
				checkListItemRefList.add(st.nextToken());
			}

		}
		return checkListItemRefList;
	}

	/**
	 * This method retrieves all checkListItemRef from the ArrayList and create
	 * the String object which needs to be inserted into DB.
	 * 
	 * @param checkListItemRefList - ArrayList contains all CheckListItem list.
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setUpdatedCheckListItemRefArrayList(ArrayList checkListItemRefList) {
		if ((checkListItemRefList != null) && !checkListItemRefList.isEmpty()) {
			StringBuffer listBuf = null;
			Iterator iterateList = checkListItemRefList.iterator();
			while (iterateList.hasNext()) {
				if (listBuf == null) {
					listBuf = new StringBuffer();
				}
				else {
					listBuf.append(LIST_DELIMITER);
				}
				listBuf.append((String) iterateList.next());

				// terminate if list size exceeds maximum
				if (listBuf.length() > MAX_LIST_SIZE) {
					return;
				}
			}
			if (listBuf.length() > 0) {
				setCMPUpdatedCheckListItemRefList(listBuf.toString());
			}
		}
	}

	public void setCustodianDocID(long aCustodianDocID) {
		setCMPCustodianDocID(new Long(aCustodianDocID));
	}

	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	public void setCheckListID(long aCheckListID) {
		setCMPCheckListID(new Long(aCheckListID));
	}

	public void setPledgorID(long aPledgorID) {
		setCMPPledgorID(new Long(aPledgorID));
	}

	public void setSubProfileID(long aSubProfileID) {
		if (ICMSConstant.DOC_TYPE_SECURITY.equals(getDocType())
				&& ICMSConstant.CHECKLIST_CO_BORROWER.equals(getDocSubType())) {
			setCMPSubProfileID(null);
		}
		else {
			setCMPSubProfileID(new Long(aSubProfileID));
		}
	}

	public void setDocType(String aDocType) {
		setCMPDocType(aDocType);
	}

	public void setDocSubType(String aDocSubType) {
		setCMPDocSubType(aDocSubType);
	}

	public void setVersionTime(long aVersionTime) {
		setCMPVersionTime(new Long(aVersionTime));
	}

	public void setLastUpdateDate(Date aLastUpdateDate) {
		setCMPLastUpdateDate(aLastUpdateDate);
	}

	public void setCustodianDocItem(Collection coll) {
		setCustodianDocItemCMR(coll);
	}

	// new setter methods implemented from ICustodianDoc
	public void addCustodianDocItem(ICustodianDocItem item) {
		// do nothing;
	}

	public void removeCustodianDocItem(ICustodianDocItem item) {
		// do nothing;
	}

	public void setCustodianDocItems(ArrayList items) {
		// do nothing;
	}

	public CCCustodianInfo getCCCustodianInfo() {
		// do nothing
		return null;
	}

	public CollateralCustodianInfo getCollateralCustodianInfo() {
		// do nothing
		return null;
	}

	public long getCollateralID() {
		if (getCMPCollateralID() != null) {
			return getCMPCollateralID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setCCCustodianInfo(CCCustodianInfo aCCCustodianInfo) {
		// do nothing
	}

	public void setCollateralCustodianInfo(CollateralCustodianInfo aCollateralCustodianInfo) {
		// do nothing
	}

	public void setCollateralID(long aCollateralID) {
		setCMPCollateralID(new Long(aCollateralID));
	}

	/**
	 * Return a custodian document object
	 * @return ICustodianDoc - the object containing the custodian document
	 *         object
	 */
	public ICustodianDoc getValue() throws CustodianException {
		OBCustodianDoc value = new OBCustodianDoc();
		AccessorUtil.copyValue(this, value, null);
		Collection coll = this.getCustodianDocItemCMR();
		Iterator iterator = coll.iterator();
		while (iterator.hasNext()) {
			EBCustodianDocItem item = (EBCustodianDocItem) iterator.next();
			ICustodianDocItem docItem = item.getValue();
			if (!docItem.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				value.addCustodianDocItem(docItem);
			}
		}
		return value;
	}

	/**
	 * Sets the custodian doc object.
	 * @param anICustodianDoc - ICustodianDoc
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException {
		checkVersionMismatch(anICustodianDoc);
		AccessorUtil.copyValue(anICustodianDoc, this, EXCLUDE_METHOD_UPDATE);
		setVersionTime(VersionGenerator.getVersionNumber());
		timestampLatestUpdate(anICustodianDoc);

		// setValue for EBCustodianDocItem
		this.setCustodianDocItems(anICustodianDoc);
	}

	/**
	 * Set custodian doc items 1. put custodian doc items from anICustodianDoc
	 * into itemMap. 2. put custodian doc items from DB into itemCMRMap. 3.
	 * compare the two maps by checkListItemRefID. 3.1 if both have same
	 * checkListItemRefID, then update this custodian doc item into DB. 3.2 if
	 * the checkListItemRefID is not exist in DB, then insert this new custodian
	 * doc item into DB.
	 * @param anICustodianDoc - ICustodianDoc
	 * @throws ConcurrentUpdateException
	 */
	private void setCustodianDocItems(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException {
		try {
			EBCustodianDocItemHome home = getEBCustodianDocItemHome();
			ArrayList custodianDocItemList = anICustodianDoc.getCustodianDocItems();
			Collection custodianDocItemCMRColl = this.getCustodianDocItemCMR();
			HashMap itemMap = new HashMap();
			HashMap itemCMRMap = new HashMap();

			if (custodianDocItemList != null) {
				// DefaultLogger.debug(this,"the custodianDocItemList size is "
				// + custodianDocItemList.size());
				for (int i = 0; i < custodianDocItemList.size(); i++) {
					ICustodianDocItem item = (ICustodianDocItem) custodianDocItemList.get(i);
					itemMap.put(new Long(item.getCheckListItemRefID()), item);
				}
			}
			if (custodianDocItemCMRColl != null) {
				Iterator iterator = custodianDocItemCMRColl.iterator();
				while (iterator.hasNext()) {
					EBCustodianDocItem itemEB = (EBCustodianDocItem) iterator.next();
					itemCMRMap.put(new Long(itemEB.getValue().getCheckListItemRefID()), itemEB);
				}
			}

			Iterator iterator = itemMap.keySet().iterator();
			while (iterator.hasNext()) {
				Long refID = (Long) iterator.next();
				// DefaultLogger.debug(this,
				// "========== setting custodian doc item : " + refID);
				if (itemCMRMap.containsKey(refID)) {
					// DefaultLogger.debug(this,
					// "========== == an existing item");
					ICustodianDocItem updateItem = (ICustodianDocItem) itemMap.get(refID);
					EBCustodianDocItem itemEB = (EBCustodianDocItem) itemCMRMap.get(refID);
					ICustodianDocItem oldItem = itemEB.getValue();
					// if (hasItemChanged(oldItem, updateItem)) {
					// DefaultLogger.debug(this,
					// "========== == an existing item has changed");

					// cms-2183 : do not timestamp all the items. only items
					// changed are to be timestamped.
					// done in trx by comparing staging and actual.
					// timestampLatestUpdate(updateItem);

					// DefaultLogger.debug(this,
					// "========== == an updated item : " + updateItem);
					itemEB.setValue(updateItem);
					// }
				}
				else {
					// DefaultLogger.debug(this, "========== == a new item");
					ICustodianDocItem newItem = (ICustodianDocItem) itemMap.get(refID);

					// cms-2183 : do not timestamp all the items. only items
					// changed are to be timestamped.
					// done in trx by comparing staging and actual.
					// timestampLatestUpdate(updateItem);

					// DefaultLogger.debug(this,
					// "========== == a new item after timestamp : " + newItem);
					EBCustodianDocItem itemObj = home.create(newItem);
					this.getCustodianDocItemCMR().add(itemObj);
				}
			}

		}
		catch (CreateException ec) {
			ec.printStackTrace();
			throw new ConcurrentUpdateException();
		}
		catch (CustodianException e) {
			e.printStackTrace();
			throw new ConcurrentUpdateException();
		}
	}

	/**
	 * Helper method to timestamp the custodian doc with the latest update date.
	 * @param anICustodianDoc - ICustodianDoc
	 */
	private void timestampLatestUpdate(ICustodianDoc anICustodianDoc) {
		anICustodianDoc.setLastUpdateDate(DateUtil.getDate());
	}

	/**
	 * Helper method to timestamp the custodian doc item with the latest update
	 * date.
	 * @param anICustodianDocItem - ICustodianDocItem
	 */
	/*
	 * private void timestampLatestUpdate(ICustodianDocItem anICustodianDocItem)
	 * { anICustodianDocItem.setLastUpdateDate(DateUtil.getDate()); }
	 */

	/**
	 * Helper method to check for changes by comparing new and old item
	 * @param oldItem - ICustodianDocItem. Assumed to be not null.
	 * @param newItem - ICustodianDocItem. Assumed to be not null.
	 * @return boolean
	 */
	/*
	 * private boolean hasItemChanged(ICustodianDocItem oldItem,
	 * ICustodianDocItem newItem) { return
	 * !(oldItem.getStatus().equals(newItem.getStatus()) &&
	 * oldItem.getReason().equals(newItem.getReason())); }
	 */

	/**
	 * Get the list of custodian doc that satisfy the search criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - contains the list of custodian doc that satisfy
	 *         the criteria
	 * @throws SearchDAOException
	 * @throws SearchDAOException
	 */
	public SearchResult ejbHomeGetDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws SearchDAOException {
		ICustodianDAO dao = CustodianDAOFactory.getCustodianDAO();
		return dao.searchCustodianDoc(aCustodianSearchCriteria);
	}

    /**
	 * Get boolean to check whether envelope barcode exist
	 * @param long - limitprofile
     * @param String - envelope barcode
	 * @return boolean - return true/false on whether envelope barcode exist
	 * @throws SearchDAOException
	 */
	public boolean ejbHomeGetCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws SearchDAOException {
		ICustodianDAO dao = CustodianDAOFactory.getCustodianDAO();
		return dao.getCheckEnvelopeBarcodeExist(limitprofile, envBarcode);
	}

    /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
	public boolean ejbHomeGetCheckDocItemBarcodeExist(String envBarcode, long checkListItemRefID) throws SearchDAOException {
		ICustodianDAO dao = CustodianDAOFactory.getCustodianDAO();
		return dao.getCheckDocItemBarcodeExist(envBarcode, checkListItemRefID);
	}

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem ejbHomeGetSecEnvItemLoc(String envBarcode) throws SearchDAOException {
		ICustodianDAO dao = CustodianDAOFactory.getCustodianDAO();
		return dao.getSecEnvItemLoc(envBarcode);
	}
	/*
	 * public ICustodianDoc ejbHomeGetNewCustodianDoc(long aCheckListItemID)
	 * throws SearchDAOException { ICustodianDAO dao =
	 * CustodianDAOFactory.getCustodianDAO(); return
	 * dao.getNewCustodianDoc(aCheckListItemID); }
	 */

	/**
	 * Get a new custodian doc given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianDoc
	 * @throws SearchDAOException
	 */
	public ICustodianDoc ejbHomeGetNewDoc(long checkListID) throws SearchDAOException {
		ICustodianDAO dao = CustodianDAOFactory.getCustodianDAO();
		return dao.getNewDoc(checkListID);
	}

	/**
	 * Get a list of new custodian doc items not yet created given a custodian
	 * doc ID.
	 * 
	 * @param custodianDocID - long
	 * @return ICustodianDocItem[]
	 * @throws SearchDAOException
	 */
	public ICustodianDocItem[] ejbHomeGetNewItems(long custodianDocID) throws SearchDAOException {
		ICustodianDAO dao = CustodianDAOFactory.getCustodianDAO();
		return dao.getNewItems(custodianDocID);
	}

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 * @throws SearchDAOException
	 */
	public long ejbHomeGetTrxID(CustodianSearchCriteria searchCriteria) throws SearchDAOException {
		ICustodianDAO dao = CustodianDAOFactory.getCustodianDAO();
		return dao.getTrxID(searchCriteria);
	}

	/**
	 * Create a custodian doc record
	 * @param anICustodianDoc - ICustodianDoc
	 * @return Long - the custodian doc ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICustodianDoc anICustodianDoc) throws CreateException {
		try {
			String custodianDocID = (new SequenceManager()).getSeqNum(SEQUENCE_NAME, true);
			AccessorUtil.copyValue(anICustodianDoc, this, EXCLUDE_METHOD_CREATE);
			setVersionTime(VersionGenerator.getVersionNumber());
			setCMPCustodianDocID(new Long(custodianDocID));
			return new Long(custodianDocID);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CreateException("Caught unknown exception: " + ex.toString());
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anICustodianDoc - ICustodianDoc
	 */
	public void ejbPostCreate(ICustodianDoc anICustodianDoc) throws CreateException {
		try {
			ArrayList custodianDocItemList = anICustodianDoc.getCustodianDocItems();
			if (custodianDocItemList != null) {
				for (int i = 0; i < custodianDocItemList.size(); i++) {
					ICustodianDocItem item = (ICustodianDocItem) custodianDocItemList.get(i);
					EBCustodianDocItem itemObj = this.getEBCustodianDocItemHome().create(item);
					this.getCustodianDocItemCMR().add(itemObj);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Caught unknown exception: " + e.toString());
		}
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
	 * Check the version of this persistEntity with database version.
	 * @param anICustodianDoc - ICustodianDoc
	 * @throws ConcurrentUpdateException if the entity version is different from
	 *         database version
	 */
	protected void checkVersionMismatch(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException {
		if (getVersionTime() != anICustodianDoc.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp");
		}
	}

	/**
	 * Method to get EB Home for custodian doc item
	 */
	protected EBCustodianDocItemHome getEBCustodianDocItemHome() throws CustodianException {
		EBCustodianDocItemHome home = (EBCustodianDocItemHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CUSTODIAN_DOC_ITEM_HOME, EBCustodianDocItemHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CustodianException("EBCustodianDocItemHome is null!");
	}

}