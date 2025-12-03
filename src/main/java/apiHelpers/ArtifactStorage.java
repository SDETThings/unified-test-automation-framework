package apiHelpers;

import com.google.gson.JsonObject;
import io.restassured.response.Response;

public class ArtifactStorage {
    private final Response response;
    private final JsonObject payload;  // can be null for GET

    public ArtifactStorage(Response response, JsonObject payload) {
        this.response = response;
        this.payload = payload;
    }

    public Response getResponse() {
        return response;
    }

    public JsonObject getPayload() {
        return payload;  // may be null
    }

    public boolean hasPayload() {
        return payload != null;
    }
}
