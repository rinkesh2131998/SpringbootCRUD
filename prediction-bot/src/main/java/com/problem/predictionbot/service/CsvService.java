package com.problem.predictionbot.service;

import com.problem.predictionbot.model.HouseInformation;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * service for reading csv to database and getting all db items.
 */
@Service
public interface CsvService {

  /**
   * save csv recors to database
   * @param file in csv with records.
   */
  public void save(MultipartFile file) throws IOException;

  /**
   * fetch all recors from database.
   * @return list of all records
   */
  public List<HouseInformation> getAllHouseInformation();
}
