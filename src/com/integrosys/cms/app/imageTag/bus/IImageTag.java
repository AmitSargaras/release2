package com.integrosys.cms.app.imageTag.bus;

import java.io.Serializable;


/**
 * Interface for Image Tag
 * @author abhijit.rudrakshawar
 
 */
public interface IImageTag extends Serializable {

	/**
	 * gets the country allowed for viewing
	 * @return String - allowed country
	 */
	
	public String getUserID();
	public String getSubProfileID();
	public String getLegalName();
	public String getCustomerName();
	public String getLegalID();
	public String getLeIDType();
	public String getIdNO();
	
	public Long getCustID();

	/**
	 * retrieves the customer name
	 * @return String - name
	 */
	public String getCustName();

	/**
	 * retrieves the LE ID of the customer
	 * @return long - customer LE ID
	 */
	public String getCustCIF();

	/**
	 * retrieves the customer segment of this diary item
	 * @return String - customer segment
	 */
	
	public void setUserID(String userID);
	public void setSubProfileID(String subProfileID);
	public void setLegalName(String legalName);
	public void setCustomerName(String customerName);
	public void setLegalID(String legalID);
	public void setLeIDType(String leIDType);
	public void setIdNO(String idNO);
	
	public void setCustID(java.lang.Long custID);

	public void setCustName(String custName);

	public void setCustCIF(String custCIF);

}
