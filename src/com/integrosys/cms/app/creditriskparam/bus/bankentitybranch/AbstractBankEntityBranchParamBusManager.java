/**
 *
 */
package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

import java.util.List;

/**
 * @author priya
 */
public abstract class AbstractBankEntityBranchParamBusManager implements IBankEntityBranchParamBusManager {

    private IBankEntityBranchParamDao bankEntityBranchParamDao;

    public IBankEntityBranchParamDao getBankEntityBranchParamDao() {
        return bankEntityBranchParamDao;
    }

    public void setBankEntityBranchParamDao(IBankEntityBranchParamDao bankEntityBranchParamDao) {
        this.bankEntityBranchParamDao = bankEntityBranchParamDao;
    }

    public List findAll() {
        return getBankEntityBranchParamDao().findAll(getBankEntityBranchParamEntityName());
    }

    public List findByGroupID(long groupId) {
        return getBankEntityBranchParamDao().findByGroupId(getBankEntityBranchParamEntityName(), groupId);
    }

    public IBankEntityBranchParam findByPrimaryKey(long key) {
        return getBankEntityBranchParamDao().findByPrimaryKey(getBankEntityBranchParamEntityName(), key);
    }

    public IBankEntityBranchParam createBankEntityBranchParam(IBankEntityBranchParam iBEBP) {
        return getBankEntityBranchParamDao().createInternalCreditRating(getBankEntityBranchParamEntityName(), iBEBP);
    }

    public IBankEntityBranchParam updateBankEntityBranchParam(IBankEntityBranchParam iBEBP) {
        return getBankEntityBranchParamDao().updateInternalCreditRating(getBankEntityBranchParamEntityName(), iBEBP);
    }

    public abstract String getBankEntityBranchParamEntityName();

}
