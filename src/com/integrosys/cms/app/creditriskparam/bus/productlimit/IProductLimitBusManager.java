package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;

import java.util.List;

public interface IProductLimitBusManager {

    public List getAll();

    public List getAllChild();

    public Object getLimitById(long id);

    public IProductProgramLimitParameter createLimit(IProductProgramLimitParameter obj);

    public IProductProgramLimitParameter updateToWorkingCopy(IProductProgramLimitParameter actual, IProductProgramLimitParameter staging);

    public IProductProgramLimitParameter findByPrimaryKey(IProductProgramLimitParameter obj);

    public IProductTypeLimitParameter getProductTypeLimitParameterByRefCode(String refCode);

    public IProductProgramLimitParameter getProductProgramLimitParameterByRefCode(String refCode);

}