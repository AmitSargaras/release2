package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.app.common.util.MapperUtil.bigDecimalToString;
import static com.integrosys.cms.app.common.util.MapperUtil.doubleToString;
import static com.integrosys.cms.app.common.util.MapperUtil.stringToBigDecimal;
import static com.integrosys.cms.app.common.util.MapperUtil.stringToDouble;
import static com.integrosys.cms.ui.collateral.CollateralConstant.IS_LEAD_BANK_STOCK_BANKING;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.OBLeadBankStock;

public class LeadBankStockMapper extends AbstractCommonMapper {

	@Override
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException
    {
		LeadBankStockForm form = (LeadBankStockForm) cForm;
		
		OBLeadBankStock obj = new OBLeadBankStock();

		obj.setDrawingPowerAsPerLeadBank(stringToBigDecimal(form.getDrawingPowerAsPerLeadBank().trim()));
		obj.setBankSharePercentage(stringToDouble(form.getBankSharePercentage().trim()));
		obj.setStockAmount(stringToBigDecimal(form.getStockAmount().trim()));
		obj.setDebtorsAmount(stringToBigDecimal(form.getDebtorAmount().trim()));
		obj.setCreditorsAmount(stringToBigDecimal(form.getCreditorsAmount().trim()));
		obj.setMarginOnStock(stringToBigDecimal(form.getMarginOnStock().trim()));
		obj.setMarginOnDebtors(stringToBigDecimal(form.getMarginOnDebtor().trim()));
		obj.setMarginOnCreditors(stringToBigDecimal(form.getMarginOnCreditors().trim()));
		
		return obj;
    }
	
	@Override
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException{
		LeadBankStockForm form = (LeadBankStockForm) cForm;
		OBLeadBankStock stock = (OBLeadBankStock) obj;
		
		String isLeadBankStockBankingArr = (String) map.get(IS_LEAD_BANK_STOCK_BANKING);
		
		form.setDrawingPowerAsPerLeadBank(bigDecimalToString(stock.getDrawingPowerAsPerLeadBank()));
		form.setBankSharePercentage(doubleToString(stock.getBankSharePercentage()));
		form.setStockAmount(bigDecimalToString(stock.getStockAmount()));
		form.setDebtorAmount(bigDecimalToString(stock.getDebtorsAmount()));
		form.setCreditorsAmount(bigDecimalToString(stock.getCreditorsAmount()));
		form.setMarginOnStock(bigDecimalToString(stock.getMarginOnStock()));
		form.setMarginOnDebtor(bigDecimalToString(stock.getMarginOnDebtors()));
		form.setMarginOnCreditors(bigDecimalToString(stock.getMarginOnCreditors()));
		form.setIsLeadBankStockBankingArr(isLeadBankStockBankingArr);
		
    	return form;
    }
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ IS_LEAD_BANK_STOCK_BANKING, String.class.getName(), SERVICE_SCOPE },
		});
	}
}
