package com.integrosys.cms.host.stp.chain;

import com.integrosys.cms.host.stp.bus.IStpMasterTrans;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Oct 14, 2008
 * Time: 3:59:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpCatalogLoader {
    /**
     * Method to begin executing Chain command
     *
     * @param obStpMasterTrans
     * @return
     * @throws Exception
     */
    boolean executeCommand(IStpMasterTrans obStpMasterTrans) throws Exception;
}
