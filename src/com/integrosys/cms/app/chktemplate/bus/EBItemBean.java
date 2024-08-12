/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBItemBean.java,v 1.2 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.TypeConverter;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;
import java.util.List;

/**
 * This entity bean represents the persistence for item information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public abstract class EBItemBean implements EntityBean, IItem {
	// private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_ITEM;
	private static final String[] EXCLUDE_METHOD = new String[] { "getItemID", "getPropertyList" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBItemBean() {
	}


    // *************Override Inteface Methods (Java 1.4 requirement) **************
    public abstract String getItemCode();
    public abstract String getItemDesc();
    public abstract String getItemType();
    public abstract Date getExpiryDate();
    public abstract String getMonitorType();
    public abstract String getDocumentVersion() ;
    public abstract String getLoanApplicationType();
    public abstract void setItemCode(String anItemCode);
    public abstract void setItemDesc(String anItemDesc);
    public abstract void setItemType(String anItemType);
    public abstract void setExpiryDate(Date anExpiryDate);
    public abstract void setMonitorType(String aMonitorType);
    public abstract void setDocumentVersion(String docVersion);
    public abstract void setLoanApplicationType(String type);
	public abstract Collection getCMRDocAppItemList();
	public abstract void setCMRDocAppItemList(Collection docAppItemList);
	
	public abstract String getStatus();
	public abstract void setStatus(String status);
	public abstract String getIsApplicableForCersaiInd();

	public abstract void setIsApplicableForCersaiInd(String isApplicableForCersaiInd);

    // ************** Abstract methods ************
	public abstract Long getCMPItemID();

    public abstract String getIsForBorrowerStr();
    
    public abstract String getIsForPledgorStr();

    public abstract String getIsPreApproveStr();

    public abstract void setCMPItemID(Long anItemID);

    public abstract void setIsForBorrowerStr(String flag);

    public abstract void setIsForPledgorStr(String flag);

    public abstract void setIsPreApproveStr(String flag);


    // ************** Not In Used ************
    public IDynamicProperty[] getPropertyList() {
        return null;
    }

    public void setPropertyList(IDynamicProperty[] propertyList) {
        //do nothing
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
	 * Create a item Information Read only so not creation is allowed
	 * @return Long - the item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate() throws CreateException {
		return null;
	}

	/**
	 * Post-Create a record
	 */
	public void ejbPostCreate() {
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
	 * Return the Interface representation of this object
	 * 
	 * @return IItem
	 */
	public IItem getValue() throws CheckListTemplateException{
		OBItem value = new OBItem();
		List addList = new ArrayList();
		
		//AccessorUtil.copyValue(this, value);
		value.setDocumentVersion(this.getDocumentVersion());
		value.setExpiryDate(this.getExpiryDate());
		value.setTenureCount(this.getTenureCount());
		value.setTenureType(this.getTenureType());
		value.setSkipImgTag(this.getSkipImgTag());
		
		value.setCMRDocAppItemList(retrieveDocAppItemList());
		value.setIsForBorrower(this.getIsForBorrower());
		value.setIsForPledgor(this.getIsForPledgor());
		value.setIsPreApprove(this.getIsPreApprove());
		value.setItemCode(this.getItemCode());
		value.setItemDesc(this.getItemDesc());
		value.setItemID(this.getItemID());
		value.setItemType(this.getItemType());
		value.setLoanApplicationType(this.getLoanApplicationType());
		value.setMonitorType(this.getMonitorType());
		value.setPropertyList(this.getPropertyList());
		
		value.setOldItemCode(this.getOldItemCode());
		value.setIsApplicableForCersaiInd(this.getIsApplicableForCersaiInd());
        return value;
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
	
	public void createDocumentAppItem(IItem anIDocumentItem) throws CheckListTemplateException {
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
	
	private void createDocumentAppItem(List aCreatetionList) throws CheckListTemplateException {
		
		Iterator iter = aCreatetionList.iterator();
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
		throw new CheckListTemplateException("EBTemplateItemLocal is null!");
	}

	/**
	 * Persist a item information
	 * 
	 * @param anIItem - IItem
	 * @throws CheckListTemplateException 
	 */
	public void setValue(IItem anIItem) throws CheckListTemplateException {
		AccessorUtil.copyValue(anIItem, this, EXCLUDE_METHOD);
		try
		{
			updateDocumentAppItem(anIItem);
		}catch (Exception ex) {
					throw new CheckListTemplateException("Exception in createTemplateItems: ", ex);
		}
	}
	
	public void updateDocumentAppItem(IItem anIDocumentItem) throws CheckListTemplateException
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
			
			// identify identify records for delete or update first
			while (iter.hasNext()) {
				EBDocAppItemLocal local = (EBDocAppItemLocal) iter.next();
				long documetId = local.getDocumentId().longValue();
				boolean update = false;
				
				
				Iterator docIter = anIDocumentItem.getCMRDocAppItemList().iterator();
				while(docIter.hasNext())
				{
					IDocumentAppTypeItem newDoc = (IDocumentAppTypeItem) docIter.next();
					if(newDoc.getDocumentId().longValue() == documetId)
					{
						// perform update
						//local.setValue(newDoc);
						newDoc.getAppType();
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
				if(newDoc.getDocumentId().longValue() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
				{
					createList.add(newDoc);
				}
			}
			
			deleteDocumentAppItems(deleteList);
			createDocumentAppItem(createList);
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in updating App Document items: ", ex);
		}
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
	
	public abstract int getTenureCount();
	
	public abstract String getTenureType();
	
	public abstract String getSkipImgTag();
	
    public abstract void setTenureCount(int tenureCount);
    
    public abstract void setTenureType(String tenureType);
    
    public abstract void setSkipImgTag(String skipImgTag);
    
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
	
	public  abstract String getDeprecated();
	
	public abstract void setDeprecated(String deprecated);
	
    public abstract String getOldItemCode();
    
    public abstract void setOldItemCode(String oldItemCode);
	
}