package com.integrosys.cms.batch.sibs.parameter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.sibs.parameter.obj.OBCommonCodeEntryWrapper;
import com.integrosys.cms.batch.sibs.parameter.obj.OBCountry;
import com.integrosys.cms.batch.sibs.parameter.obj.OBCommonCodeEntryCombineRefEntryWrapper;

/**
 * Implemetation of {@link IParameterDao} using Spring Framework hibernate
 * routine.
 * 
 * @author Cynthia Zhou
 * @author Phoon Sai Heng
 * @author Chong Jun Yong
 * @since Sep 30, 2008
 */
public class ParameterDaoImpl extends HibernateDaoSupport implements IParameterDao {

	protected void checkWriteOperationAllowed(Session session) throws InvalidDataAccessApiUsageException {
		if (getHibernateTemplate().isCheckWriteOperations()
				//&& getHibernateTemplate().getFlushMode() != HibernateTemplate.FLUSH_EAGER
				&& session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): "
							+ "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}

	public List getParameterEntryByEntityName(String entityName) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName);
		List resultList = getHibernateTemplate().findByCriteria(criteria);

		DefaultLogger.debug(this, "entityName : " + entityName);

		if (resultList.isEmpty()) {
			return null;
		}
		else if ("paramLoanProductType".equals(entityName)
                || "paramCollateralProductType".equals(entityName)
				|| "paramTradeFinanceProductType".equals(entityName)
                || "paramExtOfProductType".equals(entityName)) {
			// default reference entry code for Production Type, based on
			// entityName
			String refEntryCode = "";
			if ("paramLoanProductType".equals(entityName)) {
				refEntryCode = "LNPAR2";
			}
			else if ("paramCollateralProductType".equals(entityName)) {
				refEntryCode = "DDPAR2";
			}
			else if ("paramTradeFinanceProductType".equals(entityName)) {
				refEntryCode = "TFPAR2";
			}
            else if ("paramProductType".equals(entityName)) {
                refEntryCode = "LNPARL";
            }

			for (int i = 0; i < resultList.size(); i++) {
				OBCommonCodeEntryWrapper obCommonCode = (OBCommonCodeEntryWrapper) resultList.get(i);
				obCommonCode.setRefEntryCode(refEntryCode);

				if (i == 0) {
					DefaultLogger.debug(this, "obCommonCode.getRefEntryCode() ******** "
							+ obCommonCode.getRefEntryCode());
				}
			}

		}
		else if ("paramCountry".equals(entityName)) {
			// for Country Parameter Batch, need to filter out the record where
			// country code > 2
			List tempResultList = resultList;
			resultList = new ArrayList();

			for (int i = 0; i < tempResultList.size(); i++) {
				OBCountry obCountry = (OBCountry) tempResultList.get(i);
				if (obCountry.getCountryCode() != null && obCountry.getCountryCode().trim().length() <= 2) {
					resultList.add(obCountry);
				}
				else {
					DefaultLogger.debug(this, " ******** Error: Country Code [" + obCountry.getCountryCode()
							+ "] is not compatible and not required for data storing.");
				}
			}

			DefaultLogger.debug(this, "Country Code - resultList.size() : " + resultList.size());
			DefaultLogger.debug(this, "Country Code - tempResultList.size() : " + tempResultList.size());
		}
		else if ("paramModelYearMap".equals(entityName)) {
			// for Model_Year_Map Parameter Batch, need to combine Make & Model as Reference Entry Code
			for (int i = 0; i < resultList.size(); i++) {
				OBCommonCodeEntryCombineRefEntryWrapper obCommonCode = (OBCommonCodeEntryCombineRefEntryWrapper) resultList.get(i);
                if (obCommonCode.getRefEntryCode_1() != null && obCommonCode.getRefEntryCode_2() != null) {
                    obCommonCode.setRefEntryCode(obCommonCode.getRefEntryCode_1().trim() + "|" + obCommonCode.getRefEntryCode_2().trim());

                    if (i == 0) {
                        DefaultLogger.debug(this, "obCommonCode.getRefEntryCode() ******** "
                                + obCommonCode.getRefEntryCode());
                    }
                }
            }

			DefaultLogger.debug(this, "Model Year Map - resultList.size() : " + resultList.size());
		}

        return resultList;
	}
}
