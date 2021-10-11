package co.com.sofka.libraryapi.mappers;

import co.com.sofka.libraryapi.dtos.ResourceDTO;
import co.com.sofka.libraryapi.entities.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ResourceMapper {

    private  ModelMapper modelMapper;
    private  DateTimeFormatter dateTimeFormatter;

    public ResourceMapper(ModelMapper modelMapper, DateTimeFormatter dateTimeFormatter) {
        this.modelMapper = modelMapper;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public ResourceDTO fromEntity(Resource resource)
    {
        ResourceDTO resourceDTO = modelMapper.map(resource, ResourceDTO.class);
        resourceDTO.setDateLent(dateTimeFormatter.format(resource.getDateLent()));

        return resourceDTO;
    }
    public Resource fromDTO(ResourceDTO resourceDTO)
    {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        resource.setDateLent(LocalDate.parse(resourceDTO.getDateLent(), dateTimeFormatter));

        return resource;
    }
}
