package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdxItem;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;

import java.util.HashMap;

/**
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public class ReadPropertyIdxItemCmd extends PropertyIdxCmd {
    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
        };
    }

    public String[][] getResultDescriptor() {
        return new String[][]{
                {"PropertyIdxItemForm", "java.lang.Object", FORM_SCOPE},
        };
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {
            PropertyIdxUIHelper helper = new PropertyIdxUIHelper();
            IPropertyIdxTrxValue PropertyIdxTrxObj = (IPropertyIdxTrxValue) map.get("IPropertyIdxTrxValue");
            String event = (String) map.get("event");
            int index = Integer.parseInt((String) map.get("indexID"));

            // from_event will be "read" at maker view item list
            // from_event will be "process" at checker review item list
            // from_event will be "close" at maker close rejected item detail
            // from_event will be null at maker update item list
            // only for "read" case, item should be populated from the actual object
            // for the other 3 cases item should be populated from the staging object
            String from_event = (String) map.get("fromEvent");
            IPropertyIdxItem curItem = helper.getCurWorkingPropertyIdxItem(event, from_event, index, PropertyIdxTrxObj);
            result.put("PropertyIdxItemForm", curItem);
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
