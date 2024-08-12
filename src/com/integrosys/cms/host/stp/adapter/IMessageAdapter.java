package com.integrosys.cms.host.stp.adapter;

import java.util.List;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 25, 2008
 * Time: 5:42:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IMessageAdapter {
    byte[] constructMessageToByte(List aList) throws UnsupportedEncodingException;

    String constructMessageToString(List aList);

    List decipherMessageToList(byte[] aBytes, List aList) throws UnsupportedEncodingException;

    String decipherMessageToString(byte[] aBytes, List aList) throws UnsupportedEncodingException;
}
