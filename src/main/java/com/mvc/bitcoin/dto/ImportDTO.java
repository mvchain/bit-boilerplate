package com.mvc.bitcoin.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * ImportDTO
 *
 * @author qiyichen
 * @create 2018/2/28 16:09
 */
@Data
public class ImportDTO  implements Serializable{

    private static final long serialVersionUID = 4184235219581894510L;

    private BigInteger privateKey;
}
