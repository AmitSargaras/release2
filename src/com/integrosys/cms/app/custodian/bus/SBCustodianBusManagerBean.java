/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/SBCustodianBusManagerBean.java,v 1.27 2005/10/14 02:30:05 whuang Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.EBCMSCustomerHome;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Session bean implementation of the services provided by the custodian bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.27 $
 * @since $Date: 2005/10/14 02:30:05 $ Tag: $Name: $
 */
public class SBCustodianBusManagerBean extends AbstractCustodianBusManager implements SessionBean {
	/**
	 * Default constructor.
	 */

	public SBCustodianBusManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

    public void setSessionContext(javax.ejb.SessionContext sc) {
	}
	/**
	 * Create a custodian document in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the created custodian doc
	 * @throws CustodianException
	 */
	public ICustodianDoc create(ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			EBCustodianDocLocal custDoc = getEBCustodianDocLocalHome().create(anICustodianDoc);
			return custDoc.getValue();
		}
		catch (CreateException cex) {
			throw new CustodianException(cex.toString());
		}
	}

	/**
	 * Update a custodian that is already in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the updated custodian doc
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public ICustodianDoc update(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException, CustodianException {
		try {
			Long pk = new Long(anICustodianDoc.getCustodianDocID());
			EBCustodianDocLocal custDoc = getEBCustodianDocLocalHome().findByPrimaryKey(pk);
			custDoc.setValue(anICustodianDoc);
			return custDoc.getValue();
		}
		catch (ConcurrentUpdateException vex) {
			throw vex;
		}
		catch (FinderException fex) {
			throw new CustodianException(fex.toString());
		}
	}

	/**
	 * Delete a custodian document item from the custodian registry. This will
	 * perform a soft delete.
	 * 
	 * @param aCustodianDocItemID - long
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public void delete(long aCustodianDocItemID) throws ConcurrentUpdateException, CustodianException {
		try {
			Long pk = new Long(aCustodianDocItemID);
			EBCustodianDocItem remoteItem = getEBCustodianDocItemHome().findByPrimaryKey(pk);
			ICustodianDocItem item = remoteItem.getValue();
			item.setStatus(ICMSConstant.STATE_DELETED);
			remoteItem.setValue(item);
		}
		catch (ConcurrentUpdateException vex) {
			throw vex;
		}
		catch (FinderException fex) {
			throw new CustodianException(fex.toString());
		}
	}

	/**
	 * Retrieve a custodian document from the custodian registry.
	 * @param aCustodianID - long
	 * @return ICustodianDoc - the object encapsulating the custodian document
	 *         info
	 * @throws CustodianException
	 */
	public ICustodianDoc getCustodianDoc(long aCustodianID) throws CustodianException {
		try {
			Long pk = new Long(aCustodianID);
			EBCustodianDocLocal custDoc = getEBCustodianDocLocalHome().findByPrimaryKey(pk);
			ICustodianDoc doc = custDoc.getValue();
			doc = setOwnerInfo(doc);
			return doc;
		}
		catch (FinderException fex) {
			throw new CustodianException(fex.toString());
		}
	}

	/**
	 * Set the owner info for custodian doc
	 * @param anICustodianDoc of ICustodianDoc type
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc setOwnerInfo(ICustodianDoc anICustodianDoc) throws CustodianException {
		if (ICMSConstant.DOC_TYPE_CC.equals(anICustodianDoc.getDocType())) {
			setCCOwnerInfo(anICustodianDoc);
		}
		else {
			setCollateralOwnerInfo(anICustodianDoc);
		}
		return anICustodianDoc;
	}

	/**
	 * Set the owner info for cc custodian doc
	 * @param anICustodianDoc of ICustodianDoc type
	 * @throws CustodianException on errors
	 */
	private void setCCOwnerInfo(ICustodianDoc anICustodianDoc) throws CustodianException {
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(anICustodianDoc.getDocSubType())) {
			setBorrowerInfo(anICustodianDoc);
		}
		else if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(anICustodianDoc.getDocSubType())) {
			setBorrowerInfo(anICustodianDoc);
		}
		else if (ICMSConstant.CHECKLIST_PLEDGER.equals(anICustodianDoc.getDocSubType())) {
			setPledgorInfo(anICustodianDoc);
		}
		return;
	}

	private void setBorrowerInfo(ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			CCCustodianInfo info = new CCCustodianInfo();
			if (anICustodianDoc.getSubProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
				ICMSCustomer customer = custProxy.getCustomer(anICustodianDoc.getSubProfileID());
				info.setOwnerReference(customer.getCustomerReference());
				info.setLegalReference(customer.getCMSLegalEntity().getLEReference());
				info.setLegalName(customer.getCMSLegalEntity().getLegalName());
			}
			anICustodianDoc.setCCCustodianInfo(info);
		}
		catch (CustomerException ex) {
			throw new CustodianException("Caught CustomerException in setCoBorrowerInfo", ex);
		}
	}

	private void setPledgorInfo(ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			IPledgor pledgor = proxy.getPledgor(anICustodianDoc.getPledgorID());
			CCCustodianInfo info = new CCCustodianInfo();
			long pledgor_ref = pledgor.getSysGenPledgorID();
			if (pledgor_ref > 0) {
				info.setOwnerReference(String.valueOf(pledgor_ref));
			}
			String pledgor_leID = pledgor.getLegalID();
			if ((pledgor_leID != null) && (pledgor_leID.trim().length() > 0)) {
				info.setLegalReference(pledgor_leID);
			}
			info.setLegalName(pledgor.getPledgorName());
			anICustodianDoc.setCCCustodianInfo(info);
		}
		catch (CollateralException ex) {
			throw new CustodianException("Caught CollateralException in setPledgorInfo", ex);
		}
	}

	private void setCollateralOwnerInfo(ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateral collateral = proxy.getCollateral(anICustodianDoc.getCollateralID(), false);
			CollateralCustodianInfo info = new CollateralCustodianInfo();
			info.setCollateralType(collateral.getCollateralType());
			info.setCollateralSubType(collateral.getCollateralSubType());
            info.setSciReferenceNote(collateral.getSCIReferenceNote());
            info.setCommentRemarks(collateral.getComment());
			String colRef = collateral.getSCISecurityID();
			if ((colRef != null) && (colRef.trim().length() > 0)) {
				info.setCollateralReference(colRef);
			}
			anICustodianDoc.setCollateralCustodianInfo(info);
		}
		catch (CollateralException ex) {
			throw new CustodianException("Caught CollateralException in setCollateralOwnerInfo", ex);
		}
	}

	/**
	 * Retrieve a list of custodian documents from the custodian registry.
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - contain the list of custodian document retrieved
	 *         based on the search criteria
	 * @throws CustodianException
	 */
	public SearchResult getCustodianDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException {
		try {
			return getEBCustodianDocLocalHome().getDocList(aCustodianSearchCriteria);
		}
		catch (SearchDAOException ex) {
			throw new CustodianException(ex);
		}
	}


    /**
	 * Get boolean to check whether envelope barcode exist
	 * @param long - limitprofile
     * @param String - envelope barcode
	 * @return boolean - return true/false on whether envelope barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws CustodianException {
		try {
			return getEBCustodianDocLocalHome().getCheckEnvelopeBarcodeExist(limitprofile, envBarcode);
		}
		catch (SearchDAOException ex) {
			throw new CustodianException(ex);
		}
	}

    /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID) throws CustodianException {
		try {
			return getEBCustodianDocLocalHome().getCheckDocItemBarcodeExist(docItemBarcode, checkListItemRefID);
		}
		catch (SearchDAOException ex) {
			throw new CustodianException(ex);
		}
	}

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode) throws CustodianException {
		try {
			return getEBCustodianDocLocalHome().getSecEnvItemLoc(docItemBarcode);
		}
		catch (SearchDAOException ex) {
			throw new CustodianException(ex);
		}
	}

	/**
	 * Persist custodian print authorization details...
	 * @param custAuthz - String[]
	 * @throws CustodianException if errors
	 */
	public void persistPrintAuthzDetails(OBCustAuthorize[] custAuthz) throws CustodianException {
		// write a new eb and persist the details..
		if ((custAuthz == null) || (custAuthz.length < 1)) {
			throw new CustodianException("no id's in input param for processing..");
		}
		try {
			for (int i = 0; i < custAuthz.length; i++) {
				Date now = new Date();
				custAuthz[i].setAuthzDate(now);
				getEBCustodianAuthzHome().create(custAuthz[i]);
			}
		}
		catch (CreateException e) {
			throw new CustodianException(e.toString());
		}
		catch (RemoteException e) {
			throw new CustodianException(e.toString());
		}
	}

	/**
	 * Get the list of custodian doc by collateral ID
	 * @param aCollateralID - long
	 * @return ICustodianDoc[] - the list of custodian doc. Null if no custodian
	 *         doc found
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc[] getCustodianDocsByCollateralID(long aCollateralID) throws CustodianException {
		try {
			Collection docRemoteList = getEBCustodianDocLocalHome().findByCollateralID(new Long(aCollateralID));
			if ((docRemoteList == null) || (docRemoteList.size() == 0)) {
				return null;
			}
			Iterator iter = docRemoteList.iterator();
			EBCustodianDoc remote = null;
			ICustodianDoc[] docList = new ICustodianDoc[docRemoteList.size()];
			int ctr = 0;
			while (iter.hasNext()) {
				remote = (EBCustodianDoc) iter.next();
				docList[ctr] = remote.getValue();
				ctr++;
			}
			return docList;
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "Not custodian doc with collateralID " + aCollateralID + " exist !!!");
			return null;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the custodian doc by checklist item ID
	 * @param aCheckListID of long type
	 * @return ICustodianDoc - the custodian doc with the checklist item ID
	 * @throws CustodianException on errors
	 */



	public ICustodianDoc getCustodianDocByCheckList(long aCheckListID) throws CustodianException, FinderException {
		try {
            EBCustodianDocLocal custDoc = getEBCustodianDocLocalHome().findByCheckList(new Long(aCheckListID));
			return custDoc.getValue();
        }catch (FinderException ex) {
			DefaultLogger.info(this, "custodian doc with checkList ID " + aCheckListID + " does not exist !!!");
            throw ex;
		}catch(Exception ex){
            throw new CustodianException("Exception in getCustodianDocByCheckList: " + ex.toString());
        }
	}

	/*
	 * public ICustodianDoc getNewCustodianDoc(long aCheckListItemID) throws
	 * CustodianException { try { return
	 * getEBCustodianDocLocalHome().getNewCustodianDoc(aCheckListItemID); }
	 * catch(SearchDAOException ex) { throw new CustodianException(ex); } }
	 */

	/**
	 * Get a new custodian doc given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianDoc
	 * @throws CustodianException
	 */
	public ICustodianDoc getNewDoc(long checkListID) throws CustodianException {
		ICustodianDoc doc = null;
		try {
			DefaultLogger.debug(this, "getNewDoc - checkListID : " + checkListID);
			doc = getEBCustodianDocLocalHome().getNewDoc(checkListID);
			populateCheckListItem(doc);
		}
		catch (Exception ex) {
			throw new CustodianException(ex);
		}
		return doc;
	}

	/**
	 * Get a list of new custodian doc items not yet created given a custodian
	 * doc ID.
	 * 
	 * @param custodianDocID - long
	 * @return ICustodianDocItem[]
	 * @throws CustodianException
	 */
	public ICustodianDocItem[] getNewItems(long custodianDocID) throws CustodianException {
		try {
			DefaultLogger.debug(this, "getNewItems - custodianDocID : " + custodianDocID);
			return getEBCustodianDocLocalHome().getNewItems(custodianDocID);
		}
		catch (SearchDAOException ex) {
			throw new CustodianException(ex);
		}
	}

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws CustodianException {
		try {
			return getEBCustodianDocLocalHome().getTrxID(searchCriteria);
		}
		catch (SearchDAOException ex) {
			throw new CustodianException(ex);
		}
	}

	/**
	 * To get the home handler for the Custodian Doc Entity Bean
	 * @return EBCustodianDocLocalHome - the home handler for the custodian doc
	 *         entity bean
	 */
	protected EBCustodianDocLocalHome getEBCustodianDocLocalHome() {
		EBCustodianDocLocalHome ejbHome = (EBCustodianDocLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CUSTODIAN_DOC_LOCAL_HOME, EBCustodianDocLocalHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the Custodian Doc Entity Bean
	 * @return EBCustodianDocItemHome - the home handler for the custodian doc
	 *         entity bean
	 */
	protected EBCustodianDocItemHome getEBCustodianDocItemHome() {
		EBCustodianDocItemHome home = (EBCustodianDocItemHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CUSTODIAN_DOC_ITEM_HOME, EBCustodianDocItemHome.class.getName());
		return home;
	}

	/**
	 * To get the home handler for the Customer Entity Bean
	 * @return EBCMSCustomerHome - the home handler for the Customer entity bean
	 */
	protected EBCMSCustomerHome getEBCMSCustomerHome() {
		EBCMSCustomerHome ejbHome = (EBCMSCustomerHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CUSTOMER_JNDI,
				EBCMSCustomerHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the Custodian Authz Entity Bean
	 * @return EBCustodianAuthzHome - the home handler for the Custodian Authz
	 *         entity bean
	 */
	protected EBCustodianAuthzHome getEBCustodianAuthzHome() {
		EBCustodianAuthzHome ejbHome = (EBCustodianAuthzHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CUSTODIAN_AUTHZ_HOME, EBCustodianAuthzHome.class.getName());
		return ejbHome;
	}

	/**
	 * Helper method to populate the custodian doc items with related checklist
	 * items.
	 * 
	 * @param doc - ICustodianDoc
	 */
	private void populateCheckListItem(ICustodianDoc doc) throws CheckListException {
		if ((doc == null) || (doc.getCustodianDocItems() == null)) {
			return;
		}
		ArrayList itemsList = doc.getCustodianDocItems();
		Iterator itemsIterator = itemsList.iterator();
		ICheckListProxyManager checklistProxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		while (itemsIterator.hasNext()) {
			ICustodianDocItem custItem = (ICustodianDocItem) itemsIterator.next();
			ICheckListItem chklistItem = checklistProxy.getCheckListItem(custItem.getCheckListItemRefID());
			custItem.setCheckListItem(chklistItem);
		}
	}
}