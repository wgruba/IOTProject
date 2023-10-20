import static com.mongodb.client.model.Filters.eq;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MongoDBConnection {
    MongoClient client;

    public MongoDBConnection(String user, String password, String database) {
        client = connectClient(String.format("mongodb+srv://%s:%s@%s.wwveray.mongodb.net" +
                                             "/?retryWrites=true&w=majority",
                                             user, password, database));
    }

    public MongoClient connectClient(String connectionString) {
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(
                new ConnectionString(connectionString)).serverApi(serverApi).build();
        try {
            return MongoClients.create(settings);
        }
        catch (MongoException e) {
            e.printStackTrace();
            // throw NoClientConnectionException;
            return null;
        }
    }

    // TO DO:
    //    - objects representing data
    //    - conversion from bson files to java objects
    //    - throwing and handling Exceptions

    private String[] getPermittedUniqueTypes(String doc_type) {
        String[] permitted_types = null;
        if (Objects.equals(doc_type, "User")) {
            permitted_types = new String[]{"_id", "e-mail", "username"};
        } else if (Objects.equals(doc_type, "Event")) {
            permitted_types = new String[]{"_id"};
        } else if (Objects.equals(doc_type, "Category")) {
            permitted_types = new String[]{"_id", "name"};
        }
        return permitted_types;
    }

    public Document getDocumentByUniqueIdentifier(Object identifier, String id_type, String doc_type) {
        String[] permitted_types = getPermittedUniqueTypes(doc_type);
        if (Arrays.asList(permitted_types).contains(id_type)){
            MongoDatabase database = client.getDatabase("EventOrganizer");
            MongoCollection<Document> collection = database.getCollection(doc_type);
            return collection.find(eq(id_type, identifier)).first();
        }
        else {
            // throw new InvalidUniqueIdentifierException;
            return null;
        }
    }

    public ArrayList<Document> getMatchingDocuments(Object identifier, String id_type, String doc_type) {
        ArrayList<Document> doc_set = new ArrayList<>();
        MongoDatabase database = client.getDatabase("EventOrganizer");
        MongoCollection<Document> collection = database.getCollection(doc_type);
        try (MongoCursor<Document> cursor = collection.find(eq(id_type, identifier)).iterator()) {
            while (cursor.hasNext()) {
                doc_set.add(cursor.next());
            }
        }
        return doc_set;
    }
}