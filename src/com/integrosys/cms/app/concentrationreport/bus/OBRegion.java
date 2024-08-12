/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/concentrationreport/bus/OBRegion.java,v 1.1 2003/08/25 11:18:30 btchng Exp $
 */
package com.integrosys.cms.app.concentrationreport.bus;

/**
 * Represents a region.
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 11:18:30 $ Tag: $Name: $
 */
public class OBRegion implements IRegion {

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String code;

	private String name;

	private String description;

}
