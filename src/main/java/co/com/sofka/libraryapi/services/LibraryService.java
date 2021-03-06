package co.com.sofka.libraryapi.services;

import co.com.sofka.libraryapi.dtos.ResourceDTO;
import co.com.sofka.libraryapi.entities.Resource;
import co.com.sofka.libraryapi.mappers.ResourceMapper;
import co.com.sofka.libraryapi.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LibraryService {
    private ResourceRepository resourceRepository;
    private ResourceMapper resourceMapper;

    @Autowired
    public LibraryService(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper =resourceMapper;
    }
    public Map<String, Object> checkAvailability(String id)
    {
        HashMap<String, Object> response =  new HashMap<>();
        Resource resource = resourceMapper.fromDTO(findById(id));

        if (resource.isLent())
        {
            response.putAll(resourceLentResponse(resource));
            return response;
        }

        response.put("message", "Este recurso se encuentra disponible");
        return response;
    }
    public Map<String, Object> lend(String id)
    {
        HashMap<String, Object> response =  new HashMap<>();
        Resource resource = resourceMapper.fromDTO(findById(id));

        if (resource.isLent())
        {
            response.putAll(resourceLentResponse(resource));
            return response;
        }
        resource.lend();
        resource = resourceMapper.fromDTO(update(resourceMapper.fromEntity(resource)));

        response.put("resource", resourceMapper.fromEntity(resource));
        response.put("message", "Recurso prestado exitosamente");

        return response;
    }
    public List<ResourceDTO> filterByType(String type)
    {
        return resourceMapper.fromEntityList(resourceRepository.findByType(type));
    }
    public List<ResourceDTO> filterBySubjectArea(String subjectArea)
    {
        return resourceMapper.fromEntityList(resourceRepository.findBySubjectArea(subjectArea));
    }
    public List<ResourceDTO> filterByTypeAndSubjectArea(String type, String subjectArea)
    {
        return resourceMapper.fromEntityList(resourceRepository.
                findByTypeAndSubjectArea(type, subjectArea));
    }
    public Map<String, Object> giveBack(String id)
    {
        HashMap<String, Object> response =  new HashMap<>();
        Resource resource = resourceMapper.fromDTO(findById(id));

        if (resource.isLent())
        {
            resource.giveBack();
            resourceMapper.fromDTO(update(resourceMapper.fromEntity(resource)));
            response.put("message", "Recurso devuelto exitosamente");
            return response;
        }
        response.put("message", "Este recurso no se encuentra prestado");
        return response;
    }
    private Map<String, Object> resourceLentResponse(Resource resource)
    {
        HashMap<String, Object> response =  new HashMap<>();
        response.put("message", "Este recurso no se encuentra disponible para prestar");
        response.put("resource", resourceMapper.fromEntity(resource));
        return response;
    }

    public ResourceDTO create(ResourceDTO resourceDTO)
    {
        Resource resource = resourceMapper.fromDTO(resourceDTO);
        return resourceMapper.fromEntity(resourceRepository.save(resource));
    }
    public List<ResourceDTO> list()
    {
        return resourceMapper.fromEntityList(resourceRepository.findAll());
    }
    public ResourceDTO findById(String id)
    {
        Resource resource = resourceRepository.findById(id).orElseThrow(()->
                new RuntimeException("Recurso no encontrado"));

        return resourceMapper.fromEntity(resource);
    }
    public ResourceDTO update(ResourceDTO resourceDTO)
    {
        Resource resource = resourceMapper.fromDTO(resourceDTO);
        resourceRepository.findById(resource.getId()).orElseThrow(()->
                new RuntimeException("Recurso no encontrado"));

        return resourceMapper.fromEntity(resourceRepository.save(resource));
    }
    public Map<String, String> delete(String id)
    {
        Resource resource = resourceRepository.findById(id).orElseThrow(()->
                new RuntimeException("Recurso no encontrado"));
        resourceRepository.delete(resource);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Recurso eliminado correctamente");
        return response;
    }
}
