package ana;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import helper.FileFinder;
import helper.ReferansFinder;

public class Mapper {
    public static void main(String[] args) throws IOException {
        String mainFilePath = args[0];
        String excelName = args[1];
        File root = new File(mainFilePath);
        String excelPath = root+"\\"+excelName;
        System.out.println("### Relationship Mapping Creating for : "+root.getName()+" ###");
        FileFinder ff = new FileFinder();
        ReferansFinder rf = new ReferansFinder();
        ArrayList<File> directories = ff.getDirectories(root);
        ArrayList<File> slnFiles = ff.getSlnFiles(directories);
        ArrayList<File> csProjFiles = ff.getCsprojFiles(directories);
        try {
            File tempFile = new File(excelPath);
            if (!tempFile.exists()){
                tempFile.createNewFile();
                XSSFWorkbook wb = new XSSFWorkbook();
                Sheet sh = wb.createSheet("Sayfa1");
                FileOutputStream outputStream = new FileOutputStream(excelPath);
                wb.write(outputStream);
             
            }
            FileInputStream fis = new FileInputStream(excelPath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            CellStyle styleAqua = workbook.createCellStyle();

            styleAqua.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            styleAqua.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            CellStyle styleGold = workbook.createCellStyle();
            styleGold.setFillForegroundColor(IndexedColors.GOLD.getIndex());
            styleGold.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            CellStyle styleLavender = workbook.createCellStyle();
            styleLavender.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
            styleLavender.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFSheet sheet = workbook.getSheet("Sayfa1");
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(rowCount+1);
            Cell cell = row.createCell(0);
            cell.setCellValue(slnFiles.get(0).getName());
            cell.setCellStyle(styleLavender);
            for (int i=0;i<csProjFiles.size();i++) {
                Cell cell1 = sheet.createRow(sheet.getLastRowNum()+1).createCell(1);
                cell1.setCellValue(csProjFiles.get(i).getName());
                cell1.setCellStyle(styleAqua);
                ArrayList<String> assRefs = rf.assemblyReferansReturner(csProjFiles.get(i));
                ArrayList<String> proRefs = rf.projectReferansReturner(csProjFiles.get(i));
                Cell cell3 = sheet.createRow(sheet.getLastRowNum()+1).createCell(2);
                cell3.setCellValue("Assembly References");
                cell3.setCellStyle(styleGold);
                for (int z=0;z<assRefs.size();z++){
                    Cell cell2 = sheet.createRow(sheet.getLastRowNum()+1).createCell(2);
                    cell2.setCellValue(assRefs.get(z));
                }
                Cell cell4 = sheet.createRow(sheet.getLastRowNum()+1).createCell(2);
                cell4.setCellValue("Project References");
                cell4.setCellStyle(styleGold);
                for (int z=0;z<proRefs.size();z++){
                    Cell cell2 = sheet.createRow(sheet.getLastRowNum()+1).createCell(2);
                    cell2.setCellValue(proRefs.get(z));
                }

            }
            try (FileOutputStream outputStream = new FileOutputStream(excelPath)) {
                workbook.write(outputStream);
                System.out.println("### Mapping writing to : "+excelPath+" ###");
                workbook.close();
            }catch (Exception e){
                System.out.println("ERROR : "+e.getLocalizedMessage());
            }
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

    }
}