/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplateBean.java,v 1.22 2003/08/23 06:59:16 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
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
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;

/**
 * Implementation for the template entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2003/08/23 06:59:16 $ Tag: $Name: $
 */

public abstract class EBTemplateBean implements EntityBean, ITemplate {

	private static final long serialVersionUID = 5403155675685256415L;

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String ITEM_CODE_FORMATTER_CLASS = "com.integrosys.cms.app.chktemplate.bus.TemplateItemCodeSequenceFormatter";

	private static final String DOC_ITEM_CODE_FORMATTER_CLASS = "com.integrosys.cms.app.chktemplate.bus.DocumentItemCodeSequenceFormatter";

	private static final String ITEM_CODE_SEQUENCE_CLASS_PROPERTY = "DefaultSequencerClass";

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getTemplateID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getTemplateID" };

	/**
	 * Default Constructor
	 */
	public EBTemplateBean() {
	}

	public abstract String getTemplateType();

	public abstract String getLegalConstitution();

	public abstract String getLaw();

	public abstract String getCountry();

	public abstract String getCollateralType();

	public abstract String getCollateralSubType();

	public abstract void setTemplateType(String aTemplateType);

	public abstract void setLegalConstitution(String aLegalConstitution);

	public abstract void setLaw(String aLaw);

	public abstract void setCountry(String aCountry);

	public abstract void setCollateralType(String aCollateralType);

	public abstract void setCollateralSubType(String aCollateralSubType);
	
	public abstract void setCollateralId(String aCollateralId);
	
	public abstract String getCollateralId();

	public abstract long getVersionTime();

	public abstract void setVersionTime(long l);

	// ************** Abstract methods ************
	public abstract Long getCMPTemplateID();

	public abstract Long getCMPParentTemplateID(); // have to use Long as there

	// is a FK constraint so
	// cannot store
	// com.integrosys
	// .cms.app.common
	// .constant.ICMSConstant
	// .LONG_INVALID_VALUE

