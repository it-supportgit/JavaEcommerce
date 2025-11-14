package com.ecom.testissues;

import org.springframework.stereotype.Service;

@Service
public class PricingService {

    public double getDiscountFactor(double percent) {
        return 1 - percent / 100.0;
    }
}
