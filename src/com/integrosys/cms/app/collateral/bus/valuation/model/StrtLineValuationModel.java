/*
 * Created on Apr 30, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.model;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class StrtLineValuationModel extends GenericValuationModel {

	private boolean isFirstTime;

	private boolean isNewGoodStatus;

	private Double purchasePrice;

    private Date purchaseDate;

	private Double scrapValue;

	private int totalAssetLife;

	private String currencyCode;

	private Amount initValOMV;

    private Date initValOMVDate;

    private Integer manufactureYear;

	private Date initResidualAssetLifeDate;

	private Double initResidualAssetLife;

	private Date depreciableAssetValueDate;

	private Double depreciableAssetValue;

	private double residualAssetLife;

	private Double depreciateRate;

	/**
	 * @return Returns the isFirstTime.
	 */
	public boolean getIsFirstTime() {
		return isFirstTime;
	}

	/**
	 * @param isFirstTime The isFirstTime to set.
	 */
	public void setIsFirstTime(boolean isFirstTime) {
		this.isFirstTime = isFirstTime;
	}

	/**
	 * @return Returns the isNewGoodStatus.
	 */
	public boolean getIsNewGoodStatus() {
		return isNewGoodStatus;
	}

	/**
	 * @param isNewGoodStatus The isNewGoodStatus to set.
	 */
	public void setIsNewGoodStatus(boolean isNewGoodStatus) {
		this.isNewGoodStatus = isNewGoodStatus;
	}

	/**
	 * @return Returns the purchasePrice.
	 */
	public Double getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * @param purchasePrice The purchasePrice to set.
	 */
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

    /**
     * @return Returns the purchaseDate.
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate The purchaseDate to set.
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    /**
	 * @return Returns the scrapValue.
	 */
	public Double getScrapValue() {
		return scrapValue;
	}

	/**
	 * @param scrapValue The scrapValue to set.
	 */
	public void setScrapValue(Double scrapValue) {
		this.scrapValue = scrapValue;
	}

	/**
	 * @return Returns the totalAssetLife.
	 */
	public int getTotalAssetLife() {
		return totalAssetLife;
	}

	/**
	 * @param totalAssetLife The totalAssetLife to set.
	 */
	public void setTotalAssetLife(int totalAssetLife) {
		this.totalAssetLife = totalAssetLife;
	}

	/**
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return Returns the initValOMV.
	 */
	public Amount getInitValOMV() {
		return initValOMV;
	}

	/**
	 * @param initValOMV The initValOMV to set.
	 */
	public void setInitValOMV(Amount initValOMV) {
		this.initValOMV = initValOMV;
	}


    public Date getInitValOMVDate() {
        return initValOMVDate;
    }

    public void setInitValOMVDate(Date initValOMVDate) {
        this.initValOMVDate = initValOMVDate;
    }

    /**
	 * @return Returns the manufactureYear.
	 */
	public Integer getManufactureYear() {
		return manufactureYear;
	}

	/**
	 * @param manufactureYear The manufactureYear to set.
	 */
	public void setManufactureYear(Integer manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	/**
	 * @return Returns the initResidualAssetLife.
	 */
	public Double getInitResidualAssetLife() {
		return initResidualAssetLife;
	}

	/**
	 * @param initResidualAssetLife The initResidualAssetLife to set.
	 */
	public void setInitResidualAssetLife(Double initResidualAssetLife) {
		this.initResidualAssetLife = initResidualAssetLife;
	}

	/**
	 * @return Returns the initResidualAssetLifeDate.
	 */
	public Date getInitResidualAssetLifeDate() {
		return initResidualAssetLifeDate;
	}

	/**
	 * @param initResidualAssetLifeDate The initResidualAssetLifeDate to set.
	 */
	public void setInitResidualAssetLifeDate(Date initResidualAssetLifeDate) {
		this.initResidualAssetLifeDate = initResidualAssetLifeDate;
	}

	/**
	 * @return Returns the depreciableAssetValueDate.
	 */
	public Date getDepreciableAssetValueDate() {
		return depreciableAssetValueDate;
	}

	/**
	 * @param depreciableAssetValueDate The depreciableAssetValueDate to set.
	 */
	public void setDepreciableAssetValueDate(Date depreciableAssetValueDate) {
		this.depreciableAssetValueDate = depreciableAssetValueDate;
	}

	/**
	 * @return Returns the depreciableAssetValue.
	 */
	public Double getDepreciableAssetValue() {
		return depreciableAssetValue;
	}

	/**
	 * @param depreciableAssetValue The depreciableAssetValue to set.
	 */
	public void setDepreciableAssetValue(Double depreciableAssetValue) {
		this.depreciableAssetValue = depreciableAssetValue;
	}

	/**
	 * @return Returns the residualAssetLife.
	 */
	public double getResidualAssetLife() {
		return residualAssetLife;
	}

	/**
	 * @param residualAssetLife The residualAssetLife to set.
	 */
	public void setResidualAssetLife(double residualAssetLife) {
		this.residualAssetLife = residualAssetLife;
	}

	/**
	 * @return Returns the depreciateRate.
	 */
	public Double getDepreciateRate() {
		return depreciateRate;
	}

	/**
	 * @param depreciateRate The depreciateRate to set.
	 */
	public void setDepreciateRate(Double depreciateRate) {
		this.depreciateRate = depreciateRate;
	}

	/**
	 * Calculate and set the residual asset life every time valuation is performed.
     * This is called by StrtLineValuator.checkCompleteForVal()
	 * @return Returns the calculated residual asset life.
	 */
	public double calcResidualAssetLife() {
	    double currAssetLife = (isFirstTime)? totalAssetLife : initResidualAssetLife.doubleValue();
        double residualAssetLife = (currAssetLife - getRemainingYears());

        setResidualAssetLife(residualAssetLife);
        if (isFirstTime) {
			setInitResidualAssetLife(new Double(residualAssetLife));
		}

        return residualAssetLife;
	}

    /**
     * Calculate and return the remaining year every time valuation is
     * performed.
     * @return Returns the calculated remaining year.
     */
    public int getRemainingYears() {
        Calendar currentYear = new GregorianCalendar();
        if (isFirstTime) {
            return currentYear.get(Calendar.YEAR) - manufactureYear.intValue();
        }
        else {
            Calendar firstSysValDate = new GregorianCalendar();
            firstSysValDate.setTime(initResidualAssetLifeDate);
            return currentYear.get(Calendar.YEAR) - firstSysValDate.get(Calendar.YEAR);
        }
    }


	/**
	 * Calculate and set the straight line depreciation value for first time
	 * valuation is performed.
	 * @return Returns the calculated straight line depreciation value.
	 */
	public double calcDepreciationValue() {

        double depreciableAssetVal = ((isNewGoodStatus) ? purchasePrice.doubleValue() : initValOMV.getAmount()) - scrapValue.doubleValue();

        Date startDate = (isNewGoodStatus) ? purchaseDate : initValOMVDate;
        Calendar startCal = new GregorianCalendar();
        startCal.setTime(startDate);
        int residualAsssetLife = totalAssetLife - (startCal.get(Calendar.YEAR) - manufactureYear.intValue());

        double depreciationRate;
        
        if (residualAsssetLife != 0) {
        	depreciationRate = depreciableAssetVal / residualAsssetLife;
        } else {
        	throw new ValuationDetailIncompleteException(" ResidualAssetLife is 0 with values -- totalAssetLife [ "+
        			totalAssetLife+" ], Start Year [ "+startCal.get(Calendar.YEAR) + 
        			" ], Manufacture Year [ "+manufactureYear.intValue() + " ]");
        }

        //set the values
        setDepreciableAssetValue(new Double(depreciableAssetVal));
        setDepreciateRate(new Double(depreciationRate));

        return depreciationRate;

	}

	/**
	 * Calculate and return the year since valuation start every time valuation
	 * is performed.
	 * @return Returns the calculated year since valuation start.
	 */
	public double getYearsSinceValStart() {
		Date start = null;

        if(isFirstTime) {
            start = (isNewGoodStatus) ? purchaseDate : initValOMVDate;
        } else {
            start = initResidualAssetLifeDate;
        }

		if (start != null) {
			Date d = new Date();
			int val = (int) ((d.getTime() - start.getTime()) / (1000.0 * 60 * 60 * 24 * 365));
			return val;
		}
		else {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.valuation.IValuationModel#setDetailFromCollateral
	 * (com.integrosys.cms.app.collateral.bus.ICollateral)
	 */
	public void setDetailFromCollateral(ICollateral col) {
		// super.setDetailFromCollateral(col);
		// this is for online valuation, online valuation is not needed for
		// asset based security type
		// do nothing
	}

}
