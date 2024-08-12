package com.integrosys.cms.host.eai.covenant.handler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.OBConvenant;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckList;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.covenant.bus.CovenantItem;
import com.integrosys.cms.host.eai.covenant.bus.RecurrentDoc;
import com.integrosys.cms.host.eai.covenant.bus.StageConvenantItem;

/**
 * 
 * @author Thurein
 * @since 20-Nov-2008 Helper class that support covenant actual handler
 */
public class ConvenantHandlerHelper {

	/**
	 * copy the values from EaiRecurrentCheckList to OBRecurrentCheckList
	 * @param RecurrentDoc eaiRecurrentCL
	 * @param OBRecurrentCheckList obRecurrentCL
	 * @return OBRecurrentCheckList copiedObject
	 */
	public OBRecurrentCheckList convertToOBrecurrentCheckList(RecurrentDoc eaiRecurrentCL) {
		OBRecurrentCheckList obRecurrent = null;
		if (eaiRecurrentCL != null) {
			obRecurrent = new OBRecurrentCheckList(eaiRecurrentCL.getCmsLimitProfileID(), eaiRecurrentCL
					.getCmsSubProfileID());
			obRecurrent.setCheckListID(eaiRecurrentCL.getRecurrentDocID());

			obRecurrent.setConvenantList(convertToOBCovItems(eaiRecurrentCL.getConvenantItems()));
		}
		return obRecurrent;
	}

	public IConvenant[] convertToOBCovItems(Vector eaiCovItems) {
		IConvenant[] covArray = new IConvenant[eaiCovItems.size()];
		int i = 0;
		Iterator iter = eaiCovItems.iterator();
		while (iter.hasNext()) {
			IConvenant obCov = new OBConvenant();
			CovenantItem eaiCov = (CovenantItem) iter.next();
			obCov.setConvenantID(eaiCov.getCMSCovenantItemID().longValue());
			obCov.setConvenantRef(eaiCov.getCMSconvenantItemRefID().longValue());
			obCov.setConvenantStatus(eaiCov.getCovenantCondition());
			obCov.setInitialDocEndDate(eaiCov.getJDODocEndDate());
			obCov.setRemarks(eaiCov.getRemarks());
			covArray[i] = obCov;
		}
		return covArray;

	}

	public Vector copyConvItemsForStaging(Vector actualConvItems) {

		Iterator iter = actualConvItems.iterator();

		Vector stageConvItems = new Vector();

		while (iter.hasNext()) {

			CovenantItem convItem = (CovenantItem) iter.next();

			StageConvenantItem sgtConvItem = new StageConvenantItem();

			AccessorUtil.copyValue(convItem, sgtConvItem);

			stageConvItems.add(sgtConvItem);
		}

		return stageConvItems;
	}

	public boolean isCreateCovenantItem(CovenantItem convenantItem) {
		return IEaiConstant.UPDATE_STATUS_IND_INSERT.equals(convenantItem.getUpdateStatusIndicator());
	}

	/**
	 * Check if it is to update the covenantItem.
	 * 
	 * @param sec of type covenantItem
	 * @return boolean
	 */
	public boolean isUpdateCovenantItem(CovenantItem convenantItem) {
		return IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(convenantItem.getUpdateStatusIndicator());
	}

	/**
	 * Check if it is to delete the covenant item.
	 * 
	 * @param security of type ApprovedSecurity
	 * @return boolean
	 */
	public boolean isDeleteCovenantItem(CovenantItem convenantItem) {
		return IEaiConstant.UPDATE_STATUS_IND_DELETE.equals(convenantItem.getUpdateStatusIndicator());
	}

	/**
	 * Check if the convenant Item has changed.
	 * 
	 * @param covenant of type Covenant
	 * @return boolean
	 */
	public boolean isCovenantItemChanged(CovenantItem convenantItem) {
		return IEaiConstant.CHANGE_INDICATOR_YES.equals(convenantItem.getChangeIndicator());
	}

	public boolean isCovenantItemsChanged(Vector covItems) {
		boolean changed = false;
		Iterator iter = covItems.iterator();
		while (iter.hasNext()) {
			CovenantItem covItem = (CovenantItem) iter.next();
			if (isCovenantItemChanged(covItem)) {
				changed = true;
			}
		}
		return changed;
	}
}
