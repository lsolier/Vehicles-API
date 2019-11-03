package com.lsolier.udacity.vehiclesapi.api;

import static com.lsolier.udacity.vehiclesapi.domain.model.Condition.USED;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.lsolier.udacity.vehiclesapi.client.maps.MapsClient;
import com.lsolier.udacity.vehiclesapi.client.prices.PriceClient;
import com.lsolier.udacity.vehiclesapi.domain.model.Location;
import com.lsolier.udacity.vehiclesapi.domain.model.car.Car;
import com.lsolier.udacity.vehiclesapi.domain.model.car.Details;
import com.lsolier.udacity.vehiclesapi.domain.model.manufacturer.Manufacturer;
import com.lsolier.udacity.vehiclesapi.service.CarService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.Collections;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<Car> json;

  @MockBean
  private CarService carService;

  @MockBean
  private PriceClient priceClient;

  @MockBean
  private MapsClient mapsClient;

  /**
   * Creates pre-requisites for testing, such as an example car.
   */
  @Before
  public void setup() {
    Car car = getCar();
    car.setId(1L);
    given(carService.save(any())).willReturn(car);
    given(carService.findById(any())).willReturn(car);
    given(carService.list()).willReturn(Collections.singletonList(car));

    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  /**
   * Tests for successful creation of new car in the system
   * @throws Exception when car creation fails in the system
   */
  @Test
  public void createCar() throws Exception {
    Car car = getCar();
    URI uri = new URI("/cars");
    mvc.perform(
        post(uri)
            .with(user("adminTest").password("password").roles("ADMIN"))
            .content(json.write(car).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated());
  }

  /**
   * Tests if the read operation appropriately returns a list of vehicles.
   * @throws Exception if the read operation of the vehicle list fails
   */
  @Test
  public void listCars() throws Exception {
    URI uri = new URI("/cars");
    mvc.perform(
        get(uri)
            .with(user("admin2").password("pass").roles("ADMIN"))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.carList[0].id", is(1)))
        .andExpect(jsonPath("$._embedded.carList[0].condition", is(getCar().getCondition().name())));
  }

  /**
   * Tests the read operation for a single car by ID.
   * @throws Exception if the read operation for a single car fails
   */
  @Test
  public void findCar() throws Exception {
    URI uri = new URI("/cars/1");
    MvcResult result = mvc.perform(
        get(uri)
            .with(user("admin3").password("pass").roles("ADMIN"))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.condition", is(getCar().getCondition().name())))
        .andReturn();

    Car car = new Gson().fromJson(result.getResponse().getContentAsString(), Car.class);
    Assert.assertEquals(car.getId(), Long.valueOf(1));
    Assert.assertEquals(car.getCondition(), getCar().getCondition());
  }

  /**
   * Creates an example Car object for use in testing.
   * @return an example Car object
   */
  private Car getCar() {
    Car car = new Car();
    car.setLocation(new Location(40.730610, -73.935242));
    Details details = new Details();
    Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
    details.setManufacturer(manufacturer);
    details.setModel("Impala");
    details.setMileage(32280);
    details.setExternalColor("white");
    details.setBody("sedan");
    details.setEngine("3.6L V6");
    details.setFuelType("Gasoline");
    details.setModelYear(2018);
    details.setProductionYear(2018);
    details.setNumberOfDoors(4);
    car.setDetails(details);
    car.setCondition(USED);
    return car;
  }
}