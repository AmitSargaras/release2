package com.integrosys.cms.ui.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * <p>
 * Session Integrity Control to check the submission of current workflow whether
 * it's Limit Profile Id match the one in Global Session.
 * <p>
 * Checking will be based on the {@link ICMSTrxValue#getLimitProfileID()}
 * against the {@link ILimitProfile#getLimitProfileID()} in Global Scope of the
 * session, backed by the key of <tt>IGlobalConstant.GLOBAL_LIMITPROFILE_OB</tt>
 * <p>
 * Of course, not every event we are interested in the integrity check. As
 * stated above, we only interested on the submission of the workflow. So
 * basically, we will retrieve all possible instance of <tt>ICMSTrxValue</tt>
 * from the Service Scope. Then for that <tt>ICMSTrxValue</tt>, we will check
 * against whether for the current <tt>event</tt> required integrity check.
 * <p>
 * In order to achieve non-hardcoded approach on checking current event for the
 * <tt>ICMSTrxValue</tt>, we can create a Map, where key is the transaction type
 * of the <tt>ICMSTrxValue</tt>, value is the list of event. For such case, we
 * use the context bean where id/name is
 * <tt>sessionIntegrityWorkflowTypeEventsMap</tt>
 * <p>
 * Example of the context bean as below:
 * 
 * <pre>
 * &lt;bean id=&quot;sessionIntegrityWorkflowTypeEventsMap&quot; class=&quot;org.springframework.beans.factory.config.MapFactoryBean&quot;&gt;
 * 	&lt;property name=&quot;sourceMap&quot;&gt;
 * 		&lt;map key-type=&quot;java.lang.String&quot; value-type=&quot;java.lang.String[]&quot;&gt;
 * 			&lt;entry key=&quot;COL&quot; 
 * 				value=&quot;submit,delete,close,update,approve,reject&quot; /&gt;
 * 
 * 			&lt;entry key=&quot;FACILITY&quot; 
 * 				value=&quot;submit,submit_no_frame,close,update,approve,reject,
 * 					save_whole_obj,save_whole_obj_no_frame&quot; /&gt;
 * 
 * 			&lt;entry key=&quot;CHECKLIST&quot; 
 * 				value=&quot;submit,submit_delete,save,close,update,update_delete,
 *					approve_checklist_item,reject_checklist_item&quot; /&gt;
 * 
 * 			&lt;entry key=&quot;RECURRENT_CHECKLIST&quot; 
 * 				value=&quot;submit,close,approve,reject,save,approve_checklist_item,
 *					reject_checklist_item&quot; /&gt;
 * 
 * 			&lt;entry key=&quot;LIMIT&quot; 
 * 				value=&quot;submit,approve,reject,checker_approve_update_limits,
 *					checker_reject_update_limits,close_limits&quot; /&gt;
 * 
 * 			&lt;entry key=&quot;TAT_DOC&quot; 
 * 				value=&quot;submit,submit_todo,save,save_todo,delete,close,approve,reject&quot; /&gt;
 * 		&lt;/map&gt;
 * 	&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * @author Chong Jun Yong
 * 
 */
public class SessionIntegrityControlCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "facilityTrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.transaction.ICMSTrxValue", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.transaction.ICMSTrxValue", SERVICE_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", SERVICE_SCOPE },
				{ "service.limitTrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", SERVICE_SCOPE },
				{ "tatDocTrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (limitProfile != null) {
			String event = (String) map.get("event");
			boolean requiredIntegrityCheck = false;

			Map workflowTypeEventsMap = (Map) BeanHouse.get("sessionIntegrityWorkflowTypeEventsMap");

			ICMSTrxValue cmsTrxValue = getCurrentCmsTrxValue(map);
			if (cmsTrxValue != null) {
				if (workflowTypeEventsMap.containsKey(cmsTrxValue.getTransactionType())) {
					String[] events = (String[]) workflowTypeEventsMap.get(cmsTrxValue.getTransactionType());
					Arrays.sort(events);
					if (Arrays.binarySearch(events, event) >= 0) {
						requiredIntegrityCheck = true;
					}
				}
			}

			if (requiredIntegrityCheck) {
				if (cmsTrxValue.getLimitProfileID() != limitProfile.getLimitProfileID()) {
					throw new AccessDeniedException("Limit Profile Id [" + cmsTrxValue.getLimitProfileID()
							+ "] for current transaction; transaction id [" + cmsTrxValue.getTransactionID()
							+ "], transaction type [" + cmsTrxValue.getTransactionType()
							+ "],  doesn't match the one in Session [" + limitProfile + "], id ["
							+ limitProfile.getLimitProfileID() + "]");
				}
			}
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private ICMSTrxValue getCurrentCmsTrxValue(HashMap map) {
		if (map != null) {
			for (Iterator itr = map.values().iterator(); itr.hasNext();) {
				Object value = itr.next();
				if (value != null && value instanceof ICMSTrxValue) {
					return (ICMSTrxValue) value;
				}
			}
		}

		return null;
	}
}