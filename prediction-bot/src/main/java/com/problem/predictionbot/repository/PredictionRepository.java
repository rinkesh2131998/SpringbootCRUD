package com.problem.predictionbot.repository;

import com.problem.predictionbot.model.HouseInformation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * repository for query services.
 */
@Repository
public interface PredictionRepository extends JpaRepository<HouseInformation, Long> {
  List<HouseInformation> findAllByPriceBetween(Double minPrice, Double maxPrice);

//  List<HouseInformation> findBySqftLivingGreater(Long sqftLiving);

//  @Query("select info from HouseInformation info where info.yrBuilt > :year and info.yrRenovated > :year")
//  List<HouseInformation> findAllWithBuiltOrRenovatedYearAfter(@Param("year") Long year);
}
