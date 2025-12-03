package apiHelpers;

import base.BaseFunctions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import unifiedUtils.FileUtils;
import unifiedUtils.JsonOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class RequestConstructor extends BaseFunctions {
    JsonOperations jsonOperations;

    public RequestConstructor() throws FileNotFoundException {
    }

    public JsonArray readBaseUrlStoreJsonFile() {
        jsonOperations = new JsonOperations();
        return jsonOperations.readJsonFileAndReturnContentAsJsonElement(prop.getProperty("BaseUrlStoreJsonPath")).getAsJsonArray();
    }

    public JsonArray readEndpointStoreJsonFile() {
        jsonOperations = new JsonOperations();
        return jsonOperations.readJsonFileAndReturnContentAsJsonElement(prop.getProperty("EndpointStoreJsonPath")).getAsJsonArray();
    }

    public JsonArray readHeaderStoreJsonFile() {
        jsonOperations = new JsonOperations();
        return jsonOperations.readJsonFileAndReturnContentAsJsonElement(prop.getProperty("HeaderStoreJsonPath")).getAsJsonArray();
    }

    public JsonArray readPayloadStoreJsonFile() {
        jsonOperations = new JsonOperations();
        return jsonOperations.readJsonFileAndReturnContentAsJsonElement(prop.getProperty("PayloadStoreJsonPath")).getAsJsonArray();
    }

    public String getPracticeExtendBaseUrl(String client, String environment) {
        jsonOperations = new JsonOperations();
        JsonArray baseUrls = readBaseUrlStoreJsonFile();
        switch (client) {
            case "TEST_CLIENT_1" -> {
                switch (environment) {
                    case "DEVELOPMENT":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_1")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("DEVELOPMENT")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }
                    case "TESTING":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_1")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("TESTING")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }

                    case "PRODUCTION":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_1")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("PRODUCTION")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }
                    default:
                        throw new IllegalArgumentException("Invalid environment: " + environment);
                }
            }
            case "TEST_CLIENT_2" -> {
                switch (environment) {
                    case "DEVELOPMENT":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_2")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("DEVELOPMENT")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }
                    case "TESTING":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_2")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("TESTING")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }

                    case "PRODUCTION":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_2")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("PRODUCTION")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }
                    default:
                        throw new IllegalArgumentException("Invalid environment: " + environment);
                }
            }
            case "TEST_CLIENT_3" -> {
                switch (environment) {
                    case "DEVELOPMENT":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_3")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("DEVELOPMENT")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }
                    case "TESTING":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_3")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("TESTING")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }

                    case "PRODUCTION":
                        for (JsonElement clientElement : baseUrls) {
                            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals("TEST_CLIENT_3")) {
                                for (JsonElement environemntElement : clientElement.getAsJsonObject().get("ENDPOINT_BASE_URL_DETAILS").getAsJsonArray()) {
                                    if (environemntElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals("PRODUCTION")) {
                                        return environemntElement.getAsJsonObject().get("BASE_URL").getAsString();
                                    }
                                }
                            }
                        }
                    default:
                        throw new IllegalArgumentException("Invalid environment: " + environment);
                }
            }
            default -> throw new IllegalArgumentException("Invalid client: " + client);
        }
    }

    public String getPracticeExtendEndpointUrl(String client, String endpointIdenfifier) {
        String endpointUrl = null;
        jsonOperations = new JsonOperations();
        JsonArray endpoints = readEndpointStoreJsonFile();
        for (JsonElement clientElement : endpoints) {
            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals(client)) {
                for (JsonElement endpointElement : clientElement.getAsJsonObject().get("ENDPOINT_URL_DETAILS").getAsJsonArray()) {
                    if (endpointElement.getAsJsonObject().get("ENDPOINT_NAME").getAsString().equals(endpointIdenfifier)) {
                        endpointUrl = endpointElement.getAsJsonObject().get("ENDPOINT_URL").getAsString();
                        break;
                    }
                }
            }
            if(endpointUrl!=null)
            {
                break;
            }
        }

        return endpointUrl;
    }
    /* public JsonObject buildPracticeExtendPayload(String client, String environment, String payloadIdentifier) {
        JsonObject unalteredPayload = null;
        jsonOperations = new JsonOperations();
        JsonArray payloads = readPayloadStoreJsonFile();
        for (JsonElement clientElement : payloads) {
            if (clientElement.getAsJsonObject().get("CLIENT_NAME").getAsString().equals(client)) {
                for (JsonElement environmentElement : clientElement.getAsJsonObject().get("ENDPOINT_PAYLOAD_DETAILS").getAsJsonArray()) {
                    if (environmentElement.getAsJsonObject().get("ENVIRONMENT").getAsString().equals(environment)) {
                        for (JsonElement payloadElement : environmentElement.getAsJsonObject().get("PAYLOAD_DETAILS").getAsJsonArray()) {
                            if (payloadElement.getAsJsonObject().get("ENDPOINT_NAME").getAsString().equals(payloadIdentifier)) {
                                JsonElement payload = payloadElement.getAsJsonObject().get("RAW_PAYLOAD_JSON");
                                if (payload != null) {
                                    unalteredPayload = payload.getAsJsonObject();
                                }
                            } else {
                                throw new IllegalArgumentException("Invalid payload identifier: " + payloadIdentifier);
                            }
                        }
                    }else{
                        throw new IllegalArgumentException("Invalid environemnt: " + environment);
                    }
                }
            }
            else{
                throw new IllegalArgumentException("Invalid client: " + client);
            }

        }
        return unalteredPayload;
    }*/

    public Map<String, String> getPracticeExtendHeaders(String client, String environment, String endpointIdentifier) {
        jsonOperations = new JsonOperations();
        JsonArray headers = readHeaderStoreJsonFile();
        JsonObject commonHeaders = new JsonObject();
        JsonObject endpointHeaders = new JsonObject();
        for (JsonElement clients : headers) {
            if (clients.getAsJsonObject().get("CLIENT_NAME").getAsString().equalsIgnoreCase(client)) {
                JsonArray headerDetails = clients.getAsJsonObject().get("HEADER_DETAILS").getAsJsonArray();
                for (JsonElement environmentCheck : headerDetails) {
                    if (environmentCheck.getAsJsonObject().get("ENVIRONMENT").getAsString().equalsIgnoreCase(environment)) {
                        commonHeaders = environmentCheck.getAsJsonObject().get("COMMON_HEADERS").getAsJsonObject();
                        JsonArray endpointHeaderArray = environmentCheck.getAsJsonObject().get("ENDPOINT_SPECIFIC_HEADERS").getAsJsonArray();
                        for (JsonElement endpointCheck : endpointHeaderArray) {
                            if (endpointCheck.getAsJsonObject().get("ENDPOINT_IDENTIFIER").getAsString().equalsIgnoreCase(endpointIdentifier)) {
                                endpointHeaders = endpointCheck.getAsJsonObject().get("HEADERS").getAsJsonObject();
                                break;
                            }
                        }

                    }
                }
            }
        }
        // -------------------------------------------------------
        // 4) Build Final Map<String, String>
        // -------------------------------------------------------
        Map<String, String> headerMap = new HashMap<>();

        // Add common headers
        for (Map.Entry<String, JsonElement> entry : commonHeaders.entrySet()) {
            headerMap.put(entry.getKey(), entry.getValue().getAsString());
        }

        // Add endpoint-specific headers (override common if same key exists)
        for (Map.Entry<String, JsonElement> entry : endpointHeaders.entrySet()) {
            headerMap.put(entry.getKey(), entry.getValue().getAsString());
        }
        return headerMap;
    }

    public JsonObject getPracticeExtendPayload(String client, String environment, String payloadIdentifier) {
        jsonOperations = new JsonOperations();
        JsonArray payloads = readPayloadStoreJsonFile();

        // 1️⃣ Find matching client
        JsonObject clientObj = payloads.asList().stream()
                .map(JsonElement::getAsJsonObject)
                .filter(obj -> client.equals(obj.get("CLIENT_NAME").getAsString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid client: " + client));

        // 2️⃣ Find matching environment
        JsonObject envObj = clientObj.getAsJsonArray("ENDPOINT_PAYLOAD_DETAILS").asList().stream()
                .map(JsonElement::getAsJsonObject)
                .filter(obj -> environment.equals(obj.get("ENVIRONMENT").getAsString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid environment: " + environment));

        // 3️⃣ Find matching payload
        JsonObject payloadObj = envObj.getAsJsonArray("PAYLOAD_DETAILS").asList().stream()
                .map(JsonElement::getAsJsonObject)
                .filter(obj -> payloadIdentifier.equals(obj.get("ENDPOINT_NAME").getAsString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payload identifier: " + payloadIdentifier));

        // 4️⃣ Extract RAW_PAYLOAD_JSON safely
        JsonElement payload = payloadObj.get("RAW_PAYLOAD_JSON");

        if (payload == null || !payload.isJsonObject()) {
            payload = new JsonObject(); // or handle as needed
        }

        return payload.getAsJsonObject();
    }
    public JsonObject readTestDataJson(String client){
        jsonOperations = new JsonOperations();
        String testDataJsonPath = readClientTestDataJsonFile(client);
        return jsonOperations.readJsonFileAndReturnContentAsJsonElement(testDataJsonPath).getAsJsonObject();
    }


}
