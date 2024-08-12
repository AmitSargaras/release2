package com.integrosys.cms.app.propertyparameters.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersBusDelegate;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersDAO;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.MFTemplateTrxControllerFactory;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.OBMFTemplateTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:35:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class SBPrPaProxyManagerBean extends AbstractPrPaProxyManager implements SessionBean {

	public void ejbCreate() {
	}

	private SessionContext _context = null;

	public List getAllActural() throws PropertyParametersException {
		try {
			return getBusDelegate().getAllProParameters();
		}
		catch (RemoteException E) {
			throw new PropertyParametersException(E);
		}
	}

	public boolean allowAutoValParamTrx(String referenceId) throws PropertyParametersException {
		try {
			return getBusDelegate().allowAutoValParamTrx(referenceId);
		}
		catch (RemoteException E) {
			throw new PropertyParametersException(E);
		}
	}

	// ******************** Proxy methods forMF Template ****************

	public ICommonMFTemplate[] getMFTemplateBySecSubType(String secTypeCode, String secSubTypeCode)
			throws PropertyParametersException {
		try {

			return getPropertyParaDao().getMFTemplateBySecSubType(secTypeCode, secSubTypeCode);

		}
		catch (SearchDAOException e) {

			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("FinderException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#listMFTemplate
	 */
	public List listMFTemplate() throws PropertyParametersException {
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplate
	 */
	public IMFTemplate getMFTemplate(long mFTemplateID) throws PropertyParametersException {
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateTrxValue
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValue(ITrxContext ctx, long mFTemplateID)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_MF_TEMPLATE);
		OBMFTemplateTrxValue trxValue = new OBMFTemplateTrxValue();
		trxValue.setReferenceID(String.valueOf(mFTemplateID));
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#getMFTemplateTrxValueByTrxID
	 */
	public IMFTemplateTrxValue getMFTemplateTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_MF_TEMPLATE_BY_TRXID);
		OBMFTemplateTrxValue trxValue = new OBMFTemplateTrxValue();
		trxValue.setTransactionID(trxID);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCreateMFTemplate
	 */
	public IMFTemplateTrxValue makerCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException {
		if (value == null) {
			throw new PropertyParametersException("MF Template is null");
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_MF_TEMPLATE);
		if (trxVal == null) {
			trxVal = new OBMFTemplateTrxValue();
		}
		trxVal.setStagingMFTemplate(value);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerUpdateMFTemplate
	 */
	public IMFTemplateTrxValue makerUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal, IMFTemplate value)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_MF_TEMPLATE);
		trxVal.setStagingMFTemplate(value);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCloseCreateMFTemplate
	 */
	public IMFTemplateTrxValue makerCloseCreateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_MF_TEMPLATE);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerCloseUpdateMFTemplate
	 */
	public IMFTemplateTrxValue makerCloseUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_MF_TEMPLATE);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#makerDeleteMFTemplate
	 */
	public IMFTemplateTrxValue makerDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_MF_TEMPLATE);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerApproveUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerApproveUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_MF_TEMPLATE);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerApproveUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerApproveDeleteMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_MF_TEMPLATE);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager#checkerRejectUpdateMFTemplate
	 */
	public IMFTemplateTrxValue checkerRejectUpdateMFTemplate(ITrxContext ctx, IMFTemplateTrxValue trxVal)
			throws PropertyParametersException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_MF_TEMPLATE);
		return (IMFTemplateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		if (trxValue instanceof IMFTemplateTrxValue) {
			IMFTemplateTrxValue colTrx = (IMFTemplateTrxValue) trxValue;
			colTrx.setTransactionType(ICMSConstant.INSTANCE_MF_TEMPLATE);
			colTrx.setTrxContext(ctx);

		}
		else {
			((ICMSTrxValue) trxValue).setTrxContext(ctx);
		}
		return trxValue;
	}

	/**
	 * Helper method to operate transactions.
	 * 
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws PropertyParametersException on errors encountered
	 */
	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws PropertyParametersException {
		if (trxVal == null) {
			throw new PropertyParametersException("ITrxValue is null!");
		}

		try {
			ITrxController controller = null;

			if (trxVal instanceof IMFTemplateTrxValue) {
				controller = (new MFTemplateTrxControllerFactory()).getController(trxVal, param);
			}

			if (controller == null) {
				throw new PropertyParametersException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (PropertyParametersException e) {
			e.printStackTrace();
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new PropertyParametersException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new PropertyParametersException("Exception caught! " + e.toString(), e);
		}
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	private PropertyParametersBusDelegate getBusDelegate() {
		return new PropertyParametersBusDelegate();
	}

	private PropertyParametersDAO getPropertyParaDao() {
		return new PropertyParametersDAO();
	}

	protected void rollback() {
		_context.setRollbackOnly();
	}

}
