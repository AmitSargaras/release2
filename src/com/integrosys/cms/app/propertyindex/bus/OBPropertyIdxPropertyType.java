package com.integrosys.cms.app.propertyindex.bus;

/**
 * Title: CLIMS
 * Description: Data object for Property Type.
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 15, 2008
 */

public class OBPropertyIdxPropertyType implements IPropertyIdxPropertyType {
    private long propertyIdxPropertyTypeId;
    private long propertyIdxItemId;
    private String propertyTypeId;
    private String status;

    public long getPropertyIdxPropertyTypeId() {
        return propertyIdxPropertyTypeId;
    }

    public void setPropertyIdxPropertyTypeId(long propertyIdxPropertyTypeId) {
        this.propertyIdxPropertyTypeId = propertyIdxPropertyTypeId;
    }

    public String getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(String propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public OBPropertyIdxPropertyType() {
    }

    public OBPropertyIdxPropertyType(long propertyIdxPropertyTypeId, String propertyTypeId, String status) {
        setPropertyIdxPropertyTypeId(propertyIdxPropertyTypeId);
        setPropertyTypeId(propertyTypeId);
        setStatus(status);
    }

    public OBPropertyIdxPropertyType(OBPropertyIdxPropertyType otherData) {
        setPropertyIdxPropertyTypeId(otherData.getPropertyIdxPropertyTypeId());
        setPropertyTypeId(otherData.getPropertyTypeId());
        setStatus(otherData.getStatus());

    }

    public long getPrimaryKey() {
        return getPropertyIdxPropertyTypeId();
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        StringBuffer str = new StringBuffer("{");

        str.append("propertyIdxPropertyTypeId=" + getPropertyIdxPropertyTypeId() + " " + "propertyTypeId=" + getPropertyTypeId() + " " + "status=" + getStatus());
        str.append('}');

        return (str.toString());
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBPropertyIdxPropertyType))
            return false;
        else {
            if (((OBPropertyIdxPropertyType) obj).getPropertyTypeId().equals(this.getPropertyTypeId()))
                return true;
            else
                return false;
        }
    }

    public long getPropertyIdxItemId() {
        return propertyIdxItemId;
    }

    public void setPropertyIdxItemId(long propertyIdxItemId) {
        this.propertyIdxItemId = propertyIdxItemId;
    }
}
