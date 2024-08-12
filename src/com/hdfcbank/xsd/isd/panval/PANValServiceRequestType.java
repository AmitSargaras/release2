
package com.hdfcbank.xsd.isd.panval;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PANValServiceRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PANValServiceRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Service_User" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Service_Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Consumer_Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NSDL_User_Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAN_Details" type="{panval.isd.xsd.hdfcbank.com}PanDetailsRequestType" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="Req_Date_Time" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Req_No" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Cost_Center_No" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Filler1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Filler2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Filler3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Filler4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Filler5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PANValServiceRequestType", propOrder = {
    "serviceUser",
    "servicePassword",
    "consumerName",
    "nsdlUserName",
    "panDetails",
    "reqDateTime",
    "reqNo",
    "costCenterNo",
    "filler1",
    "filler2",
    "filler3",
    "filler4",
    "filler5"
})
public class PANValServiceRequestType {

    @XmlElement(name = "Service_User")
    protected String serviceUser;
    @XmlElement(name = "Service_Password")
    protected String servicePassword;
    @XmlElement(name = "Consumer_Name", required = true)
    protected String consumerName;
    @XmlElement(name = "NSDL_User_Name", required = true)
    protected String nsdlUserName;
    @XmlElement(name = "PAN_Details")
    protected List<PanDetailsRequestType> panDetails;
    @XmlElement(name = "Req_Date_Time", required = true)
    protected String reqDateTime;
    @XmlElement(name = "Req_No", required = true)
    protected String reqNo;
    @XmlElement(name = "Cost_Center_No", required = true)
    protected String costCenterNo;
    @XmlElement(name = "Filler1", required = true)
    protected String filler1;
    @XmlElement(name = "Filler2")
    protected String filler2;
    @XmlElement(name = "Filler3")
    protected String filler3;
    @XmlElement(name = "Filler4")
    protected String filler4;
    @XmlElement(name = "Filler5")
    protected String filler5;

    /**
     * Gets the value of the serviceUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceUser() {
        return serviceUser;
    }

    /**
     * Sets the value of the serviceUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceUser(String value) {
        this.serviceUser = value;
    }

    /**
     * Gets the value of the servicePassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicePassword() {
        return servicePassword;
    }

    /**
     * Sets the value of the servicePassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicePassword(String value) {
        this.servicePassword = value;
    }

    /**
     * Gets the value of the consumerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumerName() {
        return consumerName;
    }

    /**
     * Sets the value of the consumerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumerName(String value) {
        this.consumerName = value;
    }

    /**
     * Gets the value of the nsdlUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNSDLUserName() {
        return nsdlUserName;
    }

    /**
     * Sets the value of the nsdlUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNSDLUserName(String value) {
        this.nsdlUserName = value;
    }

    /**
     * Gets the value of the panDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the panDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPANDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PanDetailsRequestType }
     * 
     * 
     */
    public List<PanDetailsRequestType> getPANDetails() {
        if (panDetails == null) {
            panDetails = new ArrayList<PanDetailsRequestType>();
        }
        return this.panDetails;
    }

    /**
     * Gets the value of the reqDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqDateTime() {
        return reqDateTime;
    }

    /**
     * Sets the value of the reqDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqDateTime(String value) {
        this.reqDateTime = value;
    }

    /**
     * Gets the value of the reqNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqNo() {
        return reqNo;
    }

    /**
     * Sets the value of the reqNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqNo(String value) {
        this.reqNo = value;
    }

    /**
     * Gets the value of the costCenterNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCostCenterNo() {
        return costCenterNo;
    }

    /**
     * Sets the value of the costCenterNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCostCenterNo(String value) {
        this.costCenterNo = value;
    }

    /**
     * Gets the value of the filler1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiller1() {
        return filler1;
    }

    /**
     * Sets the value of the filler1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiller1(String value) {
        this.filler1 = value;
    }

    /**
     * Gets the value of the filler2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiller2() {
        return filler2;
    }

    /**
     * Sets the value of the filler2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiller2(String value) {
        this.filler2 = value;
    }

    /**
     * Gets the value of the filler3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiller3() {
        return filler3;
    }

    /**
     * Sets the value of the filler3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiller3(String value) {
        this.filler3 = value;
    }

    /**
     * Gets the value of the filler4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiller4() {
        return filler4;
    }

    /**
     * Sets the value of the filler4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiller4(String value) {
        this.filler4 = value;
    }

    /**
     * Gets the value of the filler5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiller5() {
        return filler5;
    }

    /**
     * Sets the value of the filler5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiller5(String value) {
        this.filler5 = value;
    }

}
