package com.ratelimit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ratelimit.factory.HotelsFactory;
import com.ratelimit.pojo.Hotel;

@RestController
@EnableAutoConfiguration
public class HotelsController {

	private static List<Hotel> hotelsFromCSV;

	static {
		hotelsFromCSV = HotelsFactory.getHotelsFromCSV();
	}

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/hotels")
	List<Hotel> getHotels() {
		return hotelsFromCSV;
	}


	@RequestMapping("/hotelsByCityId/{id}")
	List<Hotel> getHotelsByCityId(@PathVariable int id, @RequestParam(value = "sort", required = false, defaultValue="ASC") String sort) {
		List<Hotel> hotelsByCityId = new ArrayList<Hotel>();
		for (Hotel hotelForEach : hotelsFromCSV) {
			if (id == hotelForEach.getCityId()) {
				hotelsByCityId.add(hotelForEach);
			}
		}
		if(sort.equalsIgnoreCase("ASC"))
			Collections.sort(hotelsByCityId, Hotel.HotelsSortByPriceASC);
		else
			Collections.sort(hotelsByCityId, Hotel.HotelsSortByPriceDESC);
		return hotelsByCityId;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HotelsController.class, args);
	}

}
