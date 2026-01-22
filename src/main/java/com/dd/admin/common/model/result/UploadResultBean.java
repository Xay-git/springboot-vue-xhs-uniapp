package com.dd.admin.common.model.result;


import com.dd.admin.common.exception.enums.AbstractBaseExceptionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UploadResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("返回的状态码")
	private String status = "ok";
	@ApiModelProperty("返回的信息")
	private String data;
	@ApiModelProperty("返回的数据")
	private T result ;
}
