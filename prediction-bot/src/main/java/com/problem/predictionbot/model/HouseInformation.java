package com.problem.predictionbot.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * database entity for house tables.
 */
@Data
@Entity
//@Table
public class HouseInformation {

  private static final long serialVersionUID = 5551707547269388327L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "date")
  @Temporal(TemporalType.DATE)
  private  Date date;
  @Column(name="price")
  private  Double price;
  @Column(name="bedrooms")
  private  Double bedrooms;
  @Column(name = "bathrooms")
  private  Double bathrooms;
  @Column(name = "sqft_living")
  private  Long sqftLiving;
  @Column(name = "sqft_lot")
  private  Long sqftLoft;
  @Column(name="floors")
  private  Double floors;
  @Column(name="waterfront")
  private  Long waterfront;
  @Column(name="view")
  private  Long view;
  @Column(name="condition")
  private  Long condition;
  @Column(name="sqft_above")
  private  Long sqftAbove;
  @Column(name="sqft_basement")
  private  Long sqftBasement;
  @Column(name = "yr_built")
  private  Long yrBuilt;
  @Column(name = "yr_renovated")
  private  Long yrRenovated;
  @Column(name = "street")
  private  String street;
  @Column(name = "city")
  private  String city;
  @Column(name = "statezip")
  private  String statezip;
  @Column(name = "country")
  private  String country;

  public HouseInformation() {
    super();
  }

  public HouseInformation(Date date, Double price, Double bedrooms, Double bathrooms,
                          Long sqftLiving, Long sqftLoft, Double floors, Long waterfront,
                          Long view, Long condition, Long sqftAbove, Long sqftBasement,
                          Long yrBuilt, Long yrRenovated, String street, String city,
                          String statezip, String country) {
    this.date = date;
    this.price = price;
    this.bedrooms = bedrooms;
    this.bathrooms = bathrooms;
    this.sqftLiving = sqftLiving;
    this.sqftLoft = sqftLoft;
    this.floors = floors;
    this.waterfront = waterfront;
    this.view = view;
    this.condition = condition;
    this.sqftAbove = sqftAbove;
    this.sqftBasement = sqftBasement;
    this.yrBuilt = yrBuilt;
    this.yrRenovated = yrRenovated;
    this.street = street;
    this.city = city;
    this.statezip = statezip;
    this.country = country;
  }
}
