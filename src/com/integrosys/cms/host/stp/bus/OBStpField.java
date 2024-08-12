package com.integrosys.cms.host.stp.bus;

import org.w3c.dom.Element;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: KLYong
 * @version: ${VERSION}
 * @since: Aug 21, 2008 2:04:09 PM
 */
public class OBStpField implements Serializable{
    private String fieldID;
    private String sequence;
    private String className;
    private String classField;
    private String classFieldType;
    private String position;
    private String length;
    private String value;
    private String format;
    private String mandatory;
    private String decimalPoint;

    public OBStpField() {
    }

    public OBStpField(String id, String sequence, String className, String classField, String classFieldType, String position, String length, String value, String format, String mandatory, String decimalPoint) {
        this.fieldID = id;
        this.sequence = sequence;
        this.className = className;
        this.classField = classField;
        this.classFieldType = classFieldType;
        this.position = position;
        this.length = length;
        this.value = value;
        this.format = format;
        this.mandatory = mandatory;
        this.decimalPoint = decimalPoint;
    }

    public OBStpField(List list, int index) {
        setFieldID(((OBStpField) list.get(index)).fieldID);
        setSequence(((OBStpField) list.get(index)).sequence);
        setClassName(((OBStpField) list.get(index)).className);
        setClassField(((OBStpField) list.get(index)).classField);
        setClassFieldType(((OBStpField) list.get(index)).classFieldType);
        setPosition(((OBStpField) list.get(index)).position);
        setLength(((OBStpField) list.get(index)).length);
        setValue(((OBStpField) list.get(index)).value);
        setFormat(((OBStpField) list.get(index)).format);
        setMandatory(((OBStpField) list.get(index)).mandatory);
        setDecimalPoint(((OBStpField) list.get(index)).decimalPoint);
    }

    public String getFieldID() {
        return fieldID;
    }

    public void setFieldID(String fieldID) {
        this.fieldID = fieldID;
    }

    public String getSequence() {
        if (sequence == null || sequence.equals(""))
            this.setSequence("1");

        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassField() {
        return classField;
    }

    public void setClassField(String classField) {
        this.classField = classField;
    }

    public String getClassFieldType() {
        return classFieldType;
    }

    public void setClassFieldType(String classFieldType) {
        this.classFieldType = classFieldType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getDecimalPoint() {
        return decimalPoint;
    }

    public void setDecimalPoint(String decimalPoint) {
        this.decimalPoint = decimalPoint;
    }
}
