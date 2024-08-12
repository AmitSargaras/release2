/** Copyright Integro Technologies Pte Ltd* $Header: $*/package com.integrosys.cms.ui.report;import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description:  This maps the HTML input form to the ActionForm properties *               This ActionForm is used by CCI Reports * * @author $Author:  $<br> * @version $Revision: $ * @since $Date:  $ * Tag: $Name:  $
 */
public class CCIReportSearchForm extends CommonForm {
    private String searchDate = "";
    public String getSearchDate() {
        return searchDate;
    }
    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

}
