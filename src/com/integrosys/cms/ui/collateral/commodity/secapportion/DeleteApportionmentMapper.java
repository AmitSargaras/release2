/*
 * Created on Sep 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.commodity.secapportion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.commodity.security.SecurityForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DeleteApportionmentMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		try {
			ICollateral col = (ICollateral) obj;
			SecurityForm colForm = (SecurityForm) cForm;
			if (CollateralAction.EVENT_DELETE_ITEM.equals(colForm.getEvent())) {

				String[] deletedItems = colForm.getDeletedApportionments();
				if (deletedItems == null) {
					return col;
				}
				int[] deletedItemsInt = new int[deletedItems.length];
				for (int m = 0; m < deletedItems.length; m++) {
					deletedItemsInt[m] = Integer.parseInt(deletedItems[m]);
				}
				Arrays.sort(deletedItemsInt);

				if ((deletedItemsInt != null) && (col.getSecApportionment() != null)) {
					List secApportionmentList = col.getSecApportionment();
					int initSize = secApportionmentList.size();
					for (int i = 0; i < deletedItemsInt.length; i++) {
						int origIndex = deletedItemsInt[i];
						int curIndex = origIndex - (initSize - secApportionmentList.size());
						if ((curIndex >= 0) && (curIndex < secApportionmentList.size())) {
							secApportionmentList.remove(curIndex);
						}
					}
				}

			}
			return col;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException("Error deleting security apportionment");
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		SecurityForm colForm = (SecurityForm) cForm;
		colForm.setDeletedApportionments(new String[0]);
		return colForm;
	}

}
