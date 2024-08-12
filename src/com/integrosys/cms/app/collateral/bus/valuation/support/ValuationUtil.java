/*
 * Created on Apr 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class ValuationUtil {

	public static final String VAL_MECHANISM_MARGIN = "margin";

	public static final String VAL_MECHANISM_HAIRCUT = "haircut";

	private static final String VALUATION_METHOD = PropertyManager.getValue("valuation.mechanism");

    private final static Logger logger = LoggerFactory.getLogger(ValuationUtil.class);

    public static Date getNextValuationDate(Date curValDate, ValuationFrequency freq) {
		if (freq == null) {
			return curValDate;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(curValDate);
		if (ValuationFrequency.FREQ_DAY.equals(freq.getFreqUnit())) {
			cal.add(Calendar.DATE, freq.getFreq());
		}
		else if (ValuationFrequency.FREQ_WEEK.equals(freq.getFreqUnit())) {
			cal.add(Calendar.DATE, freq.getFreq() * 7);
		}
		else if (ValuationFrequency.FREQ_MONTH.equals(freq.getFreqUnit())) {
			cal.add(Calendar.MONTH, freq.getFreq());
		}
		else if (ValuationFrequency.FREQ_YEAR.equals(freq.getFreqUnit())) {
			cal.add(Calendar.YEAR, freq.getFreq());
		}

		return cal.getTime();
	}

	public static int[] getYMDOfDate(Date d) {
		int[] result = new int[3];
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		result[0] = cal.get(Calendar.YEAR);
		result[1] = cal.get(Calendar.MONTH);
		result[2] = cal.get(Calendar.DAY_OF_MONTH);
		return result;
	}

	public static double convertAreaToSqrtMeter(double area, String unit) {
		if (LandAreaMeasure.UOM_ACRES.equals(unit)) {
			return area / 4046.8564;
		}
		else if (LandAreaMeasure.UOM_HQT.equals(unit)) {
			return area / 10000;
		}
		else if (LandAreaMeasure.UOM_SQFT.equals(unit)) {
			return area / 0.09290;
		}
		else if (LandAreaMeasure.UOM_SQM.equals(unit)) {
			return area;
		}
		else if (LandAreaMeasure.UOM_SQY.equals(unit)) {
			return area / 0.8361;
		}
		return area;
	}

	public static double convertTimeToYear(String val, String uom) {
		if ((val != null) && (uom != null)) {
			double valDbl = Double.parseDouble(val);
			if (ICMSConstant.FREQ_UNIT_DAYS.equals(uom)) {
				return valDbl / 365;
			}
			else if (ICMSConstant.FREQ_UNIT_WEEKS.equals(uom)) {
				return valDbl * 7 / 365;
			}
			else if (ICMSConstant.FREQ_UNIT_MONTHS.equals(uom)) {
				return valDbl / 12;
			}
			else if (ICMSConstant.FREQ_UNIT_YEARS.equals(uom)) {
				return valDbl;
			}

		}
		return -1;
	}

	public static boolean checkDateExpired(Date nextValDate) {
		if (nextValDate == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(nextValDate);
		int nextValYear = cal.get(Calendar.YEAR);
		int nextValMonth = cal.get(Calendar.MONTH);
		int nextValDay = cal.get(Calendar.DAY_OF_MONTH);

		Date curDate = new Date();
		cal.setTime(curDate);
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = cal.get(Calendar.MONTH);
		int curDay = cal.get(Calendar.DAY_OF_MONTH);
		if ((nextValYear == curYear) && (nextValMonth == curMonth) && (nextValDay <= curDay)) {
			return true;
		}
		else if ((nextValYear == curYear) && (nextValMonth < curMonth)) {
			return true;
		}
		else if (nextValYear < curYear) {
			return true;
		}
		else {
			return false;
		}
	}

	public static IValuationModel getLatestValuationModel(Map valuationModelMap, ValuationFrequency freq) {

		IValuationModel latestManual = (IValuationModel) (valuationModelMap.get(ICMSConstant.VALUATION_SOURCE_TYPE_M));
		IValuationModel latestLos = (IValuationModel) (valuationModelMap.get(ICMSConstant.VALUATION_SOURCE_TYPE_S));
		IValuationModel latestSys = (IValuationModel) (valuationModelMap.get(ICMSConstant.VALUATION_SOURCE_TYPE_A));

		// when both manual and los valuation are not available or are both expired --> use system valuation
        if(isValuationNotAvailableOrExpired(latestManual, freq) && isValuationNotAvailableOrExpired(latestLos, freq)) {
            return latestSys;
        }

        if (latestManual == null && latestLos != null) {
			return latestLos;
		}

		if (latestLos == null && latestManual != null) {
			return latestManual;
		}

        //clear time not necessary since underlying is actual java.sql.Date which has no time component
        //Date latestManualValDate = DateUtil.clearTime(latestManual.getValuationDate());
		//Date latestLosValDate = DateUtil.clearTime(latestLos.getValuationDate());
		//int dateComparisonResult = latestManualValDate.compareTo(latestLosValDate);
//        logger.debug("latestManual.getValuationDate()=" + latestManual.getValuationDate()
//                   + "latestLos.getValuationDate()=" + latestLos.getValuationDate());
        int dateComparisonResult = latestManual.getValuationDate().compareTo(latestLos.getValuationDate());

        return (dateComparisonResult < 0) ? latestLos : latestManual;
	}

    private static boolean isValuationNotAvailableOrExpired(IValuationModel model, ValuationFrequency freq) {
        if(model == null || model.getValuationDate() == null) {
            return true;
        }

        return checkValuationExpired(model, freq);
    }


    private static boolean checkValuationExpired(IValuationModel model, ValuationFrequency freq) {
		Date nextValDate = ValuationUtil.getNextValuationDate(model.getValuationDate(), freq);
		return ValuationUtil.checkDateExpired(nextValDate);
	}

	/**
	 * Determine the margin value. If method use is haircut, need to apply (100
	 * - x) to get the margin. If no margin/haircut is define, margin is
	 * defaulted to 100% (equivalent to haircut is 0%). In such cases, fsv will
	 * always = cmv
	 * @param valuationModel - valuation model to set the margin in
	 * @param threshold - the value to determine if its margin or haircut
	 */
	public static void determineMargin(IValuationModel valuationModel, double threshold) {
		valuationModel.setValuationMargin(determineMargin(threshold));
	}

	public static double determineMargin(double threshold) {
		double margin = 100;
		if ((VAL_MECHANISM_MARGIN.equals(VALUATION_METHOD))) {
			margin = (threshold == ICMSConstant.DOUBLE_INVALID_VALUE) ? 100 : threshold;
		}
		else { // method is haircut
			margin = (threshold == ICMSConstant.DOUBLE_INVALID_VALUE) ? 100 : (100 - threshold);
		}
		return margin;
	}

}
