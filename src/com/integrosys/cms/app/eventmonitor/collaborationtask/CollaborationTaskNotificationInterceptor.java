package com.integrosys.cms.app.eventmonitor.collaborationtask;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.trx.AbstractCheckListTrxOperation;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CollaborationTaskNotificationInterceptor extends AbstractCollaborationTaskNotificationInterceptor {

	protected Object doInvoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();
		AbstractCheckListTrxOperation trxOperation = (AbstractCheckListTrxOperation) invocation.getThis();

		if (method.getName().equals("postProcess")) {

			ITrxResult result = (ITrxResult) arguments[0];
			ICheckListTrxValue trxValue = (ICheckListTrxValue) result.getTrxValue();

			DefaultLogger.debug(this, "<<<<<< getSendNotificationInd(): " + trxValue.getSendNotificationInd());
			DefaultLogger.debug(this, "<<<<<< getSendItemNotificationInd(): " + trxValue.getSendItemNotificationInd());

			// for checklist, only trxValue.getSendNotificationInd() check
			// for checklist receipt, both checks are required
			if (trxValue.getSendNotificationInd() || trxValue.getSendItemNotificationInd()) {
				DefaultLogger.debug(this, "<<<<<<<<<<<<<<< notification indicator: true");
				ICheckListOwner owner = trxValue.getCheckList().getCheckListOwner();

				if (owner instanceof ICollateralCheckListOwner) {
					sendCollateralNotification(trxValue, false);
				}
				else {
					if ((owner instanceof ICCCheckListOwner)
							&& (!ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(owner.getSubOwnerType()))) {
						sendCCNotification(trxValue, false);
					}
				}
			}

			result = (ITrxResult) method.invoke(trxOperation, arguments);
			return result;
		}

		return method.invoke(trxOperation, arguments);
	}

}
