/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgCheckListItemBean.java,v 1.5 2006/06/21 10:53:43 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.EBCheckListItemImageDetailLocalHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for checklist item Information
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/06/21 10:53:43 $ Tag: $Name: $
 */
public abstract class EBStgCheckListItemBean extends EBCheckListItemBean {
	/**
	 * Default Constructor
	 */
	public EBStgCheckListItemBean() {
	}

	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	/*
	 * protected String getSequenceName() { return
	 * ICMSConstant.SEQUENCE_STAGING_CHECKLIST_ITEM; }
	 */

	/**
	 * Method to get EB Local Home for item
	 */
	/*
	 * protected EBItemLocalHome getEBItemLocalHome() throws CheckListException
	 * { EBItemLocalHome home = (EBItemLocalHome)BeanController.getEJBLocalHome(
	 * ICMSJNDIConstant.EB_STAGING_ITEM_LOCAL_JNDI,
	 * EBItemLocalHome.class.getName()); if(home != null) { return home; } throw
	 * new CheckListException ("EBItemLocal is null!"); }
	 */

	/*
	 * protected IShareDoc[] retrieveDocumentshareList() throws
	 * CheckListException { try { DefaultLogger.debug(this,
	 * ">>>>>>>>>> In staging retrieveDocumentshareList" ); long checkListItemId
	 * = getCMPCheckListItemID().longValue(); DefaultLogger.debug(this,
	 * ">>>>>>>>>> retrieveDocumentshareList: checkListItemID: " +
	 * checkListItemId); return
	 * CheckListDAOFactory.getCheckListDAO().getStagingSharedDocuments
	 * (checkListItemId);
	 * 
	 * } catch (Exception ex) { throw new
	 * CheckListException("Exception at retrieveDocumentshareList: " +
	 * ex.toString()); } }
	 */

	/*
	 * protected Collection populateLEIDName(String[] checkListIDs) throws
	 * Exception { return
	 * CheckListDAOFactory.getCheckListDAO().retrieveLeIDNameForStagingShareDoc
	 * (checkListIDs); }
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
	
	protected EBCheckListItemImageDetailLocalHome getEBLocalCheckListItemImageDetail() {
		EBCheckListItemImageDetailLocalHome home = (EBCheckListItemImageDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_ITEM_IMAGE_DETAIL_LOCAL_JNDI_STAGING, EBCheckListItemImageDetailLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new RuntimeException("EBCheckListItemImageDetailLocalHome is null!");
		}
	}

}