package com.integrosys.cms.app.eventmonitor.collaborationtask;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.trx.AbstractCheckListTrxOperation;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;

public class CollaborationTaskSystemCloseNotificationInterceptor extends
		AbstractCollaborationTaskNotificationInterceptor {

	protected Object doInvoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();
		AbstractCheckListTrxOperation trxOperation = (AbstractCheckListTrxOperation) invocation.getThis();

		if (method.getName().equals("postProcess")) {

			ITrxResult result = (ITrxResult) method.invoke(trxOperation, arguments);
			ICheckListTrxValue trxValue = (ICheckListTrxValue) result.getTrxValue();
			ICheckList actual = trxValue.getCheckList();
			ICCCheckListOwner owner = (ICCCheckListOwner) actual.getCheckListOwner();

			if (owner instanceof ICCCheckListOwner) {
				sendCCNotification(trxValue, true);
			}
			else {
				sendCollateralNotification(trxValue, true);
			}

			return result;
		}

		return method.invoke(trxOperation, arguments);
	}

}