package com.integrosys.cms.host.stp;
/**
 * @author Andy Wong
 * @author Chin Kok Cheong
 *
 */
/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 3, 2008
 * Time: 11:55:09 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IMessage {

    public void setMsgBody(STPBody msgBody);

	public void setMsgHeader(STPHeader msgHeader);

    public STPBody getMsgBody();

	public STPHeader getMsgHeader();

    /*public void setCorrelationId(String correlationId);

    public String getCorrelationId();*/
}