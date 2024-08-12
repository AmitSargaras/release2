//package com.integrosys.cms.ui.genlad;
/////**
//// * Generate excel sheets.
//// * @author 2mdc
//// */
////package com.javadocx;
////
////import java.util.ArrayList;
////import java.util.HashMap;
////import java.util.Set;
////import java.util.Iterator;
////import java.util.Map;
////
////class cCreateExcelSheet extends cCreateElement {
////
////  private static cCreateExcelSheet rscInstance = null;
////
////  
////  public String toString() {
////    return this.xml;
////  }
////
////  public static cCreateExcelSheet getInstance() {
////    if (cCreateExcelSheet.rscInstance == null) {
////      cCreateExcelSheet.rscInstance = new cCreateExcelSheet();
////    }
////    return cCreateExcelSheet.rscInstance;
////  }
////
////  private void fCleanTemplateROW() {
////    this.xml = this.xml.replace("__GENERATEROW__", "");
////  }
////
////  protected void fGenerateWORKSHEET() {
////    this.xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><worksheet xmlns='http://schemas.openxmlformats.org/spreadsheetml/2006/main' xmlns:r='http://schemas.openxmlformats.org/officeDocument/2006/relationships'>__GENERATEWORKSHEET__</worksheet>";
////  }
////
////  protected void fGenerateDIMENSION(int intSizeX, int intSizeY) {
////    char letra = 'A';
////    for (int i = 0; i < intSizeY; i++) {
////      letra += 1;
////    }
////    intSizeX += intSizeY;
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<dimension ref='A1:" + letra + intSizeX + "'></dimension>__GENERATEWORKSHEET__");
////  }
////
////  protected void fGenerateSHEETVIEWS() {
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<sheetViews>__GENERATESHEETVIEWS__</sheetViews>__GENERATEWORKSHEET__");
////  }
////
////  protected void fGenerateSHEETVIEW(String tabSelected, String workbookViewId) {
////    if ("".equals(tabSelected)) {
////      tabSelected = "1";
////    }
////    if ("".equals(workbookViewId)) {
////      workbookViewId = "0";
////    }
////    this.xml = this.xml.replace("__GENERATESHEETVIEWS__", "<sheetView tabSelected='" + tabSelected + "' workbookViewId='" + workbookViewId + "'>__GENERATESHEETVIEW__</sheetView>");
////  }
////
////  protected void fGenerateSELECTION(String num) {
////    this.xml = this.xml.replace("__GENERATESHEETVIEW__", "<selection activeCell='B" + num + "' sqref='B" + num + "'></selection>");
////  }
////
////  protected void fGenerateSHEETFORMATPR(String baseColWidth, String defaultRowHeight) {
////    if ("".equals(baseColWidth)) {
////      baseColWidth = "10";
////    }
////    if ("".equals(defaultRowHeight)) {
////      defaultRowHeight = "15";
////    }
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<sheetFormatPr baseColWidth='" + baseColWidth + "' defaultRowHeight='" + defaultRowHeight + "'></sheetFormatPr>__GENERATEWORKSHEET__");
////  }
////
////  protected void fGenerateCOLS() {
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<cols>__GENERATECOLS__</cols>__GENERATEWORKSHEET__");
////  }
////
////  protected void fGenerateCOL(String min, String max, String width, String customWidth) {
////    if ("".equals(min)) {
////      min = "1";
////    }
////    if ("".equals(max)) {
////      max = "1";
////    }
////    if ("".equals(width)) {
////      width = "11.85546875";
////    }
////    if ("".equals(customWidth)) {
////      customWidth = "1";
////    }
////    this.xml = this.xml.replace("__GENERATECOLS__", "<col min='" + min + "' max='" + max + "' width='" + width + "' customWidth='" + customWidth + "'></col>");
////  }
////
////  protected void fGenerateSHEETDATA() {
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<sheetData>__GENERATESHEETDATA__</sheetData>__GENERATEWORKSHEET__");
////  }
////
////  protected void fGenerateROW(int r, int spans) {
////    String strSpans = "1:" + (spans + 1);
////    this.xml = this.xml.replace("__GENERATESHEETDATA__", "<row r='" + r + "' spans='" + strSpans + "'>__GENERATEROW__</row>__GENERATESHEETDATA__");
////  }
////
////  protected void fGenerateC(String r, String s, String t) {
////    String xmlAux = "<c r='" + r + "'";
////    if (!"".equals(s)) {
////      xmlAux += " s='" + s + "'";
////    }
////    if (!"".equals(t)) {
////      xmlAux += " t='" + t + "'";
////    }
////    xmlAux += ">__GENERATEC__</c>__GENERATEROW__";
////    this.xml = this.xml.replace("__GENERATEROW__", xmlAux);
////  }
////
////  protected void fGenerateV(Double num) {
////    String str = num.toString();
////    if (str.indexOf(".0") != -1) {
////      this.xml = this.xml.replace("__GENERATEC__", "<v>" + str.substring(0, str.indexOf(".0")) + "</v>");
////    } else {
////      this.xml = this.xml.replace("__GENERATEC__", "<v>" + num + "</v>");
////    }
////  }
////
////  protected void fGeneratePAGEMARGINS(String left, String rigth, String bottom, String top, String header, String footer) {
////    if ("".equals(left)) {
////      left = "0.7";
////    }
////    if ("".equals(rigth)) {
////      rigth = "0.7";
////    }
////    if ("".equals(bottom)) {
////      bottom = "0.75";
////    }
////    if ("".equals(top)) {
////      top = "0.75";
////    }
////    if ("".equals(header)) {
////      header = "0.3";
////    }
////    if ("".equals(footer)) {
////      footer = "0.3";
////    }
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<pageMargins left='" + left + "' right='" + rigth + "' top='" + top + "' bottom='" + bottom + "' header='" + header + "' footer='" + footer + "'></pageMargins>__GENERATEWORKSHEET__");
////  }
////
////  protected void fGeneratePAGESETUP(String paperSize, String orientation, String id) {
////    if ("".equals(paperSize)) {
////      paperSize = "9";
////    }
////    if ("".equals(orientation)) {
////      orientation = "portrait";
////    }
////    if ("".equals(id)) {
////      id = "1";
////    }
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<pageSetup paperSize='" + paperSize + "' orientation='" + orientation + "' r:id='rId" + id + "'></pageSetup>__GENERATEWORKSHEET__");
////  }
////
////  protected void fGenerateTABLEPARTS(String count) {
////    if ("".equals(count)) {
////      count = "1";
////    }
////    this.xml = this.xml.replace("__GENERATEWORKSHEET__", "<tableParts count='" + count + "'>__GENERATETABLEPARTS__</tableParts>");
////  }
////
////  protected void fGenerateTABLEPART(String id) {
////    if ("".equals(id)) {
////      id = "1";
////    }
////    this.xml = this.xml.replace("__GENERATETABLEPARTS__", "<tablePart r:id='rId" + id + "'></tablePart>");
////  }
////
////  public void fCreateExcelSheet(HashMap arrArgs) {
////    this.xml = "";
////    String type = arrArgs.get("type").toString();
////    HashMap datos = (HashMap) arrArgs.get("data");
////    int intTamDatos = datos.size();
////    int intTamCols = 0;
////
////    Set entries = datos.entrySet();
////    Iterator it = entries.iterator();
////    while (it.hasNext()) {
////      Map.Entry entry = (Map.Entry) it.next();
////      intTamCols = ((ArrayList) entry.getValue()).size();
////      break;
////    }
////    if (type.indexOf("pie") != -1) {
////      intTamCols = 1;
////    } else {
////      intTamDatos--;
////    }
////    this.fGenerateWORKSHEET();
////    this.fGenerateDIMENSION(intTamDatos, intTamCols);
////    this.fGenerateSHEETVIEWS();
////    this.fGenerateSHEETVIEW("", "");
////    this.fGenerateSELECTION("" + (intTamDatos + intTamCols));
////    this.fGenerateSHEETFORMATPR("", "");
////    this.fGenerateCOLS();
////    this.fGenerateCOL("", "", "", "");
////    this.fGenerateSHEETDATA();
////    int intRow = 1;
////    entries = datos.entrySet();
////    it = entries.iterator();
////    while (it.hasNext()) {
////      Map.Entry entry = (Map.Entry) it.next();
////      int intCol = 1;
////      char letra = 'A';
////      this.fGenerateROW(intRow, intTamCols);
////      char letraAux = 'A';
////      this.fGenerateC("" + letraAux + 1, "", "s");
////      for (int i = 0; i < intRow; i++) {
////        letraAux += 1;
////      }
////      this.fGenerateV((intTamDatos + intTamCols) * 1.0);
////      letra += 1;
////      for (int j = 0; j < ((ArrayList) entry.getValue()).size(); j++) {
////        this.fGenerateC("" + letraAux + 1, "", "s");
////        this.fGenerateV((intCol - 1) * 1.0);
////        intCol++;
////        letraAux += 1;
////      }
////      this.fCleanTemplateROW();
////      intRow++;
////      break;
////    }
////    entries = datos.entrySet();
////    it = entries.iterator();
////    while (it.hasNext()) {
////      Map.Entry entry = (Map.Entry) it.next();
////      if (entry.getKey() == "0") {
////        continue;
////      }
////      this.fGenerateROW(intRow, intTamCols);
////      int intCol = 1;
////      char letra = 'A';
////      this.fGenerateC("" + letra + intRow, "1", "s");
////      this.fGenerateV((intTamCols + intRow - 2) * 1.0);
////      letra += 1;
////      for (int j = 0; j < ((ArrayList) entry.getValue()).size(); j++) {
////        String s = "";
////        if (intCol != intTamCols) {
////          s = "1";
////        }
////        this.fGenerateC("" + letra + intRow, s, "");
////        String str = ((ArrayList) entry.getValue()).get(j).toString();
////
////        Double val = new Double(str);
////
////        this.fGenerateV(val * 1.0);
////        intCol++;
////        letra += 1;
////      }
////      intRow++;
////      this.fCleanTemplateROW();
////    }
////    this.fGenerateROW(intRow + 1, intTamCols);
////    intRow++;
////    this.fGenerateC("B" + intRow, "2", "s");
////    this.fGenerateV((intTamDatos + intTamCols + 1) * 1.0);
////    this.fGeneratePAGEMARGINS("", "", "", "", "", "");
////    this.fGenerateTABLEPARTS("");
////    this.fGenerateTABLEPART("1");
////    this.fCleanTemplate();
////  }
////}
