/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/OBAssetPostDatedCheque.java,v 1.1 2003/07/24 10:29:51 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.OBAssetBasedCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import java.util.Date;
/**
 * This class represents Asset of type Post Dated Cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/24 10:29:51 $ Tag: $Name: $
 */
public class OBAssetPostDatedCheque extends OBAssetBasedCollateral implements IAssetPostDatedCheque {
	
	

	private IPostDatedCheque[] postDatedCheques; 
	
	private Date chequeDate;
	
	private String chequeRefNumber;
	
	private double interestRate;

	private Date priCaveatGuaranteeDate;
	
	
	private String remarks;
	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getPriCaveatGuaranteeDate() {
		return priCaveatGuaranteeDate;
	}

	public void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate) {
		this.priCaveatGuaranteeDate = priCaveatGuaranteeDate;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeRefNumber() {
		return chequeRefNumber;
	}

	public void setChequeRefNumber(String chequeRefNumber) {
		this.chequeRefNumber = chequeRefNumber;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * Default Constructor.
	 */
	public OBAssetPostDatedCheque() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IAssetPostDatedCheque
	 */
	public OBAssetPostDatedCheque(IAssetPostDatedCheque obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get a list of post dated cheques of this asset.
	 * 
	 * @return IPostDatedCheque[]
	 */
	public IPostDatedCheque[] getPostDatedCheques() {
		return postDatedCheques;
	}

	/**
	 * set a list of post dated cheques as an asset.
	 * 
	 * @param postDatedCheques of type IPostDatedCheque[]
	 */
	public void setPostDatedCheques(IPostDatedCheque[] postDatedCheques) {
		this.postDatedCheques = postDatedCheques;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBAssetPostDatedCheque)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}