package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pojo.todo.todo;
import pojo.user.users;
import resources.APIResources;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static resources.Utils.getRequestSpecification;

public class MyStepdefs {
    List<Integer> list;
    ObjectMapper om;

    @Given("User has the todo tasks")
    public void user_has_the_todo_tasks() throws IOException {
        om = new ObjectMapper();
        String response = given().spec(getRequestSpecification()).log().all()
                .when().get("todos")
                .then().log().all().extract().response().asString();
        todo[] root = om.readValue(response, todo[].class);
        int todosTasks = root.length;
        Assert.assertEquals(200,todosTasks);
    }

    @Given("User belongs to the city FanCode")
    public void user_belongs_to_the_city_fan_code() throws IOException {
        om = new ObjectMapper();
        list = new ArrayList<>();
        String usersList = given().spec(getRequestSpecification())
                .when().get(APIResources.UsersAPI.getResource())
                .then().extract().response().asString();
        users[] users = om.readValue(usersList, users[].class);
        for (int i = 0; i<users.length; i++) {
            int lat = (int) Double.parseDouble(users[i].getAddress().getGeo().getLat());
            int lag = (int) Double.parseDouble(users[i].getAddress().getGeo().getLng());
            if(lat <=5 && lat >= -40 && lag <=100 && lag >=5) {
                list.add(users[i].getId());
            }
        }
    }

    @Then("User Completed task percentage should be greater than {int}%")
    public void user_completed_task_percentage_should_be_greater_than(Integer int1)throws IOException {
        om = new ObjectMapper();
        int count=0;
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i<list.size(); i++) {
            String users = given().spec(getRequestSpecification())
                    .queryParam("userId", list.get(i))
                    .when().get(APIResources.TodosAPI.getResource())
                    .then().extract().asString();
            todo[] todo1 = om.readValue(users, todo[].class);
            int totalSize = todo1.length;
            for (int j = 0; j<totalSize; j++) {
                if (todo1[j].isCompleted()) {
                    count++;
                }
            }
            if (count>=totalSize/2 && count<=totalSize){
                softAssert.assertTrue(true, "Completed task percentage is greater than " +int1);
            }else {
                softAssert.assertFalse(false, "Completed task percentage is less than " +int1);
            }
            count = 0;
        }
    }
}
