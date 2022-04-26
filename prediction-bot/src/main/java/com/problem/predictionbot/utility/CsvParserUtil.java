package com.problem.predictionbot.utility;

import com.problem.predictionbot.model.HouseInformation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.web.multipart.MultipartFile;

/**
 * utility to parse csv for dumping to database.
 */
@Slf4j
public class CsvParserUtil {
  public static String TYPE = "text/csv";

  public static boolean hasCSVFormat(MultipartFile file) {
    if (!TYPE.equals(file.getContentType())) {
      return false;
    }
    return true;
  }

  public static List<HouseInformation> csvToHouseInformation(InputStream inputStream) {
    try (final BufferedReader fileReader = new BufferedReader(
        new InputStreamReader(inputStream, "UTF-8"));
         final CSVParser csvParser = new CSVParser(fileReader,
             CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
      final List<HouseInformation> houseInformations = new ArrayList<>();
      final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      csvParser.getRecords().forEach(record -> {
        Date date = new Date();
        try {
          date = simpleDateFormat.parse(record.get("date"));
        } catch (ParseException e) {
          log.error("No dateTime found for record: {}", record);
        }
        final HouseInformation houseInformation =
            new HouseInformation(date, Double.parseDouble(record.get("price")),
                Double.parseDouble(record.get("bedrooms")),
                Double.parseDouble(record.get("bathrooms")),
                Long.parseLong(record.get("sqft_living")),
                Long.parseLong(record.get("sqft_lot")), Double.parseDouble(record.get("floors")),
                Long.parseLong(record.get("waterfront")), Long.parseLong(record.get("view")),
                Long.parseLong(record.get("condition")), Long.parseLong(record.get("sqft_above")),
                Long.parseLong(record.get("sqft_basement")), Long.parseLong(record.get("yr_built")),
                Long.parseLong(record.get("yr_renovated")), record.get("street"),
                record.get("city"), record.get("statezip"), record.get("country"));
        houseInformations.add(houseInformation);
      });
      return houseInformations;
    } catch (IOException ioException) {
      throw new RuntimeException("fail to parse CSV file: " + ioException.getMessage());
    }
  }

}
