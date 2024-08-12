package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditriskparam.proxy.bankentitybranch.IBankEntityBranchParamProxy;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 26, 2010
 * Time: 5:10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BankEntityBranchParamCmd extends AbstractCommand {

    private IBankEntityBranchParamProxy bankEntityBranchParamProxy;

    public IBankEntityBranchParamProxy getBankEntityBranchParamProxy() {
        return bankEntityBranchParamProxy;
    }

    public void setBankEntityBranchParamProxy(IBankEntityBranchParamProxy bankEntityBranchParamProxy) {
        this.bankEntityBranchParamProxy = bankEntityBranchParamProxy;
    }
}
