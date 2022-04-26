package com.problem.predictionbot.service;

import com.problem.predictionbot.model.HouseInformation;
import com.problem.predictionbot.repository.PredictionRepository;
import com.problem.predictionbot.utility.CsvParserUtil;
import java.io.IOException;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class CsvServiceImpl implements CsvService{
  private final PredictionRepository repository;

  @Override
  public void save(MultipartFile file) throws IOException {
    log.info("Saving all csv records to database");
    final List<HouseInformation> houseInformations =
        CsvParserUtil.csvToHouseInformation(file.getInputStream());
    repository.saveAll(houseInformations);
  }

  @Override
  public List<HouseInformation> getAllHouseInformation() {
    log.info("Fetching all data from database");
    return repository.findAll();
  }
}
