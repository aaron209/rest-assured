package config;

import java.util.Map;

public class RequestBody {

    public String setRequestBody(String requestBodyClassName, Map<String, Object> finalTestData){
        if(requestBodyClassName.equalsIgnoreCase("CreateRecord")){
            return CreateRecordRequestBody.setRequestBody(finalTestData);
        }
        return null;
    }
}
