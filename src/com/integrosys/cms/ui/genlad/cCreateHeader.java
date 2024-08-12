/**
 * Generate headers.
 * @author 2mdc
 */

package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

public class cCreateHeader extends cCreateElement {

  private static cCreateHeader rscInstance = null;

  
  public String toString() {
    return this.xml;
  }

  public static cCreateHeader getInstance() {
    if (cCreateHeader.rscInstance == null) {
      cCreateHeader.rscInstance = new cCreateHeader();
    }
    return cCreateHeader.rscInstance;
  }

   
  protected void fGenerateP() {
    this.xml = this.xml.replace("__GENERATEHDR__", "<" + cCreateElement.NAMESPACE + ":p>__GENERATEP__</" + cCreateElement.NAMESPACE + ":p>");
  }

  protected void fGenerateHDR() {
    this.xml = "__GENERATEHDR__";
  }

  public void fCreateHeader(String header, HashMap arrArgs) {
    this.xml = "";
    arrArgs.put("text", header);
    cCreateText objText = cCreateText.getInstance();
    objText.fCreateText(arrArgs);
    this.fGenerateHDR();
    this.xml = this.xml.replace("__GENERATEHDR__", objText.toString() + "__GENERATEHDR__");
  }
}
