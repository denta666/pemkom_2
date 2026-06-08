package Object;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utility.SecurityUtility; // import hashing

public class KaryawanDAO implements BaseDAO<Karyawan> {

    private final MongoCollection<Document> collection;

    public KaryawanDAO(MongoDatabase db) {
        this.collection = db.getCollection("karyawan");
    }

    @Override
    public void save(Karyawan entity) {
        // Hash password sebelum disimpan
        String hashedPassword = SecurityUtility.getHash(entity.getPassword(), SecurityUtility.SHA_256);

        Document doc = new Document("uidRfid", entity.getUidRfid())
                .append("idKaryawan", entity.getIdKaryawan())
                .append("namaLengkap", entity.getNamaLengkap())
                .append("role", entity.getRole())
                .append("username", entity.getUsername())
                .append("password", hashedPassword);
        collection.insertOne(doc);
    }

    @Override
    public void update(Bson filter, Karyawan entity) {
        // Hash password sebelum update
        String hashedPassword = SecurityUtility.getHash(entity.getPassword(), SecurityUtility.SHA_256);

        Document doc = new Document("uidRfid", entity.getUidRfid())
                .append("idKaryawan", entity.getIdKaryawan())
                .append("namaLengkap", entity.getNamaLengkap())
                .append("role", entity.getRole())
                .append("username", entity.getUsername())
                .append("password", hashedPassword);
        collection.replaceOne(filter, doc);
    }

    @Override
    public void delete(Bson filter) {
        collection.deleteOne(filter);
    }

    @Override
    public List<Karyawan> findAll() {
        List<Karyawan> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            list.add(new Karyawan(
                    doc.getString("uidRfid"),
                    doc.getString("idKaryawan"),
                    doc.getString("namaLengkap"),
                    doc.getString("role"),
                    doc.getString("username"),
                    doc.getString("password")
            ));
        }
        return list;
    }

    @Override
    public Karyawan findOne(Bson filter) {
        Document doc = collection.find(filter).first();
        if (doc != null) {
            return new Karyawan(
                    doc.getString("uidRfid"),
                    doc.getString("idKaryawan"),
                    doc.getString("namaLengkap"),
                    doc.getString("role"),
                    doc.getString("username"),
                    doc.getString("password")
            );
        }
        return null;
    }

    @Override
    public List<Karyawan> findMany(Bson filter) {
        List<Karyawan> list = new ArrayList<>();
        for (Document doc : collection.find(filter)) {
            list.add(new Karyawan(
                    doc.getString("uidRfid"),
                    doc.getString("idKaryawan"),
                    doc.getString("namaLengkap"),
                    doc.getString("role"),
                    doc.getString("username"),
                    doc.getString("password")
            ));
        }
        return list;
    }

    // 🔍 Cari berdasarkan username
    public Karyawan findByUsername(String username) {
        Document doc = collection.find(new Document("username", username)).first();
        if (doc != null) {
            return new Karyawan(
                    doc.getString("uidRfid"),
                    doc.getString("idKaryawan"),
                    doc.getString("namaLengkap"),
                    doc.getString("role"),
                    doc.getString("username"),
                    doc.getString("password")
            );
        }
        return null;
    }

    // 🔐 Login dengan hashing
    public Karyawan login(String username, String plainPassword) {
        String hashedPassword = SecurityUtility.getHash(plainPassword, SecurityUtility.SHA_256);

        Document filter = new Document("username", username)
                .append("password", hashedPassword);
        Document doc = collection.find(filter).first();

        if (doc != null) {
            return new Karyawan(
                    doc.getString("uidRfid"),
                    doc.getString("idKaryawan"),
                    doc.getString("namaLengkap"),
                    doc.getString("role"),
                    doc.getString("username"),
                    doc.getString("password")
            );
        }
        return null; // login gagal
    }

    // 🔍 Cari berdasarkan keyword (nama/id)
    public List<Karyawan> findByKeyword(String keyword) {
        List<Karyawan> list = new ArrayList<>();
        Document filter = new Document("$or", Arrays.asList(
                new Document("namaLengkap", new Document("$regex", keyword).append("$options", "i")),
                new Document("idKaryawan", new Document("$regex", keyword).append("$options", "i"))
        ));

        for (Document doc : collection.find(filter)) {
            list.add(new Karyawan(
                    doc.getString("uidRfid"),
                    doc.getString("idKaryawan"),
                    doc.getString("namaLengkap"),
                    doc.getString("role"),
                    doc.getString("username"),
                    doc.getString("password")
            ));
        }
        return list;
    }
}
