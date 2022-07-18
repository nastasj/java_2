package reqres.in;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class HttpTests {

    private int id;

    @Test
    public void testGetUser() throws IOException {
        JsonObject user = (JsonObject) getUser();
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
        String userData2 = updateUser(updatedUser);
        System.out.println(userData2);
        Assert.assertEquals(new Gson().fromJson(userData2,JsonObject.class).get("name").toString()
                .replace("\"", ""),"Petya");
        Assert.assertEquals(new Gson().fromJson(userData2,JsonObject.class).get("job").toString()
                .replace("\"", ""),"Dev");
    }

    private Object getUser() throws IOException {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 13);
        String json = Request.Get("https://reqres.in/api/users/" + randomNum).execute().returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("data");

    }

    public String createUser(User newUser) throws IOException {
//        String json = Request.Post("https://reqres.in/api/users/")
//                .bodyForm(new BasicNameValuePair("name", newUser.getName()),
//                            new BasicNameValuePair("job", newUser.getJob()))
//                .execute().returnContent().asString();
//        JsonElement parsed = new JsonParser().parse(json);
//        HttpResponse response = client.execute( get );
//        int code = response.getStatusLine().getStatusCode();
//        InputStream body = response.getEntity().getContent();
//        return json;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://reqres.in/api/users/");
        ArrayList<NameValuePair> postParameters = new ArrayList<>(2);
        postParameters.add(new BasicNameValuePair("name", newUser.getName()));
        postParameters.add(new BasicNameValuePair("job", newUser.getJob()));
        httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        HttpResponse httpResponse = httpclient.execute(httppost);
        HttpEntity responseEntity = httpResponse.getEntity();
        String response = null;
        if (httpResponse.getStatusLine().getStatusCode() == 201) {
            response = EntityUtils.toString(responseEntity);
        }
        int id = new Gson().fromJson(response,JsonObject.class).get("id").getAsInt();
        System.out.println(id);
        return response;
    }

    private String updateUser(User updatedUser) throws IOException {
        String userData = createUser(updatedUser);
        int id = new Gson().fromJson(userData,JsonObject.class).get("id").getAsInt();
        System.out.println(id);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPatch httppatch = new HttpPatch("https://reqres.in/api/users/" + id);
        ArrayList<NameValuePair> patchParameters = new ArrayList<>(2);
        patchParameters.add(new BasicNameValuePair("name", updatedUser.getName()));
        patchParameters.add(new BasicNameValuePair("job", updatedUser.getJob()));
        httppatch.setEntity(new UrlEncodedFormEntity(patchParameters));
        HttpResponse httpResponse = httpclient.execute(httppatch);
        HttpEntity responseEntity = httpResponse.getEntity();
        String response = null;
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            response = EntityUtils.toString(responseEntity);
        }
        return response;
    }

}
