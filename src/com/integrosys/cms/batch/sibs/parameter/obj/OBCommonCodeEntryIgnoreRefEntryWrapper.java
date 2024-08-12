package com.integrosys.cms.batch.sibs.parameter.obj;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 8, 2008
 * Time: 10:54:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBCommonCodeEntryIgnoreRefEntryWrapper extends OBCommonCodeEntryWrapper{

    private static final String[] IGNORED_PROPERTIES
            = new String[] {"entryId", "entryCode", "categoryCode", "activeStatus", "entrySource", "groupId", "entryId",
                            "categoryCodeId", "status", "refEntryCode" };


    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }
    
}
