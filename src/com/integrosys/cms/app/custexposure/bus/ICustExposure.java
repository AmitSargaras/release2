package com.integrosys.cms.app.custexposure.bus;

import java.io.Serializable;
import java.util.HashMap;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * Customer Exposure Interface
 * @author skchai
 */

public interface ICustExposure extends Serializable, IValueObject {

	// for child record in exposure
	public IContingentLiabilities[] getContingentLiabilities();

	public void setContingentLiabilities(
			IContingentLiabilities[] contingentLiabilities);
	
	public void addContingentLiabilities(
			IContingentLiabilities[] contingentLiabilities);

	public ICustExposureGroupRelationship[] getCustExposureGroupRelationship();

	public void setCustExposureGroupRelationship(ICustExposureGroupRelationship[] groupRelationship);
	
	public void addCustExposureGroupRelationship(ICustExposureGroupRelationship[] groupRelationship);

	public ICustExposureEntityRelationship[] getCustExposureEntityRelationship();
	
	public void setCustExposureEntityRelationship(
			ICustExposureEntityRelationship[] customerRelationship);
	
	public void addCustExposureEntityRelationship(ICustExposureEntityRelationship[] custRelationship);
	
	public ICMSCustomer getCMSCustomer();

	public void setCMSCustomer(ICMSCustomer cMSCustomer);

	/**
	 * The exposure by bank entity map 
	 * comprises of all limits categoried by bank entity. If there are 3 bank entity, 
	 * there will be 3 pair of Bank Entity|IExposureLimitByBankEntity in the 
	 * return mapped object
	 * 
	 * @return HashMap BankEntity|IExposureLimitByBankEntity[]
	 */
	public HashMap getLimitExposureByBankEntityMap();
	
	/**
	 * Setting Exposure by bank Entity
	 */
	public void setLimitExposureByBankEntityMap(HashMap limitExposureByBankEntityMap);
	
	/**
	 * Getting all limitProfiles that belongs to the customer
	 * 
	 * @return exposure limit profile
	 */
	public ILimitExposureProfile[] getLimitExposureProfile();

	/**
	 * Setting limitProfiles
	 * @param limitExposureProfile
	 */
	public void setLimitExposureProfile(
			ILimitExposureProfile[] limitExposureProfile);
	
	/**
	 * Add new Limit Exposure profile
	 * @param limitProfile
	 */
	public void addLimitExposureProfile(ILimitExposureProfile limitProfile);

	/**
	 * Getting the cci of this customer. If customer does not have cci,
	 * then the return object will be null.
	 *
	 * @return
	 */
	public ICCICounterpartyDetails getCCICounterpartyDetails();

	/**
	 * Setting  the cci for this customer
	 * @param cCICounterparty
	 */
	public void setCCICounterpartyDetails(ICCICounterpartyDetails cCICounterpartyDetails);
	
	/**
	 * @return the isReady
	 */
	public boolean isReady();

	/**
	 * @param isReady the isReady to set
	 */
	public void setReady(boolean isReady);
	
	/**
	 * @return the groupMemberRelation
	 */
	public String getGroupMemberRelation();

	/**
	 * @param groupMemberRelation the groupMemberRelation to set
	 */
	public void setGroupMemberRelation(String groupMemberRelation);

	/**
	 * @param groupMemberRelation the groupMemberRelation to add
	 */
	public void addGroupMemberRelation(String groupMemberRelation);
}
