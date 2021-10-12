package co.com.sofka.libraryapi.repositories;

import co.com.sofka.libraryapi.entities.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findByType(String type);
    List<Resource> findBySubjectArea(String type);
    List<Resource> findByTypeAndSubjectArea(String type, String subjectArea);
}
