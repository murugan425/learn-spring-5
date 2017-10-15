package tamil.learn.springframework.learnspringrecipeapp.controllers;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;
import tamil.learn.springframework.learnspringrecipeapp.services.RecipeService;
import tamil.learn.springframework.learnspringrecipeapp.utils.ExcelColumn;
import tamil.learn.springframework.learnspringrecipeapp.utils.ExcelHead;
import tamil.learn.springframework.learnspringrecipeapp.utils.ExcelHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
@Controller
public class ExportExcelSampleController {
    private RecipeService recipeService;

    public ExportExcelSampleController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/exportExcel")
    @ResponseBody
    public byte[] exportExcel(HttpServletResponse response) {
        List<Recipe> list = new ArrayList<Recipe>(recipeService.getAllRecipes());
        ExcelHead excelHead = new ExcelHead();
        List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
        int headerCount = 0;
        excelColumns.add(new ExcelColumn(headerCount++, "id", "PK", 9000));
        excelColumns.add(new ExcelColumn(headerCount++, "description", "Description", 27000));
        excelColumns.add(new ExcelColumn(headerCount++, "prepTime", "Prepare Time", 27000));
        excelColumns.add(new ExcelColumn(headerCount++, "cookTime", "Cook Time", 5898150));
        excelColumns.add(new ExcelColumn(headerCount++, "servings", "Servings", 9000));
        excelColumns.add(new ExcelColumn(headerCount++, "source", "Source", 9000));
        excelColumns.add(new ExcelColumn(headerCount++, "url", "Url", 9000));

        excelHead.setColumns(excelColumns);
        excelHead.setColumnCount(headerCount);

        SXSSFWorkbook workBook = new SXSSFWorkbook(100);
        ExcelHelper.getInstanse().buildExcelData(workBook, excelHead, "Sheet1", list);

        byte[] workBookDataBtyes = null;
        // exporting
        try {
            ByteArrayOutputStream workBookOutputStream = new ByteArrayOutputStream();
            workBook.write(workBookOutputStream);
            workBookOutputStream.flush();
            workBookDataBtyes = workBookOutputStream.toByteArray();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-disposition", "attachment;filename=" + "test.xls");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return workBookDataBtyes;
    }
}
