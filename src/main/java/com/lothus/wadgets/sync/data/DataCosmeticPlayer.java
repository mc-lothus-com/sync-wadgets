package com.lothus.wadgets.sync.data;

import com.lothus.core.Core;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.UUID;

public class DataCosmeticPlayer {

    public MongoCollection<Document> collection = Core.getMongo().getDatabase("wadgets").getCollection("accounts");

    public void create(CosmeticPlayer player) {
        Document found = collection.find(Filters.eq("uniqueId", player.getUniqueId().toString())).first();
        if (found == null) {
            found = Document.parse(Core.getGson().toJson(player));
            collection.insertOne(found);
        }
    }

    public void update(CosmeticPlayer player) {
        collection.updateOne(Filters.eq("uniqueId", player.getUniqueId().toString()),
                new Document("$set", Document.parse(Core.getGson().toJson(player))));
        Core.getRedis().set("cosmeticPlayer=" + player.getUniqueId().toString(), Core.getGson().toJson(player));
    }

    public void delete(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        if (found != null) {
            collection.deleteOne(Filters.eq("uniqueId", uniqueId.toString()));
        }
    }

    public CosmeticPlayer get(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        if (found != null) {
            return Core.getGson().fromJson(Core.getGson().toJson(found), CosmeticPlayer.class);
        }
        return null;
    }

    public CosmeticPlayer get(String name) {
        Document found = collection.find(Filters.eq("name", name)).first();
        if (found != null) {
            return Core.getGson().fromJson(Core.getGson().toJson(found), CosmeticPlayer.class);
        }
        return null;
    }

}
