package com.integrosys.cms.ui.imageTag;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;
/**
 *@author abhijit.rudrakshawar
 *$Form Bean for Image Tag
 */


/**
 * @author sachin.patil
 *
 */
public class ImageTagForm extends TrxContextForm implements Serializable {

	private String userID;

	private String subProfileID;

	private String legalName;

	private String customerName;

	private String legalID;

	private String leIDType;
	
	private String count;

	private String idNO;

	private String gobutton;

	private String all;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getSubProfileID() {
		return subProfileID;
	}

	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getCustomerName() {
		return customerName;
	}
	

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLegalID() {
		return legalID;
	}

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public String getLeIDType() {
		return leIDType;
	}

	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	public String getIdNO() {
		return idNO;
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}

	public String getGobutton() {
		return gobutton;
	}

	public void setGobutton(String gobutton) {
		this.gobutton = gobutton;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String[][] getMapper() {

		String[][] input = {
				{ "aCustomerSearchCriteria",
						"com.integrosys.cms.ui.imageTag.ImageTagForm" },
				{ "customerSearchCriteria",
						"com.integrosys.cms.ui.imageTag.ImageTagListMapper" },

				{ "customerList",
						"com.integrosys.cms.ui.imageTag.ImageTagListMapper" },

				{ "theOBTrxContext",
						"com.integrosys.cms.ui.common.TrxContextMapper" },
						{"imageTagProxyManager", "com.integrosys.cms.app.customer.bus.IImageTagProxyManager"}

		};

		return input;
	}

}
