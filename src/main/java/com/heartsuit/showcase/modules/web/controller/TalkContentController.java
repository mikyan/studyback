package com.heartsuit.showcase.modules.web.controller;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/talkcontent")
@ResponseBody
public class TalkContentController {

    @GetMapping("/show")
    public @ResponseBody
    String show(String oid) {
        System.out.println(oid);
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            // 连接到数据库
            MongoDatabase db = mongoClient.getDatabase("studyforum");
            System.out.println("Connect to database successfully");
            MongoCollection<Document> coll = db.getCollection("talkItems");
            ObjectId objectId=new ObjectId(oid);

            FindIterable<Document> findIterable = coll.find(new BasicDBObject("_id",objectId));

            // using a cursor to get all collection
            // DBCursor cursor = coll.find().sort(new BasicDBObject("create_time",-1));
            MongoCursor<Document> mongoCursor = findIterable.iterator();

            Document document=new Document();
            if(mongoCursor.hasNext()){
                document=mongoCursor.next();
                return document.toJson();
            }else {
                return "帖子消失了";
            }
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return null;

    }

    @PostMapping("/reply")
    public String replay(String title,String text,  String goodNum, String rid, String oid) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongoClient.getDatabase("studyforum");
        System.out.println("Connect to database successfully");
        MongoCollection<Document> coll = db.getCollection("talkItems");
        ObjectId objectId=new ObjectId(oid);

        FindIterable<Document> findIterable = coll.find(new BasicDBObject("_id",objectId));
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        Document oldDocument=new Document();
        if(mongoCursor.hasNext()){
            oldDocument=mongoCursor.next();
        }else {
            return "没有原帖";
        }
        Document replayDoc = new Document("title", title)
                .append("text", text)
                .append("goodNum", goodNum);
        oldDocument.append("rid"+rid,replayDoc);

        coll.updateOne(new BasicDBObject("_id", objectId),new BasicDBObject("$set", oldDocument));

//        String json =  " {" +
//                " 'title': " + title + ","+
//                " 'text': " + text +","+
//                " 'viewNum': " + viewNum +","+
//                " } ";
//        DBObject bson = (DBObject) JSON.parse(json);
//        coll.insert(bson);
//        System.out.println(json);
        return "回复成功";
    }
}