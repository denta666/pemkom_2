/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;

public class KehadiranDAO implements BaseDAO<Kehadiran> {
    private final MongoCollection<Document> collection;

    public KehadiranDAO(MongoDatabase db) {
        this.collection = db.getCollection("kehadiran");
    }

    @Override
    public void save(Kehadiran entity) {
        Document doc = new Document("id", entity.getId())
                .append("uidRfid", entity.getUidRfid())
                .append("nama", entity.getNama())
                .append("tanggal", entity.getTanggal())
                .append("status", entity.getStatus())
                .append("role", entity.getRole());
        collection.insertOne(doc);
    }

    @Override
    public void update(Bson filter, Kehadiran entity) {
        Document doc = new Document("id", entity.getId())
                .append("uidRfid", entity.getUidRfid())
                .append("nama", entity.getNama())
                .append("tanggal", entity.getTanggal())
                .append("status", entity.getStatus())
                .append("role", entity.getRole());
        collection.replaceOne(filter, doc);
    }

    @Override
    public void delete(Bson filter) {
        collection.deleteOne(filter);
    }

    @Override
    public List<Kehadiran> findAll() {
        List<Kehadiran> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            list.add(new Kehadiran(
                    doc.getString("id"),
                    doc.getString("uidRfid"),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("status"),
                    doc.getString("role")
            ));
        }
        return list;
    }

    @Override
    public Kehadiran findOne(Bson filter) {
        Document doc = collection.find(filter).first();
        if (doc != null) {
            return new Kehadiran(
                    doc.getString("id"),
                    doc.getString("uidRfid"),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("status"),
                    doc.getString("role")
            );
        }
        return null;
    }

    @Override
    public List<Kehadiran> findMany(Bson filter) {
        List<Kehadiran> list = new ArrayList<>();
        for (Document doc : collection.find(filter)) {
            list.add(new Kehadiran(
                    doc.getString("id"),
                    doc.getString("uidRfid"),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("status"),
                    doc.getString("role")
            ));
        }
        return list;
    }

    // 🔍 Pencarian dengan filter tanggal + keyword (nama/id)
    public List<Kehadiran> findByFilter(String tanggal, String keyword) {
        List<Kehadiran> result = new ArrayList<>();
        Document filter = new Document();

        if (tanggal != null && !tanggal.isEmpty()) {
            filter.append("tanggal", tanggal);
        }

        if (keyword != null && !keyword.isEmpty()) {
            filter.append("$or", List.of(
                new Document("nama", new Document("$regex", keyword).append("$options", "i")),
                new Document("id", new Document("$regex", keyword).append("$options", "i"))
            ));
        }

        for (Document doc : collection.find(filter)) {
            result.add(new Kehadiran(
                    doc.getString("id"),
                    doc.getString("uidRfid"),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("status"),
                    doc.getString("role")
            ));
        }

        return result;
    }
}
