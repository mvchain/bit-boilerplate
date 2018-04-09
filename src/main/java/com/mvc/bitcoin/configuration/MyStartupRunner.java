package com.mvc.bitcoin.configuration;

import com.mvc.bitcoin.bean.MyWalletTransaction;
import com.mvc.bitcoin.service.BitCoinService;
import lombok.extern.java.Log;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.listeners.TransactionConfidenceEventListener;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.KeyChainEventListener;
import org.bitcoinj.wallet.listeners.ScriptsChangeEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsSentEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qyc
 */
@Component
@Order(value = 1)
@Log
public class MyStartupRunner implements CommandLineRunner {

    @Autowired
    WalletAppKit kit;
    @Autowired
    private BitCoinService bitCoinService;


    @Override
    @Async
    public void run(String... args) throws InterruptedException, BlockStoreException {

        log.info("Start peer group");
        kit.startAsync();
        kit.awaitRunning();
        log.info("Downloading block chain");
        log.info("start listen");
        kit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
            @Override
            public void onCoinsReceived(Wallet wallet, Transaction transaction, Coin coin, Coin coin1) {
                System.out.println("coins received");
            }
        });

        kit.wallet().addCoinsSentEventListener(new WalletCoinsSentEventListener() {
            @Override
            public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
                System.out.println("coins sent");
            }
        });

        kit.wallet().addKeyChainEventListener(new KeyChainEventListener() {
            @Override
            public void onKeysAdded(List<ECKey> keys) {
                System.out.println("new key added");
            }
        });

        kit.wallet().addScriptsChangeEventListener(new ScriptsChangeEventListener() {
            @Override
            public void onScriptsChanged(Wallet wallet, List<Script> scripts, boolean isAddingScripts) {
                System.out.println("new script added");
            }
        });

        kit.wallet().addTransactionConfidenceEventListener(new TransactionConfidenceEventListener() {
            @Override
            public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
                MyWalletTransaction transaction = MyWalletTransaction.build(tx, wallet);
                bitCoinService.insertOrUpdateTrans(transaction);
                System.out.println("-----> confidence changed: " + tx.getHashAsString());
                TransactionConfidence confidence = tx.getConfidence();
                System.out.println("new block depth: " + confidence.getDepthInBlocks());
//                String str = JSON.toJSONString(tx);
//                System.out.println(str);
            }
        });

    }

}