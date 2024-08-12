package com.integrosys.cms.app.custexposure.bus;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * Business object for customer exposure
 * User: skchai
 * 
 */


public class OBCustExposure implements ICustExposure {

	private static final long serialVersionUID = 1L;
	
	private long versionTime = 0;
    private IContingentLiabilities[] contingentLiabilities;
    private ICustExposureGroupRelationship[] groupRelationship ;

    private ICustExposureEntityRelationship[] custRelationship;

    // for  View Backend System  for Customer
    private HashMap limitExposureByBankEntityMap;
	private ILimitExposureProfile[]   limitExposureProfile ;
    
    private ICCICounterpartyDetails cCICounterpartyDetails;
    
    private boolean isReady;
    
    private String groupMemberRelation;
    
    public OBCustExposure() {
    	limitExposureByBankEntityMap = new HashMap();
    	isReady = false;
    }
    
    /**
	 * @return the limitExposureByBankEntityMap
	 */
	public HashMap getLimitExposureByBankEntityMap() {
		return limitExposureByBankEntityMap;
	}

	/**
	 * @param limitExposureByBankEntityMap the limitExposureByBankEntityMap to set
	 */
	public void setLimitExposureByBankEntityMap(HashMap limitExposureByBankEntityMap) {
		this.limitExposureByBankEntityMap = limitExposureByBankEntityMap;
	}
	
    /**
	 * @return the cCICounterparty
	 */
	public ICCICounterpartyDetails getCCICounterpartyDetails() {
		return cCICounterpartyDetails;
	}

	/**
	 * @param counterparty the cCICounterparty to set
	 */
	public void setCCICounterpartyDetails(ICCICounterpartyDetails counterpartyDetails) {
		cCICounterpartyDetails = counterpartyDetails;
	}

	/**
	 * Get limit exposure profile array
	 */
	public ILimitExposureProfile[] getLimitExposureProfile() {
        return limitExposureProfile;
    }

	/**
	 * Set limit exposure profile array
	 */
    public void setLimitExposureProfile(ILimitExposureProfile[] limitExposureProfile) {
        this.limitExposureProfile = limitExposureProfile;
    }

    /**
     * Add a new limit exposure profile
     * @param new limit profile
     */
    public void addLimitExposureProfile(ILimitExposureProfile limitProfile) {
    	if (this.limitExposureProfile == null || this.limitExposureProfile.length == 0) {
    		limitExposureProfile = new OBLimitExposureProfile[1];
    		limitExposureProfile[0] = limitProfile;
    	} else {
    		ILimitExposureProfile[] newLimitExposureProfile = new OBLimitExposureProfile[limitExposureProfile.length + 1];
    		
            System.arraycopy(limitExposureProfile, 0, newLimitExposureProfile, 0,
            		limitExposureProfile.length);
            
            newLimitExposureProfile[limitExposureProfile.length] = limitProfile;
            this.limitExposureProfile = newLimitExposureProfile;
    	}
    	
    }
    

    public ICMSCustomer getcMSCustomer() {
        return cMSCustomer;
    }

    public void setcMSCustomer(ICMSCustomer cMSCustomer) {
        this.cMSCustomer = cMSCustomer;
    }

    public ICustExposureGroupRelationship[] getCustExposureGroupRelationship() {
        return groupRelationship;
    }

    public void setCustExposureGroupRelationship(ICustExposureGroupRelationship[] groupRelationship) {
        this.groupRelationship = groupRelationship;
    }


	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.ICustExposure#addCustExposureEntityRelationship(com.integrosys.cms.app.custexposure.bus.ICustExposureGroupRelationship[])
	 */
	public void addCustExposureEntityRelationship(
			ICustExposureEntityRelationship[] entityRelationship) {
		
		if (entityRelationship == null)
			return;
			
    	if (this.custRelationship == null || this.custRelationship.length == 0) {
    		custRelationship = entityRelationship;
    	} else {
    		ICustExposureEntityRelationship[] newCustExposureEntityRelationship = 
    			new OBCustExposureEntityRelationship[custRelationship.length + entityRelationship.length];
    		
            System.arraycopy(custRelationship, 0, newCustExposureEntityRelationship, 0,
            		custRelationship.length);
            
            for (int i = 0; i < entityRelationship.length; i++) {
            	newCustExposureEntityRelationship[custRelationship.length + i] = entityRelationship[i];
            }
            
            this.custRelationship = newCustExposureEntityRelationship;
    	}
		
	}

