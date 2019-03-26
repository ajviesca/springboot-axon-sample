package com.ing.asia.bps3.core.domain.biller;

import java.util.List;

public interface BillerRepository {
    List<Biller> findAll();

    Biller findById(Long id);

}
