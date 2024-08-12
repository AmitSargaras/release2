/*
 * Created on Feb 14, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.proxy;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MILmtProxyHelper {
	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	public ILimitTrxValue prepareTrxLimit(ILimitTrxValue trxValue) throws LimitException {
		HashMap hmap = new HashMap();

		ILimit actual = trxValue.getLimit();
		ILimit stage = trxValue.getStagingLimit();
		DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareTrxLimit function 41 ");
		if (null != actual) {
			//actual = prepareLimit(actual, hmap);
			actual = prepareLimitForStage(actual);
		}
		DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareTrxLimit function 46 ");
		if (null != stage) {
			//stage = prepareLimit(stage, hmap);
			stage = prepareLimitForStage(stage);
			DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareTrxLimit function 50 ");
			stage.setLimitSysXRefs(stage.getLimitSysXRefs());  //added by Shiv
		}
		DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareTrxLimit function 53 ");
		trxValue.setLimit(actual);
		trxValue.setStagingLimit(stage);

		return trxValue;
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	
	private ILimit prepareLimitForStage(ILimit limit) throws LimitException {
		try {
			DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareLimitForStage function 65 ");
			StringBuffer sb = new StringBuffer();
			ICollateralAllocation[] allocList = limit.getCollateralAllocations();
			if (null == allocList) {
				return limit;
			}
			int count = 0;
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
			
			for (int i = 0; i < allocList.length; i++) {
				ICollateralAllocation alloc = allocList[i];
				ICollateral col = alloc.getCollateral();
				if (null != col) {
					long collateralID = col.getCollateralID();
					DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareLimitForStage function 77 collateralID: "+collateralID);
					sb.append(collateralID+",");
					count++;
					if(count == 900) {
						DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareLimitForStage function 83 inside if for 900 count. => Count=>"+count);
						sb.replace(sb.length()-1, sb.length(), "");
						allocList=collateralDAO.retrieveCollateralList(sb, allocList);
						sb = new StringBuffer();
						count = 0;
					}
				}
			}
			
			System.out.println("********** IN MILmtProxyHelper of prepareLimitForStage line 93 outside if before replace sb :"+sb+" ***** sb.length :"+sb.length());
			 if(sb.length()>0) {
			  sb.replace(sb.length()-1, sb.length(), "");
			  System.out.println("********** IN MILmtProxyHelper of prepareLimitForStage line 97 outside if after replace sb : "+sb+" ***** sb.length :"+sb.length());	
			  DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareLimitForStage function 84 ");
			  allocList=collateralDAO.retrieveCollateralList(sb, allocList);
			  }
			  
			DefaultLogger.debug(this, "********** IN MILmtProxyHelper of prepareLimitForStage function 86 ");
			limit.setCollateralAllocations(allocList);
			return limit;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception!", e);
		}
	}
	
	/*private ILimit prepareLimit(ILimit limit, HashMap hmap) throws LimitException {
		try {
			ICollateralAllocation[] allocList = limit.getCollateralAllocations();
			if (null == allocList) {
				return limit;
			}

			ICollateralProxy proxy = CollateralProxyFactory.getProxy();

			for (int i = 0; i < allocList.length; i++) {
				ICollateralAllocation alloc = allocList[i];
				ICollateral col = alloc.getCollateral();
				if (null != col) {
					long collateralID = col.getCollateralID();
					// get from hashmap first
					ICollateral temp = (ICollateral) hmap.get(new Long(collateralID));
					if (null == temp) {
						temp = proxy.getCollateral(collateralID, false);
						hmap.put(new Long(collateralID), temp);
					}
					if (null != temp) {
						alloc.setCollateral(temp);
						allocList[i] = alloc;
					}
					else {
						throw new LimitException("Collateral from ICollateralProxy.getCollateral() for colalteral ID: "
								+ collateralID + " returns null!");
					}
				}
			}
			limit.setCollateralAllocations(allocList);
			return limit;
		}
		catch (CollateralException e) {
			e.printStackTrace();
			throw new LimitException("Caught CollateralException!", e);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception!", e);
		}
	}*/

	public ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		((ILimitTrxValue) trxValue).setTransactionSubType(ICMSConstant.MANUAL_INPUT_TRX_TYPE);
		((ILimitTrxValue) trxValue).setTrxReferenceID(String.valueOf(ICMSConstant.LONG_INVALID_VALUE));
		((ILimitTrxValue) trxValue).setTrxContext(ctx);
		return trxValue;
	}
}
