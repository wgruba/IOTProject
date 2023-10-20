import org.bson.Document;

import java.util.ArrayList;

public class Main {
    static String USERNAME = "Agata";
    static String PASSWORD = "haslo";
    static String DATABASE = "bazadanych";

    public static void main(String[] args) {
        MongoDBConnection mdbc = new MongoDBConnection(USERNAME, PASSWORD, DATABASE);
        ArrayList<Document> doc_list = mdbc.getMatchingDocuments(1, "_id", "User");
        for (Document d : doc_list) {
            System.out.println(d.toJson());
        }
    }
}
