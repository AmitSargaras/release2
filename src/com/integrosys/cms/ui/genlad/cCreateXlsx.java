package com.integrosys.cms.ui.genlad;
///**
// * Generate xsls.
// * @author 2mdc
// */
//package com.javadocx;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.zip.*;
//
//public class cCreateXlsx {
//
//  private ZipOutputStream objZipDocx;
//  private static cCreateXlsx rscInstance = null;
//  private String xml_Xl_Tables__Content;
//  private String xml_Xl_SharedStrings__Content;
//  private String xml_Xl_Sheet__Content;
//
//  public static cCreateXlsx getInstance() {
//    if (cCreateXlsx.rscInstance == null) {
//      cCreateXlsx.rscInstance = new cCreateXlsx();
//    }
//    return cCreateXlsx.rscInstance;
//  }
//
//  protected void fAddTable(HashMap arrArgs) {
//    cCreateExcelTable objExcelTable = cCreateExcelTable.getInstance();
//    objExcelTable.fCreateExcelTable(arrArgs);
//    this.xml_Xl_Tables__Content += objExcelTable.toString();
//  }
//
//  protected void fAddSharedStrings(HashMap arrArgs) {
//    cCreateExcelSharedStrings objExcelSS = cCreateExcelSharedStrings.getInstance();
//    objExcelSS.fCreateExcelSharedStrings(arrArgs);
//    this.xml_Xl_SharedStrings__Content += objExcelSS.toString();
//  }
//
//  protected void fAddSheet(HashMap arrArgs) {
//    cCreateExcelSheet objExcelSheet = cCreateExcelSheet.getInstance();
//    objExcelSheet.fCreateExcelSheet(arrArgs);
//    this.xml_Xl_Sheet__Content += objExcelSheet.toString();
//  }
//
//  public boolean fCreateXlsx(HashMap arrArgs) {
//    String strFileName;
//    this.xml_Xl_Tables__Content = "";
//    this.xml_Xl_SharedStrings__Content = "";
//    this.xml_Xl_Sheet__Content = "";
//    if (!"".equals(arrArgs.get("fileName").toString())) {
//      strFileName = arrArgs.get("fileName").toString();
//    } else {
//      strFileName = "document";
//    }
//    String[] filenames = new String[]{"docProps/core.xml", "[Content_Types].xml", "docProps/app.xml", "_rels/.rels", "xl/_rels/workbook.xml.rels", "xl/theme/theme1.xml", "xl/worksheets/_rels/sheet1.xml.rels", "xl/styles.xml", "xl/workbook.xml"};
//    try {
//      this.objZipDocx = new ZipOutputStream(new FileOutputStream("datos.xlsx"));
//      for (int i = 0; i < filenames.length; i++) {
//        this.objZipDocx.putNextEntry(new ZipEntry(filenames[i]));
//        String executionPath = System.getProperty("user.dir");
//        File myImageFile = new File(executionPath + "/excel/" + filenames[i]);
//        FileInputStream fis = new FileInputStream(myImageFile);
//        byte[] data = new byte[1024];
//        byte[] tmp = new byte[0];
//        byte[] myArrayImage = new byte[0];
//        int len = 0;
//        int total = 0;
//        while ((len = fis.read(data)) != -1) {
//          total += len;
//          tmp = myArrayImage;
//          myArrayImage = new byte[total];
//          System.arraycopy(tmp, 0, myArrayImage, 0, tmp.length);
//          System.arraycopy(data, 0, myArrayImage, tmp.length, len);
//        }
//        fis.close();
//        this.objZipDocx.write(myArrayImage, 0, myArrayImage.length);
//        this.objZipDocx.closeEntry();
//      }
//      this.fAddTable(arrArgs);
//      this.objZipDocx.putNextEntry(new ZipEntry("xl/tables/table1.xml"));
//      byte[] buf = this.xml_Xl_Tables__Content.getBytes();
//      this.objZipDocx.write(buf, 0, buf.length);
//      this.objZipDocx.closeEntry();
//
//      this.fAddSharedStrings(arrArgs);
//      this.objZipDocx.putNextEntry(new ZipEntry("xl/sharedStrings.xml"));
//      buf = this.xml_Xl_SharedStrings__Content.getBytes();
//      this.objZipDocx.write(buf, 0, buf.length);
//      this.objZipDocx.closeEntry();
//
//      this.fAddSheet(arrArgs);
//      this.objZipDocx.putNextEntry(new ZipEntry("xl/worksheets/sheet1.xml"));
//      buf = this.xml_Xl_Sheet__Content.getBytes();
//      this.objZipDocx.write(buf, 0, buf.length);
//      this.objZipDocx.closeEntry();
//      this.objZipDocx.close();
//      File file = new File("datos.xlsx");
//      // File (or directory) with new name
//      String id = arrArgs.get("id").toString();
//      Integer nameId = new Integer(id);
//      File file2 = new File("datos" + nameId + ".xlsx");
//      // Rename file (or directory)
//      boolean success = file.renameTo(file2);
//      if (!success) {
//        System.out.println("There is a error when try to rename the excel file");
//      }
//    } catch (Exception e) {
//      System.out.println("There is a name when generate the excel file: " + e.toString());
//      return false;
//    }
//    return true;
//  }
//}
