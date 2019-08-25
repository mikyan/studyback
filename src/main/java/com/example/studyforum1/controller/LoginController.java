//package com.example.studyforum1.controller;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.example.studyforum1.domain.MessageItems.TalkItem;
//import com.mongodb.*;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.util.JSON;
//import org.bson.Document;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/login")
//@ResponseBody
//public class LoginController {
//    @RequestMapping(path = "/{city_id}/{user_id}", method = RequestMethod.GET)
//    public Object findUser(@PathVariable("city_id") String cityId,
//                           @PathVariable("user_id") String userId ){
//        MongoClient mongoClient = new MongoClient();
//        MongoDatabase db = mongoClient.getDatabase("studyforum");
//        MongoCollection<Document> coll = db.getCollection("Users");
//
//        FindIterable<Document> findIterable = coll.find().sort(new BasicDBObject("create_time",-1));
//
//
//    }
//
//    @GetMapping("/login")
//    public @ResponseBody String login(String username,String password) {
//        MongoClient mongoClient = new MongoClient();
//        MongoDatabase db = mongoClient.getDatabase("studyforum");
//        MongoCollection<Document> coll = db.getCollection("Users");
//
//        FindIterable<Document> findIterable = coll.find().sort(new BasicDBObject("create_time",-1));
//        // counting documents in a collection
//        //System.out.println(coll.getCount());
//
//        // using a cursor to get all collection
//        // DBCursor cursor = coll.find().sort(new BasicDBObject("create_time",-1));
//        ArrayList<String> arrayList=new ArrayList<>();
//        MongoCursor<Document> mongoCursor = findIterable.iterator();
//
//        while(mongoCursor.hasNext()){
//            arrayList.add(mongoCursor.next().toJson());
//        }
//        System.out.println(arrayList.toString());
//        return "{"+"\"data\": "+arrayList.toString()+"}";
//    }
//
//    @PostMapping("/insert")
//    public String greeting(String title,String text,  String viewNum) {
//        MongoClient mongoClient = new MongoClient();
//        DB db = mongoClient.getDB("studyforum");
//        DBCollection coll = db.getCollection("talkItems");
//        BasicDBObject doc = new BasicDBObject("title", title)
//                .append("text", text)
//                .append("viewNum", viewNum);
//        coll.insert(doc);
////        String json =  " {" +
////                " 'title': " + title + ","+
////                " 'text': " + text +","+
////                " 'viewNum': " + viewNum +","+
////                " } ";
////        DBObject bson = (DBObject) JSON.parse(json);
////        coll.insert(bson);
////        System.out.println(json);
//        return doc.toString();
//    }
//}