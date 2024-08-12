package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;

import java.util.HashMap;

/**
 * @author Andy Wong
 * @since 16 Spe 2008
 */
public class DeleteItemCmd extends PropertyIdxCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.IPropertyIdx", FORM_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.IPropertyIdx", FORM_SCOPE}
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {
            IPropertyIdx propertyIdx = (IPropertyIdx) (map.get("PropertyIndex"));
            result.put("PropertyIndex", propertyIdx);
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
