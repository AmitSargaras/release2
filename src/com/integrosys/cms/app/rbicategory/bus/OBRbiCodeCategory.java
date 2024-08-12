/**
 * 
 */
package com.integrosys.cms.app.rbicategory.bus;

/**
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 1.0 $
 * @since $Date: Apr 3, 2011 3:00:42 PM $ Tag: $Name: $
 */
public class OBRbiCodeCategory {

	private long id;
	
	private String code;
	
	private String category;

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	

}
