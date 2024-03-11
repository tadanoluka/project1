package com.tadanoluka.project1.storage.parsers;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
@Getter
class WagonTxtParser implements WagonParser {

    private final String supportedFileExtension = "txt";

    @Override
    public boolean isFileExtensionSupported(String fileExtension) {
        return supportedFileExtension.equals(fileExtension);
    }

    private boolean isRowValid(String row) {
        if (row.isBlank()) return false;

        String temp = row.replaceAll("\\s+", "").replaceAll("/", "").replaceAll("0", "");
        return !temp.isBlank();
    }

    @Override
    public List<DummyWagonFromParser> parseWagons(Path path) {
        try (Scanner scanner = new Scanner(path.toFile())) {
            List<DummyWagonFromParser> wagons = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine().trim().replaceAll("\\s+", " ");

                if (!isRowValid(row)) continue;

                String[] rowArr = row.split("\\s");
                long wagonId = Long.parseLong(rowArr[1]);
                int weight = Integer.parseInt(rowArr[3]);
                int destinationStationId = Integer.parseInt(rowArr[4]);
                int freightId = Integer.parseInt(rowArr[5]);
                int consigneeId = Integer.parseInt(rowArr[6]);

                wagons.add(new DummyWagonFromParser(wagonId, destinationStationId, freightId, consigneeId, weight));
            }
            return wagons;
        } catch (FileNotFoundException e) {
            throw new WagonParserException("File not found", e);
        } catch (NumberFormatException e) {
            throw new WagonParserException("Number format error", e);
        }
    }
}
