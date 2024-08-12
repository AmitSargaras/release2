package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamList;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import com.integrosys.component.commondata.app.CommonDataSingleton;

import java.util.*;

import org.apache.struts.util.LabelValueBean;
import org.apache.commons.lang.StringUtils;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 4, 2008
 * Time: 4:53:34 PM
 * Desc: Prepare bank entity branch param item command class
 */

//public class PrepareBankEntityParamItemCmd extends AbstractCommand {
public class PrepareBankEntityParamItemCmd extends BankEntityBranchParamCmd {

    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"bankEntityBranchTrxValue","com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
        };}


    public String[][] getResultDescriptor() {
        return new String[][]{
                {"bankEntity", "java.util.ArrayList", REQUEST_SCOPE},
                {"BankEntityBranchParamItemForm", "java.lang.Object", FORM_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
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
        DefaultLogger.debug(this, "Map is " + map);

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

            HashMap bankEntityMap = CommonDataSingleton.getCodeCategoryLabelValueMap(ICMSConstant.SUB_LIMIT_DESC_CATEGORY_CODE, null, null , ICMSConstant.SUB_LIMIT_TYPE_BANK_ENTITY_ENTRY_CODE);
            IBankEntityBranchTrxValue trxValue = (IBankEntityBranchTrxValue)map.get("bankEntityBranchTrxValue");

            String indexID = (String) map.get("indexID");
            ArrayList bankEntityL = new ArrayList();
            LabelValueBean b = new LabelValueBean("Please Select", "0");
            bankEntityL.add(b);
            for (Iterator iterator = bankEntityMap.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry =  ( Map.Entry ) iterator.next ();
                b = new LabelValueBean(entry.getKey().toString(), entry.getValue().toString());
                bankEntityL.add(b);
            }

            if(StringUtils.isNotEmpty(indexID))
            {
                int index = Integer.parseInt(indexID);
                IBankEntityBranchParam editParam = (IBankEntityBranchParam) new ArrayList(trxValue.getStagingBankEntityBranchParam()).get(index);
                resultMap.put("BankEntityBranchParamItemForm", editParam);
                resultMap.put("indexID", indexID);
            }

            resultMap.put("bankEntity", bankEntityL);
            //need to pass back the remarks to parent form
            resultMap.put("remarks", (String) map.get("remarks"));

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }	
	
}