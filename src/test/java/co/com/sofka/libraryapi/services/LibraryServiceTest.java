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

    @Test
    void checkAvailabilityIfIsLent() {
        var data1 = new Resource();
        data1.setTitle("Recurso1");
        data1.setType("book");
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

    }

    @Test
    void lend() {
    }

    @Test
    void filterByType() {
    }

    @Test
    void filterBySubjectArea() {
    }

    @Test
    void filterByTypeAndSubjectArea() {
    }

    @Test
    void giveBack() {
    }

    @Test
    void resourceLentResponse() {
    }

    @Test
    void create() {
    }

    @Test
    void list() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}