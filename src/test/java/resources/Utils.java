package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {

   static RequestSpecification requestSpecification;

    public static String getGlobalValue(String key) throws IOException
    {
        Properties prop =new Properties();
        FileInputStream fis =new FileInputStream(System.getProperty("user.dir")+"//src//test//java//resources//global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static RequestSpecification getRequestSpecification() throws IOException {
        PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
        requestSpecification = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
        return requestSpecification;
    }
}

