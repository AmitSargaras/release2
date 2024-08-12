/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecPledgorDtlMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "customerList", "java.util.Collection", SERVICE_SCOPE }, });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		try {
			ICollateralTrxValue secTrxObj = (ICollateralTrxValue) (inputs.get("secTrxObj"));
			ICollateral curSec = secTrxObj.getStagingCollateral();
			ICollateralPledgor[] pledgors = curSec.getPledgors();
			SecPledgorDtlForm myForm = (SecPledgorDtlForm) commonForm;
			String[] arr = myForm.getSelectedPlegor();
			List customerList = (List) (inputs.get("customerList"));
			List tempList = new ArrayList();
			if ((arr != null) && (customerList != null)) {
				for (int i = 0; i < arr.length; i++) {
					int nextInd = Integer.parseInt(arr[i]);
					ICollateralPledgor nextPledgor = (ICollateralPledgor) (customerList.get(nextInd));
					if (!checkPledgorExists(pledgors, nextPledgor)) {
						tempList.add(nextPledgor);
					}
				}
			}
			if (pledgors == null) {
				ICollateralPledgor[] newPledgor = new ICollateralPledgor[tempList.size()];
				for (int j = 0; j < tempList.size(); j++) {
					newPledgor[j] = (ICollateralPledgor) (tempList.get(j));
				}
				curSec.setPledgors(newPledgor);
			}
			else {
				ICollateralPledgor[] newPledgor = new ICollateralPledgor[pledgors.length + tempList.size()];
				for (int j = 0; j < pledgors.length; j++) {
					newPledgor[j] = pledgors[j];
				}
				for (int j = 0; j < tempList.size(); j++) {
					newPledgor[j + pledgors.length] = (ICollateralPledgor) (tempList.get(j));
				}
				curSec.setPledgors(newPledgor);
			}
			if (customerList != null) {
				customerList.clear();
			}
			return curSec;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	private boolean checkPledgorExists(ICollateralPledgor[] existingPledgors, ICollateralPledgor curPledgor) {
		if (existingPledgors == null) {
			return false;
		}
		for (int i = 0; i < existingPledgors.length; i++) {
			if (existingPledgors[i].getLegalID() == curPledgor.getLegalID()) {
				return true;
			}
		}
		return false;
	}

}
