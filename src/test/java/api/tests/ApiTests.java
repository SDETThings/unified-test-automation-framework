package api.tests;

import api.testImplementation.ApiFlows;
import apiHelpers.ArtifactStorage;
import apiHelpers.EndpointIdentifiers;
import apiHelpers.StatusCodeList;
import com.google.gson.JsonElement;
import dataHelpers.DataProvider;
import io.restassured.http.Method;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class ApiTests extends ApiFlows {
    private static final ThreadLocal<String> testCaseId = new ThreadLocal<>();
    private static final ThreadLocal<String> environment = new ThreadLocal<>();
    private static final ThreadLocal<String> client = new ThreadLocal<>();
    private static ThreadLocal<Map<String,String>> practiceExtendUserJourneytoken = ThreadLocal.withInitial(ConcurrentHashMap::new);

    @BeforeMethod
    public void BeforeExecutionStart(ITestResult iTestResult) {
        testCaseId.set(iTestResult.getMethod().getMethodName());
        try {
            environment.set(getTestCaseMetaDataBlock(testCaseId.get()).get("environment").getAsString());
            client.set(getTestCaseMetaDataBlock(testCaseId.get()).get("client-id").getAsString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Test(description = "Validate health of Practice Extend application API",dataProvider = "getApiDataIterations", dataProviderClass = DataProvider.class, enabled = false)
    public void PE0001(JsonElement testDataSet){
        try {
            ArtifactStorage healthCheckResults = performHealthCheck(Method.GET,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(), StatusCodeList.STATUS_CODE_200);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Test(description = "Validate customer is able to register successfully using Practice Extend application API",dataProvider = "getApiDataIterations", dataProviderClass = DataProvider.class, enabled = false)
    public void PE0002(JsonElement testDataSet){
        try {
            ArtifactStorage registrationResults = userRegistration(Method.POST,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),StatusCodeList.STATUS_CODE_201);
            ArtifactStorage loginResults = userLogin(Method.POST,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),registrationResults.getResponse(),registrationResults.getPayload(), StatusCodeList.STATUS_CODE_200);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Test(description = "Validate customer is able to create/fetch/update/delete notes using Practice Extend application API",dataProvider = "getApiDataIterations", dataProviderClass = DataProvider.class, enabled = false)
    public void PE0003(JsonElement testDataSet){
        try {
            ArtifactStorage registrationResults = userRegistration(Method.POST,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(), StatusCodeList.STATUS_CODE_201);

            ArtifactStorage loginResults = userLogin(Method.POST,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),registrationResults.getResponse(),registrationResults.getPayload(), StatusCodeList.STATUS_CODE_200);
            practiceExtendUserJourneytoken.get().put("x-auth-token",loginResults.getResponse().jsonPath().get("data.token"));
            ArtifactStorage createNoteResults = createNote(Method.POST,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),practiceExtendUserJourneytoken.get(), StatusCodeList.STATUS_CODE_200);
            ArtifactStorage allNotesResults = fetchAllNotes(Method.GET,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),practiceExtendUserJourneytoken.get(), StatusCodeList.STATUS_CODE_200);
            ArtifactStorage singleNoteResults = fetchSingleNote(Method.GET,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),practiceExtendUserJourneytoken.get(),createNoteResults.getResponse(), EndpointIdentifiers.ENDPOINT_TESTDATA_SEQ_NUM_1, StatusCodeList.STATUS_CODE_200);
            ArtifactStorage updateNoteResults = updateNote(Method.PUT,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),practiceExtendUserJourneytoken.get(),createNoteResults.getResponse(), StatusCodeList.STATUS_CODE_200);
            ArtifactStorage deleteNoteResults = deleteNote(Method.DELETE,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),practiceExtendUserJourneytoken.get(),createNoteResults.getResponse(), StatusCodeList.STATUS_CODE_200);
            ArtifactStorage singleNoteAfterDeletionResults = fetchSingleNote(Method.GET,testCaseId.get(),client.get(),environment.get(),testDataSet.getAsJsonObject(),practiceExtendUserJourneytoken.get(),createNoteResults.getResponse(), EndpointIdentifiers.ENDPOINT_TESTDATA_SEQ_NUM_2, StatusCodeList.STATUS_CODE_404);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
