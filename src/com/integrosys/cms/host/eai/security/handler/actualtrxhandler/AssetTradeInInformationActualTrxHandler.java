package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.TradeInInfo;

public class AssetTradeInInformationActualTrxHandler extends AbstractCommonActualTrxHandler {

	private ISecurityDao securityDao;

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public Message persistActualTrx(Message msg) {
		SecurityMessageBody secMsgBody = (SecurityMessageBody) msg.getMsgBody();

		if (preCheckTradeIn(secMsgBody)) {
			AssetSecurity asset = secMsgBody.getAssetDetail();
			ApprovedSecurity sec = secMsgBody.getSecurityDetail();

			List storedTradeInInfos = (List) this.securityDao.retrieveTradeInInformationByCmsSecurityId(sec
					.getCMSSecurityId(), true);

			List tradeInInformations = asset.getTradeInInformation();
			for (Iterator itr = tradeInInformations.iterator(); itr.hasNext();) {
				TradeInInfo tradeIn = (TradeInInfo) itr.next();

				if (ICMSConstant.HOST_STATUS_INSERT.equals(sec.getUpdateStatusIndicator())) {
					createTradeInWithRef(tradeIn, sec, ISecurityDao.ENTITY_NAME_ACTUAL_TRADE_IN);
				}
				else if (ICMSConstant.HOST_STATUS_UDPATE.equals(sec.getUpdateStatusIndicator())) {
					int index = storedTradeInInfos.indexOf(tradeIn);
					if (index >= 0) {
						TradeInInfo storedTradeInInfo = (TradeInInfo) storedTradeInInfos.get(index);
						AccessorUtil.copyValue(tradeIn, storedTradeInInfo, new String[] { "Id", "CmsCollateralId",
								"RefId", "CurrencyCode", "VersionTime" });

						this.securityDao.update(storedTradeInInfo, TradeInInfo.class,
								ISecurityDao.ENTITY_NAME_ACTUAL_TRADE_IN);
						AccessorUtil.copyValue(storedTradeInInfo, tradeIn);
					}
					else {
						createTradeInWithRef(tradeIn, sec, ISecurityDao.ENTITY_NAME_ACTUAL_TRADE_IN);
					}
				}
			}
		}

		return msg;
	}

	public Message persistStagingTrx(Message msg, Object trxValue) {
		SecurityMessageBody secMsgBody = (SecurityMessageBody) msg.getMsgBody();

		if (preCheckTradeIn(secMsgBody)) {
			AssetSecurity asset = secMsgBody.getAssetDetail();
			ApprovedSecurity sec = secMsgBody.getSecurityDetail();

			List storedTradeInInfos = (List) this.securityDao.retrieveTradeInInformationByCmsSecurityId(sec
					.getCMSSecurityId(), false);

			List tradeInInformations = asset.getTradeInInformation();
			for (int i = 0; i < tradeInInformations.size(); i++) {
				TradeInInfo tradeIn = (TradeInInfo) tradeInInformations.get(i);

				if (ICMSConstant.HOST_STATUS_INSERT.equals(sec.getUpdateStatusIndicator())) {
					TradeInInfo stagingTradeIn = new TradeInInfo();
					AccessorUtil.copyValue(tradeIn, stagingTradeIn, new String[] { "Id", "CmsCollateralId",
							"VersionTime" });

					stagingTradeIn.setCmsCollateralId(new Long(sec.getCMSSecurityId()));
					stagingTradeIn.setCurrencyCode(sec.getCurrency());

					Long key = (Long) this.securityDao.store(stagingTradeIn, TradeInInfo.class,
							ISecurityDao.ENTITY_NAME_STAGE_TRADE_IN);
					stagingTradeIn.setId(key);

					tradeInInformations.set(i, stagingTradeIn);
				}
				else if (ICMSConstant.HOST_STATUS_UDPATE.equals(sec.getUpdateStatusIndicator())) {
					int index = storedTradeInInfos.indexOf(tradeIn);

					if (index >= 0) {
						TradeInInfo storedTradeInInfo = (TradeInInfo) storedTradeInInfos.get(index);
						AccessorUtil.copyValue(tradeIn, storedTradeInInfo, new String[] { "Id", "CmsCollateralId",
								"RefId", "CurrencyCode", "VersionTime" });
						this.securityDao.update(storedTradeInInfo, TradeInInfo.class,
								ISecurityDao.ENTITY_NAME_STAGE_TRADE_IN);

						tradeInInformations.set(i, storedTradeInInfo);
					}
					else {
						TradeInInfo stagingTradeIn = new TradeInInfo();
						AccessorUtil.copyValue(tradeIn, stagingTradeIn, new String[] { "Id", "CmsCollateralId",
								"VersionTime" });

						stagingTradeIn.setCmsCollateralId(new Long(sec.getCMSSecurityId()));
						stagingTradeIn.setCurrencyCode(sec.getCurrency());

						Long key = (Long) this.securityDao.store(stagingTradeIn, TradeInInfo.class,
								ISecurityDao.ENTITY_NAME_STAGE_TRADE_IN);
						stagingTradeIn.setId(key);

						tradeInInformations.set(i, stagingTradeIn);
					}
				}
			}
		}
		return msg;
	}

	/**
	 * To check whether required to process trade in information.
	 * @param secMsgBody security message body
	 * @return true if required to process trade in information, else false
	 */
	private boolean preCheckTradeIn(SecurityMessageBody secMsgBody) {
		AssetSecurity asset = secMsgBody.getAssetDetail();
		if (asset == null || asset.getTradeInInformation() == null || asset.getTradeInInformation().isEmpty()) {
			return false;
		}

		return true;
	}

	private void createTradeInWithRef(TradeInInfo tradeIn, ApprovedSecurity sec, String entityName) {
		tradeIn.setCmsCollateralId(new Long(sec.getCMSSecurityId()));
		tradeIn.setCurrencyCode(sec.getCurrency());

		Long key = (Long) this.securityDao.store(tradeIn, TradeInInfo.class, entityName);

		// update reference id
		tradeIn.setRefId(key);
		tradeIn.setId(key);
		this.securityDao.update(tradeIn, TradeInInfo.class, entityName);
	}
}
