package dataHelpers;

import apiHelpers.RequestConstructor;
import base.BaseFunctions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Method;

public class DataProvider extends BaseFunctions {
    RequestConstructor requestConstructor;
    private ThreadLocal<Object[]> tl = new ThreadLocal();
    public  Object[] setData(JsonArray array) {
        for(int i=0;i<array.size();i++)
        {
            tl.get()[i]=array.get(i);
        }
        return tl.get();
    }

    @org.testng.annotations.DataProvider(name = "getApiDataIterations")
    public Object[] getApiTestCaseData(Method method) throws IOException {
        requestConstructor = new RequestConstructor();
        MasterDataUtils masterDataUtils = new MasterDataUtils();
        String testCaseId = method.getName();
        String clientId = getTestCaseMetaDataBlock(testCaseId).get("client-id").getAsString();
        JsonObject completeMasterData = requestConstructor.readTestDataJson(clientId);
        JsonArray iterationDetailsArray = masterDataUtils.getIterationDataSetsFromMasterData(testCaseId, "API", completeMasterData);
        Object[] data = new Object[iterationDetailsArray.size()];
        this.tl.set(data);
        return setData(iterationDetailsArray);
    }
    @org.testng.annotations.DataProvider(name = "getWebDataIterations")
    public Object[] getWebTestCaseData(Method method) throws IOException {
        requestConstructor = new RequestConstructor();
        MasterDataUtils masterDataUtils = new MasterDataUtils();
        String testCaseId = method.getName();
        String clientId = getTestCaseMetaDataBlock(testCaseId).get("client-id").getAsString();
        JsonObject completeMasterData = requestConstructor.readTestDataJson(clientId);
        JsonArray iterationDetailsArray = masterDataUtils.getIterationDataSetsFromMasterData(testCaseId, "WEB", completeMasterData);
        Object[] data = new Object[iterationDetailsArray.size()];
        this.tl.set(data);
        return setData(iterationDetailsArray);
    }

}
