package org.backender.interactorimplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface GenericRepository<T, ID> {

    default T findById(
            ID id,
            Class<T> clazz,
            MongoCollection<Document> readCollection
    ) {
        Document doc = readCollection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return MapperUtil.fromDocument(doc, clazz);
        }

        return null;
    }

    default List<T> findAll(Class<T> clazz, MongoCollection<Document> readCollection) {
        List<T> result = new ArrayList<>();
        for (Document doc : readCollection.find()) {
            result.add(MapperUtil.fromDocument(doc, clazz));
        }
        return result;
    };

    default void delete(Map<String, String> fieldsFilters, MongoCollection<Document> writeCollection) {

        List<Bson> filters = new ArrayList<>();
        fieldsFilters.forEach((key, value) -> filters.add(Filters.eq(key, value)));

        DeleteResult result = writeCollection.deleteMany(Filters.and(filters));
    }

    void save(T entity);

}
