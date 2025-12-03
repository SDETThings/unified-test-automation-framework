package api.testImplementation;

import api.requestBuilder.headerConstruction.HeaderBuilder;
import api.requestBuilder.payloadCreation.PayloadBuilder;
import api.requestBuilder.uriConstruction.UrlBuilder;
import api.requestExecutor.InvokeApiRequest;
import apiHelpers.*;
import base.BaseFunctions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataHelpers.MasterDataUtils;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class ApiFlows extends BaseFunctions {
    MasterDataUtils masterDataUtils;
    RequestConstructor requestConstructor;
    UrlBuilder urlBuilder;
    InvokeApiRequest invokeApiRequest;
    public ArtifactStorage performHealthCheck(Method method, String name, String clientId, String environment, JsonObject iterationDetail, int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.HEALTH_CHECK_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.HEALTH_CHECK_ENDPOINT_IDENTIFIER);
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.HEALTH_CHECK_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null,null);
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.HEALTH_CHECK_ENDPOINT_IDENTIFIER);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().addHeaders(rawHeaders).buildAsMap();
        // 4. Construct paylaod
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.HEALTH_CHECK_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields);
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,null,finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.getAsString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
    public ArtifactStorage userRegistration(Method method,String name,String clientId,String environment,JsonObject iterationDetail, int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.USER_REGISTRATION_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.USER_REGISTRATION_ENDPOINT_IDENTIFIER);
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.USER_REGISTRATION_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null,null);
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.USER_REGISTRATION_ENDPOINT_IDENTIFIER);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().addHeaders(rawHeaders).buildAsMap();
        // 4. Construct paylaod
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.USER_REGISTRATION_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields);
        String[] emailParts = finalPayload.get("email").getAsString().split("@");
        finalPayload.add("name", JsonParser.parseString(finalPayload.get("name").getAsString()+generateRandomLetters(4)));
        finalPayload.add("email", JsonParser.parseString(emailParts[0]+generateRandomLetters(4)+"@"+emailParts[1]));
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,"application/json",finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.toString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
    public ArtifactStorage userLogin(Method method, String name, String clientId, String environment, JsonObject iterationDetail, Response chainResponse, JsonObject previousPayload,int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.USER_LOGIN_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.USER_LOGIN_ENDPOINT_IDENTIFIER);
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.USER_LOGIN_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null,null);
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.USER_LOGIN_ENDPOINT_IDENTIFIER);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().addHeaders(rawHeaders).buildAsMap();
        // 4. Construct paylaod
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.USER_LOGIN_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields, List.of(chainResponse),List.of(previousPayload));
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,"application/json",finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.toString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
    public ArtifactStorage createNote(Method method,String name,String clientId,String environment,JsonObject iterationDetail,Map<String,String> tlTokenMap,int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.CREATE_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.CREATE_NOTE_ENDPOINT_IDENTIFIER);
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.CREATE_NOTE_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null,null);
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.CREATE_NOTE_ENDPOINT_IDENTIFIER);
        rawHeaders.putAll(tlTokenMap);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().buildDynamicHeaders(rawHeaders).buildAsMap();
        // 4. Construct paylaod
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.CREATE_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields);
        finalPayload.addProperty("title", finalPayload.get("title").getAsString()+generateRandomNumbers(2));
        finalPayload.addProperty("description", finalPayload.get("description").getAsString()+finalPayload.get("title").getAsString().substring(finalPayload.get("title").getAsString().length()-2));
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,"application/json",finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.toString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
    public ArtifactStorage fetchAllNotes(Method method,String name,String clientId,String environment,JsonObject iterationDetail,Map<String,String> tlTokenMap,int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.FETCH_ALL_NOTES_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.FETCH_ALL_NOTES_ENDPOINT_IDENTIFIER);
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.FETCH_ALL_NOTES_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null,null);
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.FETCH_ALL_NOTES_ENDPOINT_IDENTIFIER);
        rawHeaders.putAll(tlTokenMap);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().buildDynamicHeaders(rawHeaders).buildAsMap();
        // 4. Construct paylaod
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.FETCH_ALL_NOTES_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields);
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,"application/json",finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.toString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
    public ArtifactStorage fetchSingleNote(Method method,String name,String clientId,String environment,JsonObject iterationDetail,Map<String,String> tlTokenMap,Response chainedResponse,String validationType,int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.FETCH_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.FETCH_NOTE_ENDPOINT_IDENTIFIER).get(validationType).getAsJsonObject();
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.FETCH_NOTE_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null, Map.of("id",chainedResponse.jsonPath().get("data.id").toString()));
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.FETCH_NOTE_ENDPOINT_IDENTIFIER);
        rawHeaders.putAll(tlTokenMap);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().buildDynamicHeaders(rawHeaders).buildAsMap();
        // 4. Construct payload
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.FETCH_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields);
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,"application/json",finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.toString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
    public ArtifactStorage updateNote(Method method,String name,String clientId,String environment,JsonObject iterationDetail,Map<String,String> tlTokenMap,Response chainedResponse,int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.UPDATE_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.UPDATE_NOTE_ENDPOINT_IDENTIFIER);
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.UPDATE_NOTE_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null, Map.of("id",chainedResponse.jsonPath().get("data.id").toString()));
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.UPDATE_NOTE_ENDPOINT_IDENTIFIER);
        rawHeaders.putAll(tlTokenMap);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().buildDynamicHeaders(rawHeaders).buildAsMap();
        // 4. Construct payload
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.UPDATE_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields,List.of(chainedResponse),null);
        finalPayload.addProperty("title", finalPayload.get("title").getAsString()+" - updated");
        finalPayload.addProperty("description", finalPayload.get("description").getAsString()+" - updated");
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,"application/json",finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.toString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
    public ArtifactStorage deleteNote(Method method,String name,String clientId,String environment,JsonObject iterationDetail,Map<String,String> tlTokenMap,Response chainedResponse,int expectedStatusCode) throws FileNotFoundException {
        masterDataUtils = new MasterDataUtils();
        requestConstructor = new RequestConstructor();
        requestConstructor = new RequestConstructor();
        urlBuilder = new UrlBuilder();
        invokeApiRequest = new InvokeApiRequest();
        // 1. Retrieve test data json for request body
        JsonObject requestAlterationFields = masterDataUtils.accessApiRequestData(name,iterationDetail, EndpointIdentifiers.DELETE_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject responseValidationFields = masterDataUtils.accessApiResponseData(name,iterationDetail, EndpointIdentifiers.DELETE_NOTE_ENDPOINT_IDENTIFIER);
        // 2. Construct url
        String rawBaseUrl = requestConstructor.getPracticeExtendBaseUrl(clientId, environment);
        String rawEndpointUrl = requestConstructor.getPracticeExtendEndpointUrl(clientId, EndpointIdentifiers.DELETE_NOTE_ENDPOINT_IDENTIFIER);
        String finalUrl = urlBuilder.buildRequestUrl(rawBaseUrl,null,rawEndpointUrl,null, Map.of("id",chainedResponse.jsonPath().get("data.id").toString()));
        // 3. Construct headers
        Map<String,String> rawHeaders = requestConstructor.getPracticeExtendHeaders(clientId,environment, EndpointIdentifiers.DELETE_NOTE_ENDPOINT_IDENTIFIER);
        rawHeaders.putAll(tlTokenMap);
        Map<String,String> finalHeaders = HeaderBuilder.newBuilder().buildDynamicHeaders(rawHeaders).buildAsMap();
        // 4. Construct payload
        JsonObject rawPayload = requestConstructor.getPracticeExtendPayload(clientId,environment, EndpointIdentifiers.DELETE_NOTE_ENDPOINT_IDENTIFIER);
        JsonObject finalPayload = PayloadBuilder.getPayloadBuilderInstance().buildRequestPayload( rawPayload,requestAlterationFields,null,null);
        // 5. Invoke request
        Response response = invokeApiRequest.requestAsync(method,"application/json",finalUrl,finalHeaders,null,finalPayload.isEmpty()?null:finalPayload.toString(),null,null,null);
        // 6. Validate response attributes
        Assert.assertTrue(ResponseValidator.getResponseValidatorInstance().statusCodeChecker(response, expectedStatusCode));
        Map<String,JsonElement> mismatches = ResponseValidator.getResponseValidatorInstance().validateResponse(response,responseValidationFields);
        Assert.assertTrue(mismatches.isEmpty(), "Response validation failed. Mismatches: " + mismatches);

        return new ArtifactStorage(response,finalPayload);
    }
}
