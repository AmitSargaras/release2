/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtSecMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "securityList", "java.util.Collection", SERVICE_SCOPE }, });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */

	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		return commonForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		try {
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) inputs.get("lmtTrxObj");
			ILimit curLimit = lmtTrxObj.getStagingLimit();
			ICollateralAllocation[] alloc = curLimit.getCollateralAllocations();
			LmtSecMappingForm myForm = (LmtSecMappingForm) commonForm;
			String[] arr = myForm.getSelectedSec();
			List securityList = (List) (inputs.get("securityList"));
			String limitProfileID = myForm.getLimitProfileID();

			List tempList = new ArrayList();

			if ((arr != null) && (securityList != null)) {
				for (int i = 0; i < arr.length; i++) {
					int nextInd = Integer.parseInt(arr[i]);
					if(nextInd > 9){
						nextInd = nextInd % 10;
					}
					OBCollateral nextCol = (OBCollateral) (securityList.get(nextInd));
					if (!checkSecExists(alloc, nextCol)) {
						tempList.add(nextCol);
					}
				}
			}
			if (alloc == null) {
				ICollateralAllocation[] newAlloc = new ICollateralAllocation[tempList.size()];
				for (int j = 0; j < tempList.size(); j++) {
					OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
					nextAlloc.setCollateral((OBCollateral) (tempList.get(j)));
					nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
					nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
					nextAlloc.setLimitProfileID(Long.parseLong(limitProfileID));
					nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
					newAlloc[j] = nextAlloc;
				}
				curLimit.setCollateralAllocations(newAlloc);
			}
			else {
				ICollateralAllocation[] newAlloc = new ICollateralAllocation[alloc.length + tempList.size()];
				for (int j = 0; j < alloc.length; j++) {
					newAlloc[j] = alloc[j];
				}
				for (int j = 0; j < tempList.size(); j++) {
					OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
					nextAlloc.setCollateral((OBCollateral) (tempList.get(j)));
					nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
					nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
					nextAlloc.setLimitProfileID(Long.parseLong(limitProfileID));
					nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
					newAlloc[j + alloc.length] = nextAlloc;
				}
				curLimit.setCollateralAllocations(newAlloc);
			}
			if (securityList != null) {
				securityList.clear();
			}
			return curLimit;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	private boolean checkSecExists(ICollateralAllocation[] alloc, OBCollateral nextCol) {
		if (alloc == null) {
			return false;
		}
		for (int i = 0; i < alloc.length; i++) {
			if (alloc[i].getCollateral().getCollateralID() == nextCol.getCollateralID()) {

				if (ICMSConstant.HOST_STATUS_DELETE.equals(alloc[i].getHostStatus())) {
					return false;
				}

				return true;
			}
		}
		return false;
	}
}
