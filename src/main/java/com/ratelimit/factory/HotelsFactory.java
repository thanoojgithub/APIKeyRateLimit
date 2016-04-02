package com.ratelimit.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ratelimit.pojo.Hotel;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Component
public class HotelsFactory {

	public static void main(String[] args) {
		List<Hotel> hotelsFromCSV = getHotelsFromCSV();
		System.out.println(hotelsFromCSV);
	}

	public static List<Hotel> getHotelsFromCSV() {
		List<Hotel> hotels = null;
		List<Hotel> hotelsWithCityId = new ArrayList<Hotel>();
		HeaderColumnNameTranslateMappingStrategy<Hotel> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Hotel>();
		beanStrategy.setType(Hotel.class);
		// CITY HOTELID ROOM PRICE
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("CITY", "city");
		columnMapping.put("HOTELID", "hotelId");
		columnMapping.put("ROOM", "room");
		columnMapping.put("PRICE", "price");
		beanStrategy.setColumnMapping(columnMapping);
		CsvToBean<Hotel> csvToBean = new CsvToBean<Hotel>();
		//CSV file should be in same folder of JAR file
		File f = new File("Hotels.csv");
		try (FileReader fileReader = new FileReader(f.getAbsolutePath())) {
			CSVReader reader = new CSVReader(fileReader, ',');
			hotels = csvToBean.parse(beanStrategy, reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Hotel hotel : hotels) {
			hotel.setCityId(hotel.getCity().hashCode());
			hotelsWithCityId.add(hotel);
		}
		Collections.sort(hotelsWithCityId);
		return hotelsWithCityId;
	}
}
