package com.integrosys.cms.host.mq;

import com.integrosys.cms.host.stp.bus.IStpTransBusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: May 4, 2010
 * Time: 5:19:22 PM
 * To change this template use File | Settings | File Templates.
 *
 * Interface template for message sender proxy
 *
 * support MQ or Socket communication depending injected sender bean
 */
public interface IMessageSenderProxy {

    public Object sendAndReceive(Object input, String correlationId);
}
