package com.integrosys.cms.host.stp.trade;

import com.integrosys.cms.host.stp.STPBody;

/**
 * @author $Author: marvin $<br>
 * @author Chin Kok Cheong
 * @version $Id$
 */
public class TradeResponseBody extends STPBody implements java.io.Serializable {


    private String responseCode;
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}