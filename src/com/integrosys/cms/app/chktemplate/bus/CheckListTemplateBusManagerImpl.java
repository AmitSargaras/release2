package com.integrosys.cms.app.chktemplate.bus;


import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.beanloader.BeanController;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 18, 2008
 * Time: 10:44:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckListTemplateBusManagerImpl implements IDocumentBusManager, ITemplateBusManager {
    /**
	 * Update a document item
     * @param anIDocumentItem - IDocumentItem
     * @return IDocumentItem - the document item being updated
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     */
    public IDocumentItem create(IDocumentItem anIDocumentItem) throws CheckListTemplateException {
        try {
            return getCheckListTemplateBusManager().create(anIDocumentItem);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException("Exception in create: " + ex.toString());
        }
    }

    /**
	 * Update a document item
     * @param anIDocumentItem - IDocumentItem
     * @return IDocumentItem - the document item being updated
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException if enctr concurrent update
     */
    public IDocumentItem update(IDocumentItem anIDocumentItem) throws CheckListTemplateException, ConcurrentUpdateException {
        try {
            return getCheckListTemplateBusManager().update(anIDocumentItem);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException(ex.toString());
        }
    }

    /**
	 * Get a document by ID
     * @param aDocumentItemID - long
     * @return IDocument - the document
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     */
    public IDocumentItem getDocumentItemByID(long aDocumentItemID) throws CheckListTemplateException {
        try {
            return getCheckListTemplateBusManager().getDocumentItemByID(aDocumentItemID);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException(ex.toString());
        }
    }

    /**
	 * Create a template
     * @param anITemplate - ITemplate
     * @return ICheckList - the template being created
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
     */
    public ITemplate create(ITemplate anITemplate) throws CheckListTemplateException {
        try {
            return getCheckListTemplateBusManager().create(anITemplate);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException(ex.toString());
        }
    }

    /**
	 * Update a checklist
     * @param anITemplate - ITemplate
     * @return ITemplate - the template being updated
     * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException on erros
     */
    public ITemplate update(ITemplate anITemplate) throws ConcurrentUpdateException, CheckListTemplateException {
        try {
            return getCheckListTemplateBusManager().update(anITemplate);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException(ex.toString());
        }
    }

    /**
	 * Get a template based on the value in the template type
     * @param anITemplateType - ITemplateType
     * @return ICheckList - the checklist
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     */
    public ITemplate getTemplateList(ITemplateType anITemplateType) throws CheckListTemplateException {
        try {
            return getCheckListTemplateBusManager().getTemplateList(anITemplateType);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException(ex.toString());
        }
    }

    /**
	 * Get a template by the template ID
     * @param aTemplateID - long
     * @return ITemplate - the biz object containing the template info
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     */
    public ITemplate getTemplateByID(long aTemplateID) throws CheckListTemplateException {
        try {
            return getCheckListTemplateBusManager().getTemplateByID(aTemplateID);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException(ex.toString());
        }
    }

	/**
	 * To get the remote handler for the checklist bus manager
	 * @return SBCheckListBusManager - the remote handler for the checklist bus
	 *         manager
	 */
	private SBCheckListTemplateBusManager getCheckListTemplateBusManager() {
		SBCheckListTemplateBusManager busmgr = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		return busmgr;
	}    
}
