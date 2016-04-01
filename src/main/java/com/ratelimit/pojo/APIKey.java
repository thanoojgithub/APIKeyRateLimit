package com.ratelimit.pojo;

import java.util.concurrent.atomic.AtomicInteger;

public class APIKey {

	private AtomicInteger count;
	private Long lastHttpCallTimestamp;
	
	public APIKey(AtomicInteger count, long lastHttpCallTimestamp) {
		super();
		this.count = count;
		this.lastHttpCallTimestamp = lastHttpCallTimestamp;
	}

	public AtomicInteger getCount() {
		return count;
	}

	public void setCount(AtomicInteger count) {
		this.count = count;
	}

	public Long getLastHttpCallTimestamp() {
		return lastHttpCallTimestamp;
	}

	public void setLastHttpCallTimestamp(Long lastHttpCallTimestamp) {
		this.lastHttpCallTimestamp = lastHttpCallTimestamp;
	}

}
