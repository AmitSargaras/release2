package com.integrosys.cms.app.creditriskparam.proxy.productlimit;

import java.util.List;

import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IProductLimitParameterProxy {
	List listProductLimit() throws ProductLimitException;
	List listProductType() throws ProductLimitException;
	IProductProgramLimitParameter getProductLimitById (long id) throws ProductLimitException;
	IProductLimitParameterTrxValue getTrxValue(ITrxContext ctx) throws ProductLimitException;
	IProductLimitParameterTrxValue getTrxValueById (long id) throws ProductLimitException;
	IProductLimitParameterTrxValue getTrxValueByTrxId(ITrxContext ctx, String trxId) throws ProductLimitException;
	IProductLimitParameterTrxValue makerUpdateList(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException;
	IProductLimitParameterTrxValue checkerApprove(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException;
	IProductLimitParameterTrxValue checkerReject(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException;
	IProductLimitParameterTrxValue makerClose(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException;
	IProductLimitParameterTrxValue makerDelete(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException;

}
