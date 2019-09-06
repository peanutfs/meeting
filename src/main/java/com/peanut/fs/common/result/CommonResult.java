package com.peanut.fs.common.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@ToString
public class CommonResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Integer successCode = 0;
	
	public static final Integer failureCode = -1;
	
	private Integer success;
	
	private String message;
	
	private T data;
	
	private List<T> dataList;
	
	public CommonResult() {
		this.success = successCode;
	}


	public CommonResult(Integer success, String message) {
		this.success = success;
		this.message = message;
	}

	public CommonResult(Integer success, T data) {
		this.success = success;
		this.data = data;
	}

	public CommonResult(Integer success, List<T> dataList) {
		this.success = success;
		this.dataList = dataList;
	}
	
}
