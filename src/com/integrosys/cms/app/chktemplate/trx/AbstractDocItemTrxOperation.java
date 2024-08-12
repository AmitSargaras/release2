/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/AbstractDocItemTrxOperation.java,v 1.6 2003/08/04 08:53:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//java
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;

import com.integrosys.cms.app.chktemplate.bus.EBDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManager;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManagerHome;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;


/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/04 08:53:23 $ Tag: $Name: $
 */
public abstract class AbstractDocItemTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Create the staging document item doc
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IDocumentItemTrxValue createStagingDocItem(IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws TrxOperationException {
		try {
			IDocumentItem docItem = getSBStagingCheckListTemplateBusManager().create(anIDocumentItemTrxValue.getStagingDocumentItem());
			anIDocumentItemTrxValue.setStagingDocumentItem(docItem);
			anIDocumentItemTrxValue.setStagingReferenceID(String.valueOf(docItem.getItemID()));
			return anIDocumentItemTrxValue;
		}
		catch (CheckListTemplateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a document item transaction
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return OBDocumentItemTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IDocumentItemTrxValue updateDocumentItemTransaction(IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws TrxOperationException {
		try {
			anIDocumentItemTrxValue = prepareTrxValue(anIDocumentItemTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anIDocumentItemTrxValue);
			OBDocumentItemTrxValue newValue = new OBDocumentItemTrxValue(tempValue);
			newValue.setDocumentItem(anIDocumentItemTrxValue.getDocumentItem());
			newValue.setStagingDocumentItem(anIDocumentItemTrxValue.getStagingDocumentItem());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IDocumentItemTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IDocumentItemTrxValue getDocumentItemTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IDocumentItemTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBDocumentItemTrxValue: " + cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the staging
	 *         checklist session bean
	 */
	protected SBCheckListTemplateBusManager getSBStagingCheckListTemplateBusManager() {
		SBCheckListTemplateBusManager remote = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the checklist
	 *         session bean
	 */
	protected SBCheckListTemplateBusManager getSBCheckListTemplateBusManager() {
		SBCheckListTemplateBusManager remote = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected IDocumentItemTrxValue prepareTrxValue(IDocumentItemTrxValue anIDocumentItemTrxValue) {
		if (anIDocumentItemTrxValue != null) {
			IDocumentItem actual = anIDocumentItemTrxValue.getDocumentItem();
			IDocumentItem staging = anIDocumentItemTrxValue.getStagingDocumentItem();
			
			
			
			
			if (actual != null) {
				anIDocumentItemTrxValue.setReferenceID(String.valueOf(actual.getItemID()));
					
			}
			else {
				anIDocumentItemTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIDocumentItemTrxValue.setStagingReferenceID(String.valueOf(staging.getItemID()));

			}
			else {
				anIDocumentItemTrxValue.setStagingReferenceID(null);
			}
			return anIDocumentItemTrxValue;
		}
		return null;
	}
	
	protected Map prepareDocAppTypeItemHashMap(String itemCode)
	{
		Map actualRefIdMap = new HashMap();
		try
		{
			Collection remoteList = getSBStagingCheckListTemplateBusManager().getDocumentItemByItemCode(itemCode);
			if ((remoteList == null) || ((remoteList.size()) == 0)) {
				return null;
			}
				int i = 0;
				
				Iterator iter = remoteList.iterator();
				while (iter.hasNext()) 	
				{
					if (i+1 == remoteList.size())
					{
					
						EBDocumentItem tempItem = (EBDocumentItem)iter.next();
						IDocumentItem prevStagingDocumentItem = tempItem.getValue();
						Collection docAppItemList = prevStagingDocumentItem.getCMRDocAppItemList();
						Iterator iter1 = docAppItemList.iterator();
						while (iter1.hasNext()) 
						{
							IDocumentAppTypeItem tempDocumentAppTypeItem = (IDocumentAppTypeItem)iter1.next();
							if(tempDocumentAppTypeItem.getRefId()==ICMSConstant.LONG_MIN_VALUE)
								actualRefIdMap.put(tempDocumentAppTypeItem.getAppType(),tempDocumentAppTypeItem.getDocumentLoanId());
							else
								actualRefIdMap.put(tempDocumentAppTypeItem.getAppType(),tempDocumentAppTypeItem.getDocumentLoanId());
						}
					}else
					{
						iter.next();
						i++;
					}
					
				}
		}catch(Exception e)
		{
		
		}
		
		
		
		return actualRefIdMap;
	}
	
	protected IDocumentItemTrxValue prepareChildValue(IDocumentItemTrxValue anIDocumentItemTrxValue) {
		

		if (anIDocumentItemTrxValue != null) {
			IDocumentItem actual = anIDocumentItemTrxValue.getDocumentItem();
			IDocumentItem staging = anIDocumentItemTrxValue.getStagingDocumentItem();
			
//			if (actual != null) 
//			{
//				Map actualRefIdMap =  prepareDocAppTypeItemHashMap(actual.getItemCode());
//				if(actualRefIdMap != null)
//				{
//					Collection docAppItemList = actual.getCMRDocAppItemList();
//					Iterator iter = docAppItemList.iterator();
//					while (iter.hasNext())
//					{
//						IDocumentAppTypeItem tempDocumentAppTypeItem = (IDocumentAppTypeItem)iter.next();
//						if(actualRefIdMap.containsKey(tempDocumentAppTypeItem.getAppType()))
//						{
//							Long refId = (Long)actualRefIdMap.get(tempDocumentAppTypeItem.getAppType());
//							tempDocumentAppTypeItem.setRefId(refId.longValue());
//						}
//					}
//				}
//			}
//			if (staging != null) {
//				Map actualRefIdMap =  prepareDocAppTypeItemHashMap(staging.getItemCode());
//				if(actualRefIdMap != null)
//				{
//					Collection docAppItemList = staging.getCMRDocAppItemList();
//					Iterator iter = docAppItemList.iterator();
//					while (iter.hasNext())
//					{
//						IDocumentAppTypeItem tempDocumentAppTypeItem = (IDocumentAppTypeItem)iter.next();
//						if(actualRefIdMap.containsKey(tempDocumentAppTypeItem.getAppType()))
//						{
//							Long refId = (Long)actualRefIdMap.get(tempDocumentAppTypeItem.getAppType());
//							tempDocumentAppTypeItem.setRefId(refId.longValue());
//						}
//					}
//				}
//			}
			
			
			return anIDocumentItemTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IDocumentItemTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IDocumentItemTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}