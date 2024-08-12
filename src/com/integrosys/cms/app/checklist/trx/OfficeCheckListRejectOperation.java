/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/OfficeCheckListRejectOperation.java,v 1.17 2006/10/05 02:51:13 hmbao Exp $
 */

package com.integrosys.cms.app.checklist.trx;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxNotificationException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ISecurityCustomer;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBSecurityCustomer;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.eventmonitor.waiverdeferral.DummyMonWaiverDeferalRejection;
import com.integrosys.cms.app.eventmonitor.waiverdeferral.OBWaiverDeferralInfo;
import com.integrosys.cms.app.eventmonitor.waiverdeferral.WaiverDeferralListener;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.CMSTrxForwardOperationBase;

/**
 * @author $Author: hmbao $
 * @version $Revision: 1.17 $
 * @since $Date: 2006/10/05 02:51:13 $ Tag: $Name: $
 */
public class OfficeCheckListRejectOperation extends CMSTrxForwardOperationBase {
	CheckerRejectCheckListReceiptOperation op = new CheckerRejectCheckListReceiptOperation();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#preProcess(com
	 * .integrosys.base.businfra.transaction.ITrxValue)
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		// Auto-generated method stub
		DefaultLogger.debug(this, "-----preProcess...going to CheckerRejectCheckListReceiptOperation");
		return op.preProcess(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#performProcess
	 * (com.integrosys.base.businfra.transaction.ITrxValue)
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		DefaultLogger.debug(this, "-----performProcess...going to CheckerRejectCheckListReceiptOperation");
		return op.performProcess(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#postProcess(com
	 * .integrosys.base.businfra.transaction.ITrxResult)
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		// Auto-generated method stub
		DefaultLogger.debug(this, "-----postProcess...going to CheckerRejectCheckListReceiptOperation");
		return op.postProcess(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#notifyProcess(
	 * com.integrosys.base.businfra.transaction.ITrxResult)
	 */
	public ITrxResult notifyProcess(ITrxResult result) throws TrxNotificationException {
		try {
			ICheckListTrxValue trxValue = (ICheckListTrxValue) result.getTrxValue();
			ICheckListOwner owner = (ICheckListOwner) trxValue.getCheckList().getCheckListOwner();
			ISecurityCustomer[] customerArray = getCollateralCustomer(trxValue, owner);
			OBWaiverDeferralInfo info = new OBWaiverDeferralInfo();
			if (customerArray == null) {
				assembleCommonNotifInfo(info, trxValue);
				fireNotification(info);
			}
			else {
				for (int index = 0; index < customerArray.length; index++) {
					assembleCommonNotifInfo(info, trxValue);
					assembleCustomerNotifInfo(info, customerArray[index]);
					if (owner instanceof ICollateralCheckListOwner) {
						long collID = ((ICollateralCheckListOwner) owner).getCollateralID();
						String le_id = String.valueOf(customerArray[index].getLeID());
						assembleSecurityNotifInfo(info, collID, le_id, trxValue.getOriginatingCountry());
					}
					fireNotification(info);
				}
			}
			return result;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxNotificationException("Exception in sendNotification", ex.fillInStackTrace());
		}
	}

	private ISecurityCustomer[] getCollateralCustomer(ICheckListTrxValue trxValue, ICheckListOwner owner)
			throws Exception {
		ISecurityCustomer[] customerArray = null;
		ICMSCustomer customer = null;
		if (owner.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
			if ("S".equals(trxValue.getStagingCheckList().getCheckListType())) {
				// CB Security CheckList - may have many owners.
				List customerList = getSBCheckListBusManager().getSecurityOwnerList(
						((ICollateralCheckListOwner) owner).getCollateralID(), owner.getLimitProfileID());
				if ((customerList != null) && (customerList.size() > 0)) {
					customerArray = (ISecurityCustomer[]) customerList.toArray(new ISecurityCustomer[0]);
				}
			}
			else {
				ILimitProfile lmtProfile = trxValue.getTrxContext().getLimitProfile();
				customer = CustomerProxyFactory.getProxy().getCustomer(lmtProfile.getCustomerID());
			}
		}
		else if (ICCCheckListOwner.class.isInstance(owner)) {
			customer = CustomerProxyFactory.getProxy().getCustomer(owner.getSubOwnerID());
		}
		if (customer != null) {
			customerArray = new ISecurityCustomer[1];
			customerArray[0] = new OBSecurityCustomer();
			customerArray[0].setLeID(Long.parseLong(customer.getCMSLegalEntity().getLEReference()));
			customerArray[0].setLeName(customer.getCMSLegalEntity().getLegalName());
			customerArray[0].setSegment(customer.getCMSLegalEntity().getCustomerSegment());
		}
		return customerArray;
	}

	private void assembleCommonNotifInfo(OBWaiverDeferralInfo info, ICheckListTrxValue trxValue) {
		ICheckList checkList = trxValue.getStagingCheckList();
		ICheckListItem[] items = checkList.getCheckListItemList();
		ICheckListItem[] filterItems = filter(items, new String[] { ICMSConstant.STATE_ITEM_PENDING_DEFERRAL,
				ICMSConstant.STATE_ITEM_PENDING_WAIVER });

		info.setCheckListType(checkList.getCheckListType());
		info.setApprovalDate(trxValue.getTransactionDate());
		info.setLastComment(trxValue.getComment());
		info.setItemList(filterItems);
		info.setOriginatingCountry(trxValue.getOriginatingCountry());
		info.setTransactionID(trxValue.getTransactionID());
		info.setTrxUserID(trxValue.getUID());
		info.setTrxUserTeamID(trxValue.getTeamID());
		info.setDetails(DummyMonWaiverDeferalRejection.EVEVNT_ID + " , "
				+ String.valueOf(trxValue.getCheckList().getCheckListID()) + " , " + trxValue.getOriginatingCountry());
	}

	private void assembleCustomerNotifInfo(OBWaiverDeferralInfo info, ISecurityCustomer customer) {
		info.setLeID(String.valueOf(customer.getLeID()));
		info.setLeName(customer.getLeName());
		info.setSegment(customer.getSegment());
	}

	private void assembleSecurityNotifInfo(OBWaiverDeferralInfo info, long collID, String le_id, String cty)
			throws CollateralException {
		ICollateral collateral = CollateralProxyFactory.getProxy().getCollateral(collID, true);
		info.setSecurityID(String.valueOf(collateral.getSCISecurityID()));
		info.setSecuritySubTypeID(collateral.getCollateralSubType().getSubTypeCode());
		info.setSecurityMaturityDate(op.getSecurityMaturityDate(collateral));
		info.setDetails(DummyMonWaiverDeferalRejection.EVEVNT_ID + " , " + le_id + " , " + info.getSecurityID() + " , "
				+ cty);
	}

	private void fireNotification(OBWaiverDeferralInfo info) throws Exception {
		DummyMonWaiverDeferalRejection mon = new DummyMonWaiverDeferalRejection();
		ArrayList list = new ArrayList();
		list.add(info);
		list.add("");
		list.add(mon.constructRuleParam(0));
		new WaiverDeferralListener().fireEvent(mon.getEventName(), list);
	}

	protected SBCheckListBusManager getSBCheckListBusManager() {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		return remote;
	}

	private ICheckListItem[] filter(ICheckListItem[] inItems, String[] status) {
		if ((inItems == null) || (status == null)) {
			return null;
		}
		ArrayList items = new ArrayList();
		for (int i = 0; i < inItems.length; i++) {
			for (int j = 0; j < status.length; j++) {
				if ((inItems[i].getItemStatus() != null) && ((inItems[i].getItemStatus()).equals(status[j]))) {
					items.add(inItems[i]);
					break;
				}
			}
		}
		return (ICheckListItem[]) items.toArray(new OBCheckListItem[0]);
	}
}
