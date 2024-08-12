/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/concentrationreport/bus/IRegion.java,v 1.1 2003/08/25 11:18:30 btchng Exp $
 */
package com.integrosys.cms.app.concentrationreport.bus;

/**
 * Represents a region.
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 11:18:30 $ Tag: $Name: $
 */
public interface IRegion {

	String getCode();

	String getName();

	String getDescription();

	void setCode(String code);

	void setName(String name);

	void setDescription(String description);

}
