/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgCheckListBean.java,v 1.8 2006/03/27 04:50:36 jitendra Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custodian.bus.ICustodianDocSearchResult;

/**
 * Implementation for the checklist entity bean
 * 
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/03/27 04:50:36 $ Tag: $Name: $
 */

public abstract class EBStgCheckListBean extends EBCheckListBean {

	/**
	 * Default Constructor
	 */
	public EBStgCheckListBean() {
	}

	/**
	 * To return the trx custodian doc status
	 * @param aSearchResult of ICustodianDocSearchResult type
	 * @return String - the custodian doc status
	 */
	protected String getCustodianDocStatus(ICustodianDocSearchResult aSearchResult) {
		return aSearchResult.getTrxStatus();
	}

	/**
	 * To generate the checklist item reference and set it into the checklist
	 * item
	 * @param anICheckListItem - ICheckListItem
	 * @throws Exception on errors
	 */
	protected void preCreationProcess(ICheckListItem anICheckListItem) throws Exception {
		if (anICheckListItem.getCheckListItemRef() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			anICheckListItem.setCheckListItemRef(generateItemReference());
		}
	}

	/**
	 * Generate the checklist item reference. This is to be used as the unique
	 * biz checklist item key
	 * @return long - the checklist item reference generated
	 */
	private long generateItemReference() throws Exception {
		String seq = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CHECKLIST_ITEM, true);
		return (new Long(seq)).longValue();
	}

	/**
	 * Get the name of the sequence to be used for key generation
	 * @return String - the sequence to be used
	 */
	/*
	 * protected String getSequenceName() { return
	 * ICMSConstant.SEQUENCE_STAGING_CHECKLIST; }
	 */

	// for cr-17
	protected EBDocumentshareLocalHome getEBDocumentshareLocalHome() throws CheckListException {
		// DefaultLogger.debug(this,
		// " from EBStgCheckListBean getEBDocumentshareLocalHome = ");
		EBDocumentshareLocalHome home = (EBDocumentshareLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CHECKLIST_DOCUMENT_SHARE_LOCAL_JNDI, EBDocumentshareLocalHome.class
						.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBDocumentshareLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBCheckListItemLocalHome getEBCheckListItemLocalHome() throws CheckListException {
		EBCheckListItemLocalHome home = (EBCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CHECKLIST_ITEM_LOCAL_JNDI, EBCheckListItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBCheckListItemLocal is null!");
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBCheckListItemLocalHome getActualEBCheckListItemLocalHome() throws CheckListException {
		EBCheckListItemLocalHome home = (EBCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_ITEM_LOCAL_JNDI, EBCheckListItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBCheckListItemLocal is null!");
	}

}