package com.integrosys.cms.host.eai.customer;


/**
 * JDO For Entity Relationship
 * 
 * @author allen
 * 
 */
public final class EntityRelationshipHeader implements java.io.Serializable {

	/*
	 * <map-to xml="RelationshipHeader"/> <field name="mainCifNo" type="string">
	 * <xml name="MainCIFNo" node="element" /> </field> <field
	 * name="mainCifSource" type="string"> <xml name="MainCIFSource"
	 * node="element" /> </field> <field name="mainCifName" type="string"> <xml
	 * name="MainCIFName" node="element" /> </field>
	 */

	private String mainCifNo;

	private String mainCifSource;

	private String mainCifName;

	public final String getMainCifName() {
		return mainCifName;
	}

	public final void setMainCifName(String mainCifName) {
		this.mainCifName = mainCifName;
	}

	public final String getMainCifNo() {
		return mainCifNo;
	}

	public final void setMainCifNo(String mainCifNo) {
		this.mainCifNo = mainCifNo;
	}

	public final String getMainCifSource() {
		return mainCifSource;
	}

	public final void setMainCifSource(String mainCifSource) {
		this.mainCifSource = mainCifSource;
	}

}
