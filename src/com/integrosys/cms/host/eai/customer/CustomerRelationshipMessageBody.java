package com.integrosys.cms.host.eai.customer;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerRelationshipMessageBody extends EAIBody implements java.io.Serializable {
	/*
	 * <class extends="com.integrosys.cms.host.message.castor.eai.EAIBody"name=
	 * "com.integrosys.cms.host.message.castor.eai.customer.CustomerEnquiryMessageBody"
	 * > <map-to xml="MsgBody"/> <field name="CMSHeader"
	 * type="com.integrosys.cms.host.message.castor.eai.CMSHeader"> <xml
	 * name="CMSHeader" /> </field> <field name="RelationshipHeader"type=
	 * "com.integrosys.cms.host.message.castor.eai.customer.EntityRelationshipHeader"
	 * /> <field name="Linkage"
	 * type="com.integrosys.cms.host.message.castor.eai.customer.EntityRelationship"
	 * location="RelationshipDetail" collection="vector"/> </class>
	 */
	private com.integrosys.cms.host.eai.CMSHeader cMSHeader;

	EntityRelationshipHeader entityRelationshipHeader;

	EntityRelationshipDetail entityRelationshipDetail;

	public final com.integrosys.cms.host.eai.CMSHeader getCMSHeader() {
		return cMSHeader;
	}

	public final void setCMSHeader(com.integrosys.cms.host.eai.CMSHeader header) {
		cMSHeader = header;
	}

	public final EntityRelationshipDetail getEntityRelationshipDetail() {
		return entityRelationshipDetail;
	}

	public final void setEntityRelationshipDetail(EntityRelationshipDetail entityRelationshipDetail) {
		this.entityRelationshipDetail = entityRelationshipDetail;
	}

	public final EntityRelationshipHeader getEntityRelationshipHeader() {
		return entityRelationshipHeader;
	}

	public final void setEntityRelationshipHeader(EntityRelationshipHeader entityRelationshipHeader) {
		this.entityRelationshipHeader = entityRelationshipHeader;
	}

}
