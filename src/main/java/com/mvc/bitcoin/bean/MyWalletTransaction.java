package com.mvc.bitcoin.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.Wallet;
import org.springframework.context.annotation.Primary;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * MyWalletTransaction
 *
 * @author qiyichen
 * @create 2018/3/1 11:43
 */
@Data
public class MyWalletTransaction implements Serializable {
    private static final long serialVersionUID = -8380806472534356994L;
    @Id
    private String hash;
    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date updatedAt;
    @ColumnType(jdbcType = JdbcType.BIGINT)
    private Long value;
    private String valueStr;
    private String feeStr;
    @ColumnType(jdbcType = JdbcType.BIGINT)
    private Long fee;
    @ColumnType(jdbcType = JdbcType.BIGINT)
    private Long version;
    @ColumnType(jdbcType = JdbcType.INTEGER)
    private Integer depth;
    private String fromAddress;
    private String toAddress;

    public static MyWalletTransaction build(Transaction trans, Wallet wallet) {
        MyWalletTransaction transaction = new MyWalletTransaction();
        transaction.setHash(trans.getHashAsString());
        transaction.setFeeStr(null == trans.getFee() ? "0" : trans.getFee().toFriendlyString());
        transaction.setFee(null == trans.getFee() ? 0 : trans.getFee().getValue());
        transaction.setValueStr(trans.getValue(wallet).toFriendlyString());
        transaction.setVersion(trans.getVersion());
        String from = JSON.toJSONString(trans.getInputs().stream().map(obj -> obj.getFromAddress().toString()).collect(Collectors.toList()));
        transaction.setFromAddress(from);
        String to = JSON.toJSONString(trans.getOutputs().stream().map(obj -> obj.getAddressFromP2PKHScript(wallet.getParams()).toString()).collect(Collectors.toList()));
        transaction.setToAddress(to);
        transaction.setDepth(trans.getConfidence().getDepthInBlocks());
        transaction.setValue(trans.getValue(wallet).getValue());
        transaction.setUpdatedAt(trans.getUpdateTime());
        return transaction;
    }

}
