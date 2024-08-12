/*
 * Created on Jun 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.cms.app.collateral.bus.OBSecApportionLmtDtl;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentViewHelper {
	private List secApportionLmtDtlList;

	public SecApportionmentViewHelper(List l) {
		secApportionLmtDtlList = l;
	}

	public List getLeIdDropDownValues() {
		List result = new ArrayList();
		if (secApportionLmtDtlList != null) {
			for (int i = 0; i < secApportionLmtDtlList.size(); i++) {
				OBSecApportionLmtDtl curDtl = (OBSecApportionLmtDtl) (secApportionLmtDtlList.get(i));
				String curLeId = "" + curDtl.getLeId();
				if (!result.contains(curLeId)) {
					result.add(curLeId);
				}
			}
		}
		return result;
	}

	public List getSubprofileIdDropDownValues(String leId) {
		List result = new ArrayList();
		if (secApportionLmtDtlList != null) {
			for (int i = 0; i < secApportionLmtDtlList.size(); i++) {
				OBSecApportionLmtDtl curDtl = (OBSecApportionLmtDtl) (secApportionLmtDtlList.get(i));
				String curLeId = "" + +curDtl.getLeId();
				if (curLeId.equals(leId)) {
					String curSubProfileId = "" + curDtl.getSubProfileId();
					if (!result.contains(curSubProfileId)) {
						result.add(curSubProfileId);
					}
				}
			}
		}
		return result;
	}

	public List[] getLimitIdDropDownValueLabels(String leId, String subProfileId) {
		List[] result = new List[2];
		// this contains the value of the dropdown list which is
		// cms_lsp_appr_lmt_id
		result[0] = new ArrayList();
		// this contains the label of the dropdown list which is lmt_id
		result[1] = new ArrayList();
		if ((secApportionLmtDtlList == null) || (subProfileId == null)) {
			return result;
		}
		else {
			for (int i = 0; i < secApportionLmtDtlList.size(); i++) {
				OBSecApportionLmtDtl curDtl = (OBSecApportionLmtDtl) (secApportionLmtDtlList.get(i));
				String curLeId = "" + curDtl.getLeId();
				String curSubProfileId = "" + curDtl.getSubProfileId();
				if (curLeId.equals(leId) && curSubProfileId.equals(subProfileId)) {
					String curCmsLspApprLmtId = "" + curDtl.getCmsLspApprLmtId();
					String curLimitId = "" + curDtl.getLimitId();
					if (!result[0].contains(curCmsLspApprLmtId)) {
						result[0].add(curCmsLspApprLmtId);
						result[1].add(curLimitId);
					}
				}
			}
			return result;
		}
	}

	public String getLeNameByLimitId(String leId) {
		if (secApportionLmtDtlList != null) {
			for (int i = 0; i < secApportionLmtDtlList.size(); i++) {
				OBSecApportionLmtDtl curDtl = (OBSecApportionLmtDtl) (secApportionLmtDtlList.get(i));
				String curLeId = "" + curDtl.getLeId();
				if (curLeId.equals(leId)) {
					return curDtl.getLeName();
				}
			}
		}
		return "";
	}

	public OBSecApportionLmtDtl getLimitDetailByLimitCharge(String limitId, String chargeId) {
		if (secApportionLmtDtlList != null) {
			for (int i = 0; i < secApportionLmtDtlList.size(); i++) {
				OBSecApportionLmtDtl curDtl = (OBSecApportionLmtDtl) (secApportionLmtDtlList.get(i));
				if (limitId.equals("" + curDtl.getCmsLspApprLmtId()) && chargeId.equals("" + curDtl.getChargeId())) {
					return curDtl;
				}
			}
		}
		return null;
	}

}
