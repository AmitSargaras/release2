package com.integrosys.cms.host.stp.proxy;

import com.integrosys.cms.host.stp.bus.IStpMasterTrans;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 31, 2008
 * Time: 12:00:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpAsyncProxy {
    /**
     * Submit Stp task to Stp module
     *
     * @param transactionId
     * @param referenceId
     * @param transactionType
     */
    void submitTask(String transactionId, long referenceId, String transactionType);

    /**
     * Async poller implemntation, called from commonj timer
     */
    void run();
}
