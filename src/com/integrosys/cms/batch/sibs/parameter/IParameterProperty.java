package com.integrosys.cms.batch.sibs.parameter;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 6, 2008
 * Time: 12:42:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IParameterProperty {

    public static final String TYPE_COMMON_CODE = "commoncode";
    public static final String TYPE_TABLE = "table";


    public String getRemoteEntityName();

    public void setRemoteEntityName(String remoteEntityName);

    public String getLocalName();

    public void setLocalName(String localName);

    public String getType();

    public void setType(String localType);

    public Map getSpecialHandlingMap();

    public void setSpecialHandlingMap(Map specialHandling);

    boolean getIsDependencyUpdate();

    void setIsDependencyUpdate(boolean dependencyUpdate);

    Map getSetupDetailMap();

    void setSetupDetailMap(Map setupDetails);
}
