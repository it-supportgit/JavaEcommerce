package com.ecom.testissues;

import org.springframework.stereotype.Component;

@Component
public class InfiniteLoopService {

    public void runawayLoop() {
        while (true) {
            int x = 1 + 1;
            if (x == 3) {
                break;
            }
        }
    }
}
