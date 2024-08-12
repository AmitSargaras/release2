package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBContractFacilityTypeLocalHome extends EJBLocalHome {

	/**
	 * Create a contract facility type information
	 * @param contractFacilityTypeObj is the IContractFacilityType object
	 * @return EBContractFacilityType
	 * @throws javax.ejb.CreateException on error
	 */
	public EBContractFacilityTypeLocal create(IContractFacilityType contractFacilityTypeObj) throws CreateException;

	/**
	 * Find by Primary Key which is the contract facility type ID.
	 * @param pk of long type
	 * @return EBContractFacilityType
	 * @throws javax.ejb.FinderException on error
	 */
	public EBContractFacilityTypeLocal findByPrimaryKey(Long pk) throws FinderException;

}
