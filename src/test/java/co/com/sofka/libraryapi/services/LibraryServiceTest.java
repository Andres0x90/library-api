package co.com.sofka.libraryapi.services;

import co.com.sofka.libraryapi.dtos.ResourceDTO;
import co.com.sofka.libraryapi.entities.Resource;
import co.com.sofka.libraryapi.mappers.ResourceMapper;
import co.com.sofka.libraryapi.repositories.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class LibraryServiceTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    void checkAvailabilityIfIsLent() {
        var data1 = getResourceData();
        data1.lend();
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));

        var result = libraryService.checkAvailability("xxxx");
        var resourceResult = (ResourceDTO)result.get("resource");

        assertEquals("Este recurso no se encuentra disponible para prestar",
                result.get("message"));
        assertEquals(data1.getTitle(), resourceResult.getTitle());
        assertTrue(resourceResult.isLent());
        assertNotNull(resourceResult.getDateLent());
    }
    @Test
    void checkAvailabilityIfIsNotLent() {
        var data1 = getResourceData();
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));

        var result = libraryService.checkAvailability("xxxx");

        assertEquals("Este recurso se encuentra disponible", result.get("message"));
    }

    @Test
    void lend() {
        var data1 = getResourceData();
        var data1Updated = getResourceData();
        data1Updated.lend();
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));
        when(resourceRepository.save(any())).thenReturn(data1Updated);

        var result = libraryService.lend("xxxx");
        var resourceResult = (ResourceDTO)result.get("resource");

        assertEquals("Recurso prestado exitosamente",
                result.get("message"));
        assertEquals(data1.getTitle(), resourceResult.getTitle());
        assertTrue(resourceResult.isLent());
        assertNotNull(resourceResult.getDateLent());
    }

    @Test
    void filterByType() {
        var allData = getResourceListData();
        when(resourceRepository.findByType("book")).thenReturn(allData.stream()
                .filter(data -> data.getType().equalsIgnoreCase("book"))
                .toList());

        var result = libraryService.filterByType("book");

        assertEquals(result.size(), 2);
    }

    @Test
    void filterBySubjectArea() {
        var allData = getResourceListData();
        when(resourceRepository.findBySubjectArea("tech")).thenReturn(allData.stream()
                .filter(data -> data.getSubjectArea().equalsIgnoreCase("tech"))
                .toList());

        var result = libraryService.filterBySubjectArea("tech");

        assertEquals(result.size(), 2);
    }

    @Test
    void filterByTypeAndSubjectArea() {
        var allData = getResourceListData();
        when(resourceRepository.findByTypeAndSubjectArea("magazine", "tech"))
                .thenReturn(allData.stream()
                        .filter(data -> data.getType().equalsIgnoreCase("magazine")
                                && data.getSubjectArea().equalsIgnoreCase("tech"))
                        .toList());

        var result = libraryService.filterByTypeAndSubjectArea("magazine",
                "tech");

        assertEquals(result.size(), 1);
    }

    @Test
    void giveBackIfIsLent() {
        var data1 = getResourceData();
        data1.lend();
        var data1Updated = getResourceData();
        data1Updated.giveBack();
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));
        when(resourceRepository.save(any())).thenReturn(data1Updated);

        var result = libraryService.giveBack("xxxx");

        assertEquals("Recurso devuelto exitosamente",
                result.get("message"));
    }

    @Test
    void giveBackIfIsNotLent() {
        var data1 = getResourceData();
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));

        var result = libraryService.giveBack("xxxx");

        assertEquals("Este recurso no se encuentra prestado",
                result.get("message"));
    }

    @Test
    void create() {
        var data1 = getResourceData();
        when(resourceRepository.save(any())).thenReturn(data1);

        var result = libraryService.create(resourceMapper.fromEntity(data1));

        assertEquals(data1.getTitle(), result.getTitle());
        assertEquals(data1.getType(), result.getType());
        assertEquals(data1.getSubjectArea(), result.getSubjectArea());
    }

    @Test
    void list() {
        var allData = getResourceListData();
        when(resourceRepository.findAll()).thenReturn(allData);

        var result = libraryService.list();

        assertEquals(allData.size(), result.size());
    }

    @Test
    void findById() {
        var data1 = getResourceData();
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));

        var result = libraryService.findById("xxxx");

        assertEquals(data1.getTitle(), result.getTitle());
        assertEquals(data1.getType(), result.getType());
        assertEquals(data1.getSubjectArea(), result.getSubjectArea());
    }

    @Test
    void update() {
        var data1 = getResourceData();
        var data1Updated = getResourceData();
        data1Updated.setTitle("Recurso1Actualizado");
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));
        when(resourceRepository.save(any())).thenReturn(data1Updated);

        var result = libraryService.update(resourceMapper.fromEntity(data1Updated));

        assertEquals(data1Updated.getTitle(), result.getTitle());
    }

    @Test
    void delete() {
        var data1 = getResourceData();
        when(resourceRepository.findById("xxxx")).thenReturn(Optional.of(data1));
        var result = libraryService.delete("xxxx");

        assertEquals("Recurso eliminado correctamente", result.get("message"));
    }

    private Resource getResourceData()
    {
        var data1 = new Resource();
        data1.setId("xxxx");
        data1.setTitle("Recurso1");
        data1.setType("book");
        data1.setSubjectArea("tech");

        return data1;
    }

    private List<Resource> getResourceListData(){
        var data1 = new Resource();
        data1.setTitle("Recurso1");
        data1.setType("book");
        data1.setSubjectArea("tech");
        var data2 = new Resource();
        data2.setTitle("Recurso1");
        data2.setType("book");
        data2.setSubjectArea("science");
        var data3 = new Resource();
        data3.setTitle("Recurso3");
        data3.setType("magazine");
        data3.setSubjectArea("tech");

        List<Resource> resources = new ArrayList<>();
        resources.add(data1);
        resources.add(data2);
        resources.add(data3);
        return resources;
    }
}