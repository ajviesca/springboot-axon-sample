package com.ing.asia.bps3.infrastrcuture.domain.biller;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BillerRepositoryImpl implements BillerRepository {

    private final BillerEntityJPA billerEntityJPA;

    public BillerRepositoryImpl(BillerEntityJPA billerEntityJPA) {
        this.billerEntityJPA = billerEntityJPA;
    }

    @Override
    public List<Biller> findAll() {
        List<BillerEntity> billers = billerEntityJPA.findAll();

        return billers.stream().map(billerEntity ->
                new Biller(billerEntity.getId(), billerEntity.getBillerName())
        ).collect(Collectors.toList());
    }

    @Override
    public Biller findById(Long id) {
        BillerEntity billerEntity = billerEntityJPA.findById(id).get();
        return new Biller(billerEntity.getId(), billerEntity.getBillerName());
    }
}
