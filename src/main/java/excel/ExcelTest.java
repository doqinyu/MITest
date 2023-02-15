package excel;


import com.csvreader.CsvReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.charset.Charset;

public class ExcelTest {
    //3个月前的时间戳(22-06-15)
    public static final long end_3 = 1623686400000L;
    //6个月前的时间戳(22-03-15)
    public static final long end_6 = 1647273600000L;
    //9个月前的时间戳(2021-12-15)
    public static final long end_9 = 1639497600000L;
    //12个月前的时间戳(2021-09-15)
    public static final long end_12 = 1631635200000L;


    public static void readXlsxExcel(String path) {
        long total_3 = 0;
        long total_6 = 0;
        long total_9 = 0;
        long total_12 = 0;
        try {
            //读取的时候可以使用流，也可以直接使用文件名
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(new File(path)));//
            XSSFSheet sheet = book.getSheetAt(0); //获得第一个工作表对象
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                XSSFCell cell = sheet.getRow(i).getCell(0);//获得单元格
                String s = cell.getStringCellValue();
                try {
                    long value = Long.parseLong(s);

                    if (value > end_3) {
                        total_3 = total_3 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                        total_6 = total_6 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                        total_9 = total_9 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                        total_12 = total_12 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                    }else if (value > end_6) {
                        total_6 = total_6 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                        total_9 = total_9 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                        total_12 = total_12 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                    } else if (value > end_9) {
                        total_9 = total_9 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                        total_12 = total_12 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                    } else if (value > end_12) {
                        total_12 = total_12 + Long.parseLong(sheet.getRow(i).getCell(1).getStringCellValue());
                    }

                } catch (Exception e) {

                }

                if (i % 1000 == 0) {
                    System.out.println("i = "+ i + ", total_6 = "+ total_6 + ", total_9 = "+ total_9 + ", total_12" + total_12);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        long total = total_6 + total_9 + total_12;
        System.out.println(path + "  total_6 = " + total_6 + " ， 占比 = " + String.valueOf((total_6 / total) * 100));
        System.out.println(path + "total_9 = " + total_9 + " ， 占比 = " + String.valueOf((total_9 / total) * 100));
        System.out.println(path + "total_12 = " + total_12 + " ， 占比 = " + String.valueOf((total_12 / total) * 100));
        System.out.println("====================================================");

    }


    public static void readXlsxExcel2(String path) {
        long total_1029 = 0;
        long total_1030 = 0;
        long total_1031 = 0;
        long total_1101 = 0;
        long total_1102 = 0;
        long total_1103 = 0;
        long total_1104 = 0;
        long total_1105 = 0;
        long total_1106= 0;
        long total_1107 = 0;
        long total_1108 = 0;
        long total_1109 = 0;
        long total_1110 = 0;
        long total_1111 = 0;
        long total_1112 = 0;
        long total_1113 = 0;




        try {
            //读取的时候可以使用流，也可以直接使用文件名
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(new File(path)));//
            XSSFSheet sheet = book.getSheetAt(0); //获得第一个工作表对象
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                XSSFCell date_cell = sheet.getRow(i).getCell(2);
                date_cell.setCellType(CellType.STRING);
                String date = date_cell.getStringCellValue();//获得单元格
                XSSFCell cell = sheet.getRow(i).getCell(1);
                cell.setCellType(CellType.STRING);
                int count = Integer.parseInt(cell.getStringCellValue());//获得单元格
                try {
                    switch (date) {
                        case "20221029": total_1029 += count;break;
                        case "20221030": total_1030 += count;break;
                        case "20221031": total_1031 += count;break;
                        case "20221101": total_1101 += count;break;
                        case "20221102": total_1102 += count;break;
                        case "20221103": total_1103 += count;break;
                        case "20221104": total_1104 += count;break;
                        case "20221105": total_1105 += count;break;
                        case "20221106": total_1106 += count;break;
                        case "20221107": total_1107 += count;break;
                        case "20221108": total_1108 += count;break;
                        case "20221109": total_1109 += count;break;
                        case "20221110": total_1110 += count;break;
                        case "20221111": total_1111 += count;break;
                        case "20221112": total_1112 += count;break;
                        case "20221113": total_1113 += count;break;
                    }


                } catch (Exception e) {

                }

                if (i % 1000 == 0) {
                    System.out.println("==================================> i = "+ i);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(path + "  total_1029 = " + total_1029 + " ， 占比 = " + (total_1029 *1.0) / 830808181);
        System.out.println(path + "  total_1030 = " + total_1030 + " ， 占比 = " + (total_1030 * 1.0) / 815761141);
        System.out.println(path + "  total_1031 = " + total_1031 + " ， 占比 = " + (total_1031 *1.0) / 786414879) ;
        System.out.println(path + "  total_1101 = " + total_1101 + " ， 占比 = " + (total_1101 *1.0) / 789264743);
        System.out.println(path + "  total_1102 = " + total_1102 + " ， 占比 = " + (total_1102 *1.0) / 785944138);
        System.out.println(path + "  total_1103 = " + total_1103 + " ， 占比 = " + (total_1103 *1.0) / 801951008);
        System.out.println(path + "  total_1104 = " + total_1104 + " ， 占比 = " + (total_1104 *1.0) / 820831073);
        System.out.println(path + "  total_1105 = " + total_1105 + " ， 占比 = " + (total_1105 *1.0) / 824921438);
        System.out.println(path + "  total_1106 = " + total_1106 + " ， 占比 = " + (total_1106 *1.0) / 822005760);
        System.out.println(path + "  total_1107 = " + total_1107 + " ， 占比 = " + (total_1107 *1.0) / 808763572);
        System.out.println(path + "  total_1108 = " + total_1108 + " ， 占比 = " + (total_1108 *1.0) / 805234045);
        System.out.println(path + "  total_1109 = " + total_1109 + " ， 占比 = " + (total_1109 *1.0) / 802476160);
        System.out.println(path + "  total_1110 = " + total_1110 + " ， 占比 = " + (total_1110 *1.0) / 794343244);
        System.out.println(path + "  total_1111 = " + total_1111 + " ， 占比 = " + (total_1111 *1.0) / 780624413);
        System.out.println(path + "  total_1112 = " + total_1112 + " ， 占比 = " + (total_1112 *1.0) / 791607429);
        System.out.println(path + "  total_1113 = " + total_1113 + " ， 占比 = " + (total_1113 *1.0) / 790975071);


        System.out.println("====================================================");

    }

    public static void trans (String source, String dest) {
        int row = 0;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dest)));

