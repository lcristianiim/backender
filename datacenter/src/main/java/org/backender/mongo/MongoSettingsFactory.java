package org.backender.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.componenter.components.Component;
import org.componenter.components.header.HeaderComponent;
import org.componenter.components.header.HeaderDS;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoSettingsFactory {

    public static MongoClientSettings createSettings() {
        ConnectionString string = new ConnectionString("mongodb://admin:admin@localhost:12345");

//        ClassModel<Component> componentClassModel = ClassModel.builder(Component.class).enableDiscriminator(true).build();
//        ClassModel<HeaderComponent> headerComponentClassModel = ClassModel.builder(HeaderComponent.class).enableDiscriminator(true).build();
//        ClassModel<HeaderDS> headerDSClassModel = ClassModel.builder(HeaderDS.class).enableDiscriminator(true).build();

//        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
//                .register(
//                        componentClassModel,
//                        headerComponentClassModel,
//                        headerDSClassModel
//                )
//                .automatic(true)
//                .build();

//        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        return MongoClientSettings.builder()
                .applyConnectionString(string)
//                .codecRegistry(pojoCodecRegistry)
                .build();
    }
}
