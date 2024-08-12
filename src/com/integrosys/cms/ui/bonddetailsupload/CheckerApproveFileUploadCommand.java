package com.integrosys.cms.ui.bonddetailsupload;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBondDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.bonddetailsupload.proxy.IBondDetailsUploadProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveFileUploadCommand extends AbstractCommand implements IBondDetailsUploadConstants{

	private IBondDetailsUploadProxyManager bondDetailsuploadProxy;
	
	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");
	
	public IBondDetailsUploadProxyManager getBondDetailsuploadProxy() {
		return bondDetailsuploadProxy;
	}

	public void setBondDetailsuploadProxy(IBondDetailsUploadProxyManager bondDetailsuploadProxy) {
		this.bondDetailsuploadProxy = bondDetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][]{
				{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
				{ SESSION_TRX_OBJ, OBTrxContext.class.getName(), FORM_SCOPE },
				{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(), SERVICE_SCOPE },
				{ IGlobalConstant.USER, ICommonUser.class.getName(), GLOBAL_SCOPE },
				{ Constants.GLOBAL_LOCALE_KEY, Locale.class.getName(), GLOBAL_SCOPE }
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][]{
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
				{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(), SERVICE_SCOPE }
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
			List<OBBondDetailsFile> totalUploadedList = (List<OBBondDetailsFile>) map.get(SESSION_TOTAL_UPLOADED_LIST);
			
			IFileUploadJdbc jdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			
			IFileUpload stgFile = trxValueIn.getStagingfileUpload();
			stgFile.setApproveBy(user.getEmployeeID());
			ctx.setCustomer(null); 
			ctx.setLimitProfile(null);
		
			IFileUploadTrxValue trxValueOut = getBondDetailsuploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
			DefaultLogger.info(this, "User " +user.getEmployeeID()+" approved file upload record ["+ trxValueOut.getReferenceID() +"]");
			if(trxValueOut != null) {
				for(OBBondDetailsFile file : totalUploadedList) {
					file.setFileId(Long.valueOf(trxValueOut.getReferenceID()));
				}
				
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBBondDetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? 
							totalUploadedList.size(): j + batchSize);
					jdbc.createEntireBondDetailsFile(batchList, true);
				}
				
				long countPass = 0;
				long countFail = 0;
				DefaultLogger.info(this, "Inserting Bond details for all successfully uploaded records into database");
				for(OBBondDetailsFile file : totalUploadedList) {
					if (ICMSConstant.PASS.equals(file.getStatus()) && ICMSConstant.YES.equals(file.getUploadStatus())) {
						countPass++;
						IMarketableEquity stageOb = new OBMarketableEquity();
						
						String equityID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_MARKETABLE_EQUITY, true);
						stageOb.setEquityID(Long.valueOf(equityID));
						stageOb.setRefID(stageOb.getEquityID());
						
						if(!AbstractCommonMapper.isEmptyOrNull(file.getBondCode())) {
							stageOb.setStockCode(file.getBondCode());
							IBondFeedEntry feed = bondFeedProxy.getBondFeedEntry(file.getBondCode()); 
							stageOb.setUnitPrice(CurrencyManager.convertToAmount(locale, BaseCurrency.getCurrencyCode().getCode(),
									Double.toString(feed.getUnitPrice())));
							stageOb.setStatus(ICMSConstant.ACTIVE);
						}
						if(!AbstractCommonMapper.isEmptyOrNull(file.getNoOfUnits())) {
							stageOb.setNoOfUnits(Double.valueOf(file.getNoOfUnits()));
						}
						if(!AbstractCommonMapper.isEmptyOrNull(file.getInterest())) {
							stageOb.setBondRating(file.getInterest());
						}
						
						int result = jdbc.createBondDetail(stageOb, Long.valueOf(file.getSourceSecurityId()), false);
						
						if(result > 0) {
							IMarketableEquity actualOb = new OBMarketableEquity();
							AccessorUtil.copyValue(stageOb, actualOb, new String[] {"getEquityID"});
							
							equityID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_MARKETABLE_EQUITY, true);
							actualOb.setEquityID(Long.valueOf(equityID));
							
							jdbc.createBondDetail(actualOb, Long.valueOf(file.getSourceSecurityId()), true);
						}
					}
				}
				
				DefaultLogger.info(this, "Insertion of Bond details for all successfully uploaded records completed");
				
				countFail = totalUploadedList.size() - countPass;
				
				DefaultLogger.info(this, "Total record successfully uploaded : " + countPass + ", total record failed: " + countFail);
				
				resultMap.put(SESSION_TOTAL_UPLOADED_LIST, totalUploadedList);
				resultMap.put("total", String.valueOf(totalUploadedList.size()));
				resultMap.put("correct", String.valueOf(countPass));
				resultMap.put("fail", String.valueOf(countFail));
				resultMap.put(SESSION_TRX_VALUE_OUT, trxValueOut);
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while approving the transaction ["+ trxValueIn.getStagingReferenceID() +"]");
			throw new CommandProcessingException("Error occured while approving the transaction", e);
		} catch (TrxParameterException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while approving the transaction ["+ trxValueIn.getStagingReferenceID() +"]");
			throw new CommandProcessingException("Error occured while approving the transaction", e);
		} catch (TransactionException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while approving the transaction ["+ trxValueIn.getStagingReferenceID() +"]");
			throw new CommandProcessingException("Error occured while approving the transaction", e);
		} catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while storing file detail in db");
			throw new CommandProcessingException("Error occured while storing file detail in db", e);
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Exception occurred while processing and approving the file");
			throw new CommandProcessingException("Exception occurred while processing and approving the file", e);
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
}