	public ICustExposureEntityRelationship[] getCustExposureEntityRelationship() {
        return custRelationship;
    }

    public void setCustExposureEntityRelationship(ICustExposureEntityRelationship[] customerRelationship) {
        this.custRelationship = customerRelationship;
    }
    
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.ICustExposure#addGroupRelationship(com.integrosys.cms.app.custexposure.bus.ICustExposureGroupRelationship[])
	 */
	public void addCustExposureGroupRelationship(
			ICustExposureGroupRelationship[] groupRels) {

		if (groupRels == null)
			return;
			
    	if (this.groupRelationship == null || this.groupRelationship.length == 0) {
    		this.groupRelationship = groupRels;
    	} else {
    		ICustExposureGroupRelationship[] newCustExposureGroupRelationship = 
    			new OBCustExposureGroupRelationship[groupRelationship.length + groupRels.length];
            System.arraycopy(groupRelationship, 0, newCustExposureGroupRelationship, 0,
            		groupRelationship.length);
            
            for (int i = 0; i < groupRels.length; i++) {
            	newCustExposureGroupRelationship[groupRelationship.length + i] = groupRels[i];
            }

            this.groupRelationship = newCustExposureGroupRelationship;
    	}
		
	}

    public IContingentLiabilities[] getContingentLiabilities() {
        return contingentLiabilities;
    }

    public void setContingentLiabilities(IContingentLiabilities[] contingentLiabilitiesa) {
        contingentLiabilities = contingentLiabilitiesa;
    }
    
    /* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.ICustExposure#addContingentLiabilities(com.integrosys.cms.app.custexposure.bus.IContingentLiabilities[])
	 */
	public void addContingentLiabilities(
			IContingentLiabilities[] cLiabilities) {
		
		if (cLiabilities == null)
			return;
			
    	if (this.contingentLiabilities == null || this.contingentLiabilities.length == 0) {
    		contingentLiabilities = cLiabilities;
    	} else {
    		IContingentLiabilities[] newContingentLiabilities = 
    			new OBContingentLiabilities[contingentLiabilities.length + cLiabilities.length];
    		
            System.arraycopy(contingentLiabilities, 0, newContingentLiabilities, 0,
            		contingentLiabilities.length);
            
            for (int i = 0; i < cLiabilities.length; i++) {
            	newContingentLiabilities[contingentLiabilities.length + i] = cLiabilities[i];
            }
            
            this.contingentLiabilities = newContingentLiabilities;
    	}
		
	}

    private ICMSCustomer cMSCustomer ;


    public ICMSCustomer getCMSCustomer() {
        return cMSCustomer;
    }

