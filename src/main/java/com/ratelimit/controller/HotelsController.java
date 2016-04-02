package com.ratelimit.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ratelimit.factory.HotelsFactory;
import com.ratelimit.pojo.APIKey;
import com.ratelimit.pojo.GenericResponse;
import com.ratelimit.pojo.Hotel;

@RestController
@EnableConfigurationProperties
public class HotelsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HotelsController.class);

	@Value("${timeIntervalPerRequestInMilliSeconds}")
	Long timeIntervalPerRequestInMilliSeconds;

	@Value("${suspendedTimeIntervalInMilliSeconds}")
	Long suspendedTimeIntervalInMilliSeconds;

	@Value("${limit}")
	int limit;

	@Autowired
	private ServletContext servletContext;

	private static List<Hotel> hotelsFromCSV;

	static {
		LOGGER.info("loading the CSV file into List of Objects");
		hotelsFromCSV = HotelsFactory.getHotelsFromCSV();
		LOGGER.info("loaded the CSV file into List of Objects");
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public Long getTimeIntervalPerRequestInMilliSeconds() {
		return timeIntervalPerRequestInMilliSeconds;
	}

	public Long getSuspendedTimeIntervalInMilliSeconds() {
		return suspendedTimeIntervalInMilliSeconds;
	}

	@RequestMapping("/")
	GenericResponse home() {
		LOGGER.info("Welcome, API Key Rate Limit");
		return new GenericResponse(200, "Welcome, API Key Rate Limit", null);
	}

	@RequestMapping("/generateAPIKey")
	GenericResponse generateAPIKey() {
		LOGGER.info("generating API Key");
		try {
			InetAddress.getLocalHost();
			return new GenericResponse(200, "SUCCESS", InetAddress.getLocalHost().getHostAddress().replace(".", ""));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return new GenericResponse(500, "FAILED to generate API Key", null);
		}
	}

	@RequestMapping("/hotels")
	GenericResponse getHotels() {
		return new GenericResponse(200, "SUCCESS", hotelsFromCSV);
	}

	@RequestMapping("/hotelsByCityId/{aPIKey}/{id}")
	GenericResponse getHotelsByCityId(@PathVariable int id, @PathVariable String aPIKey,
			@RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort) {
		GenericResponse genericResponse = new GenericResponse(500, "FAILED", null);
		APIKey aPIKeyIns = null;
		if (this.getServletContext().getAttribute(aPIKey) != null) {
			aPIKeyIns = (APIKey) this.getServletContext().getAttribute(aPIKey);
			Long diffOfLastTime = System.currentTimeMillis() - aPIKeyIns.getLastHttpCallTimestamp();
			if (aPIKeyIns.getCount().get() != 0) {
				if (aPIKeyIns.getCount().get() <= limit) {
					if (diffOfLastTime <= timeIntervalPerRequestInMilliSeconds) {
						genericResponse.setStatus(500);
						genericResponse.setMsg("REQUEST REJECTED for next "
								+ timeIntervalPerRequestInMilliSeconds / 1000.0f + " seconds.");
					} else {
						getHotelList(id, sort, genericResponse, aPIKeyIns);
					}
				} else {
					aPIKeyIns.getCount().set(0);
					aPIKeyIns.setLastHttpCallTimestamp(System.currentTimeMillis());
					genericResponse.setStatus(500);
					genericResponse.setMsg("REQUEST REJECTED for next " + suspendedTimeIntervalInMilliSeconds / 60000.0f
							+ " seconds.");
				}
			} else {
				if (diffOfLastTime <= suspendedTimeIntervalInMilliSeconds) {
					genericResponse.setStatus(500);
					genericResponse.setMsg("REQUEST REJECTED for next " + suspendedTimeIntervalInMilliSeconds / 60000.0f
							+ " minutes.");
				} else {
					getHotelList(id, sort, genericResponse, aPIKeyIns);
				}
			}
		} else {
			this.getServletContext().setAttribute(aPIKey, new APIKey(new AtomicInteger(1), System.currentTimeMillis()));
			genericResponse.setStatus(200);
			genericResponse.setMsg("SUCCESS");
			List<Hotel> hotelsByCityId = new ArrayList<Hotel>();
			for (Hotel hotelForEach : hotelsFromCSV) {
				if (id == hotelForEach.getCityId()) {
					hotelsByCityId.add(hotelForEach);
				}
			}
			if (sort.equalsIgnoreCase("ASC"))
				Collections.sort(hotelsByCityId, Hotel.HotelsSortByPriceASC);
			else
				Collections.sort(hotelsByCityId, Hotel.HotelsSortByPriceDESC);
			genericResponse.setObj(hotelsByCityId);
		}
		return genericResponse;
	}

	private void getHotelList(int id, String sort, GenericResponse genericResponse, APIKey aPIKeyIns) {
		genericResponse.setStatus(200);
		genericResponse.setMsg("SUCCESS");
		aPIKeyIns.getCount().getAndIncrement();
		aPIKeyIns.setLastHttpCallTimestamp(System.currentTimeMillis());
		List<Hotel> hotelsByCityId = new ArrayList<Hotel>();
		for (Hotel hotelForEach : hotelsFromCSV) {
			if (id == hotelForEach.getCityId()) {
				hotelsByCityId.add(hotelForEach);
			}
		}
		if (sort.equalsIgnoreCase("ASC"))
			Collections.sort(hotelsByCityId, Hotel.HotelsSortByPriceASC);
		else
			Collections.sort(hotelsByCityId, Hotel.HotelsSortByPriceDESC);
		genericResponse.setObj(hotelsByCityId);
	}

}