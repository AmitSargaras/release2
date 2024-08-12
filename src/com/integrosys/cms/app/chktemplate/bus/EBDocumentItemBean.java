/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBDocumentItemBean.java,v 1.8 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//javax

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.TypeConverter;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;

/**
 * This entity bean represents the persistence for document item Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public abstract class EBDocumentItemBean implements EntityBean, IDocumentItem {
	private static final String[] EXCLUDE_METHOD = new String[] { "getItemID" , "getCMRDocAppItemList" };

	private static final String ITEM_CODE_FORMATTER_CLASS = "com.integrosys.cms.app.chktemplate.bus.DocumentItemCodeSequenceFormatter";

	private static final String ITEM_CODE_SEQUENCE_CLASS = PropertyManager.getValue("DefaultSequencerClass");

	private static final String CC_ITEM_CODE_GENERATION_FLAG = PropertyManager.getValue("cctemplate.generateItemCode");

	private static final String SEC_ITEM_CODE_GENERATION_FLAG = PropertyManager
			.getValue("sectemplate.generateItemCode");

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBDocumentItemBean() {
	}

	// *************Override Inteface Methods (Java 1.4 requirement)
	// **************
	public abstract String getItemCode();

	public abstract String getItemDesc();

	public abstract String getItemType();
	
	public abstract int getTenureCount();
	
	public abstract String getTenureType();
	
	public abstract String getSkipImgTag();
	
	public abstract String getStatementType();
	
	public abstract void setStatementType(String anStatementType);
	
	public abstract String getIsRecurrent();

	public abstract void setIsRecurrent(String isRecurrent);
	
	public abstract String getRating();
	
	public abstract void setRating(String rating) ;

	public abstract String getSegment();

	public abstract void setSegment(String segment);

	public abstract String getTotalSancAmt();

	public abstract void setTotalSancAmt(String totalSancAmt);

	public abstract String getClassification() ;

	public abstract void setClassification(String classification);

	public abstract String getGuarantor() ;

	public abstract void setGuarantor(String guarantor) ;
	
	public abstract String getStatus();
	
	public abstract String getDeprecated();

	public abstract Date getExpiryDate();

	public abstract String getMonitorType();

	public abstract String getDocumentVersion();

	public abstract String getLoanApplicationType();

	public abstract void setTenureCount(int anTenureCount);
	
	public abstract void setTenureType(String anTenureType);
	
	public abstract void setSkipImgTag(String anSkipImgTag);
	
	public abstract void setStatus(String aStatus);
	
	public abstract void setDeprecated(String anDeprecated);

	public abstract void setItemDesc(String anItemDesc);

	public abstract void setItemType(String anItemType);
	
	public abstract void setItemCode(String anItemCode);

	public abstract void setExpiryDate(Date anExpiryDate);

	public abstract void setMonitorType(String aMonitorType);

	public abstract void setDocumentVersion(String docVersion);

	public abstract void setLoanApplicationType(String type);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long l);

	// ************** Abstract methods ************
	public abstract Long getCMPItemID();

	public abstract String getIsForBorrowerStr();

	public abstract String getIsForPledgorStr();

	public abstract String getIsPreApproveStr();

	// -------------------------------------------------

	public abstract void setCMPItemID(Long anItemID);

	public abstract void setIsForBorrowerStr(String flag);

	public abstract void setIsForPledgorStr(String flag);

	public abstract void setIsPreApproveStr(String flag);
	
	public abstract Collection getCMRDocAppItemList();
	public abstract void setCMRDocAppItemList(Collection docAppItemList);
	
	public abstract String getIsApplicableForCersaiInd();
	public abstract void setIsApplicableForCersaiInd(String isApplicableForCersaiInd);

	// ************** Not In Used ************
	public IDynamicProperty[] getPropertyList() {
		return null;
	}

	public void setPropertyList(IDynamicProperty[] propertyList) {
		// do nothing
	}

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the item ID
	 * @return long - the item ID
	 */
	public long getItemID() {
		if (getCMPItemID() != null) {
			return getCMPItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get is-for-borrower indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsForBorrower() {
		return TypeConverter.convertStringToBooleanEquivalent(getIsForBorrowerStr());
	}

	/**
	 * Helper method to get is-for-pledgor indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsForPledgor() {
		return TypeConverter.convertStringToBooleanEquivalent(getIsForPledgorStr());
	}

	/**
	 * Helper method to get pre-approve indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsPreApprove() {
		return TypeConverter.convertStringToBooleanEquivalent(getIsPreApproveStr());
	}

	/**
	 * Helper method to set the item ID
	 * @param anItemID - long
	 */
	public void setItemID(long anItemID) {
		setCMPItemID(new Long(anItemID));
	}

	/**
	 * Helper method to set is-for-borrower indicator
	 * @param flag - boolean
	 */
	public void setIsForBorrower(boolean flag) {
		setIsForBorrowerStr(flag ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set is-for-pledgor indicator
	 * @param flag - boolean
	 */
	public void setIsForPledgor(boolean flag) {
		setIsForPledgorStr(flag ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set pre-approve indicator
	 * @param flag - boolean
	 */
	public void setIsPreApprove(boolean flag) {
		setIsPreApproveStr(flag ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	// *****************************************************
	/**
	 * Create a document item Information
	 * @param anIDocumentItem - the document item
	 * @return Long - the document item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IDocumentItem anIDocumentItem) throws CreateException {
		if (anIDocumentItem == null) {
			throw new CreateException("IDocumentItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			preCreationProcess(anIDocumentItem);
			AccessorUtil.copyValue(anIDocumentItem, this, EXCLUDE_METHOD);
			setItemID(pk);
			setVersionTime(VersionGenerator.getVersionNumber());	
			
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("fail to create document item [" + anIDocumentItem + "]");
			cex.initCause(ex);
			throw cex;
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param anIDocumentItem - the document item
	 */
	public void ejbPostCreate(IDocumentItem anIDocumentItem) throws CreateException{
		try {
		//createDocumentAppItem(anIDocumentItem);
		}catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("fail to create document item [" + anIDocumentItem + "]");
			cex.initCause(ex);
			throw cex;
		}
	}

	/**
	 * Return the Interface representation of this object
	 * @return IDocumentItem
	 */
	public IDocumentItem getValue() throws CheckListTemplateException {
		OBDocumentItem value = new OBDocumentItem();
		value.setDocumentVersion(this.getDocumentVersion());
		value.setExpiryDate(this.getExpiryDate());
		value.setIsForBorrower(this.getIsForBorrower());
		value.setIsForPledgor(this.getIsForPledgor());
		value.setIsPreApprove(this.getIsPreApprove());
		value.setTenureCount(this.getTenureCount());
		value.setTenureType(this.getTenureType());
		value.setSkipImgTag(this.getSkipImgTag());
		value.setStatementType(this.getStatementType());
		value.setIsRecurrent(this.getIsRecurrent());
		value.setRating(this.getRating());
		value.setSegment(this.getSegment());
		value.setTotalSancAmt(this.getTotalSancAmt());
		value.setClassification(this.getClassification());
		value.setGuarantor(this.getGuarantor());
		value.setDeprecated(this.getDeprecated());
		value.setStatus(this.getStatus());
		value.setItemCode(this.getItemCode());
		value.setItemDesc(this.getItemDesc());
		value.setItemID(this.getItemID());
		value.setItemType(this.getItemType());
		value.setLoanApplicationType(this.getLoanApplicationType());
		value.setMonitorType(this.getMonitorType());
		value.setPropertyList(this.getPropertyList());
		value.setVersionTime(this.getVersionTime());
		value.setCMRDocAppItemList(retrieveDocAppItemList());
		value.setIsApplicableForCersaiInd(this.getIsApplicableForCersaiInd());
		value.setOldItemCode(this.getOldItemCode());
		
		//AccessorUtil.copyValue(this, value);
		return value;
	}
	
	private Collection retrieveDocAppItemList() throws CheckListTemplateException {
		try {
			Collection col = getCMRDocAppItemList();

			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				List cList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBDocAppItemLocal local = (EBDocAppItemLocal) iter.next();
					IDocumentAppTypeItem obj = local.getValue();
					cList.add(obj);

				}
				return  cList;
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("failed to retrieve checklist dynamic property list ", ex);
		}

	}

	/**
	 * Persist a document item information
	 * @param anIDocumentItem - IDocumentItem
	 * @throws ConcurrentUpdateException if enctr concurrent update
	 */
	public void setValue(IDocumentItem anIDocumentItem) throws CheckListTemplateException, ConcurrentUpdateException {
		
		try{
		if (getVersionTime() != anIDocumentItem.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp");
		}
		AccessorUtil.copyValue(anIDocumentItem, this, EXCLUDE_METHOD);
		updateDocumentAppItem(anIDocumentItem);
		setVersionTime(VersionGenerator.getVersionNumber());
		}		catch (Exception ex) {
					throw new CheckListTemplateException("Exception in createTemplateItems: ", ex);
		}
	}
	
	private void updateDocumentAppItem(IDocumentItem anIDocumentItem) throws CheckListTemplateException
	{
		try
		{
			Collection col = getCMRDocAppItemList();
			
			if(anIDocumentItem.getCMRDocAppItemList()== null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				deleteDocumentAppItems(new ArrayList(col));
				return;
			}
			
			if ((col == null) || (col.size() == 0)) {
				// create new records
				createDocumentAppItem(anIDocumentItem);
				return;
			}
			
			Iterator iter = col.iterator();
			ArrayList createList = new ArrayList();
			ArrayList deleteList = new ArrayList(); // contains list of local
			// interfaces
			
			// identify identify records for delete or udpate first
			while (iter.hasNext()) {
				EBDocAppItemLocal local = (EBDocAppItemLocal) iter.next();
				long documetId = local.getDocumentId().longValue();
				String appType = local.getAppType();
				boolean update = false;
				
				
				Iterator docIter = anIDocumentItem.getCMRDocAppItemList().iterator();
				while(docIter.hasNext())
				{
					IDocumentAppTypeItem newDoc = (IDocumentAppTypeItem) docIter.next();
					if(newDoc.getDocumentId().longValue() == documetId)
					{
						// perform update
						local.setValue(newDoc);
						update = true;
						break;
						
					}

				}
				
				if (!update) {
					// add for delete
					deleteList.add(local);
				}
				
			}
			
			
			// next identify records for add
			Iterator docIter = anIDocumentItem.getCMRDocAppItemList().iterator();
			while(docIter.hasNext())
			{
				IDocumentAppTypeItem newDoc = (IDocumentAppTypeItem) docIter.next();
				if(newDoc.getDocumentLoanId().longValue() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
				{
					createList.add(newDoc);
				}
			}
			
			deleteDocumentAppItems(deleteList);
			createDocumentAppItem(anIDocumentItem);
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in updating App Document items: ", ex);
		}
	}
	
	private void createDocumentItems(List aCreationList) throws CheckListTemplateException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRDocAppItemList();
		Iterator iter = aCreationList.iterator();
		try {
			EBDocAppItemLocalHome home = getEBDocAppItemLocalHome();
			while (iter.hasNext()) {
				IDocumentAppTypeItem obj = (IDocumentAppTypeItem) iter.next();
				// To generate the item code if the item id is
				// com.integrosys.cms
				// .app.common.constant.ICMSConstant.LONG_INVALID_VALUE as this
				// means that the item is not from the global checklist

                EBDocAppItemLocal local = home.create(getCMPItemID(), obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in createTemplateItems: ", ex);
		}
	}
	
	private void deleteDocumentAppItems(List aDeletionList) throws CheckListTemplateException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRDocAppItemList();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBDocAppItemLocal local = (EBDocAppItemLocal) iter.next();
				col.remove(local);
				local.remove();
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in deleteTemplateItems: ", ex);
		}
	}
	
	private void deleteTemplateItems(List aDeletionList) throws CheckListTemplateException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRDocAppItemList();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBTemplateItemLocal local = (EBTemplateItemLocal) iter.next();
				col.remove(local);
				local.remove();
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in deleteTemplateItems: ", ex);
		}
	}
	
	public void createDocumentAppItem(IDocumentItem anIDocumentItem) throws CheckListTemplateException {
		List documentAppTypeItemListings = new ArrayList();
		Iterator iter = anIDocumentItem.getCMRDocAppItemList().iterator();
		Collection col = this.getCMRDocAppItemList();
		
		try{
			while(iter.hasNext())
			{
				IDocumentAppTypeItem aDocumentAppTypeItem = (OBDocumentAppTypeItem)iter.next();
				EBDocAppItemLocalHome home = getEBDocAppItemLocalHome();
				EBDocAppItemLocal local =  home.create(getCMPItemID() , aDocumentAppTypeItem);
				col.add(local);
			}
			
			
			
		}
		catch (Exception ex) {
		throw new CheckListTemplateException("Exception in createDocAppItem: ", ex);
		} 
	}
	
	protected EBDocAppItemLocalHome getEBDocAppItemLocalHome() throws CheckListTemplateException {
		EBDocAppItemLocalHome home = (EBDocAppItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DOC_APP_ITEM_LOCAL_JNDI, EBDocAppItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListTemplateException("EBDocAppItemLocalHome is null!");
	}

	/**
	 * Get the list of document items
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the result containing the list of document items
	 *         that satisfy the criteria
	 * @throws SearchDAOException is error at the DAO
	 */
	public SearchResult ejbHomeGetDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException {
		return CheckListTemplateDAOFactory.getDocumentDAO().searchDocumentItemList(aCriteria);
	}
	
	public SearchResult ejbHomeGetFilteredDocumentItemList(DocumentSearchCriteria aCriteria,List docCrit) throws SearchDAOException {
		return CheckListTemplateDAOFactory.getDocumentDAO().searchFilteredDocumentItemList(aCriteria,docCrit);
	}

	/**
	 * Get the number of doc item under the same category and having the same
	 * description
	 * @param aCategory of String type
	 * @param aDescription of String type
	 * @return int - the number of doc items
	 * @throws SearchDAOException
	 */
	public int ejbHomeGetNoOfDocItemByDesc(String aCategory, String aDescription) throws SearchDAOException {
		return CheckListTemplateDAOFactory.getDocumentDAO().getNoOfDocItemByDesc(aCategory, aDescription);
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

	/**
	 * Get the name of the sequence to be used for the item id
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_DOCUMENT_ITEM;
	}

	/**
	 * To system generate the item code
	 * @param anIDocumentItem - IDocumentItem
	 * @throws Exception on errors
	 */
	protected void preCreationProcess(IDocumentItem anIDocumentItem) throws Exception {
		String flag = (anIDocumentItem.getItemType().equals(ICMSConstant.DOC_TYPE_CC)) ? CC_ITEM_CODE_GENERATION_FLAG
				: SEC_ITEM_CODE_GENERATION_FLAG;
		if (Boolean.getBoolean(flag)) { // if true
			anIDocumentItem.setItemCode(generateItemCode());
		}
	}

	/**
	 * Generate the item code
	 * @return String - the item code generated
	 */
	private String generateItemCode() throws Exception {
		return (new SequenceManager(ITEM_CODE_SEQUENCE_CLASS, ITEM_CODE_FORMATTER_CLASS)).getSeqNum(
				ICMSConstant.SEQUENCE_DOCUMENT_ITEM_CODE, true);
	}
	


	public abstract String getOldItemCode();
	public abstract void setOldItemCode(String anOldItemCode);
}