package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.cms.ui.common.TrxContextForm;
import java.io.Serializable;
import java.util.List;

/**
* Describe this class.
* Purpose: To set get and set method for the value needed by Auto Valuation Parameters
* Description: Have set and get method to store the screen value and get the value from other command class
*
* @author $Author$<br>
* @version $Revision$
* @since $Date$
* Tag: $Name$
*/

public class PropertyIdxForm extends TrxContextForm implements Serializable {
	
	
	 private String[] selectedPropertySubtype = new String[0];
	 private String[] unselectedPropertySubtype = new String[0];
	 private List propertyIdxItemList;
	 private String[] deletedItemList;
	 private String valuationDescription;
	 private String country;
	
	
    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
     * This method defines a String array which tells what object is to be formed from the form and using what mapper classes to form it.
     * @return input
     */

    public String[][] getMapper() {

        String[][] input = {
                {"PropertyIndex", "com.integrosys.cms.ui.systemparameters.propertyindex.PropertyIdxMapper"},
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
            };
        return input;

   }

	public String[] getSelectedPropertySubtype() {
		return selectedPropertySubtype;
	}

	public void setSelectedPropertySubtype(String[] selectedPropertySubtype) {
		this.selectedPropertySubtype = selectedPropertySubtype;
	}

	public String[] getUnselectedPropertySubtype() {
		return unselectedPropertySubtype;
	}

	public void setUnselectedPropertySubtype(String[] unselectedPropertySubtype) {
		this.unselectedPropertySubtype = unselectedPropertySubtype;
	}

	public List getPropertyIdxItemList() {
		return propertyIdxItemList;
	}

	public void setPropertyIdxItemList(List propertyIdxItemList) {
		this.propertyIdxItemList = propertyIdxItemList;
	}

	public String[] getDeletedItemList() {
		return deletedItemList;
	}

	public void setDeletedItemList(String[] deletedItemList) {
		this.deletedItemList = deletedItemList;
	}

	public String getValuationDescription() {
		return valuationDescription;
	}

	public void setValuationDescription(String valuationDescription) {
		this.valuationDescription = valuationDescription;
	}

	
}



