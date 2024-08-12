package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 7, 2008
 * Time: 5:38:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBDynamicPropertySetup implements IDynamicPropertySetup {

    private long propertySetupID = ICMSConstant.LONG_INVALID_VALUE; //primary key
    private String secSubtype = null;
    private String property = null;
    private String label = null;
    private String categoryCode = null;
    private String[] entryCodes = null;
    private String[] entryDescription = null;
    private String inputType = null;


    /**
     * Get the Property Setup ID
     * @return property setup id
     */
    public long getPropertySetupID() {
        return propertySetupID;
    }

    /**
     * Sets the property setup id
     * @param propertySetupID - property setup id
     */
    public void setPropertySetupID(long propertySetupID) {
        this.propertySetupID = propertySetupID;
    }

    /**
     * Gets the security subtype that this property is applicable to
     * @return security subtype
     */
    public String getSecSubtype() {
        return secSubtype;
    }

    /**
     * Sets the security subtype that this property is applicable to
     * @param secSubtype
     */
    public void setSecSubtype(String secSubtype) {
        this.secSubtype = secSubtype;
    }

    /**
     * Gets the name of the dynamic property
     * This should correspond to the name in PropertyUtils
     * @return name of dynamic property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Sets the name of the dynamic property
     * This should correspond to the name in PropertyUtils
     * @param property - name of the dynamic property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Gets the (key of the) label for display on screen for this dynamic property
     * This should be the key for the label in the properties file
     * @return key of label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the key of the label meant for display on screen for this dynamic property.
     * @param label - key of the label in properties file
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the category code that this dynamic property will be referring to
     * @return category code
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * Sets the category code that this dynamic property will be referring to
     * @param categoryCode - Category code
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /************************ For Future Expansion *************************/
    
    /**
     * Gets the entry codes for the category code
     * @return list of entry codes for the category code
     */
    public String[] getEntryCodes() {
        return entryCodes;
    }

    /**
     * Sets the list of entry codes for the category code
     * @param entryCodes - entry codes
     */
    public void setEntryCodes(String[] entryCodes) {
        this.entryCodes = entryCodes;
    }


    /**
     * Gets the entry description for the category code.
     * The ordering for the entry description will match that of the entry codes
     * i.e. (entryCodes[0], entryDescription[0]) is a pair  
     * @return list of entry description for the cateogry code
     */
    public String[] getEntryDescription() {
        return entryDescription;
    }

    /**
     * Sets the list of entry description for the category code
     * The ordering for the entry description will match that of the entry codes
     * i.e. (entryCodes[0], entryDescription[0]) is a pair
     * @param entryDescription - entry description.
     */
    public void setEntryDescription(String[] entryDescription) {
        this.entryDescription = entryDescription;
    }

    /**
     * Gets the input type for the dynamic property.
     * Input type refers to checkbox, radio button, textbox etc
     * Currently not in use - for future expansion.
     * @return input type for the dynamic property
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * Gets the input type for the dynamic property.
     * Input type refers to checkbox, radio button, textbox etc
     * Currently not in use - for future expansion.
     * @param inputType - input type for the dynamic property
     */
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    /**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
