package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 5:35:46 PM
 * Desc: Bank entity branch param item form
 */
public class BankEntityBranchParamItemForm extends TrxContextForm implements Serializable {

    public String[][] getMapper() {
        String[][] input =
                {
                        {"BankEntityBranchParamItemForm", "com.integrosys.cms.ui.creditriskparam.bankentitybranch.BankEntityBranchParamItemMapper"},
                        {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                };
        return input;
    }

    private String entityTypeCode;
    private String entityTypeDesc;
    private String branchCode;
    private String branchCodeDesc;
    private String branchCodeSrc;

    public String getEntityTypeCode() {
        return entityTypeCode;
    }

    public void setEntityTypeCode(String entityTypeCode) {
        this.entityTypeCode = entityTypeCode;
    }

    public String getEntityTypeDesc() {
        return entityTypeDesc;
    }

    public void setEntityTypeDesc(String entityTypeDesc) {
        this.entityTypeDesc = entityTypeDesc;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchCodeDesc() {
        return branchCodeDesc;
    }

    public void setBranchCodeDesc(String branchCodeDesc) {
        this.branchCodeDesc = branchCodeDesc;
    }

    public String getBranchCodeSrc() {
        return branchCodeSrc;
    }

    public void setBranchCodeSrc(String branchCodeSrc) {
        this.branchCodeSrc = branchCodeSrc;
    }
}
