/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CollateralCustodianInfo.java,v 1.1 2004/02/05 01:35:03 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.io.Serializable;

import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;

/**
 * Helper class that encapsulate the collateral custodian specify info
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/05 01:35:03 $ Tag: $Name: $
 */
public class CollateralCustodianInfo implements Serializable {
	private ICollateralType collateralType = null;

	private ICollateralSubType collateralSubType = null;

	private String collateralReference = null;

    private String sciReferenceNote = null;

    private String commentRemarks = null;

	public CollateralCustodianInfo() {
	}

	public ICollateralType getCollateralType() {
		return this.collateralType;
	}

	public ICollateralSubType getCollateralSubType() {
		return this.collateralSubType;
	}

	public String getCollateralReference() {
		return this.collateralReference;
	}

    public String getSciReferenceNote() {
		return this.sciReferenceNote;
	}

    public String getCommentRemarks() {
		return this.commentRemarks;
	}

	public void setCollateralType(ICollateralType anICollateralType) {
		this.collateralType = anICollateralType;
	}

	public void setCollateralSubType(ICollateralSubType anICollateralSubType) {
		this.collateralSubType = anICollateralSubType;
	}

	public void setCollateralReference(String aCollateralReference) {
		this.collateralReference = aCollateralReference;
	}

    public void setSciReferenceNote(String aSciReferenceNote) {
		this.sciReferenceNote = aSciReferenceNote;
	}

    public String setCommentRemarks(String aCommentRemarks) {
		return this.commentRemarks = aCommentRemarks;
	}

}
