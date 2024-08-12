package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.feed.bus.forex.OBForexFeedEntry;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 3, 2008
 * Time: 6:27:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBForexWrapper extends OBForexFeedEntry implements ISynchronizer, Serializable {

    private static final String[] MATCHING_PROPERTIES = new String[] { "buyCurrency", "sellCurrency" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "forexFeedEntryID", "buyCurrency", "sellCurrency", "effectiveDate", "forexFeedEntryRef" };

    /****** Methods from ISynchronizer ******/
    public String[] getMatchingProperties() {
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        setBuyUnit(1);      //default to 1 since remote obj unable to give
        setEffectiveDate(new Date());
        setForexFeedEntryRef(getForexFeedEntryID());
        setVersionTime(1); //default to 1 

        trimStringAttributes();
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) { //no action
        trimStringAttributes();
    }

    private void trimStringAttributes() {
        if(getBuyCurrency() != null){
            setBuyCurrency(getBuyCurrency().trim());
        }
        if(getSellCurrency() != null){
            setSellCurrency(getSellCurrency().trim());
        }
    }

}
