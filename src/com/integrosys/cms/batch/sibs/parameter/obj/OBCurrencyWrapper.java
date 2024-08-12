package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.base.businfra.currency.OBCurrency;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 3, 2008
 * Time: 6:18:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBCurrencyWrapper extends OBCurrency implements ISynchronizer {

    private static final String[] MATCHING_PROPERTIES = new String[] { "currencyCode" };
    private static final String[] IGNORED_PROPERTIES = new String[] {"currencyCode" };

    /**** New fields ****/
    private String updateStatusIndicator;
    private Date lastUpdatedDate;


    /**** Default Constructor ****/
    public OBCurrencyWrapper(String string) {
        super(string);
    }
    

    /**** Getter & Setters ****/
    public String getUpdateStatusIndicator() {
        return updateStatusIndicator;
    }

    public void setUpdateStatusIndicator(String updateStatusIndicator) {
        this.updateStatusIndicator = updateStatusIndicator;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /****** Methods from ISynchronizer ******/
    public String[] getMatchingProperties() {
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        setUpdateStatusIndicator((updateStatusIndicator == null) ? "I" : "U");
        setLastUpdatedDate(new Date());
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        setUpdateStatusIndicator("D");
        setLastUpdatedDate(new Date());
    }

    
}
