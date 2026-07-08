package Object;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import utility.SecurityUtility;

public class MigrasiEnkripsiUid {

    public static void jalankan(MongoDatabase db) {
        MongoCollection<Document> collection = db.getCollection("kehadiran");

        int totalDiproses = 0;
        int totalDilewati = 0;

        for (Document doc : collection.find()) {
            String uidRfid = doc.getString("uidRfid");

            if (uidRfid == null || uidRfid.isEmpty()) {
                continue;
            }

            // Cek apakah sudah terenkripsi (coba decrypt, kalau gagal berarti masih plain text)
            boolean sudahTerenkripsi;
            try {
                SecurityUtility.decrypt(uidRfid);
                sudahTerenkripsi = true;
            } catch (Exception e) {
                sudahTerenkripsi = false;
            }

            if (sudahTerenkripsi) {
                totalDilewati++;
                continue; // skip, sudah terenkripsi sebelumnya
            }

            // Masih plain text -> enkripsi dan update
            String uidTerenkripsi = SecurityUtility.encrypt(uidRfid);
            collection.updateOne(
                    Filters.eq("_id", doc.getObjectId("_id")),
                    Updates.set("uidRfid", uidTerenkripsi)
            );
            totalDiproses++;
        }

        System.out.println("Migrasi selesai. Data dienkripsi: " + totalDiproses + ", dilewati (sudah terenkripsi): " + totalDilewati);
    }
}