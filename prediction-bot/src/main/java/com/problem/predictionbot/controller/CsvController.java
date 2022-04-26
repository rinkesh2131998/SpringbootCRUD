package com.problem.predictionbot.controller;

import com.problem.predictionbot.exception.CsvFileUploadException;
import com.problem.predictionbot.exception.InvalidFileFormatException;
import com.problem.predictionbot.model.HouseInformation;
import com.problem.predictionbot.service.CsvService;
import com.problem.predictionbot.utility.CsvParserUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/csv")
public class CsvController {
  private final CsvService csvService;

  @PostMapping("/upload")
  public ResponseEntity<Object> uploadCsv(@RequestParam("file") MultipartFile file) {
    //validate if file is csv
    if (CsvParserUtil.hasCSVFormat(file)) {
      try {
        csvService.save(file);
        return new ResponseEntity<>(Collections.singletonMap("status", "Uploaded"), HttpStatus.OK);
      } catch (IOException ioException) {
        log.error("Error on uploading file for saving to database, cause: {}", ioException);
        throw new CsvFileUploadException("Cannot upload file to database");
      }
    } else {
      throw new InvalidFileFormatException("File Format not supported, upload csv file");
    }
  }

  @GetMapping("/houseInformation")
  public ResponseEntity<Object> getAllHouseInformation() {
    final List<HouseInformation> houseInformations = csvService.getAllHouseInformation();
    if (houseInformations.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(houseInformations, HttpStatus.OK);
    }
  }
}
