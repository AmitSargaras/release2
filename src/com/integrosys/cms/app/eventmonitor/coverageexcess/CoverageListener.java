/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/coverageexcess/CoverageListener.java,v 1.1 2003/09/14 09:19:57 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.coverageexcess;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;

public class CoverageListener extends CommonCMSEventListener {

	private ILimitProxy limitProxy;

	private ICollateralProxy collateralProxy;

	public void setLimitProxy(ILimitProxy limitProxy) {
		this.limitProxy = limitProxy;
	}

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	/**
	 * This method is invoked by the EventManager on IEventListeners when an
	 * event is trigged.
	 * 
	 * @param eventID is the String value of the Event ID that is causing the
	 *        triggering
	 * @param params is a List of String value parameters. This parameter should
	 *        be null if no parameters are required In this listener, only one
	 *        element is expected in the List, which is of type
	 *        OBGuarantorNoticeInfo
	 * @throw EventHandlingException if an exception occurs while servicing the
	 *        event
	 */
	public void fireEvent(String eventID, List params) throws EventHandlingException {
		OBCoverageInfo eventInfo = (OBCoverageInfo) params.get(0);
		eventInfo.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
				"SUBJECT NOT FOUND"));
		eventInfo.setLimitProfile(getLimitProfile(eventInfo.getLimitProfileID()));

		sendNotification(eventID, params, getEventRecipientGroup(eventID));
	}

	private ILimitProfile getLimitProfile(long profileID) throws EventHandlingException {

		ILimitProfile profile = null;

		try {
			profile = this.limitProxy.getLimitProfile(profileID);
		}
		catch (LimitException e) {
			throw new EventHandlingException(e);
		}

		// next u need to fetch the ICollateral because what I have is
		// incomplete:
		ILimit[] limitList = profile.getLimits();
		if ((null != limitList) && (limitList.length != 0)) {
			Map colMap = new WeakHashMap(); // to be use as a temporary
			// cache to minimise no. of
			// collateral fetch requests
			for (int i = 0; i < limitList.length; i++) {
				ILimit limit = limitList[i];
				ICollateralAllocation[] allocList = limit.getCollateralAllocations();
				if ((null != allocList) && (allocList.length != 0)) {
					for (int j = 0; j < allocList.length; j++) {
						ICollateralAllocation alloc = allocList[j];
						ICollateral col = alloc.getCollateral();
						Long collateralID = new Long(col.getCollateralID());

						ICollateral newCol = (ICollateral) colMap.get(collateralID);
						if (null == newCol) {
							try {
								newCol = this.collateralProxy.getCollateral(collateralID.longValue(), true);
							}
							catch (CollateralException ex) {
								throw new EventHandlingException("failed to retrieve collateral details for id ["
										+ collateralID + "]", ex);
							}

							colMap.put(collateralID, newCol); // push into
							// cache
						}
						// now set back into collateral allocation
						alloc.setCollateral(newCol);
						allocList[j] = alloc;
					}
				}
				limit.setCollateralAllocations(allocList);
				limitList[i] = limit;
			}
		}

		profile.setLimits(limitList);

		return profile;
	}
}
