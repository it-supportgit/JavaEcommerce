package com.ecom.testissues;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutService {

    private final PricingService pricingService;

    public CheckoutService(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public BigDecimal calculateTotal(List<BigDecimal> itemPrices, BigDecimal discountPercent) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal p : itemPrices) {
            sum = sum.add(p);
        }
        BigDecimal total = sum.subtract(discountPercent);
        return total;
    }
}
