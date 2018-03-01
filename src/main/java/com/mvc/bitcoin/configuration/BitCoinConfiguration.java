package com.mvc.bitcoin.configuration;

import com.mvc.bitcoin.service.BitCoinService;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * bitCoin config
 *
 * @author qiyichen
 * @create 2018/2/26 13:49
 */
@Configuration
public class BitCoinConfiguration {

    @Value("${bitcoin.block.env}")
    String blockEnv;
    private final static String TEST_KEY = "testnet";
    private final static String DEFAULT_FILE_PREFIX = "DEFAULT_FILE_PREFIX";
    @Value("${bitcoin.block.path}")
    String blockPath;

    @Bean
    public WalletAppKit defaultWalletAppKit() {
        WalletAppKit kit = new WalletAppKit(getNetWork(), new File(blockPath + blockEnv), DEFAULT_FILE_PREFIX);
        return kit;
    }

    /**
     * get net work by env
     *
     * @return
     */
    public NetworkParameters getNetWork() {
        NetworkParameters params = null;
        if (TEST_KEY.equalsIgnoreCase(blockEnv)) {
            params = TestNet3Params.get();
        } else {
            params = MainNetParams.get();
        }
        return params;

    }
}
