package com.tadanoluka.project1.storage.parsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
@Getter
class WagonXlsxParser implements WagonParser{

    private final String supportedFileExtension = "xlsx";

    @Override
    public boolean isFileExtensionSupported(String fileExtension) {
        return supportedFileExtension.equals(fileExtension);
    }

    private Optional<Double> getCellDoubleValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING -> {
                return Optional.of(Double.parseDouble(cell.getStringCellValue()));
            }
            case NUMERIC -> {
                return Optional.of(cell.getNumericCellValue());
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    @Override
    public List<DummyWagonFromParser> parseWagons(Path path) {
        try (
            FileInputStream fileInputStream = new FileInputStream(path.toFile());
            Workbook workbook = new XSSFWorkbook(fileInputStream)
        ) {
            List<DummyWagonFromParser> wagons = new ArrayList<>();
            for (Sheet sheet : workbook) {
                int startRowIndex = 11;
                int totalRows = sheet.getPhysicalNumberOfRows();
                for (int rowIndex = startRowIndex; rowIndex < totalRows; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    long wagonId = getCellDoubleValue(row.getCell(1)).orElseThrow().longValue();
                    int destinationStationId = getCellDoubleValue(row.getCell(4)).orElseThrow().intValue();
                    int freightId = getCellDoubleValue(row.getCell(5)).orElseThrow().intValue();
                    int consigneeId = getCellDoubleValue(row.getCell(7)).orElseThrow().intValue();
                    int weight = getCellDoubleValue(row.getCell(3)).orElseThrow().intValue();


                    wagons.add(new DummyWagonFromParser(wagonId, destinationStationId, freightId, consigneeId, weight));
                }
            }
            return wagons;
        } catch (FileNotFoundException e) {
            throw new WagonParserException("File not found", e);
        } catch (IOException e) {
            throw new WagonParserException("Failed to read stored files", e);
        } catch (NoSuchElementException e) {
            throw new WagonParserException("Could not found data in file", e);
        }
    }
}
