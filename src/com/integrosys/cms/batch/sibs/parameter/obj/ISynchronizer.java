package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 3, 2008
 * Time: 10:42:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ISynchronizer {

    public String[] getMatchingProperties();

    public String[] getIgnoreProperties();    

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty);

    public void updatePropertiesForDelete(IParameterProperty paramProperty);

}
