package com.mvc.bitcoin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * InitDTO
 *
 * @author qiyichen
 * @create 2018/2/28 19:24
 */
@Data
public class InitDTO implements Serializable {
    private static final long serialVersionUID = 4233998411643369106L;

    private String pass;
}
