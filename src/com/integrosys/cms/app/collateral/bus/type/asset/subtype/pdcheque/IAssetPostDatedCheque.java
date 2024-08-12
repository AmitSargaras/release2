/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/IAssetPostDatedCheque.java,v 1.1 2003/07/24 10:29:51 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import java.util.Date;

/**
 * This interface represents Asset of type Post Dated Cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/24 10:29:51 $ Tag: $Name: $
 */
public interface IAssetPostDatedCheque extends IAssetBasedCollateral {
	/**
	 * Get a list of post dated cheques of this asset.
	 * 
	 * @return IPostDatedCheque[]
	 */
	public IPostDatedCheque[] getPostDatedCheques();

	/**
	 * set a list of post dated cheques as an asset.
	 * 
	 * @param postDatedCheques of type IPostDatedCheque[]
	 */
	public void setPostDatedCheques(IPostDatedCheque[] postDatedCheques);
	
	public Date getChequeDate();
	
	public void setChequeDate(Date chequeDate);
	
	public String getChequeRefNumber();
	
	public void setChequeRefNumber(String chequeRefNumber);
	
	public double getInterestRate();
	
	public void setInterestRate(double interestRate);
	
	public Date getPriCaveatGuaranteeDate();
	
	public void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate);
	
	public String getRemarks();
	
	public void setRemarks(String remarks);
}
