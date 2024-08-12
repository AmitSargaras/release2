/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/IMemo.java,v 1.4 2004/11/21 10:41:32 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * This interface defines the list of attributes that will be available to the
 * custodian document memo. The set of attributes will be common to both
 * lodgement and withdrawal memo
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/11/21 10:41:32 $ Tag: $Name: $
 */
public interface IMemo extends Serializable {
	public String getMemoReference();

	public Date getMemoDate();

	public String getMemoFrom();

	public String getMemoTo();

	public String getMemoType();

	public String getMemoSubject();

	public String getFAMCode();

	public ICustodianDoc[] getCustodianDocList();

	public ICMSCustomer getCustomer();
}
