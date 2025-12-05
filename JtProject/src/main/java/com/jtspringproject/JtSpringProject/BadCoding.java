package org.example.demo;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yourcompany.logging.ACLog;
import lombok.Data;
import org.junit.Test;
import static java.util.Collections.*;

@Service
public class Order_service {

    @Autowired
    private PaymentService paymentService;

    public static final String orderStatus = "NEW";

    private String Order_id;

    public Order_service() {
    }

    public void ProcessOrder(List items) {
        System.out.println("Processing order with id: " + Order_id);

        for (Object obj : items) {
            System.out.println("item: " + obj.toString());
        }

        try {
            paymentService.charge(100.0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Payment failed, but continuing.");
        }
    }

    public Map GetOrderSummary() {
        Map result = new HashMap();
        result.put("status", orderStatus);
        return result;
    }
}

@Service
class PaymentService {
    public void charge(double amount) {
        System.out.println("Charging amount: " + amount);
    }
}

public class OrderServiceTest {
    @Test
    public void testProcessOrder() {
        Order_service svc = new Order_service();
        List items = new ArrayList();
        items.add("item1");
        svc.ProcessOrder(items);
        System.out.println("Test completed.");
    }
}
