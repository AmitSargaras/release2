package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitCreditGrade;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * Handler for processing credit grade of an AA / Limit
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitCreditGradeActualTrxHandler extends AbstractCommonActualTrxHandler {

	private ILimitDao limitDao;

	private Map variationProperties;

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public void setVariationProperties(Map variationProperties) {
		this.variationProperties = variationProperties;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMessage = (AAMessageBody) msg.getMsgBody();

		LimitHelper limitHelper = new LimitHelper();

		boolean variation = aaMessage.getLimitProfile().getCMSLimitProfileId() != null
				&& aaMessage.getLimitProfile().getCMSLimitProfileId().longValue() > 0;
		String sourceId = msg.getMsgHeader().getSource();

		Vector creditGradeVector = aaMessage.getCreditGrade();
		if (creditGradeVector == null || creditGradeVector.isEmpty()) {
			return msg;
		}

		for (Iterator iterator = creditGradeVector.iterator(); iterator.hasNext();) {
			LimitCreditGrade creditGrade = (LimitCreditGrade) iterator.next();

			Map parameters = new WeakHashMap();
			parameters.put("LOSAANumber", creditGrade.getLOSAANumber());
			parameters.put("creditGradeId", new Long(creditGrade.getCreditGradeId()));
			parameters
					.put("creditGradeType.standardCodeValue", creditGrade.getCreditGradeType().getStandardCodeValue());

			LimitCreditGrade temp = (LimitCreditGrade) this.limitDao.retrieveNonDeletedObjectByParameters(parameters,
					"updateStatusIndicator", LimitCreditGrade.class);

			if (temp == null) {
				if (creditGrade.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) {
					creditGrade.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
				}
				else if (creditGrade.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))) {
					creditGrade.setChangeIndicator(ICMSConstant.FALSE_VALUE);
				}
			}
			else {
				if (creditGrade.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR))) {
					creditGrade.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
				}
			}

			if ((creditGrade.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
					&& (creditGrade.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {
				this.limitDao.store(creditGrade, LimitCreditGrade.class);
			}
			else if ((creditGrade.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
					&& ((creditGrade.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (creditGrade
							.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {
				LimitCreditGrade tmpCreditGrade = (LimitCreditGrade) temp;

				if (variation) {
					List copyingProperties = (List) this.variationProperties.get(new VariationPropertiesKey(sourceId,
							LimitCreditGrade.class.getName()));
					limitHelper.copyVariationProperties(creditGrade, tmpCreditGrade, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(creditGrade, tmpCreditGrade, new String[] { IEaiConstant.CMSID });
				}

				this.limitDao.update(tmpCreditGrade, tmpCreditGrade.getClass());

				creditGrade.setCmsId(tmpCreditGrade.getCmsId());
			}
			else {
				if (temp != null) {
					creditGrade.setCmsId(temp.getCmsId());
				}
			}
		}

		return msg;
	}

}
