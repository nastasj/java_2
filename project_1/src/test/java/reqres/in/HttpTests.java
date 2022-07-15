package reqres.in;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class HttpTests {

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
        User newUser = new User().withName("Nastya").withJob("QA");
        String userData = createUser(newUser);
        System.out.println(userData);
    }

    private Object getUser() throws IOException {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 13);
        String json = Request.Get("https://reqres.in/api/users/" + randomNum).execute().returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("data");

    }

    private String createUser(User newUser) throws IOException {
//        String json = Request.Post("https://reqres.in/api/users/")
//                .bodyForm(new BasicNameValuePair("name", newUser.getName()),
//                            new BasicNameValuePair("job", newUser.getJob()))
//                .execute().returnContent().asString();
//        JsonElement parsed = new JsonParser().parse(json);
//        HttpResponse response = client.execute( get );
//        int code = response.getStatusLine().getStatusCode();
//        InputStream body = response.getEntity().getContent();
//        return j;
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
        return response;
    }

}
