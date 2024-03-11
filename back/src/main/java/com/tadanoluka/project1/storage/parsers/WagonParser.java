package com.tadanoluka.project1.storage.parsers;

import com.tadanoluka.project1.database.entity.Wagon;

import java.nio.file.Path;
import java.util.List;

public interface WagonParser {

    boolean isFileExtensionSupported(String fileExtension);

    List<DummyWagonFromParser> parseWagons(Path pathToFile);
}
