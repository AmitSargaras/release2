package com.integrosys.cms.batch.feeds.stock;

import java.util.Date;

public interface IStockPrice {

	public long getTempID();

	public void setTempID(long tempID);

	public String getRecordType();

	public void setRecordType(String recordType);

	public String getStockCode();

	public void setStockCode(String stockCode);

	public String getCounterName();

	public void setCounterName(String counterName);

	public String getExchangeCode();

	public void setExchangeCode(String exchangeCode);

	public Double getUnitPrice();

	public void setUnitPrice(Double unitPrice);

	public String getUnitPriceCurrency();

	public void setUnitPriceCurrency(String unitPriceCurrency);

	public String getIsinCode();

	public void setIsinCode(String isinCode);

	public String getStockExcTraBoardCode();

	public void setStockExcTraBoardCode(String stockExcTraBoardCode);

	public String getStockExcTraBoardDes();

	public void setStockExcTraBoardDes(String stockExcTraBoardDes);

	public String getStockTypes();

	public void setStockTypes(String stockTypes);

	public long getListedSharesQuantity();

	public void setListedSharesQuantity(long listedSharesQuantity);

	public Double getParValue();

	public void setParValue(Double parValue);

	public Double getPaidUpCapital();

	public void setPaidUpCapital(Double paidUpCapital);

	public Date getExpiryDate();

	public void setExpiryDate(Date expiryDate);

	public String getShareStatus();

	public void setShareStatus(String shareStatus);

	public String getStockExcSusCouIndicator();

	public void setStockExcSusCouIndicator(String stockExcSusCouIndicator);

    public Date getDateLaunched();

    public void setDateLaunched(Date dateLaunched);
}
