package com.integrosys.cms.app.propertyparameters.proxy;

import java.util.List;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.SBPropertyParametersHome;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:28:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrPaProxyManagerImpl implements IPrPaProxyManager {

	public List getAllActural() throws PropertyParametersException {
		try {
			return getPrPaProxyManager().getAllActural();
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	public IPrPaTrxValue getCCDocumentLocationByTrxID(String aTrxID) throws PropertyParametersException {
		try {
			return getPrPaProxyManager().getCCDocumentLocationByTrxID(aTrxID);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	public IPrPaTrxValue getCCDocumentLocationTrxValue(long aDocumentLocationID) throws PropertyParametersException {
		try {
			return getPrPaProxyManager().getCCDocumentLocationTrxValue(aDocumentLocationID);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	public IPrPaTrxValue makerCreateDocumentLocation(ITrxContext anITrxContext,
			IPropertyParameters anICCDocumentLocation) throws PropertyParametersException {
		try {
			return getPrPaProxyManager().makerCreateDocumentLocation(anITrxContext, anICCDocumentLocation);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	public IPrPaTrxValue makerUpdateDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException {
		try {
			return getPrPaProxyManager().makerUpdateDocumentLocation(anITrxContext, anICCDocumentLocationTrxValue,
					anICCDocumentLocation);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	public IPrPaTrxValue makerDeleteDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException {
		try {
			return getPrPaProxyManager().makerDeleteDocumentLocation(anITrxContext, anICCDocumentLocationTrxValue,
					anICCDocumentLocation);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	public IPrPaTrxValue checkerApproveDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException {
		try {
			return getPrPaProxyManager().checkerApproveDocumentLocation(anITrxContext, anIPrPaTrxValue);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	public IPrPaTrxValue checkerRejectDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException {
		try {
			return getPrPaProxyManager().checkerRejectDocumentLocation(anITrxContext, anIPrPaTrxValue);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}

	}

	public IPrPaTrxValue makerEditRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue,
			IPropertyParameters anIpropertyParameters) throws PropertyParametersException {
		try {
			return getPrPaProxyManager().makerEditRejectedDocumentLocation(anITrxContext, anIPrPaTrxValue,
					anIpropertyParameters);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}

	}

	public IPrPaTrxValue makerCloseRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException {
		try {
			return getPrPaProxyManager().makerCloseRejectedDocumentLocation(anITrxContext, anIPrPaTrxValue);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

	// ******************** Proxy methods for MF Template ****************

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateBySecSubType
	 */
	public ICommonMFTemplate[] getMFTemplateBySecSubType(String secTypeCode, String secSubTypeCode)
			throws PropertyParametersException {
		try {

			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.getMFTemplateBySecSubType(secTypeCode, secSubTypeCode);

		}
		catch (Exception e) {

			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#listMFTemplate
	 */
	public List listMFTemplate() throws PropertyParametersException {
		try {

			SBPropertyParameters mgr = getSBPropertyParameters();
			return mgr.listMFTemplate();

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at listMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplate
	 */
	public IMFTemplate getMFTemplate(long mFTemplateID) throws PropertyParametersException {
		try {
			SBPropertyParameters mgr = getSBPropertyParameters();
			return mgr.getMFTemplate(mFTemplateID);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at getMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateTrxValue
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValue(ITrxContext ctx, long mFTemplateID)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.getMFTemplateTrxValue(ctx, mFTemplateID);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at getMFTemplateTrxValue: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at getMFTemplateTrxValue: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateTrxValueByTrxID
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.getMFTemplateTrxValueByTrxID(ctx, trxID);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at getMFTemplateTrxValueByTrxID: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at getMFTemplateTrxValueByTrxID: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCreateMFTemplate
	 */
	public IMFTemplateTrxValue makerCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.makerCreateMFTemplate(ctx, trxVal, value);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerCreateMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerCreateMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerUpdateMFTemplate
	 */
	public IMFTemplateTrxValue makerUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.makerUpdateMFTemplate(ctx, trxVal, value);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerUpdateMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerUpdateMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCloseCreateMFTemplate
	 */
	public IMFTemplateTrxValue makerCloseCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.makerCloseCreateMFTemplate(ctx, trxVal);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerCloseCreateMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerCloseCreateMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCloseUpdateMFTemplate
	 */
	public IMFTemplateTrxValue makerCloseUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.makerCloseUpdateMFTemplate(ctx, trxVal);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerCloseUpdateMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerCloseUpdateMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerDeleteMFTemplate
	 */
	public IMFTemplateTrxValue makerDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.makerDeleteMFTemplate(ctx, trxVal);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerDeleteMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at makerDeleteMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerApproveUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerApproveUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.checkerApproveUpdateMFTemplate(ctx, trxVal);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at checkerApproveUpdateMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at checkerApproveUpdateMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerApproveUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerApproveDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.checkerApproveDeleteMFTemplate(ctx, trxVal);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at checkerApproveDeleteMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at checkerApproveDeleteMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerRejectUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerRejectUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		try {
			SBPrPaProxyManager proxy = getPrPaProxyManager();
			return proxy.checkerRejectUpdateMFTemplate(ctx, trxVal);
		}
		catch (PropertyParametersException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at checkerRejectUpdateMFTemplate: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at checkerRejectUpdateMFTemplate: " + e.toString());
		}
	}

	private SBPrPaProxyManager getPrPaProxyManager() {
		SBPrPaProxyManager proxymgr = (SBPrPaProxyManager) BeanController.getEJB(
				JNDIConstants.SB_PRPA_PROXY_MANAGER_HOME, SBPrPaProxyManagerHome.class.getName());
		return proxymgr;
	}

	private SBPropertyParameters getSBPropertyParameters() throws PropertyParametersException {
		SBPropertyParameters busmgr = (SBPropertyParameters) BeanController.getEJB(
				JNDIConstants.SB_PROPERTY_PARAMETERS_HOME, SBPropertyParametersHome.class.getName());
		if (busmgr == null) {
			throw new PropertyParametersException("SBPropertyParameters is null!");
		}
		return busmgr;
	}

	public boolean allowAutoValParamTrx(String referenceId) throws PropertyParametersException {
		try {
			return getPrPaProxyManager().allowAutoValParamTrx(referenceId);
		}
		catch (Exception e) {
			throw new PropertyParametersException(e);
		}
	}

}
