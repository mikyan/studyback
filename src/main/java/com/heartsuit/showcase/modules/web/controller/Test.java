package com.heartsuit.showcase.modules.web.controller;//package com.example.studyforum1.controller;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.BulkWriteOperation;
//import com.mongodb.BulkWriteResult;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
//
//public class Test {
//    public static void main(String[] args) {
//        MongoClient mongoClient = new MongoClient();
//        DB db = mongoClient.getDB("local");
//        DBCollection coll = db.getCollection("mikyan");
//
//        // insert a document
//        BasicDBObject doc = new BasicDBObject("name", "MongoDB")
//                .append("type", "database")
//                .append("count", 1)
//                .append("info", new BasicDBObject("x", 123).append("y", 456));
//        coll.insert(doc);
//
//        // find the first document in a collection
//        DBObject myDoc = coll.findOne();
//        System.out.println(myDoc);
//
//        // adding multiple documents
//        for (int i = 1; i <= 100; i ++) {
//            coll.insert(new BasicDBObject("i", i));
//        }
//
//        // counting documents in a collection
//        System.out.println(coll.getCount());
//
//        // using a cursor to get all collection
//        DBCursor cursor = coll.find();
//        try {
//            while (cursor.hasNext())
//                System.out.println(cursor.next());
//        } finally {
//            cursor.close();
//        }
//
//        // get a single document with a guery
//        BasicDBObject query = new BasicDBObject("i", 23);
//        cursor = coll.find(query);
//        try {
//            while (cursor.hasNext())
//                System.out.println(cursor.next());
//        } finally {
//            cursor.close();
//        }
//
//        query = new BasicDBObject("j", new BasicDBObject("$ne", 93))
//                .append("i", new BasicDBObject("$gt", 90));
//        cursor = coll.find(query);
//        try {
//            while (cursor.hasNext())
//                System.out.println(cursor.next());
//        } finally {
//            cursor.close();
//        }
//
//        query = new BasicDBObject("i", new BasicDBObject("$ne", 93).append("$gt", 90));
//
//        cursor = coll.find(query);
//        try {
//            while (cursor.hasNext())
//                System.out.println(cursor.next());
//        } finally {
//            cursor.close();
//        }
//
////        // delete all
////        cursor = coll.find();
////        try {
////            while (cursor.hasNext())
////                coll.remove(cursor.next());
////        } finally {
////            cursor.close();
////        }
////
////        // count
////        System.out.println(coll.getCount());
//
////        BulkWriteOperation builder = coll.initializeOrderedBulkOperation();
////        builder.insert(new BasicDBObject("_id", 1));
////        builder.insert(new BasicDBObject("_id", 2));
////        builder.insert(new BasicDBObject("_id", 3));
//
////        builder.find(new BasicDBObject("_id", 1)).updateOne(new BasicDBObject("$set", new BasicDBObject("x", 123)));
////        builder.find(new BasicDBObject("_id", 2)).remove();
////        builder.find(new BasicDBObject("_id", 3)).replaceOne(new BasicDBObject("_id", 3).append("x", 4));
//
////        BulkWriteResult result = builder.execute();
//
//
//    }
//}