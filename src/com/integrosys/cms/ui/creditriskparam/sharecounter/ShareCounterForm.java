/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ShareCounterForm
 *
 * Created on 9:40:04 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 9:40:04 AM
 */
public class ShareCounterForm extends TrxContextForm implements Serializable {

	private static final String formName = "ShareCounterForm";

	public static final String mapperName = "com.integrosys.cms.ui.creditriskparam.sharecounter.ShareCounterMapper";

	private String parameterId[];

	private String parameterType[];

	private String marginOfAdvance[];

	private String maximumCap[];

	private String isIntSuspend[];

	private String status[];

	private String isLiquid[];

	private String versionTime[];

	private String feedId[];

	private String parameterRef[];

	private String isFi[];

	// private String maxCollCapFi[] ;
	// private String maxCollCapNonFi[] ;
	// private String quotaCollCapFi[] ;
	// private String quotaCollCapNonFi[] ;
	private String paramBoardType[];

	private String paramShareStatus[];
	
	private String stockExchange;
	
	private String stockType;

	public String[] getFeedId() {
		return feedId;
	}

	public void setFeedId(String[] feedId) {
		this.feedId = feedId;
	}

	public String[] getIsIntSuspend() {
		return isIntSuspend;
	}

	public void setIsIntSuspend(String[] intSuspend) {
		isIntSuspend = intSuspend;
	}

	public String[] getIsLiquid() {
		return isLiquid;
	}

	public void setIsLiquid(String[] liquid) {
		isLiquid = liquid;
	}

	public String[] getMarginOfAdvance() {
		return marginOfAdvance;
	}

	public void setMarginOfAdvance(String[] marginOfAdvance) {
		this.marginOfAdvance = marginOfAdvance;
	}

	public String[] getMaximumCap() {
		return maximumCap;
	}

	public void setMaximumCap(String[] maximumCap) {
		this.maximumCap = maximumCap;
	}

	public String[] getParameterId() {
		return parameterId;
	}

	public void setParameterId(String[] parameterId) {
		this.parameterId = parameterId;
	}

	public String[] getParameterRef() {
		return parameterRef;
	}

	public void setParameterRef(String[] parameterRef) {
		this.parameterRef = parameterRef;
	}

	public String[] getParameterType() {
		return parameterType;
	}

	public void setParameterType(String[] parameterType) {
		this.parameterType = parameterType;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[] getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String[] versionTime) {
		this.versionTime = versionTime;
	}

	public String[] getIsFi() {
		return isFi;
	}

	public void setIsFi(String[] isFi) {
		this.isFi = isFi;
	}

	// public String[] getMaxCollCapFi ()
	// {
	// return maxCollCapFi;
	// }
	//
	// public void setMaxCollCapFi ( String[] maxCollCapFi )
	// {
	// this.maxCollCapFi = maxCollCapFi;
	// }
	//
	// public String[] getMaxCollCapNonFi ()
	// {
	// return maxCollCapNonFi;
	// }
	//
	// public void setMaxCollCapNonFi ( String[] maxCollCapNonFi )
	// {
	// this.maxCollCapNonFi = maxCollCapNonFi;
	// }
	//
	// public String[] getQuotaCollCapFi ()
	// {
	// return quotaCollCapFi;
	// }
	//
	// public void setQuotaCollCapFi ( String[] quotaCollCapFi )
	// {
	// this.quotaCollCapFi = quotaCollCapFi;
	// }
	//
	// public String[] getQuotaCollCapNonFi ()
	// {
	// return quotaCollCapNonFi;
	// }
	//
	// public void setQuotaCollCapNonFi ( String[] quotaCollCapNonFi )
	// {
	// this.quotaCollCapNonFi = quotaCollCapNonFi;
	// }

	public String[] getParamBoardType() {
		return paramBoardType;
	}

	public void setParamBoardType(String[] paramBoardType) {
		this.paramBoardType = paramBoardType;
	}

	public String[] getParamShareStatus() {
		return paramShareStatus;
	}

	public void setParamShareStatus(String[] paramShareStatus) {
		this.paramShareStatus = paramShareStatus;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String[][] getMapper() {
		return new String[][] { { formName, mapperName }, { "BusinessParameterGroup", mapperName },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

}