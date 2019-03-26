package com.ing.asia.bps3.infrastrcuture.domain.biller;

import com.ing.asia.bps3.core.entity.biller.Biller;
import com.ing.asia.bps3.core.entity.biller.BillerRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BillerRepositoryImpl implements BillerRepository {

    private final BillerJPA billerJPA;

    public BillerRepositoryImpl(BillerJPA billerJPA) {
        this.billerJPA = billerJPA;
    }

    @Override
    public List<Biller> findAll() {
        List<BillerEntity> billers = billerJPA.findAll();

        return billers.stream().map(billerEntity -> {
            return new Biller(billerEntity.getId(), billerEntity.getBillerName());
        }).collect(Collectors.toList());
    }

    @Override
    public Biller findById(Long id) {
        BillerEntity billerEntity = billerJPA.findById(id).get();
        return new Biller(billerEntity.getId(), billerEntity.getBillerName());
    }
}
