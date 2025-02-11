package com.project.finalProject.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {
	public String main(String voiceMessage) {

		String apiURL = "https://clovachatbot.ncloud.com/api/chatbot/messenger/v1/6504/0c8bd1014df31108fc39ee34d2524b4fed81f330117158cde999c25746d2992f";
		String secretKey = "enhwQUZVQXd4Y3lnTU1nanFoT3BhaFlBS1ptcUxYelY=";
		
		String chatbotMessage = "";	// 응답 메시지
        String result = null;

        try {
            //String apiURL = "https://ex9av8bv0e.apigw.ntruss.com/custom_chatbot/prod/";

            URL url = new URL(apiURL);

            String message = getReqMessage(voiceMessage);
            System.out.println("##" + message);

            String encodeBase64String = makeSignature(message, secretKey);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;UTF-8");
            con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

            // post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(message.getBytes("UTF-8"));
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();

            BufferedReader br;

            if(responseCode==200) { // Normal call
                System.out.println(con.getResponseMessage());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                con.getInputStream()));
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    chatbotMessage = decodedString;
                }
                //chatbotMessage = decodedString;
                in.close();
                
                // 응답 메시지 출력
                System.out.println(chatbotMessage);
                
                result = jsonToString(chatbotMessage);
            } else {  // Error occurred
                chatbotMessage = con.getResponseMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }
	
	// 챗봇 이미지 링크 + 멀티 링크 답변 포함
	public String imgLinkMainService(String voiceMessage) {

		String apiURL = "https://cktafv2n0r.apigw.ntruss.com/custom/v1/6504/0c8bd1014df31108fc39ee34d2524b4fed81f330117158cde999c25746d2992f";
		String secretKey = "enhwQUZVQXd4Y3lnTU1nanFoT3BhaFlBS1ptcUxYelY=";
		String chatbotMessage = "";	// 응답 메시지
        String result = null;

        try {
            //String apiURL = "https://ex9av8bv0e.apigw.ntruss.com/custom_chatbot/prod/";

            URL url = new URL(apiURL);

            String message = getReqMessage(voiceMessage);
            System.out.println("##" + message);

            String encodeBase64String = makeSignature(message, secretKey);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;UTF-8");
            con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

            // post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(message.getBytes("UTF-8"));
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();

            BufferedReader br;

            if(responseCode==200) { // Normal call
                System.out.println(con.getResponseMessage());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                con.getInputStream()));
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    chatbotMessage = decodedString;
                }
                //chatbotMessage = decodedString;
                in.close();
                
                // 응답 메시지 출력
                System.out.println(chatbotMessage);
                
                // 서비스에서 JSON 파싱하지 않고 chatbot4.js로 그대로 메시지 전송
                //chat4.js에서 파싱하고 출력
                // result = jsonToString(chatbotMessage);
            } else {  // Error occurred
                chatbotMessage = con.getResponseMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return chatbotMessage;
    }

    public static String makeSignature(String message, String secretKey) {

        String encodeBase64String = "";

        try {
            byte[] secrete_key_bytes = secretKey.getBytes("UTF-8");

            SecretKeySpec signingKey = new SecretKeySpec(secrete_key_bytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            //encodeBase64String = Base64.encodeToString(rawHmac, Base64.NO_WRAP);
            encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

            return encodeBase64String;

        } catch (Exception e){
            System.out.println(e);
        }

        return encodeBase64String;

    }

    public static String getReqMessage(String voiceMessage) {

        String requestBody = "";

        try {

            JSONObject obj = new JSONObject();

            long timestamp = new Date().getTime();

            System.out.println("##"+timestamp);

            obj.put("version", "v2");
            obj.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");
//=> userId is a unique code for each chat user, not a fixed value, recommend use UUID. use different id for each user could help you to split chat history for users.

            obj.put("timestamp", timestamp);

            JSONObject bubbles_obj = new JSONObject();

            bubbles_obj.put("type", "text");

            JSONObject data_obj = new JSONObject();
            data_obj.put("description", voiceMessage);

            bubbles_obj.put("type", "text");
            bubbles_obj.put("data", data_obj);

            JSONArray bubbles_array = new JSONArray();
            bubbles_array.put(bubbles_obj);

            obj.put("bubbles", bubbles_array);
            obj.put("event", "send");
            
            //웰컴 메시지 출력
            if(voiceMessage == "") {
            	obj.put("event",  "open");
            } else {
            	obj.put("event",  "send");
            }

            requestBody = obj.toString();

        } catch (Exception e){
            System.out.println("## Exception : " + e);
        }

        return requestBody;

    }
    
    public String jsonToString(String jsonResultStr) {
    	String result = "";
    	
    	
    	try {
			// JSON 형태의 문자열에서 JSON 오브젝트 "predictions" 추출해서 JSONArray에 저장
			// x, y 추출
    		
			JSONObject jsonObj = new JSONObject(jsonResultStr);
			JSONArray chatArray = (JSONArray) jsonObj.get("bubbles");			
			JSONObject chatObj0 = (JSONObject) chatArray.get(0);
			JSONObject chatData = (JSONObject) chatObj0.get("data");
			result = (String)chatData.get("description");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    }
}