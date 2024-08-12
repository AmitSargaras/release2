package com.integrosys.cms.app.propertyparameters.proxy;

import java.util.List;

import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:35:19 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IPrPaProxyManager {

	public List getAllActural() throws PropertyParametersException;

	public IPrPaTrxValue getCCDocumentLocationByTrxID(String aTrxID) throws PropertyParametersException;

	public IPrPaTrxValue getCCDocumentLocationTrxValue(long aDocumentLocationID) throws PropertyParametersException;

	public IPrPaTrxValue makerCreateDocumentLocation(ITrxContext anITrxContext,
			IPropertyParameters anICCDocumentLocation) throws PropertyParametersException;

	public IPrPaTrxValue makerUpdateDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException;

	public IPrPaTrxValue makerDeleteDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException;

	public IPrPaTrxValue checkerApproveDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException;

	public IPrPaTrxValue checkerRejectDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException;

	public IPrPaTrxValue makerEditRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue,
			IPropertyParameters anIpropertyParameters) throws PropertyParametersException;

	public IPrPaTrxValue makerCloseRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException;

	public boolean allowAutoValParamTrx(String referenceId) throws PropertyParametersException;

	// ******************** Proxy methods for MF Template ****************
	/**
	 * Gets all active MF CommonTemplate by security type code and security
	 * subtype code.
	 * 
	 * @return List of ICommonMFTemplate
	 * @throws PropertyParametersException on errors encountered
	 */
	public ICommonMFTemplate[] getMFTemplateBySecSubType(String secTypeCode, String secSubTypeCode)
			throws PropertyParametersException;

	/**
	 * Gets all active MF Template.
	 * 
	 * @return List of IMFTemplate
	 * @throws PropertyParametersException on errors encountered
	 */
	public List listMFTemplate() throws PropertyParametersException;

	/**
	 * Gets the MF Template details by MF Template ID.
	 * 
	 * @param ctx transaction context
	 * @param mFTemplateID MF Template ID
	 * @return IMFTemplate
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplate getMFTemplate(long mFTemplateID) throws PropertyParametersException;

	/**
	 * Gets the MF Template trx value by MF Template ID.
	 * 
	 * @param ctx transaction context
	 * @param mFTemplateID MF Template ID
	 * @return MF Template transaction value for the MF Template ID
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValue(ITrxContext ctx, long mFTemplateID)
			throws PropertyParametersException;

	/**
	 * Gets the MF Template trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws PropertyParametersException;

	/**
	 * Maker creates a MF Template.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @param value a MF Template object to use for updating.
	 * @return updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue makerCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException;

	/**
	 * Maker updates a MF Template.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @param value a MF Template object to use for updating.
	 * @return updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue makerUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException;

	/**
	 * Maker close MF Template created by him/her, and rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @return the updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue makerCloseCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException;

	/**
	 * Maker close MF Template updated by him/her, and rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @return the updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue makerCloseUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException;

	/**
	 * Checker approve MF Template updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @return the updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue checkerApproveUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException;

	/**
	 * Checker approve MF Template deleted by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @return the updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue checkerApproveDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException;

	/**
	 * Checker reject MF Template updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @return updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue checkerRejectUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException;

	/**
	 * Maker delete a MF Template.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Template transaction value
	 * @return updated MF Template transaction value
	 * @throws PropertyParametersException on errors encountered
	 */
	public IMFTemplateTrxValue makerDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException;

}
