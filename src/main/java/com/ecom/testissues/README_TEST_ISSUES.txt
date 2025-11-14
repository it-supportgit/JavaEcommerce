Test issues for automated PR detection.
Files added under com.ecom.testissues:

- CircularServiceA.java / CircularServiceB.java : introduce circular constructor injection
- InfiniteLoopService.java : contains an intentional infinite loop
- CheckoutService.java & PricingService.java : complex logical error in discount calculation
- ProductControllerTestIssue.java & OrderServiceTest.java : multi-file method signature mismatch

Purpose: create PR with these changes to validate n8n AI reviewer detects the issues.
