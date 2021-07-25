package com.exadel.discount.controller;

import com.exadel.discount.model.entity.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiscountTRyIT extends AbstractIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestRestTemplate testRestTemplate; // available with Spring Web MVC

    @LocalServerPort
    private Integer port;

    // given
    @Before
    void setUp() {
        AddSomeDataForTests addSomeDataForTests = new AddSomeDataForTests();
        addSomeDataForTests.addData();
    }

    @WithMockUser(username = "admin@mail.com", roles = {"USER", "ADMIN"})
    @Test
    public void retrieveDiscountById() {
//        // given
//        given(superHeroRepository.getSuperHero(2))
//                .willReturn(new SuperHero("Rob", "Mannon", "RobotMan"));

        // when
        ResponseEntity<Discount> discountResponse = testRestTemplate.getForEntity("/discounts/5f69268b-705e-4fb9-8147-722b4ec1d9da", Discount.class);

        // then
        assertThat(discountResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(discountResponse.getBody().getCategory().getName().equals("Sport"));
    }

    @WithMockUser(username = "admin@mail.com", roles = {"USER", "ADMIN"})
    @Test
    public void postNewDiscount() {
        //given

        //create new vendor for test
        Vendor testVendor = new Vendor();
        testVendor.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d2"));
        testVendor.setName("Dog stuff");

        //create new Country for test
        Country testCountry = new Country();
        testCountry.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d7"));
        testCountry.setName("Ukraine");

        //create new city for test
        City testCity = new City();
        testCity.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d8"));
        testCity.setName("Kyiv");
        testCity.setCountry(testCountry);

        //create new vendor locations for test
        Set<VendorLocation> testVendorLocations = new HashSet<>();
        VendorLocation testVendorLocation1 = new VendorLocation();
        testVendorLocation1.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d3"));
        Point point1 = new Point();
        point1.setLatitude(24.3454111);
        point1.setLongitude(10.123111);
        testVendorLocation1.setVendor(testVendor);
        testVendorLocation1.setCity(testCity);
        testVendorLocation1.setCountry(testCountry);

        VendorLocation testVendorLocation2 = new VendorLocation();
        testVendorLocation2.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d4"));

        Point point2 = new Point();
        point2.setLatitude(24.3454222);
        point2.setLongitude(10.123222);
        testVendorLocation2.setVendor(testVendor);
        testVendorLocation1.setCity(testCity);
        testVendorLocation1.setCountry(testCountry);

        testVendorLocations.add(testVendorLocation1);
        testVendorLocations.add(testVendorLocation2);
        testVendor.setVendorLocations(testVendorLocations);

        //create new category for test
        Category testCategory = new Category();
        testCategory.setName("Dogs");
        testCategory.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d6"));

        //create new tags for test
        Set<Tag> tags = new HashSet<>();
        Tag testTag = new Tag();
        testTag.setName("Pets");
        testTag.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d5"));
        tags.add(testTag);

        //create new discount for test
        Discount testDiscount = new Discount();
        testDiscount.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d1"));
        testDiscount.setName("Discount on yoga weekdays");
        testDiscount.setDescription("Amazing yoga for two days. Almost free.");
        testDiscount.setPromo("7abcde7");
        testDiscount.setDiscountType(DiscountType.PERCENT);
        testDiscount.setValue(BigDecimal.valueOf(5));
        testDiscount.setStartTime(LocalDateTime.parse("2021-07-20T00:00:00"));
        testDiscount.setEndTime(LocalDateTime.parse("2021-08-20T00:00:00"));
        testDiscount.setActive(true);
        testDiscount.setArchived(false);
        testDiscount.setVendor(testVendor);

        testDiscount.setVendorLocations(testVendorLocations);
        testDiscount.setCategory(testCategory);

        // when


        this.webTestClient
                .get()
                .uri("/api/customers")
                .exchange()
                .expectStatus().is2xxSuccessful();

    }

    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"USER", "ADMIN"})
    void shouldBlockBookCreationForNonAdminUsers() {

        RestAssuredMockMvc
                .given()
                .auth().with(SecurityMockMvcRequestPostProcessors.user("admin@mail.com").roles("ADMIN"))
                .contentType("application/json")
                .body("{\"name\": \"Discount on yoga weekdays\", " +
                        "\"promo\": \"11aa3g7\"," +
                        "\"discountType\": \"PERCENT\"," +
                        "\"value\":\"10\"," +
                        "\"categoryId\": \"5a009936-ac14-4b4b-9121-3638122ea6b5\"," +
                        "\"vendorId\": \"3633f3cf-7208-4d67-923d-ce6b2cec29e2\", " +
                        "\"vendorLocationsIds\": [\"bb682ec1-c86a-4b64-b306-53346c189aca\", " +
                        "\"6089a6fb-572b-4f29-a2c6-46ac0a5fbca5\"]," +
                        "\"tagIds\": [\"537edc43-1616-4622-bf45-7b060e6d6471\"]}")
                .when()
                .post("/discounts")
                .then()
                // .statusCode(201)
                .assertThat(MockMvcResultMatchers.status().isOk())
                .assertThat(MockMvcResultMatchers.status().is2xxSuccessful())
                .assertThat(jsonPath("$.name").value("Discount on yoga weekdays"))
                .assertThat(jsonPath("$.vendor.id").value("3633f3cf-7208-4d67-923d-ce6b2cec29e2"));
    }
}
