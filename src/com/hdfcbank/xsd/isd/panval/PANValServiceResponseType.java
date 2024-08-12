
package com.hdfcbank.xsd.isd.panval;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PANValServiceResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PANValServiceResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Return_Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Return_Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAN_Details" type="{panval.isd.xsd.hdfcbank.com}PAN_DETAILS_TYPE" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="ResponseTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Req_no" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PANValServiceResponseType", propOrder = {
    "returnCode",
    "returnDescription",
    "panDetails",
    "responseTime",
    "reqNo"
})
public class PANValServiceResponseType {

    @XmlElement(name = "Return_Code", required = true)
    protected String returnCode;
    @XmlElement(name = "Return_Description", required = true)
    protected String returnDescription;
    @XmlElement(name = "PAN_Details")
    protected List<PANDETAILSTYPE> panDetails;
    @XmlElement(name = "ResponseTime", required = true)
    protected String responseTime;
    @XmlElement(name = "Req_no", required = true)
    protected String reqNo;

    /**
     * Gets the value of the returnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnCode(String value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the returnDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDescription() {
        return returnDescription;
    }

    /**
     * Sets the value of the returnDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDescription(String value) {
        this.returnDescription = value;
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
     * {@link PANDETAILSTYPE }
     * 
     * 
     */
    public List<PANDETAILSTYPE> getPANDetails() {
        if (panDetails == null) {
            panDetails = new ArrayList<PANDETAILSTYPE>();
        }
        return this.panDetails;
    }

    /**
     * Gets the value of the responseTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the value of the responseTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseTime(String value) {
        this.responseTime = value;
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

}
