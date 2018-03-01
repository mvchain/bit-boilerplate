package com.mvc.bitcoin.service;

import com.mvc.bitcoin.bean.WatchAddress;
import com.mvc.bitcoin.mapper.MyWalletTransactionMapper;
import com.mvc.bitcoin.mapper.WatchAddressMapper;
import com.mvc.bitcoin.bean.MyWalletTransaction;
import lombok.extern.java.Log;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * bitCoin service
 *
 * @author qiyichen
 * @create 2018/2/26 12:05
 */
@Service
@Log
public class BitCoinService {

    @Autowired
    private WalletAppKit kit;
    @Autowired
    private WatchAddressMapper watchAddressMapper;
    @Autowired
    private MyWalletTransactionMapper myWalletTransactionMapper;

    /**
     * get balance from third web wallet, bitcoinj can not get from balance with any address
     */
    public String getBalance() {
        return kit.wallet().getBalance().toFriendlyString();
    }

    /**
     * create wallet.
     *
     * @return
     */
    public String addAddress() {
        Address address = kit.wallet().freshReceiveAddress();
        saveWatchAddress(address.toString());
        return address.toString();
    }

    /**
     * import wallet by privateKey
     *
     * @param privateKey
     */
    public void importAccount(BigInteger privateKey) {
        Integer size = kit.wallet().getImportedKeys().size();
        Assert.isTrue(size == 0, "wallet exist. please create a new wallet");
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        kit.wallet().importKey(ecKey);
    }

    /**
     * export private key.
     *
     * @param pass
     * @return
     */
    public BigInteger exportPrivKey(String pass) {
        kit.wallet().decrypt(pass);
        ECKey ecKey = kit.wallet().getImportedKeys().get(0);
        return ecKey.getPrivKey();
    }

    /**
     * send coins
     *
     * @return
     */
    public String sendCoins(String coin, String toAddress, String pass) throws InsufficientMoneyException {
//        Assert.isTrue(kit.wallet().checkPassword(pass), "password is wrong");
        log.info("send money to: " + toAddress);
        Coin value = Coin.parseCoin(coin);
        // if the wallet have more than 1 ecKey, we need to choose one for pay
        Address to = Address.fromBase58(kit.params(), toAddress);
//        kit.wallet().decrypt(pass);
        Wallet.SendResult result = kit.wallet().sendCoins(kit.peerGroup(), to, value);
        log.info("coins sent. transaction hash: " + result.tx.getHashAsString());
        return result.tx.getHashAsString();
    }

    /**
     * get all trans.
     *
     * @return
     */
    public List<MyWalletTransaction> getTrans() {
        Iterable<WalletTransaction> transIterable = kit.wallet().getWalletTransactions();
        List<MyWalletTransaction> result = new ArrayList();
        transIterable.forEach(obj -> {
            result.add(MyWalletTransaction.build(obj.getTransaction(), kit.wallet()));
        });
        return result;
    }

    /**
     * get trans by hash.
     *
     * @return
     */
    public MyWalletTransaction getTransByHash(String hash) {
        Transaction trans = kit.wallet().getTransaction(Sha256Hash.wrap(hash));
        MyWalletTransaction transaction = MyWalletTransaction.build(trans, kit.wallet());
        return transaction;
    }

    /**
     * when create new wallet, save it.
     * when the server started, add to watch.
     *
     * @param address
     */
    private void saveWatchAddress(String address) {
        WatchAddress watchAddress = new WatchAddress();
        watchAddress.setAddress(address);
        watchAddressMapper.insert(watchAddress);
    }

    /**
     * init wallet with password
     *
     * @param pass
     */
    public String init(String pass) {
        if (kit.wallet().getImportedKeys().size() == 0) {
            ECKey ecKey = new ECKey();
            kit.wallet().importKeysAndEncrypt(Arrays.asList(ecKey), pass);
        }
        return address();
    }

    /**
     * get address. if the wallet have more than one address, should remove it.
     *
     * @return
     */
    public String address() {
        List<ECKey> keys = kit.wallet().getImportedKeys();
        if (keys.size() == 0) {
            return null;
        }
        return keys.get(0).toAddress(kit.params()).toString();
    }

    public void insertOrUpdateTrans(MyWalletTransaction transaction) {
        if (null == myWalletTransactionMapper.selectByPrimaryKey(transaction.getHash())) {
            myWalletTransactionMapper.insert(transaction);
        } else {
            myWalletTransactionMapper.updateByPrimaryKey(transaction);
        }
    }

}