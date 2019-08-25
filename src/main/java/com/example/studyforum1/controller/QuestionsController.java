package com.example.studyforum1.controller;


import java.util.HashMap;
import java.util.Map;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")

public class QuestionsController {

    @GetMapping("/show")
    @ResponseBody
    public DBObject show() {
        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("studyforum");
        DBCollection coll = db.getCollection("questions");

        // counting documents in a collection
        System.out.println(coll.getCount());

        // using a cursor to get all collection
        DBCursor cursor = coll.find();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
                return cursor.next();
            }
        } finally {
            cursor.close();

        }
        return null;

    }

    @PostMapping("/insert")
    public String greeting(@RequestBody Map<String,String> res) {
        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("studyforum");
        DBCollection coll = db.getCollection("questions");
        BasicDBObject doc = new BasicDBObject("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new BasicDBObject("x", 123).append("y", 456));

        coll.insert(doc);
        for (int i = 1; i <= 100; i ++) {
            coll.insert(new BasicDBObject("i", i));
        }
        return "123";

    }
}