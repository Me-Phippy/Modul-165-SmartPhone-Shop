package org;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.types.Decimal128;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SmartphoneRepository {
    private final MongoCollection<Document> collection;

    public SmartphoneRepository(MongoClient mongoClient, String databaseName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection("smartphones");
    }

    public void insert(Smartphone smartphone) {
        Document doc = toDocument(smartphone);
        collection.insertOne(doc);
        smartphone.setId(doc.getObjectId("_id").toString());
    }

    public List<Smartphone> findAll() {
        List<Smartphone> smartphones = new ArrayList<>();
        for (Document doc : collection.find()) {
            smartphones.add(fromDocument(doc));
        }
        return smartphones;
    }

    public Smartphone findById(String id) {
        Document doc = collection.find(new Document("_id", new ObjectId(id))).first();
        return doc != null ? fromDocument(doc) : null;
    }

    public boolean update(Smartphone smartphone) {
        Document filter = new Document("_id", new ObjectId(smartphone.getId()));
        Document update = new Document("$set", toDocument(smartphone));
        return collection.updateOne(filter, update).getModifiedCount() > 0;
    }

    public boolean delete(String id) {
        return collection.deleteOne(new Document("_id", new ObjectId(id))).getDeletedCount() > 0;
    }

    private Document toDocument(Smartphone smartphone) {
        return new Document()
                .append("brand", smartphone.getBrand())
                .append("model", smartphone.getModel())
                .append("price", smartphone.getPrice() != null ? new Decimal128(smartphone.getPrice()) : null)
                .append("ramGB", smartphone.getRamGB())
                .append("screenSizeInches", smartphone.getScreenSizeInches())
                .append("storageGB", smartphone.getStorageGB())
                .append("operatingSystem", smartphone.getOperatingSystem())
                .append("osVersion", smartphone.getOsVersion())
                .append("resolution", smartphone.getResolution())
                .append("processorCores", smartphone.getProcessorCores())
                .append("batteryCapacityMAh", smartphone.getBatteryCapacityMAh())
                .append("connectivity", smartphone.getConnectivity())
                .append("mobileStandard", smartphone.getMobileStandard());
    }

    private Smartphone fromDocument(Document doc) {
        Smartphone smartphone = new Smartphone();
        smartphone.setId(doc.getObjectId("_id").toHexString());
        smartphone.setBrand(doc.getString("brand"));
        smartphone.setModel(doc.getString("model"));

        Object priceObj = doc.get("price");
        if (priceObj instanceof Decimal128) {
            smartphone.setPrice(((Decimal128) priceObj).bigDecimalValue());
        } else if (priceObj instanceof BigDecimal) {
            smartphone.setPrice((BigDecimal) priceObj);
        } else {
            smartphone.setPrice(null);
        }

        smartphone.setRamGB(doc.getInteger("ramGB"));
        smartphone.setScreenSizeInches(doc.getDouble("screenSizeInches"));
        smartphone.setStorageGB(doc.getInteger("storageGB"));
        smartphone.setOperatingSystem(doc.getString("operatingSystem"));
        smartphone.setOsVersion(doc.getString("osVersion"));
        smartphone.setResolution(doc.getString("resolution"));
        smartphone.setProcessorCores(doc.getInteger("processorCores"));
        smartphone.setBatteryCapacityMAh(doc.getInteger("batteryCapacityMAh"));
        smartphone.setConnectivity(doc.getList("connectivity", String.class));
        smartphone.setMobileStandard(doc.getString("mobileStandard"));
        return smartphone;
    }
}
