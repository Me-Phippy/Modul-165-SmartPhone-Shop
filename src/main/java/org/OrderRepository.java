package org;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRepository {
    private final MongoCollection<Document> collection;
    private final CustomerRepository customerRepository;
    private final SmartphoneRepository smartphoneRepository;

    public OrderRepository(
            MongoClient mongoClient,
            String databaseName,
            CustomerRepository customerRepository,
            SmartphoneRepository smartphoneRepository
    ) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection("orders");
        this.customerRepository = customerRepository;
        this.smartphoneRepository = smartphoneRepository;
    }

    private Document toDocument(Order order) {
        List<Document> itemDocs = order.getItems().stream()
                .map(item -> new Document()
                        .append("smartphoneId", new ObjectId(item.getSmartphone().getId()))
                        .append("quantity", item.getQuantity())
                        .append("unitPrice", item.getUnitPrice().doubleValue())
                ).collect(Collectors.toList());

        Document doc = new Document()
                .append("customerId", new ObjectId(order.getCustomer().getId()))
                .append("orderDate", order.getOrderDate())
                .append("items", itemDocs)
                .append("total", order.getTotal().doubleValue());

        if (order.getId() != null) {
            doc.append("_id", new ObjectId(order.getId()));
        }

        return doc;
    }

    private Order fromDocument(Document doc) {
        if (doc == null) return null;

        Order order = new Order();
        order.setId(doc.getObjectId("_id").toString());
        order.setOrderDate((LocalDateTime) doc.get("orderDate"));

        // Load customer
        String customerId = doc.getObjectId("customerId").toString();
        order.setCustomer(customerRepository.findById(customerId));

        // Load order items
        List<Document> itemDocs = (List<Document>) doc.get("items");
        List<OrderItem> items = itemDocs.stream()
                .map(itemDoc -> {
                    OrderItem item = new OrderItem();
                    String smartphoneId = itemDoc.getObjectId("smartphoneId").toString();
                    item.setSmartphone(smartphoneRepository.findById(smartphoneId));
                    item.setQuantity(itemDoc.getInteger("quantity"));
                    item.setUnitPrice(BigDecimal.valueOf(itemDoc.getDouble("unitPrice")));
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(items);


        return order;
    }

    public void insert(Order order) {
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        // Ensure total is calculated before insert
        order.calculateTotal();

        Document orderDoc = toDocument(order);
        collection.insertOne(orderDoc);
        order.setId(orderDoc.getObjectId("_id").toString());
    }

    public Order update(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null for update operation");
        }

        // Recalculate total before update
        order.calculateTotal();

        Document update = toDocument(order);
        collection.replaceOne(
                Filters.eq("_id", new ObjectId(order.getId())),
                update
        );
        return order;
    }

    public Order findById(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        return fromDocument(doc);
    }

    public List<Order> findByCustomerId(String customerId) {
        return collection.find(Filters.eq("customerId", new ObjectId(customerId)))
                .map(this::fromDocument)
                .into(new ArrayList<>());
    }

    public List<Order> findAll() {
        return collection.find()
                .map(this::fromDocument)
                .into(new ArrayList<>());
    }

    public boolean delete(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id)))
                .getDeletedCount() > 0;
    }

    public List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return collection.find(
                        Filters.and(
                                Filters.gte("orderDate", startDate),
                                Filters.lte("orderDate", endDate)
                        ))
                .map(this::fromDocument)
                .into(new ArrayList<>());
    }
}