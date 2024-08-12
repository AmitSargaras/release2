package com.integrosys.cms.host.stp.chain;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Oct 14, 2008
 * Time: 6:24:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpCommand {

    public boolean execute(Map c) throws Exception;
}
