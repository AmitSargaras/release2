/**
 *
 */
package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

import java.util.Collection;
import java.util.List;

/**
 * @author priya
 */
public interface IBankEntityBranchParamBusManager {

    public Collection getBankEntityBranchParam() throws BankEntityBranchParamException;

    public Collection getBankEntityBranchParamByGroupID(long groupID) throws BankEntityBranchParamException;

    public Collection updateBankEntityBranchParam(Collection entityBranch) throws BankEntityBranchParamException;

    public Collection createBankEntityBranchParam(Collection entityBranch) throws BankEntityBranchParamException;    

}
