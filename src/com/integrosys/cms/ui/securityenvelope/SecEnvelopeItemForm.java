/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.securityenvelope;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SecEnvelopeItemForm extends CommonForm implements Serializable {


    private String indId;
    private String secEnvelopeItemId;
    private String secEnvelopeItemAddr;
    private String secEnvelopeItemCab;
    private String secEnvelopeItemDrw;
    private String secEnvelopeItemBarcode;
    private String secEnvelopeRefId;
    private String status;
    private String fromEvent;
	
	public String getFromEvent() {
		return fromEvent;
	}

	public void setFromEvent(String fromEvent) {
		this.fromEvent = fromEvent;
	}

    public String getIndId() {
		return indId;
	}

	public void setIndId(String indId) {
		this.indId = indId;
	}

    public String getSecEnvelopeItemId() {
		return secEnvelopeItemId;
	}

	public void setSecEnvelopeItemId(String secEnvelopeItemId) {
		this.secEnvelopeItemId = secEnvelopeItemId;
	}

    public String getSecEnvelopeItemAddr() {
		return secEnvelopeItemAddr;
	}

	public void setSecEnvelopeItemAddr(String secEnvelopeItemAddr) {
		this.secEnvelopeItemAddr = secEnvelopeItemAddr;
	}

	public String getSecEnvelopeItemCab() {
		return secEnvelopeItemCab;
	}

	public void setSecEnvelopeItemCab(String secEnvelopeItemCab) {
		this.secEnvelopeItemCab = secEnvelopeItemCab;
	}

    public String getSecEnvelopeItemDrw() {
		return secEnvelopeItemDrw;
	}

	public void setSecEnvelopeItemDrw(String secEnvelopeItemDrw) {
		this.secEnvelopeItemDrw = secEnvelopeItemDrw;
	}

    public String getSecEnvelopeItemBarcode() {
		return secEnvelopeItemBarcode;
	}

	public void setSecEnvelopeItemBarcode(String secEnvelopeItemBarcode) {
		this.secEnvelopeItemBarcode = secEnvelopeItemBarcode;
	}

    public String getSecEnvelopeRefId() {
		return secEnvelopeRefId;
	}

	public void setSecEnvelopeRefId(String secEnvelopeRefId) {
		this.secEnvelopeRefId = secEnvelopeRefId;
	}

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = 
		{
			{"SecEnvelopeItemForm", "com.integrosys.cms.ui.securityenvelope.SecEnvelopeItemMapper"},
		};
		return input;
	}

}
