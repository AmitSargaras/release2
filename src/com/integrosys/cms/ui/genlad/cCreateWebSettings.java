/**
 * Generate web settings.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

public class cCreateWebSettings extends cCreateElement {

  private static cCreateWebSettings rscInstance = null;

  public static cCreateWebSettings getInstance() {
    if (cCreateWebSettings.rscInstance == null) {
      cCreateWebSettings.rscInstance = new cCreateWebSettings();
    }
    return cCreateWebSettings.rscInstance;
  }

  public cCreateWebSettings() {
    this.xml = "";
  }

   
  public String toString() {
    return this.xml;
  }

  public void fGenerateWebSettings() {
    this.xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><" + cCreateElement.NAMESPACE + ":webSettings xmlns:r='http://schemas.openxmlformats.org/officeDocument/2006/relationships' xmlns:w='http://schemas.openxmlformats.org/wordprocessingml/2006/main'><" + cCreateElement.NAMESPACE + ":optimizeForBrowser /></" + cCreateElement.NAMESPACE + ":webSettings>";
  }
}
