
package com.hdfcbank.xsd.isd.panval;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PAN_DETAILS_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PAN_DETAILS_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PAN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAN_Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FATHER_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SEEDING_STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Value1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Value2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PAN_DETAILS_TYPE", propOrder = {
    "pan",
    "panStatus",
    "name",
    "fathername",
    "dob",
    "seedingstatus",
    "value1",
    "value2"
})
public class PANDETAILSTYPE {

    @XmlElement(name = "PAN", required = true)
    protected String pan;
    @XmlElement(name = "PAN_Status", required = true)
    protected String panStatus;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "FATHER_NAME")
    protected String fathername;
    @XmlElement(name = "DOB")
    protected String dob;
    @XmlElement(name = "SEEDING_STATUS")
    protected String seedingstatus;
    @XmlElement(name = "Value1")
    protected String value1;
    @XmlElement(name = "Value2")
    protected String value2;

    /**
     * Gets the value of the pan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAN() {
        return pan;
    }

    /**
     * Sets the value of the pan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAN(String value) {
        this.pan = value;
    }

    /**
     * Gets the value of the panStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPANStatus() {
        return panStatus;
    }

    /**
     * Sets the value of the panStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPANStatus(String value) {
        this.panStatus = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the fathername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFATHERNAME() {
        return fathername;
    }

    /**
     * Sets the value of the fathername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFATHERNAME(String value) {
        this.fathername = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOB() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOB(String value) {
        this.dob = value;
    }

    /**
     * Gets the value of the seedingstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEEDINGSTATUS() {
        return seedingstatus;
    }

    /**
     * Sets the value of the seedingstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEEDINGSTATUS(String value) {
        this.seedingstatus = value;
    }

    /**
     * Gets the value of the value1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue1() {
        return value1;
    }

    /**
     * Sets the value of the value1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue1(String value) {
        this.value1 = value;
    }

    /**
     * Gets the value of the value2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue2() {
        return value2;
    }

    /**
     * Sets the value of the value2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue2(String value) {
        this.value2 = value;
    }

}
