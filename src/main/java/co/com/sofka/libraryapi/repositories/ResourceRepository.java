package co.com.sofka.libraryapi.repositories;

import co.com.sofka.libraryapi.entities.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceRepository extends MongoRepository<Resource, String> {
}
