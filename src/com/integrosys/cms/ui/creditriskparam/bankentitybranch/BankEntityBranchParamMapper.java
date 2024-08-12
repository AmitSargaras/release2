package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.OBBankEntityBranchTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamList;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.OBBankEntityBranchParamList;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.OBBankEntityBranchParam;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

import java.util.*;

import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 6:13:25 PM
 * Desc: Bank entity branch param mapper
 */
public class BankEntityBranchParamMapper extends AbstractCommonMapper {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"bankEntityBranchTrxValue", "com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
                //{"event", "java.lang.String", REQUEST_SCOPE},
        });
    }

    public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap)
            throws MapperException {

        try {
            BankEntityBranchParamForm form = (BankEntityBranchParamForm) aForm;
            IBankEntityBranchParamList paramList = (IBankEntityBranchParamList) object;
            OBBankEntityBranchTrxValue trxValue = (OBBankEntityBranchTrxValue) hashMap.get("bankEntityBranchTrxValue");
            form.setLastRemarks(paramList.getLastRemarks());
            form.setLastActionBy(paramList.getLastActionBy());

            if(paramList.getBankEntityBranchParams()!=null)
            {
                ArrayList tmp = sortByBankEntity((ArrayList) paramList.getBankEntityBranchParams());
                //store sorted list into staging and form
                form.setBankEntityBranchParams(tmp);
                trxValue.setStagingBankEntityBranchParam(tmp);
            } else {
                form.setBankEntityBranchParams(new ArrayList());
            }

            if ("checker_prepare".equals(aForm.getEvent())) {
                //we sort 1st before do item castor comparison
                trxValue.setBankEntityBranchParam(sortByBankEntity((ArrayList) trxValue.getBankEntityBranchParam()));
                renderCompareItem(trxValue.getBankEntityBranchParam(),
                        trxValue.getStagingBankEntityBranchParam(),
                        form);
            }

            form.setDeleteItems(new String[0]);

            return form;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }


    public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
            throws MapperException {

        String event = aForm.getEvent();
        IBankEntityBranchParamList paramList = null;
        BankEntityBranchParamForm form = (BankEntityBranchParamForm) aForm;

        OBBankEntityBranchTrxValue trxValue = (OBBankEntityBranchTrxValue) hashMap.get("bankEntityBranchTrxValue");

        if (paramList == null) {
            paramList = new OBBankEntityBranchParamList();
        }
        paramList.setBankEntityBranchParams(trxValue.getStagingBankEntityBranchParam());
        if (event.equals("maker_delete_item")) {
            deleteItem(paramList, form);
        }

        paramList.setRemarks(form.getRemarks());
        return paramList;
    }

    private void deleteItem(IBankEntityBranchParamList paramList, BankEntityBranchParamForm aForm) {
        String[] deleteInd = aForm.getDeleteItems();
        ArrayList obCol = new ArrayList(paramList.getBankEntityBranchParams());

        if (deleteInd != null && deleteInd.length > 0) {
            Collection temp = new ArrayList();
            for (int i = 0; i < deleteInd.length; i++) {
                int nextDelInd = Integer.parseInt(deleteInd[i]);
                temp.add(obCol.get(nextDelInd));
            }

            obCol.removeAll(temp);
            temp.clear();
        }

        paramList.setBankEntityBranchParams(obCol);
    }

    private static void renderCompareItem(Collection actual, Collection stage, BankEntityBranchParamForm aForm) throws Exception {
        IBankEntityBranchParam[] oldLmtRef = null;
        IBankEntityBranchParam[] newLmtRef = null;

        if (actual != null) {
            ArrayList compareitemList = new ArrayList(actual);
            oldLmtRef = (IBankEntityBranchParam[]) compareitemList.toArray(new IBankEntityBranchParam[compareitemList.size()]);
        }
        if (stage != null) {
            ArrayList comparestagingitemList = new ArrayList(stage);
            newLmtRef = (IBankEntityBranchParam[]) comparestagingitemList.toArray(new IBankEntityBranchParam[comparestagingitemList.size()]);
        }

        if (oldLmtRef == null) {
            oldLmtRef = new IBankEntityBranchParam[0];
        }
        if (newLmtRef == null) {
            newLmtRef = new IBankEntityBranchParam[0];
        }

        aForm.setBankEntityBranchParams(CompareOBUtil.compOBArray(newLmtRef, oldLmtRef));
    }

    private static ArrayList sortByBankEntity(ArrayList list)
    {
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            IBankEntityBranchParam o =  (OBBankEntityBranchParam) iterator.next();
            o.setEntityTypeDesc(CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.SUB_LIMIT_DESC_CATEGORY_CODE, o.getEntityType()));
        }

        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                IBankEntityBranchParam ob1 = (IBankEntityBranchParam) o1;
                IBankEntityBranchParam ob2 = (IBankEntityBranchParam) o2;

                String br1 = CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_CODE_BKGLOC, ob1.getBranchCode());
                String br2 = CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_CODE_BKGLOC, ob2.getBranchCode());

                int ntt = ob1.getEntityTypeDesc().compareToIgnoreCase(ob2.getEntityTypeDesc());
                if (ntt == 0)
                    return br1.compareToIgnoreCase(br2);
                else
                    return ntt;
            }
        };

        Collections.sort(list,comp);

        return list;
    }

}
