/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;

/**
 * @author user
 *
 */
public class OBGroupExpCustGrp implements IGroupExpCustGrp {

	private static final long serialVersionUID = 1L;
	private ICustGrpIdentifier custGroup;
	private IGroupExpCustGrpEntityLimit[] groupExpCustGrpEntityLimit;
	private IGroupExpCustGrpSubLimit[] groupExpCustGrpSubLimit; 
	private IGroupExpCustGrpOtrLimit[] groupExpCustGrpOtrLimit;
	
	/**
	 * Default constructor
	 */
	public OBGroupExpCustGrp() {
		
	}
	
	/**
	 * Constructor that take in cust group as argument
	 * @param custGroupIdentifier
	 */
	public OBGroupExpCustGrp(ICustGrpIdentifier custGroupIdentifier) {
		this.custGroup = custGroupIdentifier;
	}
	
	/**
	 * @return the custGroup
	 */
	public ICustGrpIdentifier getCustGroup() {
		return custGroup;
	}
	
	/**
	 * @param custGroup the custGroup to set
	 */
	public void setCustGroup(ICustGrpIdentifier custGroup) {
		this.custGroup = custGroup;
	}
	
	/**
	 * @return the groupExpCustGrpEntityLimit
	 */
	public IGroupExpCustGrpEntityLimit[] getGroupExpCustGrpEntityLimit() {
		return groupExpCustGrpEntityLimit;
	}
	
	/**
	 * @param groupExpCustGrpEntityLimit the groupExpCustGrpEntityLimit to set
	 */
	public void setGroupExpCustGrpEntityLimit(
			IGroupExpCustGrpEntityLimit[] groupExpCustGrpEntityLimit) {
		this.groupExpCustGrpEntityLimit = groupExpCustGrpEntityLimit;
	}
	
	/**
	 * @return the groupExpCustGrpSubLimit
	 */
	public IGroupExpCustGrpSubLimit[] getGroupExpCustGrpSubLimit() {
		return groupExpCustGrpSubLimit;
	}
	
	/**
	 * @param groupExpCustGrpSubLimit the groupExpCustGrpSubLimit to set
	 */
	public void setGroupExpCustGrpSubLimit(
			IGroupExpCustGrpSubLimit[] groupExpCustGrpSubLimit) {
		this.groupExpCustGrpSubLimit = groupExpCustGrpSubLimit;
	}
	
	/**
	 * @return the groupExpCustGrpOtrLimit
	 */
	public IGroupExpCustGrpOtrLimit[] getGroupExpCustGrpOtrLimit() {
		return groupExpCustGrpOtrLimit;
	}
	
	/**
	 * @param groupExpCustGrpOtrLimit the groupExpCustGrpOtrLimit to set
	 */
	public void setGroupExpCustGrpOtrLimit(
			IGroupExpCustGrpOtrLimit[] groupExpCustGrpOtrLimit) {
		this.groupExpCustGrpOtrLimit = groupExpCustGrpOtrLimit;
	}
	
	/**
     * Return a String representation of this object.
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }
}
