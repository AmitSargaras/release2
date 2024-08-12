package com.integrosys.cms.app.collateral.bus.valuation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralDetailFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.model.GenericValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationFrequency;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class ValuationHandler implements IValuationHandler {

	private Map valuationDaoMap;

	private Map valuatorMap;

	private IValuationDAO genericValuationDao;

	/**
	 * @return the valuationDaoMap
	 */
	public Map getValuationDaoMap() {
		return valuationDaoMap;
	}

	/**
	 * @return the valuatorMap
	 */
	public Map getValuatorMap() {
		return valuatorMap;
	}

	/**
	 * @param valuationDaoMap the valuationDaoMap to set
	 */
	public void setValuationDaoMap(Map valuationDaoMap) {
		this.valuationDaoMap = valuationDaoMap;
	}

	/**
	 * @param valuatorMap the valuatorMap to set
	 */
	public void setValuatorMap(Map valuatorMap) {
		this.valuatorMap = valuatorMap;
	}

	public void setGenericValuationDao(IValuationDAO genericValuationDao) {
		this.genericValuationDao = genericValuationDao;
	}

	public IValuationDAO getGenericValuationDao() {
		return genericValuationDao;
	}

	public IValuationModel performOnlineValuation(ICollateral col) throws ValuationException {

		String subtypeCode = "unknown";
		if (col.getCollateralSubType() != null) {
			subtypeCode = col.getCollateralSubType().getSubTypeCode();
		}

		if (!CollateralDetailFactory.isCollateralOnlineValuationRequired(col)) {
			throw new ValuationNotRequiredException("collateral, id [" + col.getCollateralID() + "] host id ["
					+ col.getSCISecurityID() + "] not required to be valuated", subtypeCode);
		}

		try {
			IValuationDAO valDao = (IValuationDAO) getValuationDaoMap().get(subtypeCode);
			IValuator valuator = (IValuator) getValuatorMap().get(subtypeCode);

			IValuationModel valModel = valuator.createValuationModelInstance();
			valModel.setDetailFromCollateral(col);

			List errorList = new ArrayList();
			valDao.retrieveValuationInfoForCal(valModel);

			if (!valDao.retrieveValuationParams(valModel, errorList)) {
				throw new ValuationDetailIncompleteException(errorList);
			}

			if (!valuator.checkCompleteForVal(valModel, errorList)) {
				throw new ValuationDetailIncompleteException(errorList);
			}

			valuator.performValuation(valModel);
			valuator.saveOnlineValuationInfoInCollateral(col, valModel);
			return valModel;

		}
		catch (ValuationException e) {
			throw e;

		}
		catch (RuntimeException e) {
			throw new ValuationSystemException("Runtime Exception Encountered!", e);
		}
	}

	public IValuationModel performSystemValuation(IValuationModel colInfo) throws ValuationException {

		try {
			DefaultLogger.debug(this, "=============== In ValuationHandler.performSystemValuation Collateral ID:["
					+ colInfo.getCollateralId() + "] sec id: [" + colInfo.getSecId() + "] SUBTYPE: "
					+ colInfo.getSecSubtype());

			IValuationDAO valDao = (IValuationDAO) getValuationDaoMap().get(colInfo.getSecSubtype());
			IValuator valuator = (IValuator) getValuatorMap().get(colInfo.getSecSubtype());

			IValuationModel valModel = valuator.createValuationModelInstance();

			copySecurityInfo(colInfo, valModel);
			valModel.setValuationType(ICMSConstant.VALUATION_SOURCE_TYPE_A);

			List errorList = new ArrayList();
			// ========== No longer need to recalculate the next remargin date;
			// its already saved in the db =======//
			// if (!valDao.retrieveValuationDate(valModel, errorList)) {
			// if (checkPrevValDateRequired(valModel)) { //no longer have this
			// req thus will always return false
			// throw new ValuationDetailIncompleteException(errorList);
			// }
			// }
			// else if (!checkValuationDateMatch(valModel)) {
			// errorList.add("VALUATION: Valuation date not match");
			// throw new ValuationDetailIncompleteException(errorList);
			// }

			valDao.retrieveValuationInfoForCal(valModel);

			if (!valuator.checkCompleteForVal(valModel, errorList)) {
				throw new ValuationDetailIncompleteException(errorList);
			}

			valuator.performValuation(valModel);

			// valModel.setNextValuationDate(ValuationUtil.getNextValuationDate(valModel.getValuationDate(),
			// valModel
			// .getValuationFrequency()));
			valDao.persistValuation(valModel);

			IValuationModel latestValuation = calculateSecCmvFsv(valModel);

			// update the latestValuation with the latest valuation margin (only
			// available in the original valModel)
			latestValuation.setValuationMargin(valModel.getValuationMargin());

			// check for null - this is for the scenario where the latest
			// valuation was not the one just calculated
			// and that the date is null - which should have been prevented
			// instead.
			Date lastestValuationDate = latestValuation.getValuationDate();
			latestValuation.setNextValuationDate(ValuationUtil.getNextValuationDate(
					(lastestValuationDate == null) ? new Date() : lastestValuationDate, valModel
							.getValuationFrequency()));
			// val freq should get the current one from valModel

			valDao.persistFsvCmv(latestValuation);

			valDao.persistOtherInfo(valModel);

			DefaultLogger.debug(this, "****END VALUATION - OMV=[" + latestValuation.getValOMV() + "FSV="
					+ latestValuation.getValFSV() + "] ****");

			return valModel;

		}
		catch (ValuationException e) {
			throw e;

		}
		catch (RuntimeException e) {
			throw new ValuationSystemException("Runtime Exception Encountered!", e);
		}
	}

	// method that will be used in manaul valuation, system valuation or los
	// valuation coming in
	// it will try to update the security's fsv and cmv based on the new
	// infromation
	// for manual input valuation, it will always update the security ,
	// for los valuation, update security's fsv and cmv only if the latest
	// manual input valuation has expired or there is no prev manual valuation,
	// for sys valuation, update security's fsv and cmv only if the latest
	// manual input and los valuation has both expired or there is no prev
	// manul/los valuation

	public IValuationModel calculateSecCmvFsv(ICollateral col, Amount omv, Amount fsv, String valuationType,
			Date valuationDate, String valuer) throws ValuationException {
		IValuationModel latestValuation = null;
		try {
			IValuationModel valModel = new GenericValuationModel();
			valModel.setSecurityCountry(col.getCollateralLocation());
			valModel.setSecSubtype(col.getSCISubTypeValue());
			List errorList = new ArrayList();
			getGenericValuationDao().retrieveValuationParams(valModel, errorList);
			if (errorList.size() > 0) {
				throw new ValuationDetailIncompleteException(errorList);
			}
			else {
				if (col.getCollateralID() != ICMSConstant.LONG_INVALID_VALUE) {
					valModel.setCollateralId(col.getCollateralID());
					valModel.setValuationType(valuationType);
					valModel.setValuationDate(valuationDate);
					valModel.setValuer(valuer);
					valModel.setValOMV(omv);
					valModel.setValFSV(fsv);
				}
				latestValuation = calculateSecCmvFsv(valModel);
			}
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "error calculating security cmv and fsv for collateral [" + col + "], ignored.",
					ex);
		}

		return latestValuation;
	}

	public IValuationModel calculateSecCmvFsv(IValuationModel currentValuation) throws ValuationException {
		Map prevValToRef = new HashMap();
		long colId = currentValuation.getCollateralId();
		String sourceType = currentValuation.getValuationType();

		if (colId != ICMSConstant.LONG_INVALID_VALUE) {
			prevValToRef = getGenericValuationDao().retrievePrevValuationInfo(colId, sourceType);
		}
		// override the prev valuation of the same type
		prevValToRef.put(sourceType, currentValuation);
		ValuationFrequency freq = currentValuation.getValuationFrequency();
		return ValuationUtil.getLatestValuationModel(prevValToRef, freq);

	}

	private void copySecurityInfo(IValuationModel source, IValuationModel dest) {
		dest.setCollateralId(source.getCollateralId());
		dest.setSecType(source.getSecType());
		dest.setSecSubtype(source.getSecSubtype());
		dest.setSecurityCountry(source.getSecurityCountry());
		dest.setSecCurrency(source.getSecCurrency());
		dest.setValuationMargin(source.getValuationMargin());
		dest.setValuationFrequency(source.getValuationFrequency());
	}

}
