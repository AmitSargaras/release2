/**
 * Generate fonts table.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class cCreateFontTable extends cCreateElement {

  private static cCreateFontTable rscInstance = null;

  
  public String toString() {
    return this.xml;
  }

  public static cCreateFontTable getInstance() {
    if (cCreateFontTable.rscInstance == null) {
      cCreateFontTable.rscInstance = new cCreateFontTable();
    }
    return cCreateFontTable.rscInstance;
  }

  protected void fGenerateFONT(String name) {
    if ("".equals(name)) {
      name = "Calibri";
    }
    this.xml = "<" + cCreateElement.NAMESPACE + ":font " + cCreateElement.NAMESPACE + ":name='" + name + "'>__GENERATEFONT__</" + cCreateElement.NAMESPACE + ":font>";
  }

  protected void fGeneratePANOSE1(String val) {
    if ("".equals(val)) {
      val = "020F0502020204030204";
    }
    this.xml = this.xml.replace("__GENERATEFONT__", "<" + cCreateElement.NAMESPACE + ":panose1 " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":panose1>__GENERATEPANOSE1__");
  }

  protected void fGenerateCHARSET(String val) {
    if ("".equals(val)) {
      val = "00";
    }
    this.xml = this.xml.replace("__GENERATEPANOSE1__", "<" + cCreateElement.NAMESPACE + ":charset " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":charset>__GENERATECHARSET__");
  }

  protected void fGenerateFAMILY(String val) {
    if ("".equals(val)) {
      val = "swiss";
    }
    this.xml = this.xml.replace("__GENERATECHARSET__", "<" + cCreateElement.NAMESPACE + ":family " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":family>__GENERATEFAMILY__");
  }

  protected void fGeneratePITCH(String val) {
    if ("".equals(val)) {
      val = "00";
    }
    this.xml = this.xml.replace("__GENERATEFAMILY__", "<" + cCreateElement.NAMESPACE + ":pitch " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":pitch>__GENERATEPITCH__");
  }

  protected void fGenerateSIG(String usb0, String usb1, String usb2, String usb3, String csb0, String csb1) {
    if ("".equals(usb0)) {
      usb0 = "A00002EF";
    }
    if ("".equals(usb1)) {
      usb1 = "4000207B";
    }
    if ("".equals(usb2)) {
      usb2 = "00000000";
    }
    if ("".equals(usb3)) {
      usb3 = "00000000";
    }
    if ("".equals(csb0)) {
      csb0 = "0000009F";
    }
    if ("".equals(csb1)) {
      csb1 = "00000000";
    }
    this.xml = this.xml.replace("__GENERATEPITCH__", "<" + cCreateElement.NAMESPACE + ":sig " + cCreateElement.NAMESPACE + ":usb0='" + usb0 + "' " + cCreateElement.NAMESPACE + ":usb1='" + usb1 + "' "
            + cCreateElement.NAMESPACE + ":usb2='" + usb2 + "' " + cCreateElement.NAMESPACE + ":usb3='" + usb3 + "' " + cCreateElement.NAMESPACE + ":csb0='" + csb0 + "' "
            + cCreateElement.NAMESPACE + ":csb1='" + csb1 + "'></" + cCreateElement.NAMESPACE + ":sig>");
  }

  public void fCreateFont(HashMap arrArgs) {
    this.xml = "";
    if (arrArgs.containsKey("name") && arrArgs.containsKey("pitch") && arrArgs.containsKey("usb0") && arrArgs.containsKey("usb1") && arrArgs.containsKey("usb2") && arrArgs.containsKey("usb3") && arrArgs.containsKey("csb0") && arrArgs.containsKey("csb1") && arrArgs.containsKey("family") && arrArgs.containsKey("charset") && arrArgs.containsKey("panose1")) {
      this.fGenerateFONT(arrArgs.get("name").toString());
      this.fGeneratePANOSE1(arrArgs.get("panose1").toString());
      this.fGenerateCHARSET(arrArgs.get("charset").toString());
      this.fGenerateFAMILY(arrArgs.get("family").toString());
      this.fGeneratePITCH(arrArgs.get("pitch").toString());
      this.fGenerateSIG(arrArgs.get("usb0").toString(), arrArgs.get("usb1").toString(), arrArgs.get("usb2").toString(), arrArgs.get("usb3").toString(), arrArgs.get("csb0").toString(), arrArgs.get("csb1").toString());
    } else {
    	DefaultLogger.debug(this,"A empty font was tried to insert.");
    }
  }
}
