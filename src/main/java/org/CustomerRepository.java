package org;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing Customer entities in MongoDB
 */
public class CustomerRepository {
    private final MongoCollection<Document> collection;

    public CustomerRepository(MongoClient mongoClient, String databaseName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection("customers");
    }

    /**
     * Convert Customer to MongoDB Document
     * @param customer Source customer
     * @return MongoDB Document representation
     */
    private Document toDocument(Customer customer) {
        return new Document("_id", customer.getId() != null && ObjectId.isValid(customer.getId())
                ? new ObjectId(customer.getId())
                : new ObjectId())
                .append("lastName", customer.getLastName())
                .append("firstName", customer.getFirstName())
                .append("mobilePhone", customer.getMobilePhone())
                .append("email", customer.getEmail());
    }

    /**
     * Convert MongoDB Document to Customer
     * @param document Source document
     * @return Customer entity
     */
    private Customer fromDocument(Document document) {
        if (document == null) return null;

        Customer customer = new Customer();
        customer.setId(document.getObjectId("_id").toString());
        customer.setLastName(document.getString("lastName"));
        customer.setFirstName(document.getString("firstName"));
        customer.setMobilePhone(document.getString("mobilePhone"));
        customer.setEmail(document.getString("email"));

        return customer;
    }

    /**
     * Insert a new customer
     * @param customer Customer to insert
     * @return Inserted customer with generated ID
     */
    public Customer insert(Customer customer) {
        Document document = toDocument(customer);
        collection.insertOne(document);
        customer.setId(document.getObjectId("_id").toString());
        return customer;
    }

    /**
     * Update an existing customer
     * @param customer Customer with updated information
     * @return Updated customer
     */
    public Customer update(Customer customer) {
        Document update = toDocument(customer);
        collection.replaceOne(
                Filters.eq("_id", new ObjectId(customer.getId())),
                update
        );
        return customer;
    }

    /**
     * Find customer by ID
     * @param id Customer ID
     * @return Customer or null if not found
     */
    public Customer findById(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        return fromDocument(doc);
    }

    /**
     * Find customers by last name
     * @param lastName Last name to search for
     * @return List of matching customers
     */
    public List<Customer> findByLastName(String lastName) {
        return collection.find(Filters.eq("lastName", lastName))
                .map(this::fromDocument)
                .into(new ArrayList<>());
    }

    /**
     * Find all customers
     * @return List of all customers
     */
    public List<Customer> findAll() {
        return collection.find()
                .map(this::fromDocument)
                .into(new ArrayList<>());
    }

    /**
     * Delete a customer by ID
     * @param id Customer ID to delete
     * @return True if deletion was successful
     */
    public boolean delete(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id))).getDeletedCount() > 0;
    }
}