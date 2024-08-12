package com.integrosys.cms.app.propertyparameters.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:35:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SBPrPaProxyManager extends EJBObject {

	public List getAllActural() throws PropertyParametersException, RemoteException;

	public IPrPaTrxValue getCCDocumentLocationByTrxID(String aTrxID) throws PropertyParametersException,
			RemoteException;

	public IPrPaTrxValue getCCDocumentLocationTrxValue(long aDocumentLocationID) throws PropertyParametersException,
			RemoteException;

	public IPrPaTrxValue makerCreateDocumentLocation(ITrxContext anITrxContext,
			IPropertyParameters anICCDocumentLocation) throws PropertyParametersException, RemoteException;

	public IPrPaTrxValue makerUpdateDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException, RemoteException;

	public IPrPaTrxValue makerDeleteDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException, RemoteException;

	public IPrPaTrxValue checkerApproveDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException, RemoteException;

	public IPrPaTrxValue checkerRejectDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException, RemoteException;

	public IPrPaTrxValue makerEditRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue,
			IPropertyParameters anIpropertyParameters) throws PropertyParametersException, RemoteException;

	public IPrPaTrxValue makerCloseRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException, RemoteException;

	public boolean allowAutoValParamTrx(String referenceId) throws PropertyParametersException, RemoteException;

	// ******************** Proxy methods for MF Template ****************
	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateBySecSubType
	 */
	public ICommonMFTemplate[] getMFTemplateBySecSubType(String secTypeCode, String secSubTypeCode)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateTrxValue
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValue(ITrxContext ctx, long mFTemplateID)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateTrxValueByTrxID
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCreateMFTemplate
	 */
	public IMFTemplateTrxValue makerCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerUpdateMFTemplate
	 */
	public IMFTemplateTrxValue makerUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCloseUpdateMFTemplate
	 */
	public IMFTemplateTrxValue makerCloseUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCloseCreateMFTemplate
	 */
	public IMFTemplateTrxValue makerCloseCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerDeleteMFTemplate
	 */
	public IMFTemplateTrxValue makerDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerApproveUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerApproveUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerApproveDeleteMFTemplate
	 */
	public IMFTemplateTrxValue checkerApproveDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerRejectUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerRejectUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException, RemoteException;

}
