package com.mvc.bitcoin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * SendCoinDTO
 *
 * @author qiyichen
 * @create 2018/2/28 16:29
 */
@Data
public class SendCoinDTO implements Serializable {
    private static final long serialVersionUID = -8514641983237013266L;

    private String pass;
    private Float value;
    private String toAddress;

}
