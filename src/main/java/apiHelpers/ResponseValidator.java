package apiHelpers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.Assert;
import unifiedReports.LoggerFactory;
import unifiedReports.requestLogger.APIConsoleLogger;

import java.util.HashMap;
import java.util.Map;

public class ResponseValidator {
    private ResponseValidator(){
    }
    public static ResponseValidator getResponseValidatorInstance(){
        return new ResponseValidator();
    }
    Map<String, JsonElement> mismatches;
    Gson gson = new Gson();

    public boolean statusCodeChecker(Response response, int expectedStatusCode) {
        if(response.getStatusCode() == expectedStatusCode){
            LoggerFactory.getApiConsoleLogger().logMessage("✅ Status Code Validation Passed: Expected " + expectedStatusCode + " and received " + response.getStatusCode());
            LoggerFactory.getApiReportLogger().logInfo("✅ Status Code Validation Passed: Expected " + expectedStatusCode + " and received " + response.getStatusCode());
            return true;
        }else{
            LoggerFactory.getApiConsoleLogger().logMessage("❌ Status Code Validation Failed: Expected " + expectedStatusCode + " but received " + response.getStatusCode());
            LoggerFactory.getApiReportLogger().logInfo("✅ Status Code Validation Passed: Expected " + expectedStatusCode + " and received " + response.getStatusCode());
            return false;
        }
    }

    public Map<String, JsonElement> validateResponse(Response response, JsonObject expectedResponseFields, Response... chainedResponse) {
        mismatches = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        for (String key : expectedResponseFields.keySet()) {
            JsonElement actualValue =  gson.toJsonTree(response.jsonPath().get(key));
            JsonElement expectedValue =  gson.toJsonTree(expectedResponseFields.get(key));
            if (expectedValue.isJsonPrimitive() && expectedValue.getAsJsonPrimitive().isString()) {
                if (expectedValue.getAsString().startsWith("%") || expectedValue.getAsString().startsWith("$")) {
                    if (chainedResponse.length > 0) {
                        Response previousResponse = chainedResponse[0];
                        JsonElement expectedChainedValue = previousResponse.jsonPath().get(expectedValue.getAsString().substring(2));
                        if (!actualValue.equals(expectedChainedValue)) {
                            mismatches.put(key, actualValue);
                        }
                    } else if (expectedValue.equals("not null")) {
                        if (actualValue.isJsonNull()) {
                            mismatches.put(key, null);
                        }
                    }
                } else {
                    if (expectedValue.getAsString().equals("not null")) {
                        if (actualValue.isJsonNull()) {
                            mismatches.put(key, null);
                        }
                    }else if (!actualValue.equals(expectedValue)) {
                        mismatches.put(key, expectedValue);
                    }
                }
            } else {
                if (!actualValue.equals(expectedValue)) {
                    mismatches.put(key, expectedValue);
                }
            }
        }
        LoggerFactory.getApiConsoleLogger().logValidationResults(mismatches);
        LoggerFactory.getApiReportLogger().logValidationResults(mismatches);

        return mismatches;
    }
}
