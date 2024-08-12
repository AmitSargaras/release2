package com.integrosys.cms.batch.sibs.parameter;

import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 30, 2008
 * Time: 11:08:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IParameterDao {

    // ************* Entity Names In Hibernate File - to read from parameter.properties **********

    // **************** Implementation Methods ******************
//    public Collection update(String entityName, Collection entryList);
    public List getParameterEntryByEntityName(String entityName);


}
