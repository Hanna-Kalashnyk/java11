package com.exadel.discount.controller;

import com.exadel.discount.model.entity.*;
import com.exadel.discount.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AddSomeDataForTests {

    DiscountRepository discountRepository;
    CategoryRepository categoryRepository;
    VendorRepository vendorRepository;
    VendorLocationRepository vendorLocationRepository;
    TagRepository tagRepository;
    CityRepository cityRepository;
    Vendor testVendor;
    CountryRepository countryRepository;
    Country testCountry;
    City testCity;
    VendorLocation testVendorLocation1;
    VendorLocation testVendorLocation2;
    Category testCategory;
    Tag testTag;
    Discount testDiscount;

    public AddSomeDataForTests() {

    }

    void newData() {
        addData();
        //      Add created entities into database
        synchronized (this) {
            vendorRepository.save(testVendor);
            countryRepository.save(testCountry);
            cityRepository.save(testCity);
            vendorLocationRepository.save(testVendorLocation1);
            vendorLocationRepository.save(testVendorLocation2);
            categoryRepository.save(testCategory);
            tagRepository.save(testTag);
            discountRepository.save(testDiscount);
        }
    }

    public void addData() {
        //create new vendor for test
        testVendor = new Vendor();
        testVendor.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d2"));
        testVendor.setName("Dog stuff");

        //create new Country for test
        testCountry = new Country();
        testCountry.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d7"));
        testCountry.setName("Ukraine");

        //create new city for test
        testCity = new City();
        testCity.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d8"));
        testCity.setName("Kyiv");
        testCity.setCountry(testCountry);

        //create new vendor locations for test
        Set<VendorLocation> testVendorLocations = new HashSet<>();
        testVendorLocation1 = new VendorLocation();
        testVendorLocation1.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d3"));
        Point point1 = new Point();
        point1.setLatitude(24.3454111);
        point1.setLongitude(10.123111);
        testVendorLocation1.setVendor(testVendor);
        testVendorLocation1.setCity(testCity);
        testVendorLocation1.setCountry(testCountry);

        testVendorLocation2 = new VendorLocation();
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
        testCategory = new Category();
        testCategory.setName("Dogs");
        testCategory.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d6"));

        //create new tags for test
        Set<Tag> tags = new HashSet<>();
        testTag = new Tag();
        testTag.setName("Pets");
        testTag.setId(UUID.fromString
                ("93577f24-f68f-403e-aa04-0a60c3a445d5"));
        tags.add(testTag);

        //create new discount for test
        testDiscount = new Discount();
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
    }
}
