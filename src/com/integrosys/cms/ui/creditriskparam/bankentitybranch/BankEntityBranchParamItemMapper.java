package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.OBBankEntityBranchParam;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 6:13:25 PM
 * Desc: Bank entity branch param mapper
 */
public class BankEntityBranchParamItemMapper extends AbstractCommonMapper {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
        });
    }

    public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap)
            throws MapperException {
        BankEntityBranchParamItemForm form = (BankEntityBranchParamItemForm) aForm;
        IBankEntityBranchParam param = (IBankEntityBranchParam) object;

        form.setBranchCode(param.getBranchCode());
        form.setEntityTypeCode(param.getEntityType());
        form.setEntityTypeDesc(CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.SUB_LIMIT_DESC_CATEGORY_CODE, param.getEntityType()));

        return form;
    }


    public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
            throws MapperException {

        BankEntityBranchParamItemForm form = (BankEntityBranchParamItemForm) aForm;
        IBankEntityBranchParam param = new OBBankEntityBranchParam();
        param.setBranchCode(form.getBranchCode());
        param.setEntityType(form.getEntityTypeCode());
        param.setEntityTypeDesc(CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.SUB_LIMIT_DESC_CATEGORY_CODE, param.getEntityType()));
        param.setStatus(ICMSConstant.STATE_ACTIVE);

        return param;
    }
}

