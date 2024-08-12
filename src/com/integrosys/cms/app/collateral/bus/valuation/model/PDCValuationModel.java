/*
 * Created on May 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.model;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PDCValuationModel extends GenericValuationModel {

	private List postDatedChequeList;

	public PDCValuationModel() {
		super();
		this.postDatedChequeList = new ArrayList();
	}

	/**
	 * @return Returns the postDatedChequeList.
	 */
	public List getPostDatedChequeList() {
		return postDatedChequeList;
	}

	/**
	 * @param postDatedChequeList The postDatedChequeList to set.
	 */
	public void setPostDatedChequeList(List postDatedChequeList) {
		this.postDatedChequeList = postDatedChequeList;
	}

	public void addPostDatedCheque(IPostDatedCheque cheque) {
		postDatedChequeList.add(cheque);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.valuation.IValuationModel#setDetailFromCollateral
	 * (com.integrosys.cms.app.collateral.bus.ICollateral)
	 */
	public void setDetailFromCollateral(ICollateral col) {
		super.setDetailFromCollateral(col);
		// TODO Auto-generated method stub
		if (col instanceof IAssetPostDatedCheque) {
			IAssetPostDatedCheque pdcCol = (IAssetPostDatedCheque) col;
			IPostDatedCheque[] arr = pdcCol.getPostDatedCheques();
			if (arr != null) {
				for (int i = 0; i < arr.length; i++) {
					addPostDatedCheque(arr[i]);
				}
			}
		}
	}
}
