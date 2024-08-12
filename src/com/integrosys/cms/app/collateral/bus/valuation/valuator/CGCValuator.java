package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.dao.ICGCValuationDAO;
import com.integrosys.cms.app.collateral.bus.valuation.model.CGCValuationModel;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 22, 2008 Time: 11:49:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class CGCValuator implements IValuator {

	private static final String DEFAULT_CURRENCY = "MYR";

	private ICGCValuationDAO cgcValuationDAO;

	public boolean checkCompleteForVal(IValuationModel model, List errorList) {

		CGCValuationModel valModel = (CGCValuationModel) model;
		boolean result = true;

		if (valModel.getGuaranteeAmount() == null) {
			errorList.add("No Guarantee Amount");
			result = false;
		}

		if (valModel.getSecuredPortion() <= ICMSConstant.INT_INVALID_VALUE) {
			errorList.add("No value for secured portion");
			result = false;
		}

		if (valModel.getUnsecuredPortion() <= ICMSConstant.INT_INVALID_VALUE) {
			errorList.add("No value for unsecured portion");
			result = false;
		}

		// either (secured % & secured amt) or (unsecured % & unsecured amt) is
		// required
		// if((valModel.getSecuredPortion() > ICMSConstant.INT_INVALID_VALUE) &&
		// (valModel.getSecuredAmountOrigin() != null)
		// || ((valModel.getUnsecuredPortion() > ICMSConstant.INT_INVALID_VALUE)
		// && (valModel.getUnsecuredAmountOrigin() != null)) ) {
		// return true;
		// }

		return result;
	}

	public void performValuation(IValuationModel model) throws ValuationException {

		CGCValuationModel valModel = (CGCValuationModel) model;

		double totalFacOutstandingAmt = cgcValuationDAO.getTotalFacOutstandingAmt(model); // in
		// MYR
		double totalSecOMVAmt = cgcValuationDAO.getTotalSecOMVAmt(model); // in
		// MYR

		double totalSecuredAmt;
		double totalUnsecuredAmt;

		if (totalSecOMVAmt > totalFacOutstandingAmt) {
			totalSecuredAmt = totalFacOutstandingAmt;
			totalUnsecuredAmt = 0;
		}
		else {
			totalSecuredAmt = totalSecOMVAmt;
			totalUnsecuredAmt = totalFacOutstandingAmt - totalSecOMVAmt;
		}

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> totalFacOutstandingAmt = [" + totalFacOutstandingAmt
				+ "] totalSecOMVAmt = [" + totalSecOMVAmt + "] totalSecuredAmt = [" + totalSecuredAmt
				+ "] totalUnsecuredAmt = [" + totalUnsecuredAmt + "]");

		double cgcSecuredPortionAmt = valModel.getSecuredPortion() / 100.0 * totalSecuredAmt;
		double cgcUnsecuredPortionAmt = valModel.getUnsecuredPortion() / 100.0 * totalUnsecuredAmt;
		double cgcTotalCover = cgcSecuredPortionAmt + cgcUnsecuredPortionAmt;

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> model.getSecuredPortion = [" + valModel.getSecuredPortion()
				+ "] model.getUnsecuredPortion = [" + valModel.getUnsecuredPortion() + "] cgcSecuredPortionAmt = ["
				+ cgcSecuredPortionAmt + "] cgcUnsecuredPortionAmt = [" + cgcUnsecuredPortionAmt
				+ "] cgcTotalCover = [" + cgcTotalCover + "]");

		String guaranteeCurrency = valModel.getGuaranteeAmount().getCurrencyCode();
		valModel.setCalcSecuredAmount(new Amount(cgcSecuredPortionAmt, guaranteeCurrency));
		valModel.setCalcUnsecuredAmount(new Amount(cgcUnsecuredPortionAmt, guaranteeCurrency));
		valModel.setCalcTotalCgcCoverAmount(new Amount(cgcTotalCover, guaranteeCurrency));

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> model.cgcSecuredPortionAmt = ["
				+ valModel.getCalcSecuredAmount() + "] model.cgcUnsecuredPortionAmt = ["
				+ valModel.getCalcUnsecuredAmount() + "] model.cgcTotalCover = ["
				+ valModel.getCalcTotalCgcCoverAmount() + "]");

		Amount cmvAmt = new Amount(cgcTotalCover, DEFAULT_CURRENCY);
		Amount convertedGuaranteeAmt = AmountConversion.getConversionAmount(valModel.getGuaranteeAmount(),
				DEFAULT_CURRENCY);
		if (convertedGuaranteeAmt != null) {
			cmvAmt = (cgcTotalCover <= convertedGuaranteeAmt.getAmount()) ? cmvAmt : valModel.getGuaranteeAmount();
		}

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> cmvAmt = " + cmvAmt);

		valModel.setValuationDate(new Date());
		valModel.setValOMV(cmvAmt);

	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public ICGCValuationDAO getCgcValuationDAO() {
		return cgcValuationDAO;
	}

	public void setCgcValuationDAO(ICGCValuationDAO cgcValuationDAO) {
		this.cgcValuationDAO = cgcValuationDAO;
	}

	public IValuationModel createValuationModelInstance() {
		return new CGCValuationModel();
	}
}
