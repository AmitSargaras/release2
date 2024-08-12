package com.integrosys.cms.ui.custexposure.group;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.report.ReportCommandAccessor;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.cms.batch.reports.IReportRequestManager;
import com.integrosys.cms.batch.reports.ReportRequestManagerFactory;
import com.integrosys.cms.batch.reports.OBReport;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.app.custexposure.proxy.group.GroupExposureProxyFactory;
import com.integrosys.cms.app.custexposure.proxy.group.IGroupExposureProxy;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.trx.group.OBGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.bus.group.IGroupExposure;
import com.integrosys.cms.app.custexposure.bus.group.IGroupExpCustGrp;
import java.util.*;
import java.text.SimpleDateFormat;

/**
* Describe this class.
* Purpose:This class implements command
* Description: For retrieving value in group svc
*
* @author $Grace Teh$<br>
* @version $Revision$
* @since $11 August 2008$
* Tag: $Name$
*/
//public class ReadGrpExpReportCommand extends AbstractCommand {
public class ReadGrpExpReportCommand extends ReportCommandAccessor implements ICommand, ICommonEventConstant {
    
    /**
     * Defines a two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"grpID", "java.lang.String", REQUEST_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"session.IGrpExposure", "com.integrosys.cms.app.custexposure.bus.group.IGroupExposure", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
        });
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"GroupExposureTrxValue", "com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue", SERVICE_SCOPE},
                {"OBReportList", "java.util.Collection", REQUEST_SCOPE},
                {"countryName", "java.lang.String", REQUEST_SCOPE},
                {"searchDate", "java.lang.String", REQUEST_SCOPE},
                {"searchDate_str", "java.lang.String", REQUEST_SCOPE},
                {"session.IGrpExposure", "com.integrosys.cms.app.custexposure.bus.group.IGroupExposure", SERVICE_SCOPE},
        }
        );
    }


    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String grpID = (String) inputMap.get("grpID");
        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        IGroupExposure aIGrpExposure = (IGroupExposure)inputMap.get("session.IGrpExposure");

        // Get Exposure report from the system with taking care for user country and current date
        Date searchDate = new Date();
        String searchDate_str = getDateAsString(searchDate);

        ITeam team = (ITeam) inputMap.get(IGlobalConstant.USER_TEAM);
        List ctryVals = Arrays.asList(team.getCountryCodes());
        String countryName = "";
        if (ctryVals != null){
            countryName = (String) ctryVals.get(0);
        }
        
        Debug("grpID = " + grpID);
        Debug("Report Team :" + team.getTeamID() + ", CountryName : " + countryName +   ", searchDate : " + searchDate);

        //IGroupExposure aIGrpExposure = new OBGroupExposure();
        IGroupExposureProxy proxy = GroupExposureProxyFactory.getProxy();
        IGroupExposureTrxValue trxValue = null;
        OBReport[] reports =null;

        // Deprecated:
        //IReportRequestManager manager = ReportRequestManagerFactory.getProxyManager();

        try {
            if (grpID != null && !"".equals(grpID)){
                trxValue = proxy.getGroupExposure((Long.parseLong(grpID)));
            }
            
            if (trxValue == null){
                trxValue = new OBGroupExposureTrxValue();
            } else {
                aIGrpExposure = trxValue.getGroupExposure();
                
                if(aIGrpExposure != null){
                    IGroupExpCustGrp grpExpCustGrp = aIGrpExposure.getGroupExpCustGrp();
                    //Debug("getGroupName = " + grpExpCustGrp.getGroupName());
                  }

                // Get the Exposures Reports
                reports = getReportRequestManager().getGroupExposureReportsByCountryAndDate(countryName, team.getOrganisationCodes(), searchDate,  this.getReportCategoryIds() );

            }

            trxValue.setGroupExposure(aIGrpExposure);

            resultMap.put("GroupExposureTrxValue", trxValue);
            resultMap.put("countryName", countryName);
            resultMap.put("searchDate", searchDate);
            resultMap.put("searchDate_str", searchDate_str);
            resultMap.put("OBReportList", reports );

        } catch (Exception e) {
            DefaultLogger.error(this, "Error occurring in retrieving Group Exposure Reports " + e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Existing doExecute()\n");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }



    private String[] getReportCategoryIds() {
           List list = new ArrayList();
           list.add(ReportConstants.GROUP_CATEGORY);
           return (String[]) list.toArray(new String[0]);
    }

     public static String getDateAsString(Date date) {
           try {
               if (date != null){
                   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                   return sdf.format(date);
               }
           } catch (Exception ex) {
           }
           return "";
     }


    /**
     * @param msg
     */
    private void Debug(String msg) {
    	DefaultLogger.debug(this,ReadGrpExpReportCommand.class.getName() + " = " + msg);
    }


}
