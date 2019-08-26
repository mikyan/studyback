package com.heartsuit.showcase.modules.web.controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController//如果是前后端分离的项目，可以直接使用这个RestController代替Controller
@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/api/aboutschool")
    @ResponseBody
    String home()
    {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongoClient.getDatabase("studyforum");
        System.out.println("Connect to database successfully");
        MongoCollection<Document> coll = db.getCollection("talkItems");


        return "Hello World!";
    }

}