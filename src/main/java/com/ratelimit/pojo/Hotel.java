package com.ratelimit.pojo;

import java.util.Comparator;

/**
 * CITY HOTELID ROOM PRICE Bangkok 1 Deluxe 1000
 * 
 * @author thanooj.kalathuru
 *
 */

public class Hotel implements Comparable<Hotel> {

	private String city;
	private int hotelId;
	private String room;
	private String price;
	private int cityId;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "Hotels [city=" + city + ", hotelId=" + hotelId + ", room=" + room + ", price=" + price + ", cityId="
				+ cityId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + cityId;
		result = prime * result + hotelId;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (cityId != other.cityId)
			return false;
		if (hotelId != other.hotelId)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}

	@Override
	public int compareTo(Hotel o) {
		return this.getCityId() - o.getCityId();
	}

	public static Comparator<Hotel> HotelsSortByPriceASC = new Comparator<Hotel>() {
		public int compare(Hotel h1, Hotel h2) {
			// ascending order
			return h1.getPrice().compareTo(h2.getPrice());
			// descending order
			// return fruitName2.compareTo(fruitName1);
		}
	};

	public static Comparator<Hotel> HotelsSortByPriceDESC = new Comparator<Hotel>() {
		public int compare(Hotel h1, Hotel h2) {
			// descending order
			return h2.getPrice().compareTo(h1.getPrice());
		}
	};

}