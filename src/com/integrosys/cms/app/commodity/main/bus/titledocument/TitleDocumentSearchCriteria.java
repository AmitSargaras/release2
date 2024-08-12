/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/TitleDocumentSearchCriteria.java,v 1.3 2004/11/17 06:42:27 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 11:15:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class TitleDocumentSearchCriteria extends CommodityMainInfoSearchCriteria {

	private String documentType;

	public TitleDocumentSearchCriteria() {
		super(ICommodityMainInfo.INFO_TYPE_TITLEDOC);
	}

	public String getType() {
		return documentType;
	}

	public void setType(String type) {
		documentType = type;
	}
}
