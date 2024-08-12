package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;

import com.integrosys.cms.host.eai.StandardCode;

public class ReceivableDetail implements Serializable {
	private String receivablesByBank;

	private StandardCode accountNoLocation;

	private String accountNo;

	private StandardCode invoiceType;

	public String getReceivablesByBank() {
		return receivablesByBank;
	}

	public void setReceivablesByBank(String receivablesByBank) {
		this.receivablesByBank = receivablesByBank;
	}

	public StandardCode getAccountNoLocation() {
		return accountNoLocation;
	}

	public void setAccountNoLocation(StandardCode accountNoLocation) {
		this.accountNoLocation = accountNoLocation;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public StandardCode getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(StandardCode invoiceType) {
		this.invoiceType = invoiceType;
	}

}
