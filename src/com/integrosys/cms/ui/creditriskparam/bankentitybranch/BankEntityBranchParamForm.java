package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 5:25:11 PM
 * Desc: Bank entity branch param list form
 */
public class BankEntityBranchParamForm extends TrxContextForm implements Serializable {

    public String[][] getMapper() {
        String[][] input =
                {
                        {"BankEntityBranchParamForm", "com.integrosys.cms.ui.creditriskparam.bankentitybranch.BankEntityBranchParamMapper"},
                        {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                };
        return input;
    }

    private String [] deleteItems;

    private Collection bankEntityBranchParams;

    private String lastActionBy;

    private String lastRemarks;

    private String remarks;

    public String getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(String lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public String getLastRemarks() {
        return lastRemarks;
    }

    public void setLastRemarks(String lastRemarks) {
        this.lastRemarks = lastRemarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Collection getBankEntityBranchParams() {
        return bankEntityBranchParams;
    }

    public void setBankEntityBranchParams(Collection bankEntityBranchParams) {
        this.bankEntityBranchParams = bankEntityBranchParams;
    }

    public String[] getDeleteItems() {
        return deleteItems;
    }

    public void setDeleteItems(String[] deleteItems) {
        this.deleteItems = deleteItems;
    }
 
}
