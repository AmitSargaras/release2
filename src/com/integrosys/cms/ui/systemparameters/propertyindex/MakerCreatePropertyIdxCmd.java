package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;

/**
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public class MakerCreatePropertyIdxCmd extends PropertyIdxCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.IPropertyIdx", FORM_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            IPropertyIdxTrxValue PropertyIdxTrxObj = (IPropertyIdxTrxValue) map.get("IPropertyIdxTrxValue");
            IPropertyIdx property = (IPropertyIdx) (map.get("PropertyIndex"));
            PropertyIdxUIHelper helper = new PropertyIdxUIHelper();

            if (!(PropertyIdxTrxObj != null &&
                    PropertyIdxTrxObj.getStagingPrIdx() != null &&
                    PropertyIdxTrxObj.getStagingPrIdx().getPropertyIdxItemList() != null &&
                    PropertyIdxTrxObj.getStagingPrIdx().getPropertyIdxItemList().size() > 0)) {
                exceptionMap.put("itemEmptyError", new ActionMessage("error.property.item"));
            }

            if (exceptionMap.size() == 0) {
                property.setPropertyIdxItemList(PropertyIdxTrxObj.getStagingPrIdx().getPropertyIdxItemList());
                IPropertyIdxTrxValue trxValue = getPropertyIdxProxy().makerCreatePropertyIdx(ctx, property);
                result.put("request.ITrxValue", trxValue);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
