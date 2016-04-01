package com.ratelimit.pojo;

public class GenericResponse {

	private int status;
	private String msg;
	private Object obj;

	public GenericResponse(int status, String msg, Object obj) {
		super();
		this.status = status;
		this.msg = msg;
		this.obj = obj;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	// Response<SomeObject>("200", "success!", new SomeObject());
}
