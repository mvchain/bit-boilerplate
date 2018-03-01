package com.mvc.bitcoin.controller;

import com.mvc.bitcoin.dto.ExportDTO;
import com.mvc.bitcoin.dto.ImportDTO;
import com.mvc.bitcoin.dto.InitDTO;
import com.mvc.bitcoin.dto.SendCoinDTO;
import com.mvc.bitcoin.service.BitCoinService;
import com.mvc.bitcoin.bean.MyWalletTransaction;
import com.mvc.common.msg.Result;
import com.mvc.common.msg.ResultGenerator;
import org.bitcoinj.core.InsufficientMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author qyc
 */
@Controller
@RequestMapping("bitCoin")
public class BitCoinController {

    @Autowired
    BitCoinService bitCoinService;

    @GetMapping("balance")
    @ResponseBody
    Result balance() {
        return ResultGenerator.genSuccessResult(bitCoinService.getBalance());
    }

    @PostMapping("newAddress")
    @ResponseBody
    Result createAccount() throws NoSuchAlgorithmException {
        return ResultGenerator.genSuccessResult(bitCoinService.addAddress());
    }

    @GetMapping("address")
    @ResponseBody
    Result allAddress() {
        return ResultGenerator.genSuccessResult(bitCoinService.address());
    }

    @PostMapping("import")
    @ResponseBody
    Result importAccount(@RequestBody ImportDTO importDTO) {
        bitCoinService.importAccount(importDTO.getPrivateKey());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("export")
    @ResponseBody
    Result export(@RequestBody ExportDTO exportDTO) {
        bitCoinService.exportPrivKey(exportDTO.getPassword());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("trans")
    @ResponseBody
    Result sendCoin(@RequestBody SendCoinDTO sendCoinDTO) throws InsufficientMoneyException {
        String result = bitCoinService.sendCoins(sendCoinDTO.getValue().toString(), sendCoinDTO.getToAddress(), sendCoinDTO.getPass());
        return ResultGenerator.genSuccessResult(result);
    }

    @GetMapping("trans")
    @ResponseBody
    Result getTrans() {
        List<MyWalletTransaction> result = bitCoinService.getTrans();
        return ResultGenerator.genSuccessResult(result);
    }

    @GetMapping("transByHash")
    @ResponseBody
    Result getTrans(@RequestParam String hash) {
        MyWalletTransaction result = bitCoinService.getTransByHash(hash);
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("init")
    @ResponseBody
    Result initWallet(@RequestBody InitDTO initDTO) {
        String result = bitCoinService.init(initDTO.getPass());
        return ResultGenerator.genSuccessResult(result);
    }

}