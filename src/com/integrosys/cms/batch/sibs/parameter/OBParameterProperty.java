package com.integrosys.cms.batch.sibs.parameter;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 6, 2008
 * Time: 12:34:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBParameterProperty implements IParameterProperty {

    private String remoteEntityName;
    private String localName;
    private String type;
    private boolean isDependencyUpdate;
    private Map specialHandlingMap;
    private Map setupDetailMap;


    public OBParameterProperty() {
    }


    public String getRemoteEntityName() {
        return remoteEntityName;
    }

    public void setRemoteEntityName(String remoteEntityName) {
        this.remoteEntityName = remoteEntityName;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsDependencyUpdate() {
        return isDependencyUpdate;
    }

    public void setIsDependencyUpdate(boolean dependencyUpdate) {
        isDependencyUpdate = dependencyUpdate;
    }

    public Map getSpecialHandlingMap() {
        return specialHandlingMap;
    }

    public void setSpecialHandlingMap(Map specialHandling) {
        this.specialHandlingMap = specialHandling;
    }


    public Map getSetupDetailMap() {
        return setupDetailMap;
    }

    public void setSetupDetailMap(Map setupDetails) {
        this.setupDetailMap = setupDetails;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OBParameterProperty that = (OBParameterProperty) o;

        if (isDependencyUpdate != that.isDependencyUpdate) return false;
        if (localName != null ? !localName.equals(that.localName) : that.localName != null) return false;
        if (remoteEntityName != null ? !remoteEntityName.equals(that.remoteEntityName) : that.remoteEntityName != null)
            return false;
        if (setupDetailMap != null ? !setupDetailMap.equals(that.setupDetailMap) : that.setupDetailMap != null)
            return false;
        if (specialHandlingMap != null ? !specialHandlingMap.equals(that.specialHandlingMap) : that.specialHandlingMap != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (remoteEntityName != null ? remoteEntityName.hashCode() : 0);
        result = 31 * result + (localName != null ? localName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isDependencyUpdate ? 1 : 0);
        result = 31 * result + (specialHandlingMap != null ? specialHandlingMap.hashCode() : 0);
        result = 31 * result + (setupDetailMap != null ? setupDetailMap.hashCode() : 0);
        return result;
    }


    public String toString() {
        return "remoteEntityName = " + remoteEntityName +
               "\n localName = " + localName +
                "\n type = " + type +
                "\n isDependencyUpdate = " + isDependencyUpdate +
                "\n specialHandlingMap = " + specialHandlingMap +
                "\n setupDetailMap = " + setupDetailMap; 
    }
}
