package com.integrosys.cms.ui.image;

import java.io.Serializable;


/**
 * This interface defines the operations that are provided by a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/09/30 11:32:23 $ Tag: $Name: $
 */
public interface IImageUpload extends Serializable {

	/**
	 * gets the country allowed for viewing
	 * @return String - allowed country
	 */
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
	public void setCustID(java.lang.Long custID);

	public void setCustName(String custName);

	public void setCustCIF(String custCIF);

}
