package com.mvc.bitcoin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ExportDTO
 *
 * @author qiyichen
 * @create 2018/2/28 16:16
 */
@Data
public class ExportDTO implements Serializable{


    private static final long serialVersionUID = 1648962488375150097L;

    private String password;

}
