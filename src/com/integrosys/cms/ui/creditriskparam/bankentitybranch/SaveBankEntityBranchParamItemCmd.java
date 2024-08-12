package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 5, 2008
 * Time: 2:30:34 AM
 * Desc: Save bank entity branch param item command class
 */
//public class SaveBankEntityBranchParamItemCmd
//        extends AbstractCommand {
public class SaveBankEntityBranchParamItemCmd
    extends BankEntityBranchParamCmd {

    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"BankEntityBranchParamItemForm", "com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"bankEntityBranchTrxValue", "com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
        };
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map)
            throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

            IBankEntityBranchParam entry = (IBankEntityBranchParam) map.get("BankEntityBranchParamItemForm");
            IBankEntityBranchTrxValue value = (IBankEntityBranchTrxValue) map.get("bankEntityBranchTrxValue");
            IBankEntityBranchParam editParam = null;
            ArrayList itemL = null;

            if(value.getStagingBankEntityBranchParam() == null) //item list would be null for initial submission
                itemL = new ArrayList();
            else
                itemL = new ArrayList(value.getStagingBankEntityBranchParam());

            String indexID = (String) map.get("indexID");
            String event = (String) map.get("event");

            if (itemL == null)  //for intial setup
                itemL = new ArrayList();

            if(StringUtils.isNotEmpty(indexID))
                editParam = (IBankEntityBranchParam) itemL.get(Integer.parseInt(indexID));

            if (itemL.size() > 0) {
                if(editParam==null || !editParam.getBranchCode().equals(entry.getBranchCode()))
                {
                    //validate duplicate branch code with different entity, only when there is more than 1 item
                    for (Iterator iterator = itemL.iterator(); iterator.hasNext();) {
                        IBankEntityBranchParam iBankEntityBranchParam = (IBankEntityBranchParam) iterator.next();

                        if (iBankEntityBranchParam.getBranchCode().equals(entry.getBranchCode())) {
                            exceptionMap.put("duplicateEntryError", new ActionMessage("error.bank.entity.branch.param.item.duplicate"));
                            break;
                        }
                    }
                }
            }

            if (exceptionMap.size() == 0) {
                if (("maker_edit_item").equals(event)) {
                    //set the existing OB with amended fields to retain cmsRefId for ejb update
                    editParam.setBranchCode(entry.getBranchCode());
                    editParam.setEntityType(entry.getEntityType());
                    int index = Integer.parseInt(indexID);
                    itemL.remove(index);
                    itemL.add(index, editParam);
                } else {
                    itemL.add(entry);
                }
                //sort the list with new/updated item before setting into staging
                Collections.sort(itemL);
                value.setStagingBankEntityBranchParam(itemL);
            }
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}