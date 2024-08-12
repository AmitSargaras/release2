/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.io.Serializable;

import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;

/**
 * Group exposure interface that aggregate all group exposure info
 * @author skchai
 *
 */
public interface IGroupExpCustGrp extends Serializable {

	public void setCustGroup(ICustGrpIdentifier custGroup);
	public ICustGrpIdentifier getCustGroup();
	
	public void setGroupExpCustGrpEntityLimit(IGroupExpCustGrpEntityLimit[] limits);
	public IGroupExpCustGrpEntityLimit[] getGroupExpCustGrpEntityLimit();
	
	public void setGroupExpCustGrpSubLimit(IGroupExpCustGrpSubLimit[] limits);
	public IGroupExpCustGrpSubLimit[] getGroupExpCustGrpSubLimit();
	
	public void setGroupExpCustGrpOtrLimit(IGroupExpCustGrpOtrLimit[] limits);
	public IGroupExpCustGrpOtrLimit[] getGroupExpCustGrpOtrLimit();
}
