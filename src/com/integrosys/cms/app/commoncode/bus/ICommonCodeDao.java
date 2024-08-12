package com.integrosys.cms.app.commoncode.bus;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 30, 2008
 * Time: 10:50:19 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ICommonCodeDao {

    // **************** Entity Names In Hibernate File ******************
    public static final String ACTUAL_COMMON_CODE_CATEGORY = "actualCommonCodeCategory";
    public static final String STAGE_COMMON_CODE_CATEGORY = "stageCommonCodeCategory";
    public static final String ACTUAL_COMMON_CODE_ENTRY = "actualEntryCode";
    public static final String STAGE_COMMON_CODE_ENTRY = "stageEntryCode";
    public static final String ENTRY_SOURCE_SIBS = "ARBS";
    public static final String ENTRY_SOURCE_TSPR = "TSPR";


    // **************** Implementation Methods ******************
    public Collection update(String entityName, Collection entryList);

    public Collection updateStaging(String entityName, Collection entryList);

    public List getEntityEntryByEntityName(String entityName);

    public Collection updateTable(String entityName, Collection entryList);
    
    /**
     * Retrieve the common code entry values for the given category code.
     * Only common code entry that are still active will be retrieved;
     * i.e. deleted common code entry will not be retrieved.
     * @param entityName - The entity name to retrieve from
     * @param categoryCode - Category code to retrieve the common code entries for
     * @return Collection of ICommonCodeEntry objects
     */
    public List getCommonCodeEntryByCategorySource(String entityName, String categoryCode, String source, 
                                                   String remoteEntityName);

    public long getCategoryCodeId(String entityName, String categoryCode);
    
    public Boolean isDuplicateRecord(String entityName, String categoryCode,String cpsId);
}
