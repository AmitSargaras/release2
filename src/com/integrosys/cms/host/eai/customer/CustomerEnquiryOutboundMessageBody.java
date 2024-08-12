package com.integrosys.cms.host.eai.customer;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerEnquiryOutboundMessageBody extends EAIBody implements java.io.Serializable {
	/*
	 * <class extends="com.integrosys.cms.host.message.castor.eai.EAIBody"name=
	 * "com.integrosys.cms.host.message.castor.eai.customer.CustomerEnquiryMessageBody"
	 * > <map-to xml="MsgBody"/> <field name="CMSHeader"
	 * type="com.integrosys.cms.host.message.castor.eai.CMSHeader"> <xml
	 * name="CMSHeader" /> </field> <field name="SearchHeader"
	 * type="com.integrosys.cms.host.message.castor.eai.customer.SearchHeader"/>
	 * <field name="SearchDetail"
	 * type="com.integrosys.cms.host.message.castor.eai.customer.SearchDetail"/>
	 * </class>
	 */
	private com.integrosys.cms.host.eai.CMSHeader cMSHeader;

	private SearchEnquiry search;

	public final com.integrosys.cms.host.eai.CMSHeader getCMSHeader() {
		return cMSHeader;
	}

	public final void setCMSHeader(com.integrosys.cms.host.eai.CMSHeader header) {
		cMSHeader = header;
	}

	public final SearchEnquiry getSearch() {
		return search;
	}

	public final void setSearch(SearchEnquiry search) {
		this.search = search;
	}
}
