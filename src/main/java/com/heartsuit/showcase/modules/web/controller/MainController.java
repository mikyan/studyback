package com.heartsuit.showcase.modules.web.controller;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/main")
@ResponseBody
public class MainController {

    @GetMapping("/login")
    public @ResponseBody
    String login(String username,String password) {
        String pass=getSHA256Str(password);
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );


            // 连接到数据库
            MongoDatabase db = mongoClient.getDatabase("studyforum");
            System.out.println("Connect to database successfully");
            MongoCollection<Document> coll = db.getCollection("Users");
            FindIterable<Document> findIterable = coll.find(new BasicDBObject("username",username).append("password",pass));
            // counting documents in a collection
            //System.out.println(coll.getCount());

            // using a cursor to get all collection
            // DBCursor cursor = coll.find().sort(new BasicDBObject("create_time",-1));
            ArrayList<String> arrayList=new ArrayList<>();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            String tokenkey="mikyan";

            if(mongoCursor.hasNext())
            {
                String json =  " {" +
                " \"code\": " + 0 + ","+
                " \"message\": " + "登陆成功" +","+
                " \"token\": " + tokenkey + username + (new Date().getTime()+60*60*1000)+","+
                " } ";
                return json;
            }else{
                String json =  " {" +
                        " \"code\": " + 1 + ","+
                        " \"message\": " + "登陆失败，账号或密码错误" +","+
                        " } ";
                return json;

            }
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return null;

    }

    @GetMapping("/register")
    public  @ResponseBody String register(String username,String pass) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongoClient.getDatabase("studyforum");
        System.out.println("Connect to database successfully");
        MongoCollection<Document> coll = db.getCollection("Users");
        FindIterable<Document> findIterable = coll.find(new BasicDBObject("username",username));
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        if(mongoCursor.hasNext()){
            String json= "{" +
                    " \"code\": " + -1 + ","+
                    " \"message\": " + "注册失败，账户名已存在" +""+
                    " } ";
            return json;
        }
        String passSHA=getSHA256Str(pass);
        Document doc = new Document("username", username)
                .append("password", passSHA);
        coll.insertOne(doc);
//        String json =  " {" +
//                " 'title': " + title + ","+
//                " 'text': " + text +","+
//                " 'viewNum': " + viewNum +","+
//                " } ";
//        DBObject bson = (DBObject) JSON.parse(json);
//        coll.insert(bson);
//        System.out.println(json);
        String json= "{" +
                " \"code\": " + 2 + ","+
                "\"message\": " + "注册成功" +","+
                " } ";
        return json;
    }

    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}