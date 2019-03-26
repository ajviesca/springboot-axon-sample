package com.ing.asia.bps3.infrastrcuture.domain.biller;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;

import java.util.List;

public class BillerServiceImpl implements BillerService {

    private final BillerRepository billerRepository;

    public BillerServiceImpl(BillerRepository billerRepository) {
        this.billerRepository = billerRepository;
    }

    @Override
    public List<Biller> getAllBillers() {
        return billerRepository.findAll();
    }
}
