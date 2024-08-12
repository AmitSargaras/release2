package com.integrosys.cms.host.stp.common;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Dec 14, 2008
 * Time: 1:27:42 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpErrorMessageFetcher {

    /**
     * Fetch Stp error messages either from CMS validation or SIBS
     *
     * @param transactionId - PK in TRANSACTION table
     * @return list of error message to be display on UI
     */
    public List getErrorMessage(String transactionId);
}
