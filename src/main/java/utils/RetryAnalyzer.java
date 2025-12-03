package utils;

import base.BaseFunctions;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer extends BaseFunctions implements IRetryAnalyzer {
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        int maxRetryCount = 1;
        if (retryCount <= maxRetryCount) {
            retryCount++;
            //setRetryCount(retryCount);
            return true;
        }
        return false;

    }
}
