package com.heartsuit.showcase.modules.web.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    public BufferedImage getImage(String id) throws IOException {

        return ImageIO.read(new FileInputStream(new File("/opt/frontserver/images/"+id +".png")));
    }

    //插入学校周边数据
    @PostMapping("/insert")
    public String insert(String title, String description, String viewNum, MultipartFile image) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("studyforum");
        MongoCollection<Document> coll = db.getCollection("aboutschool");
        Document document = new Document("title", title).
                append("description", description).
                append("viewNum", viewNum);
        ObjectId objectId=document.getObjectId("_id");
        String imgName=objectId.toString();
        String exName = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        if(exName!="png"){
            return "请使用png格式的图片";
        }
        String realName=imgName+"."+exName;
        File file = new File("/opt/frontserver/images/"+realName);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            image.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "请重新上传图片";
        }

        coll.insertOne(document);
        return "Hello DevOps. Welcome " ;
    }

    @PostMapping("/update")
    public String update(int id) {
        return showtext(id);
    }


}