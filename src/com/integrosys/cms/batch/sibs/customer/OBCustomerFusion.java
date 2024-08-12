/**
 * 
 */
package com.integrosys.cms.batch.sibs.customer;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author User
 * date : 26 sep 08
 */
public class OBCustomerFusion implements ICustomer {

	//private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private String customerID = null;
	private String customerNewID = null;
	private String recordType = null;
	private String endLineInd;

	/**
	 * Default Constructor
	 */
	public OBCustomerFusion() {
	}

	/**
	 * Construct OB from interface
	 *
	 * @param value is of type ICustomer3
	 */
	public OBCustomerFusion(ICustomer value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/****************************************
	 * Setter Methods
	 *
	 ***************************************/

	// "H"-eader, "D"-etail, "T"-railer
	public void setRecordType(String recType) {
		this.recordType = recType;
	}

	public void setCustomerID(String custID) {
		this.customerID = custID;
	}

	public void setCustomerNewID(String custNewID) {
		this.customerNewID = custNewID;
	}


	/****************************************
	 * Getter Methods
	 *
	 ***************************************/

	// "H"-eader, "D"-etail, "T"-railer
	public String getRecordType() {
		return recordType;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getCustomerNewID() {
		return customerNewID;
	}

	public void setEndLineIndicator(String end) {
		this.endLineInd = end;
	}
	public String getEndLineIndicator() {
		return endLineInd;
	}

}
