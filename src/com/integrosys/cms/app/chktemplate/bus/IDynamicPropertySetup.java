package com.integrosys.cms.app.chktemplate.bus;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 7, 2008
 * Time: 6:04:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDynamicPropertySetup extends Serializable{

    public long getPropertySetupID();

    public void setPropertySetupID(long propertySetupID);

    public String getSecSubtype();

    public void setSecSubtype(String secSubtype);

    public String getProperty();

    public void setProperty(String property);

    public String getLabel();

    public void setLabel(String label);

    public String getCategoryCode();

    public void setCategoryCode(String categoryCode);

    
    /************************ For Future Expansion *************************/

    public String[] getEntryCodes();

    public void setEntryCodes(String[] entryCodes);
    
    public String[] getEntryDescription();

    public void setEntryDescription(String[] entryDescription);

    public String getInputType();

    public void setInputType(String inputType);
}
