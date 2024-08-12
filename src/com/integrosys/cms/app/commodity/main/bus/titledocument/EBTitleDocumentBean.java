/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/EBTitleDocumentBean.java,v 1.6 2004/07/23 05:59:58 lyng Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for title document type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/07/23 05:59:58 $ Tag: $Name: $
 */
public abstract class EBTitleDocumentBean implements ITitleDocument, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create/update title document. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getTitleDocumentID" };

	/**
	 * Get title document type id.
	 * 
	 * @return long
	 */
	public long getTitleDocumentID() {
		return this.getTitleDocumentPK().longValue();
	}

	/**
	 * Set title document type id.
	 * 
	 * @param titleDocumentID of type long
	 */
	public void setTitleDocumentID(long titleDocumentID) {
		this.setTitleDocumentPK(new Long(titleDocumentID));
	}

	public abstract Long getTitleDocumentPK();

	public abstract void setTitleDocumentPK(Long pk);

	/**
	 * Retrieve an instance of title document type.
	 * 
	 * @return title document business object
	 */
	public ITitleDocument getValue() {
		OBTitleDocument value = new OBTitleDocument();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Set the title document type object.
	 * 
	 * @param value of type ITitleDocument
	 * @throws ConcurrentUpdateException if more than one client accessing this
	 *         title document type at the same time
	 */
	public void setValue(ITitleDocument value) throws ConcurrentUpdateException {
		checkVersionMismatch(value);
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * To soft delete the title document type.
	 */
	public void softDelete() {
		this.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Check the version of this title document type.
	 * 
	 * @param value title document type
	 * @throws ConcurrentUpdateException if the entity version is invalid
	 */
	private void checkVersionMismatch(ITitleDocument value) throws ConcurrentUpdateException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value ITitleDocument object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ITitleDocument value) throws CreateException {
		try {
			String docID = (new SequenceManager()).getSeqNum(CommodityConstant.SEQUENCE_TITLE_DOCUMENT_SEQ, true);
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setTitleDocumentPK(new Long(docID));

			if (value.getCommonRef() == ICMSConstant.LONG_MIN_VALUE) {
				setCommonRef(getTitleDocumentID());
			}
			else {
				// else maintain this reference id.
				setCommonRef(value.getCommonRef());
			}
			this.setVersionTime(VersionGenerator.getVersionNumber());

			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of ITitleDocument
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ITitleDocument value) throws CreateException {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}
}
