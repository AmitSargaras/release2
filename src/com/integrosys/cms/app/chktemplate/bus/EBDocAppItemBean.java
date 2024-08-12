/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBItemBean.java,v 1.2 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.TypeConverter;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;

/**
 * This entity bean represents the persistence for item information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public abstract class EBDocAppItemBean implements EntityBean , IDocumentAppTypeItem {
	
	private static final String[] EXCLUDE_METHOD = new String[] {"getDocumentId"};
	private static final String[] EXCLUDE_UPDATE_METHOD = new String[] {"getDocumentLoanId"};
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBDocAppItemBean() {
	}


    // *************Override Inteface Methods (Java 1.4 requirement) **************
    public abstract Long getCMPDocumentId();
    public abstract void setCMPDocumentId(Long documentId);
    public abstract String getAppType();
    public abstract void setAppType(String appType);
//    public abstract EBItemLocal getCMRItem();
//    public abstract void setCMRItem(EBItemLocal item);
    public abstract Long getDocumentLoanId();
    public abstract void setDocumentLoanId(Long documentLoanId);
	public abstract long getRefId();
	public abstract void setRefId(long refId);
	public abstract String getStatus();
	public abstract void setStatus(String status);
    


    // *****************************************************
	/**
	 * Create a item Information Read only so not creation is allowed
	 * @return Long - the item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(Long aDocumentId , IDocumentAppTypeItem aDocumentAppTypeItem) throws CreateException {
		if (aDocumentAppTypeItem == null) {
			throw new CreateException("IDocumentAppTypeItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			AccessorUtil.copyValue(aDocumentAppTypeItem, this, EXCLUDE_METHOD);
			setDocumentLoanId(new Long(pk));
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("failed to create checklist template");
			cex.initCause(ex);
			throw cex;
		}
	}
	
	public Long getDocumentId()
	{
		if (getCMPDocumentId() != null) {
			return getCMPDocumentId();
		}
		return new Long(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
	}
	
	public void setDocumentId(Long documentId) {
		// do nothing (When a cmp-field and a cmr-field (relationship) are
		// mapped to the same column,
		// the setXXX method for the cmp-field may not be called. The cmp-field
		// is read-only.)
	}

	/**
	 * Post-Create a record
	 */
	public void ejbPostCreate(Long aDocumentId , IDocumentAppTypeItem aDocumentAppTypeItem) throws CreateException 
	{
		/*** Only load when you have childrens of such objects***/

	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return IItem
	 */
	
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_DOC_APP_ITEM;
	}
	
	
	public IDocumentAppTypeItem getValue() throws CheckListTemplateException{
		OBDocumentAppTypeItem value = new OBDocumentAppTypeItem();
		value.setDocumentId(getDocumentId());
		value.setDocumentLoanId(getDocumentLoanId());
		value.setAppType(getAppType());
		value.setRefId(getRefId());
		value.setStatus(getStatus());
        return value;
	}

	/**
	 * Persist a item information
	 * 
	 * @param anIItem - IItem
	 */
	public void setValue(IDocumentAppTypeItem anIItem) {
		AccessorUtil.copyValue(anIItem, this, EXCLUDE_UPDATE_METHOD);
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
}