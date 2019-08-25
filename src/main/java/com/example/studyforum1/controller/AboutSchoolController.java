package com.example.studyforum1.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api/aboutschool")
public class AboutSchoolController {

    @GetMapping("/showtext")
    public String showtext(int id) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("studyforum");
        MongoCollection<Document> coll = db.getCollection("aboutschool");

        FindIterable<Document> findIterable = coll.find().sort(new BasicDBObject("create_time",-1)).skip(id*10);
        ArrayList<String> arrayList=new ArrayList<>();
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        int i=id;
        while(mongoCursor.hasNext()&&i<(id+10)){
            arrayList.add(mongoCursor.next().toJson());
            i++;
        }
        System.out.println(arrayList.toString());
        return "{"+"\"data\": "+arrayList.toString()+"}";
    }

    @RequestMapping(value = "/get",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage getImage() throws IOException {

        return ImageIO.read(new FileInputStream(new File("D:/test.jpg")));
    }

    @PostMapping("/inserttext")
    public String inserttext(String title,String description,  String viewNum) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("studyforum");
        MongoCollection<Document> coll = db.getCollection("aboutschool");
        Document document = new Document("title", title).
                append("description", description).
                append("viewNum", viewNum);
        coll.insertOne(document);
        return "Hello DevOps. Welcome " ;
    }

    @PostMapping("/inserttext")
    public String update(int id) {
        return showtext(id);
    }


    @PostMapping("/greeting")
    public String greeting(@RequestBody HashMap<String, String> params) {
        return "Hello DevOps. Welcome " + params.get("name");
    }
}