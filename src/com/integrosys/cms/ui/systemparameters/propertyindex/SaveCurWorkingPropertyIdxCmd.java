package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public class SaveCurWorkingPropertyIdxCmd extends PropertyIdxCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.IPropertyIdx", FORM_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {

            IPropertyIdx propertyIdx = (IPropertyIdx) (map.get("PropertyIndex"));
            boolean isExist = getPropertyIdxProxy().isSecSubTypeValTypeExist(propertyIdx.getPropertyIdxId(), propertyIdx.getPropertyIdxSecSubTypeList(), propertyIdx.getValDescr());
            if (isExist) {
                exceptionMap.put("secsubtypeValdescExistError", new ActionMessage("error.property.secsubtype.valdesc.exist"));
            }
            if (exceptionMap.size() == 0) {
                IPropertyIdxTrxValue PropertyIdxTrxObj = (IPropertyIdxTrxValue) (map.get("IPropertyIdxTrxValue"));
                DefaultLogger.debug("this", "Save cur...getStagingPrIdx : " + PropertyIdxTrxObj.getStagingPrIdx());

                if (PropertyIdxTrxObj.getStagingPrIdx() != null) {
                    propertyIdx.setPropertyIdxItemList(PropertyIdxTrxObj.getStagingPrIdx().getPropertyIdxItemList());
                }

                PropertyIdxTrxObj.setStagingPrIdx(propertyIdx);
                result.put("IPropertyIdxTrxValue", PropertyIdxTrxObj);
            }
        }
        catch (Exception ex) {
            DefaultLogger.error(this, ex);
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
