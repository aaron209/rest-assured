package library;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlReader {

    private Map<String, Map<String, Map<String, Object>>>  initialTestData= null;
    private Map<String, Map<String, Object>> defaultTestData = null;
    private String _fileName;

    public YamlReader(String fileName){
        _fileName = "src/test/java/testData/"+fileName+".yaml";
    }

    public Map<String, Map<String, Map<String, Object>>> getTestData() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream fileInputStream = new FileInputStream(_fileName);
        initialTestData = yaml.load(fileInputStream);
        return initialTestData;
    }

    public Map<String, Map<String, Object>> getDefaultData() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream fileInputStream = new FileInputStream(_fileName);
        defaultTestData = yaml.load(fileInputStream);
        return  defaultTestData;
    }

    public Map<String, Object> getFinalTestData(Map<String, Object> defaultTestData, Map<String, Object> currentTestData){
        Map<String, Object> finalTestMap = new HashMap<>();
        for(Map.Entry<String, Object> current : currentTestData.entrySet()){
            boolean uniqueKey = true;
            for(Map.Entry<String, Object> defaultData : defaultTestData.entrySet()){
                if(current.getKey().equals(defaultData.getKey())) {
                    defaultData.setValue(current.getValue());
                    finalTestMap.put(defaultData.getKey(), defaultData.getValue());
                    uniqueKey = false;
                    break;
                }else{
                    finalTestMap.put(defaultData.getKey(), defaultData.getValue());
                    if(uniqueKey){
                        finalTestMap.put(current.getKey(),current.getValue());
                    }
                }
            }
        }
        return finalTestMap;
    }
}
