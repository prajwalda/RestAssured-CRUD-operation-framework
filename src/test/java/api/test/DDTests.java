package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DDTests {
    @Test(dataProviderClass = DataProviders.class , priority = 1, dataProvider = "Data")
    public void testPostUser(String userId, String userName, String fName, String lName, String useremail, String pwd, String ph) {

        User user = new User();
        user.setId(Integer.parseInt(userId));
        user.setUsername(userName);
        user.setFirstName(fName);
        user.setLastName(lName);
        user.setEmail(useremail);
        user.setPassword(pwd);
        user.setPhone(ph);

        Response response = UserEndpoints.createUser(user);

        Assert.assertEquals(response.getStatusCode(),200,"correct");

    }

    @Test
    public void testGetUser(){
        Response response = UserEndpoints.readUser("testUser1");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }

//    @Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
    @Test
    public void testDeleteUser(){
        Response response = UserEndpoints.deleteUser("testUser1");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test(priority = 2,dataProvider = "Data",dataProviderClass = DataProviders.class)
    public void testUpdateUser(String userId, String userName, String fName, String lName, String useremail, String pwd, String ph){

        User user = new User();
        user.setId(Integer.parseInt(userId));
        user.setUsername(userName);
        user.setFirstName(fName);
        user.setLastName(lName);
        user.setEmail(useremail);
        user.setPassword(pwd);
        user.setPhone(ph);

        Response response = UserEndpoints.updateUser("testUser1",user);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("firstName"),fName);
    }
}