            //读取的时候可以使用流，也可以直接使用文件名
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(new File(source)));//
            XSSFSheet sheet = book.getSheetAt(1); //获得第一个工作表对象
            String lastStr = null;
            String lastRes = null;
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                row = i;
                try {
                    String videoId = sheet.getRow(i).getCell(1).getStringCellValue();
                    XSSFCell cell = sheet.getRow(i).getCell(3);
                    cell.setCellType(CellType.STRING);
                    String res = cell.getStringCellValue();
                    if (lastStr == null) {
                        lastStr = videoId;
                        lastRes = res;
                    } else if (lastStr .equals(videoId)) {
                        lastRes = lastRes +"," + res;
                    } else {

                        bw.write(lastStr + "    " + lastRes + "\n");
                        bw.flush();

                        lastStr = videoId;
                        lastRes = res;

                    }

                } catch (Exception e) {
                    System.out.println("exception , row = " + row + ", exp = " +e.getMessage());
                }

                if (row % 1000 == 0) {
                    System.out.println("========================= row = " + row + " =======================");
                }

            }

        } catch (Exception e) {
            System.out.println("exception , row = " + row + ", exp = " +e.getMessage());
        }

    }



    public static void trans2 (String source, String dest) {
        int row = 0;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dest)));

            //读取的时候可以使用流，也可以直接使用文件名
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(new File(source)));//
            XSSFSheet sheet = book.getSheetAt(1); //获得第一个工作表对象
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                row = i;
                try {
                    XSSFCell cell = sheet.getRow(i).getCell(1);
                    cell.setCellType(CellType.STRING);
                    String videoId = cell.getStringCellValue();
                    cell = sheet.getRow(i).getCell(3);
                    cell.setCellType(CellType.STRING);
                    String res = cell.getStringCellValue();
                    bw.write(videoId + "    " + res + "\n");
                    bw.flush();

                } catch (Exception e) {
                    System.out.println("exception , row = " + row + ", exp = " +e.getMessage());
                }

                if (row % 1000 == 0) {
                    System.out.println("========================= row = " + row + " =======================");
                    Thread.sleep(10);
                }

            }

        } catch (Exception e) {
            System.out.println("exception , row = " + row + ", exp = " +e.getMessage());
        }

    }

    public static void readCsvExcel(String path) {
        long total = 0;
        long total_3 = 0;
        long total_6 = 0;
        long total_9 = 0;
        long total_12 = 0;
        int cnt = 0;
        try {
            // 创建CSV读对象
            CsvReader reader = new CsvReader(path, ',', Charset.forName("UTF-8"));
            while (reader.readRecord()) {

                String s = reader.get(1);
                try {
                    long time = Long.parseLong(s);
                    if (time <=0 ) {
                        continue;
                    }
                    long value = Long.parseLong(reader.get(2));
                    total = total + value;
                    if (time < end_12) {
                        total_3 = total_3 + value;
                        total_6 = total_6 + value;
                        total_9 = total_9 + value;
                        total_12 = total_12 + value;
                    } else if (time < end_9) {
                        total_3 = total_3 + value;
                        total_6 = total_6 + value;
                        total_9 = total_9 + value;
                    } else if (time < end_6) {
                        total_3 = total_3 + value;
                        total_6 = total_6 + value;
                    } else if (time < end_3) {
                        total_3 = total_3 + value;
                    }

                } catch (Exception e) {

                }

                cnt ++;
//                if (cnt == 10) {
//                    break;
//                }
                if (cnt % 1000 == 1) {
                    System.out.println("i = "+ cnt + ", total_6 = "+ total_6 + ", total_9 = "+ total_9 + ", total_12 = " + total_12);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("total = " + total);
        System.out.println(path + "  total_3 = " + total_3 + ", per = " + String.format("%.5f",(total_3*1.0/ total) * 100));
        System.out.println(path + "  total_6 = " + total_6 + ", per = " + String.format("%.5f",(total_6*1.0/ total) * 100));
        System.out.println(path + "  total_9 = " + total_9+ ", per = " +  String.format("%.5f",(total_9*1.0/ total)* 100));
        System.out.println(path + "  total_12 = " + total_12+ ", per = " +  String.format("%.5f",(total_12*1.0/ total) * 100));
        System.out.println("====================================================");

    }

    public void test() {
        System.out.println("begin fun .....");
        System.out.println("end fun .....");
    }

    public static void main(String[] args) {
       String file = "/Users/doqinvera/Downloads/微帧 (5)1108-11113.xlsx";
//        readCsvExcel(file);
//        file = "/Users/doqinvera/Downloads/1_imp_detail_page的总次数_事件分析_非合计_2022-09-14至2022-09-14_SensorsAnalytics.csv";
//        readCsvExcel(file);
//        file = "/Users/doqinvera/Downloads/1_imp_detail_page的总次数_事件分析_非合计_2022-09-13至2022-09-13_SensorsAnalytics.csv";
//        readCsvExcel(file);
//        file = "/Users/doqinvera/Downloads/1_imp_detail_page的总次数_事件分析_非合计_2022-09-12至2022-09-12_SensorsAnalytics.csv";
//        readCsvExcel(file);

            String source = "/Users/doqinvera/Desktop/曝光top1w视频.xlsx";
            //String dest = "/Users/doqinvera/Desktop/12-20source.txt";
            String dest2 = "/Users/doqinvera/Desktop/source.txt";
            //trans(source, dest);
            trans2(source, dest2);
//        System.out.println("begin fun .....");
//        readXlsxExcel2(file);
//        System.out.println("end fun .....");
    }
}
