/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ShareCounterMapper
 *
 * Created on 9:45:22 AM
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

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.OBShareCounter;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 9:45:22 AM
 */
public class ShareCounterMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm commonForm, HashMap hashMap) throws MapperException {
		OBShareCounter ob = new OBShareCounter();
		ShareCounterForm form = (ShareCounterForm) commonForm;

		// DefaultLogger.debug ( this , " Is null ? : " + ( form.getFeedId () ==
		// null ) ) ;
		// DefaultLogger.debug ( this , " Is null ? : " + ( form.getIsIntSuspend
		// () == null ) ) ;
		// DefaultLogger.debug ( this , " Is null ? : " + ( form.getIsLiquid ()
		// == null ) ) ;

		DefaultLogger.debug(this, "Margin Of Advance : " + form.getMarginOfAdvance());

		ob.setFeedId(form.getFeedId());
		ob.setIsIntSuspend(form.getIsIntSuspend());
		ob.setIsLiquid(form.getIsLiquid());
		ob.setMarginOfAdvance(form.getMarginOfAdvance());
		ob.setMaximumCap(form.getMaximumCap());
		ob.setParameterId(form.getParameterId());
		ob.setParameterRef(form.getParameterRef());
		ob.setParameterType(form.getParameterType());
		ob.setStatus(form.getStatus());
		ob.setVersionTime(form.getVersionTime());
		ob.setIsFi(form.getIsFi());

		ob.setParamBoardType(form.getParamBoardType());
		ob.setParamShareStatus(form.getParamShareStatus());

		return ob;
	}

	public CommonForm mapOBToForm(CommonForm commonForm, Object object, HashMap hashMap) throws MapperException {
		OBShareCounter ob = (OBShareCounter) object;
		ShareCounterForm form = (ShareCounterForm) commonForm;

		form.setFeedId(ob.getFeedId());
		form.setIsIntSuspend(ob.getIsIntSuspend());
		form.setIsLiquid(ob.getIsLiquid());
		form.setMarginOfAdvance(ob.getMarginOfAdvance());
		form.setMaximumCap(ob.getMaximumCap());
		form.setParameterId(ob.getParameterId());
		form.setParameterRef(ob.getParameterRef());
		form.setParameterType(ob.getParameterType());
		form.setStatus(ob.getStatus());
		form.setVersionTime(ob.getVersionTime());
		form.setIsFi(ob.getIsFi());

		form.setParamBoardType(ob.getParamBoardType());
		form.setParamShareStatus(ob.getParamShareStatus());

		return form;
	}

	public String[][] getParameterDescriptor() {
		return super.getParameterDescriptor();
	}
}
