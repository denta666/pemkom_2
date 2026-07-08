package Object;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;
import utility.SecurityUtility;

public class KehadiranDAO implements BaseDAO<Kehadiran> {

    private final MongoCollection<Document> collection;

    public KehadiranDAO(MongoDatabase db) {
        this.collection = db.getCollection("kehadiran");
    }

    // CREATE
    @Override
    public void save(Kehadiran entity) {
        Document doc = new Document("id", entity.getId())
                .append("uidRfid", SecurityUtility.encrypt(entity.getUidRfid()))
                .append("nama", entity.getNama())
                .append("tanggal", entity.getTanggal())
                .append("jamMasuk", entity.getJamMasuk())
                .append("jamKeluar", entity.getJamKeluar())
                .append("status", entity.getStatus())
                .append("role", entity.getRole());
        collection.insertOne(doc);
    }

    // UPDATE (ubah status berdasarkan id + tanggal)
    @Override
    public void update(Bson filter, Kehadiran entity) {
        Bson filterDoc = new Document("id", entity.getId())
                .append("tanggal", entity.getTanggal());

        Document updateDoc = new Document("$set", new Document("status", entity.getStatus())
                .append("uidRfid", SecurityUtility.encrypt(entity.getUidRfid()))
                .append("nama", entity.getNama())
                .append("jamMasuk", entity.getJamMasuk())
                .append("jamKeluar", entity.getJamKeluar())
                .append("role", entity.getRole()));

        collection.updateOne(filterDoc, updateDoc);
    }

    // DELETE
    @Override
    public void delete(Bson filter) {
        collection.deleteOne(filter);
    }

    // READ ALL
    @Override
    public List<Kehadiran> findAll() {
        List<Kehadiran> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            list.add(new Kehadiran(
                    doc.getString("id"),
                    SecurityUtility.decrypt(doc.getString("uidRfid")),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("jamMasuk"),
                    doc.getString("jamKeluar"),
                    doc.getString("status"),
                    doc.getString("role")
            ));
        }
        return list;
    }

    // READ ONE
    @Override
    public Kehadiran findOne(Bson filter) {
        Document doc = collection.find(filter).first();
        if (doc != null) {
            return new Kehadiran(
                    doc.getString("id"),
                    SecurityUtility.decrypt(doc.getString("uidRfid")),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("jamMasuk"),
                    doc.getString("jamKeluar"),
                    doc.getString("status"),
                    doc.getString("role")
            );
        }
        return null;
    }

    // READ MANY
    @Override
    public List<Kehadiran> findMany(Bson filter) {
        List<Kehadiran> list = new ArrayList<>();
        for (Document doc : collection.find(filter)) {
            list.add(new Kehadiran(
                    doc.getString("id"),
                    SecurityUtility.decrypt(doc.getString("uidRfid")),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("jamMasuk"),
                    doc.getString("jamKeluar"),
                    doc.getString("status"),
                    doc.getString("role")
            ));
        }
        return list;
    }

    // 🔍 Cari berdasarkan keyword (nama/id) - tidak menyentuh uidRfid, jadi aman
    public List<Kehadiran> findByKeyword(String keyword) {

        Document filter = new Document(
                "$or", List.of(
                        new Document("nama",
                                new Document("$regex", keyword)
                                        .append("$options", "i")),
                        new Document("id",
                                new Document("$regex", keyword)
                                        .append("$options", "i"))
                )
        );

        return findMany(filter);
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
                    SecurityUtility.decrypt(doc.getString("uidRfid")),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("jamMasuk"),
                    doc.getString("jamKeluar"),
                    doc.getString("status"),
                    doc.getString("role")
            ));
        }

        return result;
    }

    // uidRfid yang masuk di sini adalah UID ASLI hasil scan, jadi harus dienkripsi dulu sebelum query
    public Kehadiran findByUidAndTanggal(String uidRfid, String tanggal) {
        String uidTerenkripsi = SecurityUtility.encrypt(uidRfid);
        Document filter = new Document("uidRfid", uidTerenkripsi)
                .append("tanggal", tanggal);
        Document doc = collection.find(filter).first();

        if (doc != null) {
            return new Kehadiran(
                    doc.getString("id"),
                    SecurityUtility.decrypt(doc.getString("uidRfid")),
                    doc.getString("nama"),
                    doc.getString("tanggal"),
                    doc.getString("jamMasuk"),
                    doc.getString("jamKeluar"),
                    doc.getString("status"),
                    doc.getString("role")
            );
        }
        return null;
    }

    // entity.getUidRfid() di sini juga UID ASLI, harus dienkripsi dulu untuk filter maupun update
    public void update(Kehadiran entity) {
        String uidTerenkripsi = SecurityUtility.encrypt(entity.getUidRfid());

        Document filter = new Document("uidRfid", uidTerenkripsi)
                .append("tanggal", entity.getTanggal());

        Document updateDoc = new Document("$set", new Document("jamKeluar", entity.getJamKeluar())
                .append("status", entity.getStatus())
                .append("uidRfid", uidTerenkripsi)
                .append("nama", entity.getNama())
                .append("jamMasuk", entity.getJamMasuk())
                .append("role", entity.getRole()));

        collection.updateOne(filter, updateDoc);
    }

}