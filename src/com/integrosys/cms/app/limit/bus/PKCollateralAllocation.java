/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/PKCollateralAllocation.java,v 1.2 2003/08/01 02:48:01 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

/**
 * Primary key for EBCollateralAllocationBean.
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/01 02:48:01 $ Tag: $Name: $
 * @deprecated This class is no longer in use as the primary key is now of type
 *             Long
 */
public class PKCollateralAllocation implements Serializable {
	/** primary key: collateral id */
	public Long collateralPK;

	/** primary key: limit id */
	public Long limitPK;

	/**
	 * Default constructor.
	 */
	public PKCollateralAllocation() {
	}

	/**
	 * Default constructor.
	 * 
	 * @param collateraID is of type long
	 * @param limitID is of type long
	 */
	public PKCollateralAllocation(long collateralID, long limitID) {
		collateralPK = new Long(collateralID);
		limitPK = new Long(limitID);
	}

	/**
	 * To hash this object.
	 * 
	 * @return hashcode of this object.
	 */
	public int hashCode() {
		String hash1 = null;
		String hash2 = null;
		if (null != collateralPK) {
			hash1 = collateralPK.toString();
		}
		if (null != limitPK) {
			hash2 = limitPK.toString();
		}

		String hash = hash1 + hash2;
		return (hash.hashCode());
	}

	/**
	 * To compare an object to this primary key object.
	 * 
	 * @param that an object to be compored.
	 * @return true if the param object is equal to this primary key object,
	 *         otherwise false.
	 */
	public boolean equals(Object that) {
		if (!(that instanceof PKCollateralAllocation)) {
			return false;
		}
		else {
			if (that.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}