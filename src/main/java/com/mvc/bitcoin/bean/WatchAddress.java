package com.mvc.bitcoin.bean;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * WatchAddress
 *
 * @author qiyichen
 * @create 2018/2/28 14:31
 */
@Data
public class WatchAddress {

    private BigInteger id;
    private String address;
    private Date createdAt;
    private Date updatedAt;

}