    public void setCMSCustomer(ICMSCustomer cMSCustomer) {
        this.cMSCustomer = cMSCustomer;
    }


    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }
    
    public static void main(String[] args)  throws Exception {
    	ICustExposure temp = new OBCustExposure();
    	
    	ILimitExposureProfile data = new OBLimitExposureProfile();
    	data.setEntityID(123l);
    	temp.addLimitExposureProfile(data);
    	
    	ILimitExposureProfile data1 = new OBLimitExposureProfile();
    	data1.setEntityID(1234l);    	
    	temp.addLimitExposureProfile(data1);
    	
    	ILimitExposureProfile data2 = new OBLimitExposureProfile();
    	data2.setEntityID(12345l);       	
    	temp.addLimitExposureProfile(data2);
    	
    	ILimitExposureProfile data3 = new OBLimitExposureProfile();
    	data3.setEntityID(123456l);       	
    	temp.addLimitExposureProfile(data3);
    	
    	temp.setGroupMemberRelation("OKOKOK");
    	
//    	System.out.println("Size of limitExposureProfile : " + temp.getLimitExposureProfile().length);
    	ILimitExposureProfile[] limitProfileArray = temp.getLimitExposureProfile();
    	
//    	for (int i = 0; i < limitProfileArray.length; i++) {
//    		System.out.println(limitProfileArray[i].getEntityID());
//    	}
    	
    	ICustExposureEntityRelationship er1 = new OBCustExposureEntityRelationship();
    	er1.setRelationName("name1");
    	ICustExposureEntityRelationship er2 = new OBCustExposureEntityRelationship();
    	er2.setRelationName("name2");
    	ICustExposureEntityRelationship er3 = new OBCustExposureEntityRelationship();
    	er3.setRelationName("name3");
    	ICustExposureEntityRelationship[] ers = new OBCustExposureEntityRelationship[3];
    	ers[0] = er1;
    	ers[1] = er2;
    	ers[2] = er3;
    	temp.addCustExposureEntityRelationship(null);
    	temp.addCustExposureEntityRelationship(ers);
    	
//    	System.out.println("Size of CustExposureEntityRelationship : " + temp.getCustExposureEntityRelationship().length);
    	ICustExposureEntityRelationship[] custRelationshipArray = temp.getCustExposureEntityRelationship();
//    	for (int i = 0; i < custRelationshipArray.length; i++) {
//    		System.out.println(custRelationshipArray[i].getRelationName());
//    	}
    	
    	
    	ICustExposureGroupRelationship eg1 = new OBCustExposureGroupRelationship();
    	eg1.setGroupName("Gname1");
    	ICustExposureGroupRelationship eg2 = new OBCustExposureGroupRelationship();
    	eg2.setGroupName("Gname2");
    	ICustExposureGroupRelationship eg3 = new OBCustExposureGroupRelationship();
    	eg3.setGroupName("Gname3");
    	ICustExposureGroupRelationship[] egs = new OBCustExposureGroupRelationship[3];
    	egs[0] = eg1;
    	egs[1] = eg2;
    	egs[2] = eg3;
    	temp.addCustExposureGroupRelationship(null);
    	temp.addCustExposureGroupRelationship(egs);
    	
//    	System.out.println("Size of CustExposureGroupRelationship : " + temp.getCustExposureEntityRelationship().length);
    	ICustExposureGroupRelationship[] groupRelationshipArray = temp.getCustExposureGroupRelationship();
//    	for (int i = 0; i < groupRelationshipArray.length; i++) {
//    		System.out.println(groupRelationshipArray[i].getGroupName());
//    	}
//    	
//    	System.out.println(temp.getGroupMemberRelation());
    }

	/**
	 * @return the isReady
	 */
	public boolean isReady() {
		return isReady;
	}

	/**
	 * @param isReady the isReady to set
	 */
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	/**
	 * @return the groupMemberRelation
	 */
	public String getGroupMemberRelation() {
		return groupMemberRelation;
	}

	/**
	 * @param groupMemberRelation the groupMemberRelation to set
	 */
	public void setGroupMemberRelation(String groupMemberRelation) {
		this.groupMemberRelation = groupMemberRelation;
	}
	
	/**
	 * @param groupMemberRelation the groupMemberRelation to add
	 */
	public void addGroupMemberRelation(String groupMemberRelationTemp) {
		
		if (groupMemberRelationTemp == null || groupMemberRelationTemp.length() == 0)
			return;
			
		if (this.groupMemberRelation == null || this.groupMemberRelation.length() == 0)
			this.groupMemberRelation = groupMemberRelationTemp;
		else
			this.groupMemberRelation += "; " + groupMemberRelationTemp;
	}
}
