package com.integrosys.cms.batch.sibs.parameter;

import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 30, 2008
 * Time: 11:06:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IParameterBusManager extends Serializable {

    public static final String PROPERTY_FILE_NAME = "parameter.xml";

    public static final String SPECIAL_HANDLE_DISTINCT = "distinct";
    public static final String SPECIAL_HANDLE_NO_DELETE = "nodelete";

    public static final String JOB_PARAM_KEY = "remoteEntityName";  //this maps to DomParser.ENTRY_TAG_ATTRIBUTE_REMOTE_NAME


    // **************** Controller Main Methods ******************
    public void updateParameter();

    public Collection updateParameter(IParameterProperty paramProperty);
}
