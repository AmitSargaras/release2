package com.integrosys.cms.ui.stockdetailsupload;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBStockDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.limit.bus.IChargeDetailJdbc;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.stockdetailsupload.proxy.IStockdetailsUploadProxyManager;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveStockdetailsUploadCommand extends AbstractCommand implements ICommonEventConstant, IStockDetailsUploadConstants {

	private IStockFeedProxy stockFeedProxy = (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	private IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
	
	private IStockdetailsUploadProxyManager stockdetailsuploadProxy;
	
	public IStockdetailsUploadProxyManager getStockdetailsuploadProxy() {
		return stockdetailsuploadProxy;
	}

	public void setStockdetailsuploadProxy(IStockdetailsUploadProxyManager stockdetailsuploadProxy) {
		this.stockdetailsuploadProxy = stockdetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
			{ SESSION_TRX_OBJ, OBTrxContext.class.getName(), FORM_SCOPE },
			{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ IGlobalConstant.USER, ICommonUser.class.getName(), GLOBAL_SCOPE },
			{ Constants.GLOBAL_LOCALE_KEY, Locale.class.getName(), GLOBAL_SCOPE }
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ TOTAL, String.class.getName(), REQUEST_SCOPE },
			{ PASS, String.class.getName(), REQUEST_SCOPE },
			{ FAIL, String.class.getName(), REQUEST_SCOPE }
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get(SESSION_TRX_VALUE_OUT);
		
		try {
			Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
			OBTrxContext ctx = (OBTrxContext) map.get(SESSION_TRX_OBJ);
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			List<OBStockDetailsFile> totalUploadedList = (List<OBStockDetailsFile>) map.get(SESSION_TOTAL_UPLOADED_LIST);
			
			IFileUpload stgFile = trxValueIn.getStagingfileUpload();
			stgFile.setApproveBy(user.getEmployeeID());
			ctx.setCustomer(null);
			ctx.setLimitProfile(null);
		
			IFileUploadTrxValue trxValueOut = getStockdetailsuploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
			DefaultLogger.info(this, "User " +user.getEmployeeID()+" approved file upload record ["+ trxValueOut.getReferenceID() +"]");
			if(trxValueOut != null) {
				for(OBStockDetailsFile file : totalUploadedList) {
					file.setFileId(Long.valueOf(trxValueOut.getReferenceID()));
				}
				
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBStockDetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? 
							totalUploadedList.size(): j + batchSize);
					fileUploadJdbc.createEntireStockDetailsFile(batchList, true);
				}
				
				long countPass = 0;
				long countFail = 0;
				DefaultLogger.info(this, "Inserting script details for all successfully uploaded records");
				for(OBStockDetailsFile file : totalUploadedList) {
					if (ICMSConstant.PASS.equals(file.getStatus()) && ICMSConstant.YES.equals(file.getUploadStatus())) {
						countPass++;
						IMarketableEquity stageOb = new OBMarketableEquity();
						
						String equityID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_MARKETABLE_EQUITY, true);
						stageOb.setEquityID(Long.valueOf(equityID));
						stageOb.setRefID(stageOb.getEquityID());
						stageOb.setStatus(ICMSConstant.ACTIVE);
						
						if(StringUtils.isNotBlank(file.getNameOfStockExchange())) {
							stageOb.setStockExchange(getStockExchangeCode(file.getNameOfStockExchange()));
						}
						
						if(StringUtils.isNotBlank(file.getScriptCode())) {
							stageOb.setIsinCode(file.getScriptCode());
							
							if(StringUtils.isNotBlank(file.getNameOfStockExchange()) && StringUtils.isNotBlank(file.getScriptCode())) {
								IStockFeedEntry stockFeedEntry = stockFeedProxy.getStockFeedEntryStockExc(getStockExchangeCode(file.getNameOfStockExchange()),file.getScriptCode());
								if(stockFeedEntry != null) {
									stageOb.setUnitPrice(CurrencyManager.convertToAmount(locale, BaseCurrency.getCurrencyCode().getCode(),
											Double.toString(stockFeedEntry.getScriptValue())));
								}
							}
						}
						
						if(StringUtils.isNotBlank(file.getNoOfUnits())) {
							stageOb.setNoOfUnits(Double.valueOf(file.getNoOfUnits()));
						}
						
						if(StringUtils.isNotBlank(file.getIssuerIdType())) {
							stageOb.setIssuerIdType(getIssuerIdTypeCode(file.getIssuerIdType()));
						}
						
						if(StringUtils.isNotBlank(file.getNominalValue())) {
							stageOb.setNominalValue(CurrencyManager.convertToAmount(locale, BaseCurrency.getCurrencyCode().getCode(), file.getNominalValue()));
						}
						
						if(StringUtils.isNotBlank(file.getCertificateNo())) {
							stageOb.setCertificateNo(file.getCertificateNo());
						}
						
						if(StringUtils.isNotBlank(file.getIssuerName())) {
							stageOb.setIssuerName(file.getIssuerName());
						}
						
						int result = fileUploadJdbc.createStockDetails(stageOb, Long.valueOf(file.getSourceSecurityId()), false);
						
						if(result > 0) {
							IMarketableEquity actualOb = new OBMarketableEquity();
							AccessorUtil.copyValue(stageOb, actualOb, new String[] {"getEquityID"});
							
							equityID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_MARKETABLE_EQUITY, true);
							actualOb.setEquityID(Long.valueOf(equityID));
							
							fileUploadJdbc.createStockDetails(actualOb, Long.valueOf(file.getSourceSecurityId()), true);
						}
					}
				}
				
				DefaultLogger.info(this, "Data successfully inserted for script details");
				
				countFail = totalUploadedList.size() - countPass;
				
				DefaultLogger.info(this, "Total record successfully uploaded : " + countPass + ", total record failed: " + countFail);
				
				resultMap.put(SESSION_TOTAL_UPLOADED_LIST, totalUploadedList);
				resultMap.put(TOTAL, String.valueOf(totalUploadedList.size()));
				resultMap.put(PASS, String.valueOf(countPass));
				resultMap.put(FAIL, String.valueOf(countFail));
				resultMap.put(SESSION_TRX_VALUE_OUT, trxValueOut);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Exception occurred while processing and approving the file", e);
			throw new CommandProcessingException("Exception occurred while processing and approving the file", e);
		}
		
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private String getIssuerIdTypeCode(String issuerIdType) {
		if("1".equals(issuerIdType))
			return ISSUER_PROMOTERS;
		else if("2".equals(issuerIdType)) 
			return ISSUER_GROUP_COMPANY;
		else if("3".equals(issuerIdType))
			return ISSUER_OTHERS;
		return null;
	}

	private String getStockExchangeCode(String nameOfStockExchange) {
		if(STOCK_EXCHANGE_BSE.equals(nameOfStockExchange))
			return "001";
		else if(STOCK_EXCHANGE_NSE.equals(nameOfStockExchange))
			return "002";
		else if(STOCK_EXCHANGE_OTHERS.equals(nameOfStockExchange))
			return "003";
		return null;
	}
	
}
