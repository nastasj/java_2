package reqres.in;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class HttpTests extends Helper {

    @Test
    public void testGetUser() throws IOException {
        JsonObject user = (JsonObject) getUser();
        System.out.println(user);
        Assert.assertTrue(user.has("id"));
        Assert.assertTrue(user.has("email"));
        Assert.assertTrue(user.has("first_name"));
        Assert.assertTrue(user.has("last_name"));
        Assert.assertTrue(user.has("avatar"));
    }

    @Test
    public void testCreateUser() throws IOException {
        User newUser = new User();
        newUser.setName("Nastya");
        newUser.setJob("QA");
        String userData = createUser(newUser);
        System.out.println(userData);
        Assert.assertEquals(new Gson().fromJson(userData,JsonObject.class).get("name").toString()
                .replace("\"", ""),"Nastya");
        Assert.assertEquals(new Gson().fromJson(userData,JsonObject.class).get("job").toString()
                .replace("\"", ""),"QA");

    }

    @Test
    public void testUpdateUser() throws IOException {
        User updatedUser = new User();
        updatedUser.setName("Petya");
        updatedUser.setJob("Dev");
        String userData = updateUser(updatedUser);
        System.out.println(userData);
        Assert.assertEquals(new Gson().fromJson(userData,JsonObject.class).get("name").toString()
                .replace("\"", ""),"Petya");
        Assert.assertEquals(new Gson().fromJson(userData,JsonObject.class).get("job").toString()
                .replace("\"", ""),"Dev");
    }

    @Test
    public void testDeleteUser() throws IOException {
        User deletedUser = new User();
        HttpResponse httpResponse = deleteUser(deletedUser);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 204);
    }



}
