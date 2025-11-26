package com.ecom.testissues;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CircularServiceA {
    private final CircularServiceB serviceB;

    public String callB() {
        return serviceB.respond();
    }

    public String respond() {
        return "A response";
    }
}
