import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.*;

public class Extractor {

    private String fileName, workBookName;
    private int numOfRows;
    private HashMap<String, ArrayList<String>> excelInfo;
    private Workbook workBook;
    private Sheet sheet;

    public Extractor(String fileName, String workBookName) throws Exception{
        this.fileName = "./" + fileName + ".xlsx";
        this.workBookName = workBookName; //Workbook name may be different for each file
        excelInfo = new HashMap<>(); //HashMap key contains column title and values is an ArrayList
        workBook = WorkbookFactory.create(new FileInputStream(this.fileName));
        sheet = workBook.getSheet(this.workBookName);
        numOfRows = sheet.getLastRowNum();
    }

    //Extracts the excel data
    public HashMap extractInfo(){
        Row row = sheet.getRow(0); //Start at first row
        Cell cellTitle;
        for(int i=0; row.getCell(i)!=null; i++) {
            cellTitle = row.getCell(i); //First row contains titles
            excelInfo.put(convertCellToString(cellTitle), columnData(i));
        }
        return excelInfo;
    }

    //Retrieves column data under each title
    public ArrayList columnData(int column){
        ArrayList<String> tempList = new ArrayList<>();
        Row dataRow;
        for(int i=1; i<=numOfRows; i++){ //Looping through each row and getting the cell data
            dataRow = sheet.getRow(i);
            tempList.add(convertCellToString(dataRow.getCell(column)));
        }
        return tempList;
    }

    //Converts cell data into String
    public String convertCellToString(Cell cell){
        DataFormatter formatter = new DataFormatter();
        String cellString = formatter.formatCellValue(cell);
        return cellString;
    }
}