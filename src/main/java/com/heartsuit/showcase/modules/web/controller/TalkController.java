package com.heartsuit.showcase.modules.web.controller;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/talk")
@ResponseBody
public class TalkController {

    @GetMapping("/show")
    public @ResponseBody
    String show(int id) {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            // 连接到数据库
            MongoDatabase db = mongoClient.getDatabase("studyforum");
            System.out.println("Connect to database successfully");
            MongoCollection<Document> coll = db.getCollection("talkItems");

            FindIterable<Document> findIterable = coll.find()
                    .sort(new BasicDBObject("create_time", -1))
                    .skip(id * 10)
                    .projection(new BasicDBObject()
                            .append("title", 1)
                            .append("text", "1")
                            .append("viewNum", 1)
                            .append("_id", 1));
            // counting documents in a collection
            //System.out.println(coll.getCount());

            // using a cursor to get all collection
            // DBCursor cursor = coll.find().sort(new BasicDBObject("create_time",-1));
            ArrayList<String> arrayList = new ArrayList<>();
            MongoCursor<Document> mongoCursor = findIterable.iterator();

            int i = id;
            while (mongoCursor.hasNext() && i < (id + 10)) {
                arrayList.add(mongoCursor.next().toJson());
                i++;
            }
            System.out.println(arrayList.toString());
            return "{" + "\"data\": " + arrayList.toString() + "}";
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;

    }

    @PostMapping("/insert")
    public String greeting(String title, String text, String viewNum) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("studyforum");
        System.out.println("Connect to database successfully");
        MongoCollection<Document> coll = db.getCollection("talkItems");
        Document doc = new Document("title", title)
                .append("text", text)
                .append("viewNum", viewNum);
        coll.insertOne(doc);
//        String json =  " {" +
//                " 'title': " + title + ","+
//                " 'text': " + text +","+
//                " 'viewNum': " + viewNum +","+
//                " } ";
//        DBObject bson = (DBObject) JSON.parse(json);
//        coll.insert(bson);
//        System.out.println(json);
        return "发帖成功";
    }
}