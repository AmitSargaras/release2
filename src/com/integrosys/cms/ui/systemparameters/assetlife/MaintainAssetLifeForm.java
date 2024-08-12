/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/MaintainAssetLifeForm.java,v 1.1 2007/01/30 Jerlin Exp $
 */
package com.integrosys.cms.ui.systemparameters.assetlife;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by Asset Life Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/30$ Tag: $Name$
 */

public class MaintainAssetLifeForm extends TrxContextForm implements Serializable {

	private String subTypeCodes[];

	private String subTypeDescs[];

	private String subTypeNames[];

	private String yearValues[];

	/**
	 * Description : get method for form to get the sub type code
	 * 
	 * @return subTypeCodes
	 */

	public String[] getSubTypeCodes() {

		return subTypeCodes;

	}

	/**
	 * Description : set the sub type code
	 * 
	 * @param subTypeCodes is the sub type code value
	 */

	public void setSubTypeCodes(String[] subTypeCodes) {

		this.subTypeCodes = subTypeCodes;

	}

	/**
	 * Description : get method for form to get the sub type description
	 * 
	 * @return subTypeDescs
	 */

	public String[] getSubTypeDescs() {

		return subTypeDescs;

	}

	/**
	 * Description : set the sub type description
	 * 
	 * @param subTypeDescs is the sub type description
	 */

	public void setSubTypeDescs(String[] subTypeDescs) {

		this.subTypeDescs = subTypeDescs;

	}

	/**
	 * Description : get method for form to get the sub type Name
	 * 
	 * @return subTypeNames
	 */

	public String[] getSubTypeNames() {

		return subTypeNames;

	}

	/**
	 * Description : set the sub type name
	 * 
	 * @param subTypeNames is the sub type name
	 */

	public void setSubTypeNames(String[] subTypeNames) {

		this.subTypeNames = subTypeNames;

	}

	/**
	 * Description : get method for form to get the year value
	 * 
	 * @return yearValues
	 */

	public String[] getYearValues() {

		return yearValues;

	}

	/**
	 * Description : set the value for year
	 * 
	 * @param yearValues is the year value
	 */

	public void setYearValues(String[] yearValues) {

		this.yearValues = yearValues;

	}

	public String toString() {

		return AccessorUtil.printMethodValue(this);

	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialCollateralAssetLife", "com.integrosys.cms.ui.systemparameters.assetlife.MaintainAssetLifeMapper" },

		{ "CollateralAssetLifes", "com.integrosys.cms.ui.systemparameters.assetlife.MaintainAssetLifeMapper" },

		{ "CollateralAssetLifeTrxValue", "com.integrosys.cms.ui.systemparameters.assetlife.MaintainAssetLifeMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.systemparameters.assetlife.MaintainAssetLifeMapper" }

		};

		return input;

	}
}
