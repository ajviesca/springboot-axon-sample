package com.ing.asia.bps3.presentation.biller;


import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/biller")
public class BillerController {

    private final BillerService billerService;

    public BillerController(BillerService billerService) {
        this.billerService = billerService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Biller>> getAllBillers() {
        return ResponseEntity.ok(billerService.getAllBillers());
    }
}
