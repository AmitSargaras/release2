package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:33:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalCreditRatingBusManagerStagingImpl extends InternalCreditRatingBusManagerImpl {

    public String getInternalCreditRatingxEntityName() {
        return IInternalCreditRatingDao.STAGING_ENTITY_NAME;
    }

}