package com.ecom.testissues;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CircularServiceB {
    private final CircularServiceA serviceA;

    public String callA() {
        return serviceA.respond();
    }

    public String respond() {
        return "B response";
    }
}
