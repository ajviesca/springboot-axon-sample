package com.ing.asia.bps3.infrastrcuture.domain.biller;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BillerRepositoryImpl implements BillerRepository {

    private final BillerJPA billerJPA;

    public BillerRepositoryImpl(BillerJPA billerJPA) {
        this.billerJPA = billerJPA;
    }

    @Override
    public List<Biller> findAll() {
        List<BillerEntity> billers = billerJPA.findAll();
        return billers.stream().map(getBillerEntityToBillerMapper()).collect(Collectors.toList());
    }

    @Override
    public Biller findById(Long id) {
        BillerEntity billerEntity = billerJPA.findById(id).get();
        return toBiller(billerEntity);
    }

    private Function<BillerEntity, Biller> getBillerEntityToBillerMapper() {
        return billerEntity -> toBiller(billerEntity);
    }

    private Biller toBiller(BillerEntity billerEntity) {
        return new Biller(billerEntity.getId(), billerEntity.getBillerName());
    }
}
