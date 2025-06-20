package com.archpatterns.walletmanager.dtos;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer code;
    private String detail;
    private String message;
    private String localizedException;

}

