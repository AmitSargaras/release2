package com.integrosys.cms.host.stp.chain;

import com.integrosys.cms.host.stp.bus.IStpTransBusManager;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: USER
 * Date: Feb 14, 2009
 * Time: 2:17:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpCommandExceptionHandler {
    IStpTransBusManager getStpTransBusManager();

    boolean postprocess(Map context, Exception e);
}
