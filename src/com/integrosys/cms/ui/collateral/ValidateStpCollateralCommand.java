package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.cms.ui.collateral.cash.cashfd.FixedDepositForm;

import com.integrosys.component.commondata.app.CommonDataSingleton;
/**
 * <p>
 * Command to be executed before command to approve collateral workflow is
 * executed.
 * <p>
 * The criteria to check is if for a application type, a collateral having
 * provided collateral subtype must be fired into backend system first (by
 * checking {@link ICollateral#getSCISecurityID()}). So this command is reusable
 * at level of 1 application type, 1 collateral subtype.
 * <p>
 * <b>Note</b> If there are more than 1 collateral match the collateral subtype
 * provided, only the first one will be output to the client.
 * @author Chong Jun Yong
 * 
 */
public class ValidateStpCollateralCommand extends AbstractCommand {

	private String applicationType;

	private String collateralSubTypeId;

	private ILimitProxy limitProxy;

	private boolean hasAccessStpSystem = PropertyManager.getBoolean("has.access.stp.system", true);

	/**
	 * Constructor to indicate that which application type, and which collateral
	 * subtype must be fired into backend system.
	 * @param applicationType loan application type, such as HP, MO, SF, etc.
	 * @param collateralSubTypeId collateral subtype id, such as AB102, PT704,
	 *        etc.
	 */
	public ValidateStpCollateralCommand(String applicationType, String collateralSubTypeId) {
		this.applicationType = applicationType;
		this.collateralSubTypeId = collateralSubTypeId;
	}

	/**
	 * To set limit proxy to be used to retrieve full snapshot of AA if there is
	 * no AA found in global session, this will based on the workflow limit
	 * profile id to retrieve the AA.
	 * @param limitProxy limit proxy service bean
	 */
	public void setLimitProxy(ILimitProxy limitProxy) {
		this.limitProxy = limitProxy;
	}

	/**
	 * To set whether the cms has the access of stp system, such as SIBS. This
	 * command will validate the collateral only if the system has the access.
	 * @param hasAccessStpSystem to indicate whether the CMS has the access of
	 *        stp system.
	 */
	public void setHasAccessStpSystem(boolean hasAccessStpSystem) {
		this.hasAccessStpSystem = hasAccessStpSystem;
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE } };
	}

	/***/
	//added by Anup K.
	//add maker/checker detail for individual Fd
	public void getCheckerFD(HashMap map,OBCashFd fd,ICollateralTrxValue collateralTrxValue){
		OBCommonUser user=(OBCommonUser)map.get(IGlobalConstant.USER);
		String checker_id=user.getLoginID();
		if(null!=fd.getDepositInfo()){
		for(int i=0;i<fd.getDepositInfo().length;i++){
			if("E".equalsIgnoreCase(fd.getDepositInfo()[i].getFlag())){
				fd.getDepositInfo()[i].setChecker_date(collateralTrxValue.getTransactionDate());
				fd.getDepositInfo()[i].setChecker_id(checker_id);
				fd.getDepositInfo()[i].setFlag(null);
			}
		}
		}
		collateralTrxValue.setStagingCollateral(fd);
	}
	/***/
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ICollateralTrxValue collateralTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		if (collateralTrxValue.getStagingCollateral() instanceof OBCashFd) {
		OBCashFd fd = new OBCashFd();
		fd=(OBCashFd) collateralTrxValue.getStagingCollateral();
		getCheckerFD(map,fd,collateralTrxValue);
		
		// update for lien - basel serial no.
		

		int ser1=1,ser=1;
		String max_basel_serial="";
		OBCashFd cashFd=new OBCashFd();
		OBCashDeposit cashDep[];
		ILienMethod lien[];
		// ILienMethod oblien=new OBLien();
		cashFd=(OBCashFd)collateralTrxValue.getStagingCollateral();
		cashDep=(OBCashDeposit[]) cashFd.getDepositInfo();
		// get lien information from FD and set basel serial no.
		for(int i=0;i<cashDep.length;i++){
			OBCashDeposit obcashDep = cashDep[i];
		 max_basel_serial=CollateralDAOFactory.getDAO().getMaxBaselSerialNo(obcashDep);
			lien=cashDep[i].getLien();
			   if(null!=lien){
				  for(int j=0;j<lien.length;j++){
					 // oblien=lien[j];
					 if(null==lien[j].getBaselSerial()||"null".equals(lien[j].getBaselSerial())){
						 lien[j].setBaselSerial(max_basel_serial);
						 ser=ser1+Integer.parseInt(max_basel_serial);
						 max_basel_serial=String.valueOf(ser);
					 }
				  }
			   }cashDep[i].setLien(lien);
		}cashFd.setDepositInfo(cashDep);
		collateralTrxValue.setStagingCollateral(cashFd);
	
		}
		if (this.hasAccessStpSystem) {

			if (limitProfile == null) {
				ITrxContext trxContext = collateralTrxValue.getTrxContext();
				limitProfile = trxContext.getLimitProfile();
				if (limitProfile == null) {
					throw new AccessDeniedException(
							"not able to validate first collateral as there is no limit profile");
				}
				try {
					if (this.limitProxy == null) {
						this.limitProxy = LimitProxyFactory.getProxy();
					}
					//TODO: Date:- 13-08-2011 Changed By sachin patil
					/*
					 * limitProfile.getLimitProfileID() can return LONG_INVALID_VALUE = -999999999 on websphere server on testing Mode
					 * */
					//limitProfile = this.limitProxy.getLimitProfile(limitProfile.getLimitProfileID());
					if(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE!=limitProfile.getLimitProfileID())
					{
						limitProfile = this.limitProxy.getLimitProfile(limitProfile.getLimitProfileID());
				    }
				}
				catch (LimitException ex) {
					throw new CommandProcessingException("failed to retrieve limit profile, by id ["
							+ limitProfile.getLimitProfileID() + "]", ex);
				}
			}

			// to get the first collateral match the criteria, the rest will be
			// ignored.
			boolean isErrorFound = false;
			if (this.applicationType.equals(limitProfile.getApplicationType())
					&& !this.collateralSubTypeId.equals(collateralTrxValue.getStagingCollateral()
							.getCollateralSubType().getSubTypeCode())) {
				for (int i = 0; i < limitProfile.getLimits().length && !isErrorFound; i++) {
					ILimit limit = limitProfile.getLimits()[i];
					if (limit.getCollateralAllocations() != null) {
						for (int j = 0; j < limit.getCollateralAllocations().length && !isErrorFound; j++) {
							ICollateralAllocation limitSecMap = limit.getCollateralAllocations()[j];
							
							
							//Not required for HDFC	
							/*if (this.collateralSubTypeId.equals(limitSecMap.getCollateral().getCollateralSubType()
									.getSubTypeCode())
									&& StringUtils.isBlank(limitSecMap.getCollateral().getSCISecurityID())) {
								exceptionMap.put("pendingPerfectError", new ActionMessage(
										"error.collateral.application.type.first.collateral", new Object[] {
												this.applicationType,
												CommonDataSingleton.getCodeCategoryLabelByValue(
														CategoryCodeConstant.COMMON_CODE_SECURITY_SUB_TYPE,
														this.collateralSubTypeId),
												String.valueOf(limitSecMap.getCollateral().getCollateralID()) }));
								isErrorFound = true;
							}*/
						}
					}
				}
			}

			if (!exceptionMap.isEmpty()) {
				exceptionMap.put(STOP_COMMAND_CHAIN, new ActionMessage("stop"));
			}

		}
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		returnMap.put(COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}