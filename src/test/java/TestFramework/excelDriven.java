package TestFramework;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class excelDriven {

    @Test
    public void addBook() throws IOException {

        dataDriven d=new dataDriven();

        ArrayList data=d.getData("RestAddbook","RestAssured");

        HashMap<String, Object>  map = new HashMap<>();

        for (int i=0;i<data.size();i++) {

            if(i%5==0) {
                i++;
                map.put("name", data.get(i));
            }

            i++;
            map.put("isbn", data.get(i));
            i++;
            map.put("aisle", data.get(i));
            i++;
            map.put("author", data.get(i));

            RestAssured.baseURI="http://216.10.245.166";

            Response resp=given().
                    header("Content-Type","application/json").
                    body(map).
                    when().post("/Library/Addbook.php").
                    then().assertThat().statusCode(200).extract().response();

            JsonPath js= ReusableMethods.rawToJson(resp);
            String id=js.get("ID");
            String json = new ObjectMapper().writeValueAsString(map);
            System.out.println("Request_" + id + " : " + json);

        }

    }

    public static String GenerateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));

    }
}

