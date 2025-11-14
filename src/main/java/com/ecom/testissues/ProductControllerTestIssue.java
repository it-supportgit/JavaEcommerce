package com.ecom.testissues;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ProductControllerTestIssue {

    private final OrderServiceTest orderService;

    public ProductControllerTestIssue(OrderServiceTest orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/test/mismatch")
    public List<String> getAllOrders() {
        return orderService.getAllOrderIds();
    }
}