	/**
	 * Get the template ID
	 * @return long - the long value of the template ID
	 */
	public long getTemplateID() {
		if (getCMPTemplateID() != null) {
			return getCMPTemplateID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the parent template ID.
	 * @return long - the long value of the parent template ID
	 */
	public long getParentTemplateID() {
		if (getCMPParentTemplateID() != null) {
			return getCMPParentTemplateID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Not implemented here
	 */
	public ITemplate getTemplate() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public ITemplateItem[] getTemplateItemList() {
		return null;
	}

	// setters
	public abstract void setCMPTemplateID(Long aTemplateID);

	public abstract void setCMPParentTemplateID(Long aParentTemplateID);

	/**
	 * Helper method to set the parent template ID
	 * @param aParentTemplateID - long
	 */
	public void setParentTemplateID(long aParentTemplateID) {
		// Due to the FK constraints, cannot set
		// com.integrosys.cms.app.common.constant
		// .ICMSConstant.LONG_INVALID_VALUE as default
		if (aParentTemplateID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			setCMPParentTemplateID(new Long(aParentTemplateID));
		} else {
            setCMPParentTemplateID(null);           //handle websphere 
        }
	}

	/**
	 * Helper method to set the template ID
	 * @param aTemplateID - long
	 */
	public void setTemplateID(long aTemplateID) {
		setCMPTemplateID(new Long(aTemplateID));
	}

	/**
	 * Not implemented here
	 */
	public void setTemplate(ITemplate anITemplate) {
		// do nothing
	}

	/**
	 * Not implemented here
	 */
	public void setTemplateItemList(ITemplateItem[] anITemplateItemList) {
		// do nothing
	}

	// ************** CMR methods ***************
	// Getters
	/**
	 * Get all Template Items
	 * 
	 * @return Collection of EBTemplateItemLocal objects
	 */
	public abstract Collection getCMRTemplateItems();

	// Setters
	/**
	 * Set all Template Items
	 * 
	 * @param aTemplateItems is of type Collection of EBTemplateItemLocal
	 *        objects
	 */
	public abstract void setCMRTemplateItems(Collection aTemplateItems);

	/**
	 * Add a item into the template
	 * @param anItem - IItem
	 */
	public void addItem(IItem anItem) {
		// do nothing
	}

	/**
	 * Add an list of items into the template
	 * @param anItemList - IItem[]
	 */
	public void addItems(IItem[] anItemList) {
		// do nothing
	}

	/**
	 * Remove a list of items into the template
	 * @param anItemIndexList - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList) {
		// do nothing
	}

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IItem anItem) {
		// do nothing
	}

	/**
	 * Return a template object
	 * @return ITemplate - the object containing the template object
	 */
	public ITemplate getValue() throws CheckListTemplateException {
		ITemplate template = new OBTemplate();
		AccessorUtil.copyValue(this, template, null);
		template.setTemplateItemList(retrieveTemplateItems());
		if (template.getParentTemplateID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			ITemplate parent = retrieveTemplate(template.getParentTemplateID());
			template = MergeTemplateUtilFactory.getMergeTemplateUtil().prepareTemplateForRetrieval(parent, template);
		}
		ITemplateItem[] itemList = template.getTemplateItemList();
		if (itemList != null) {
			Arrays.sort(itemList);
		}
		template.setTemplateItemList(itemList);
        return template;
	}

	/**
	 * To retrieve the template of which this template inherited
	 * @param aTemplateID - long
	 * @return ITemplate - the master template that this template inherited
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplate retrieveTemplate(long aTemplateID) throws CheckListTemplateException {
		try {
			EBTemplateLocalHome home = getEBTemplateLocalHome();
			EBTemplateLocal local = home.findByPrimaryKey(new Long(aTemplateID));
			return local.getValue();
		}
		catch (FinderException ex) {
			return null;
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in retrieveTemplate() : ", ex);
		}
	}

	/**
	 * To retrieve the list of template items
	 * @return ITemplateItem[] - the list of template items
	 * @throws CheckListTemplateException on error
	 */
	private ITemplateItem[] retrieveTemplateItems() throws CheckListTemplateException {
		try {
			Collection col = getCMRTemplateItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			ArrayList itemList = new ArrayList();
			Iterator iter = col.iterator();
			while (iter.hasNext()) {
				EBTemplateItemLocal local = (EBTemplateItemLocal) iter.next();
				ITemplateItem obj = local.getValue();
                if(!isItemExpired(obj)) {
                    itemList.add(obj);
                }
            }

            if(itemList.size() == 0) {
                return null;
            }

            ITemplateItem[] templateItemList = (ITemplateItem[]) itemList.toArray(new ITemplateItem[itemList.size()]);
			return templateItemList;
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception at retrieveTemplateItems: ", ex);
		}
	}


    private boolean isItemExpired(ITemplateItem item) {
        IItem itm = item.getItem();
        //Andy Wong, 21 May 2010: strip current time when comparing against expiry date, template expiry date doesnt include time
        Date currentDate = DateUtil.clearTime(DateUtil.getDate());
        return (itm.getExpiryDate() != null && itm.getExpiryDate().before(currentDate));
    }


    /**
	 * Sets the template object.
	 * @param anITemplate - ITemplate
	 * @throws CheckListTemplateException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ITemplate anITemplate) throws CheckListTemplateException, ConcurrentUpdateException {
		try {
			if (getVersionTime() != anITemplate.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			ITemplate template = anITemplate;

			AccessorUtil.copyValue(template, this, EXCLUDE_METHOD_UPDATE);
			updateTemplateItems(template.getCountry(), template.getTemplateItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("Exception in setValue: ", ex);
		}
	}

	/**
	 * Create the child items that are under this template
	 * @param anITemplate - ITemplate
	 * @throws CheckListTemplateException
	 */
	public void createTemplateItems(ITemplate anITemplate) throws CheckListTemplateException {
		updateTemplateItems(anITemplate.getCountry(), anITemplate.getTemplateItemList());
	}

	/**
	 * To get the list of law and the customer types
	 * @param lawList of String type
	 * @return LawSearchResultItem[] - the list of law and customer types
	 * @throws SearchDAOException if errors in DAO
	 * @throws RemoteException on remote errors
	 */
	public LawSearchResultItem[] ejbHomeGetLawCustomerTypes(String[] lawList) throws SearchDAOException {
		return CheckListTemplateDAOFactory.getTemplateDAO().getLawCustomerTypes(lawList);
	}

	/**
	 * To get the list of templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws SearchDAOException is errors in DAO
	 * @throws RemoteException on remote errors
	 */
	public SearchResult ejbHomeSearchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException {
		return CheckListTemplateDAOFactory.getTemplateDAO().searchTemplateList(aCriteria);
	}

	private void updateTemplateItems(String aCountryCode, ITemplateItem[] anITemplateItemList)
			throws CheckListTemplateException {
		try {
			Collection col = getCMRTemplateItems();
			if (anITemplateItemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				// delete all records
				deleteTemplateItems(new ArrayList(col));
				return;
			}
			if ((col == null) || (col.size() == 0)) {
				// create new records
				createTemplateItems(aCountryCode, Arrays.asList(anITemplateItemList));
				return;
			}
			//Added by Anil to retrieve application date
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date(applicationDate);
			
			Iterator iter = col.iterator();
			ArrayList createList = new ArrayList();
			ArrayList deleteList = new ArrayList(); // contains list of local
			// interfaces

			// identify identify records for delete or udpate first
			while (iter.hasNext()) {
				EBTemplateItemLocal local = (EBTemplateItemLocal) iter.next();
				long templateID = local.getTemplateItemID();
				boolean update = false;

				for (int ii = 0; ii < anITemplateItemList.length; ii++) {
					ITemplateItem newOB = anITemplateItemList[ii];
					if (newOB.getTemplateItemID() == templateID) {
						// perform update
						newOB.setLastUpdateDate(appDate);
						local.setValue(newOB);
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
			for (int ii = 0; ii < anITemplateItemList.length; ii++) {
				if (anITemplateItemList[ii].getTemplateItemID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					createList.add(anITemplateItemList[ii]);
				}
			}
			deleteTemplateItems(deleteList);
			createTemplateItems(aCountryCode, createList);
		}
		catch (CheckListTemplateException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in updateTemplateItems: ", ex);
		}
	}

	/**
	 * Delete the list of template items under the current template
	 * @param aDeletionList - List
	 * @throws CheckListTemplateException on errors
	 */
	private void deleteTemplateItems(List aDeletionList) throws CheckListTemplateException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRTemplateItems();
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

	/**
	 * Create the list of template items under the current template
	 * @param aCreationList - List
	 * @throws CheckListTemplateException on errors
	 */
	private void createTemplateItems(String aCountryCode, List aCreationList) throws CheckListTemplateException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamEntry generalParamEntry = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		String applicationDate = generalParamEntry.getParamValue();
		Date appDate = new Date(applicationDate);
		Collection col = getCMRTemplateItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBTemplateItemLocalHome home = getEBTemplateItemLocalHome();
			while (iter.hasNext()) {
				ITemplateItem obj = (ITemplateItem) iter.next();
				// To generate the item code if the item id is
				// com.integrosys.cms
				// .app.common.constant.ICMSConstant.LONG_INVALID_VALUE as this
				// means that the item is not from the global checklist
				if ((!obj.isInherited()) && (obj.getItemCode() == null)) {
					preCreationProcess(obj, aCountryCode);
				}
				if(obj.getCreationDate()==null){
					obj.setCreationDate(appDate);
				}
				obj.setLastUpdateDate(appDate);
                DefaultLogger.debug(this, ">>>>>>>>>>>>> EBTemplateBean.getParentID = " + obj.getParentItemID());
                EBTemplateItemLocal local = home.create(new Long(getTemplateID()), obj);
				local.createPropertyList(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in createTemplateItems: ", ex);
		}
	}

	/**
	 * To system generate the item code
	 * @param anITemplateItem - IItem
	 * @param aCountryCode - String
	 * @throws Exception on errors
	 */
	protected void preCreationProcess(ITemplateItem anITemplateItem, String aCountryCode) throws Exception {
		anITemplateItem.getItem().setItemCode(generateItemCode(aCountryCode));
	}

	/**
	 * Generate the item code If the country is null, assumed that it is a
	 * global item
	 * @return String - the item code generated
	 */
	private String generateItemCode(String aCountryCode) throws Exception {
		String seqClassName = PropertyManager.getValue(ITEM_CODE_SEQUENCE_CLASS_PROPERTY);
		if (aCountryCode != null) {
			String seqNo = (new SequenceManager(seqClassName, ITEM_CODE_FORMATTER_CLASS)).getSeqNum(
					ICMSConstant.SEQUENCE_TEMPLATE_ITEM_CODE, true);
			return aCountryCode + seqNo;
		}
		return (new SequenceManager(seqClassName, DOC_ITEM_CODE_FORMATTER_CLASS)).getSeqNum(
				ICMSConstant.SEQUENCE_DOCUMENT_ITEM_CODE, true);
	}

	/**
	 * Create a template.
	 * @param anITemplate - ITemplate
	 * @return Long - the template ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ITemplate anITemplate) throws CreateException {
		if (anITemplate == null) {
			throw new CreateException("ITemplate is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			ITemplate template = anITemplate;

			// DefaultLogger.debug(this, "Creating Template: " + template);
			setTemplateID(pk);
			AccessorUtil.copyValue(template, this, EXCLUDE_METHOD_CREATE);
			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("fail to create template [" + anITemplate + "]");
			cex.initCause(ex);
			throw cex;
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anITemplate - ITemplate
	 */
	public void ejbPostCreate(ITemplate anITemplate) throws CreateException {
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
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_TEMPLATE;
	}

	/**
	 * Method to get EB Local Home for template item
	 */
	protected EBTemplateItemLocalHome getEBTemplateItemLocalHome() throws CheckListTemplateException {
		EBTemplateItemLocalHome home = (EBTemplateItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_TEMPLATE_ITEM_LOCAL_JNDI, EBTemplateItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListTemplateException("EBTemplateItemLocal is null!");
	}

	/**
	 * Method to get EB Local Home for template
	 */
	protected EBTemplateLocalHome getEBTemplateLocalHome() throws CheckListTemplateException {
		EBTemplateLocalHome home = (EBTemplateLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_TEMPLATE_LOCAL_JNDI, EBTemplateLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListTemplateException("EBTemplateLocal is null!");
	}
}