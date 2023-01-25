package guru.qa;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipParsing {

    ClassLoader cl = ZipParsing.class.getClassLoader();

    @Test
    @DisplayName("Парсинг данных из zip архива")
    void zipParseTest() throws Exception {

        try (
                InputStream resource = cl.getResourceAsStream("Archives.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().endsWith(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(0)[0]).contains("John");

                } else if (entry.getName().endsWith(".pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("Windows & Linux keymap");

                } else if (entry.getName().contains(".xlsx")) {
                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(0).getCell(0)
                            .getStringCellValue()).contains("1. Внутренняя экономика (S1)");

                }
            }
        }
    }
}
