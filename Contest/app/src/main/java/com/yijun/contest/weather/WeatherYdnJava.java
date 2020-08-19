package com.yijun.contest.weather;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class WeatherYdnJava{
@RequiresApi(api= Build.VERSION_CODES.O)
public static void main(Context context)throws Exception{

        final String appId="LHyI4Ire";
        final String consumerKey="dj0yJmk9ZXN2TEN5S0FiNUI3JmQ9YWk9TEh5STRJcmUmcGo9MCZzPWNvbnN1bWVyc2VjcmV0JnN2PTAmeD0xMQ--".trim();
        final String consumerSecret="88cac39cf0cd7950fd5b4b177aa9ceffc98ebb0d".trim();
        final String url="https://weather-ydn-yql.media.yahoo.com/forecastrss?location=seoul&format=json&u=c";

        final long timestamp=new Date().getTime()/1000;
        byte[]nonce=new byte[32];
        Random rand=new Random();
        rand.nextBytes(nonce);
        final String oauthNonce=new String(nonce).replaceAll("\\W","").trim();

        List<String> parameters=new ArrayList<>();
        parameters.add("oauth_consumer_key="+consumerKey);
        parameters.add("oauth_nonce="+oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp="+timestamp);
        parameters.add("oauth_version=1.0");
//Makesurevalueisencoded
        parameters.add("location="+ URLEncoder.encode("sunnyvale,ca","UTF-8"));
        parameters.add("format=json");
        Collections.sort(parameters);

        StringBuffer parametersList=new StringBuffer();
        for(int i=0;i<parameters.size();i++){
        parametersList.append(((i>0)?"&":"")+parameters.get(i));
        }

        String signatureString="GET&"+
        URLEncoder.encode(url,"UTF-8")+"&"+
        URLEncoder.encode(parametersList.toString(),"UTF-8").trim();

        String signature=null;
        try{
        SecretKeySpec signingKey=new SecretKeySpec((consumerSecret+"&").getBytes(),"HmacSHA1");
        Mac mac= Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[]rawHMAC=mac.doFinal(signatureString.getBytes());
        signature=android.util.Base64.encodeToString(rawHMAC,android.util.Base64.NO_WRAP);
//Encoderencoder=Base64.getEncoder();
//signature=encoder.encodeToString(rawHMAC);
        }catch(Exception e){
        System.err.println("Unabletoappendsignature");
        System.exit(0);
        }

        final String authorizationLine="OAuth"+
        "oauth_consumer_key=\""+consumerKey+"\","+
        "oauth_nonce=\""+oauthNonce+"\","+
        "oauth_timestamp=\""+timestamp+"\","+
        "oauth_signature_method=\"HMAC-SHA1\","+
        "oauth_signature=\""+signature+"\","+
        "oauth_version=\"1.0\"";


        JSONObject object=new JSONObject();
        try{
                object.put("oauth_consumer_key",consumerKey);
                object.put("oauth_signature_method","HMAC-SHA1");
                object.put("oauth_timestamp",timestamp);
                object.put("oauth_nonce",oauthNonce);
                object.put("oauth_signature",signature);
                object.put("oauth_version","1.0");
        }catch(JSONException e){
                e.printStackTrace();
        }


        RequestQueue requestQueue= Volley.newRequestQueue(context);
        final String finalSignature = signature;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){
@Override
public void onResponse(JSONObject response){
        Log.i("AAA","weather response:"+response);

        }
        },new Response.ErrorListener(){
@Override
public void onErrorResponse(VolleyError error){
        Log.i("AAA","weather error:"+error);
        }
        }){
@Override
public Map<String,String>getHeaders() throws AuthFailureError {
        Map<String,String> headers=new HashMap<String,String>();
//        headers.put("X-Yahoo-App-Id","LHyI4Ire");
//        headers.put("cache-control","no-cache");
//나의시간도필요함
//headers.put("oauth_consumer_key","dj0yJmk9ZXN2TEN5S0FiNUI3JmQ9YWk9TEh5STRJcmUmcGo9MCZzPWNvbnN1bWVyc2VjcmV0JnN2PTAmeD0xMQ--");
//headers.put("oauth_signature_method","HMAC-SHA1");
//headers.put("oauth_timestamp","1597758913");
//headers.put("oauth_nonce","sPsNdlquXBW");
//headers.put("oauth_signature","QBKE/5MGrLq30slcpGbC+XevH3k=");
//headers.put("oauth_version","1.0");
//        headers.put("Content-Type","application/json");
//        headers.put("Authorization",authorizationLine);
        headers.put("X-Yahoo-App-Id",appId);
        headers.put("cache-control","no-cache");

        return headers;
        }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String,String>();
                        params.put("oauth_consumer_key",consumerKey);
                        params.put("oauth_signature_method","HMAC-SHA1");
                        params.put("oauth_timestamp",""+timestamp);
                        params.put("oauth_nonce",oauthNonce);
                        params.put("oauth_signature",finalSignature);
                        params.put("oauth_version","1.0");
                return params;
                }
        };
        requestQueue.add(request);

        }
        }

