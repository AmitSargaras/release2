package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 1:51:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBankEntityBranchParamDao {

    public final static String ACTUAL_ENTITY_NAME = "actualBankEntityBranch";
    public final static String STAGING_ENTITY_NAME = "stageBankEntityBranch";

    public List findAll(String entityName);

    public List findByGroupId(String entityName, long groupId);

    public IBankEntityBranchParam findByPrimaryKey(String entityName, long key);

    public IBankEntityBranchParam createInternalCreditRating(String entityName, IBankEntityBranchParam iBEBP);

    public IBankEntityBranchParam updateInternalCreditRating(String entityName, IBankEntityBranchParam iBEBP);

}