package reqres.in;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Helper {

    public Object getUser() throws IOException {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 13);
        String json = Request.Get("https://reqres.in/api/users/" + randomNum).execute().returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("data");

    }

    public String createUser(User newUser) throws IOException {
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
        int id = new Gson().fromJson(response, JsonObject.class).get("id").getAsInt();
        System.out.println(id);
        return response;
    }

    public String updateUser(User updatedUser) throws IOException {
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

    public HttpResponse deleteUser(User deletedUser) throws IOException {
        String userData = createUser(deletedUser);
        int id = new Gson().fromJson(userData,JsonObject.class).get("id").getAsInt();
        System.out.println(id);
        HttpClient httpclient = new DefaultHttpClient();
        HttpDelete httdelete = new HttpDelete("https://reqres.in/api/users/" + id);
        HttpResponse httpResponse = httpclient.execute(httdelete);
        return httpResponse;
    }
}
