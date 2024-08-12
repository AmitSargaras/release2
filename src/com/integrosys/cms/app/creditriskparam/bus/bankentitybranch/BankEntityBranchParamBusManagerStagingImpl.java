package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:33:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankEntityBranchParamBusManagerStagingImpl extends BankEntityBranchParamBusManagerImpl {

    public String getBankEntityBranchParamEntityName() {
        return IBankEntityBranchParamDao.STAGING_ENTITY_NAME;
    }

}