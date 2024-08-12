package com.integrosys.cms.host.eai.customer;


/**
 * JDO For Entity Relationship
 * 
 * @author allen
 * 
 */
public final class EntityRelationshipDetail implements java.io.Serializable {

	/*
	 * <map-to xml="RelationshipHeader"/> <field name="mainCifNo" type="string">
	 * <xml name="MainCIFNo" node="element" /> </field> <field
	 * name="mainCifSource" type="string"> <xml name="MainCIFSource"
	 * node="element" /> </field> <field name="mainCifName" type="string"> <xml
	 * name="MainCIFName" node="element" /> </field>
	 */

	java.util.Vector linkage;

	public final java.util.Vector getLinkage() {
		return linkage;
	}

	public final void setLinkage(java.util.Vector linkage) {
		this.linkage = linkage;
	}

	private char updateStatusIndicator;

	private char changeIndicator;

	public final char getChangeIndicator() {
		return changeIndicator;
	}

	public final void setChangeIndicator(char changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public final char getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public final void setUpdateStatusIndicator(char updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

}
