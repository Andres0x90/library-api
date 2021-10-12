package co.com.sofka.libraryapi.controllers;

import co.com.sofka.libraryapi.dtos.ResourceDTO;
import co.com.sofka.libraryapi.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/resources")
public class LibraryController {
    private LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/check-availability/{id}")
    public ResponseEntity<Map> checkAvailability(@PathVariable String id)
    {
        return new ResponseEntity<>(libraryService.checkAvailability(id), HttpStatus.OK);
    }
    @PutMapping("/lend/{id}")
    public ResponseEntity<Map> lend(@PathVariable String id)
    {
        return new ResponseEntity<>(libraryService.lend(id), HttpStatus.OK);
    }
    @GetMapping("/filter-by-type/{type}")
    public ResponseEntity<List> filterByType(@PathVariable String type)
    {
        return new ResponseEntity<>(libraryService.filterByType(type), HttpStatus.OK);
    }
    @GetMapping("/filter-by-subject-area/{subjectArea}")
    public ResponseEntity<List> filterBySubjectArea(@PathVariable String subjectArea)
    {
        return new ResponseEntity<>(libraryService.filterBySubjectArea(subjectArea), HttpStatus.OK);
    }
    @GetMapping("/filter-by-type-and-subject-area")
    public ResponseEntity<List> filterByTypeAndSubjectArea(@RequestParam String type,
                                                           @RequestParam String subjectArea)
    {
        return new ResponseEntity<>(libraryService.filterByTypeAndSubjectArea(type, subjectArea),
                HttpStatus.OK);
    }
    @PutMapping("/give-back/{id}")
    public ResponseEntity<Map> giveBack(@PathVariable String id)
    {
        return new ResponseEntity<>(libraryService.giveBack(id), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ResourceDTO> create(@RequestBody ResourceDTO resourceDTO) {
        return new ResponseEntity<>(libraryService.create(resourceDTO), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List> list() {
        return new ResponseEntity<>(libraryService.list(), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ResourceDTO> findById(@PathVariable String id) {
        return new ResponseEntity<>(libraryService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResourceDTO> list(@RequestBody ResourceDTO resourceDTO)
    {
        return new ResponseEntity<>(libraryService.update(resourceDTO) , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable String id) {
        return new ResponseEntity<>(libraryService.delete(id), HttpStatus.OK);
    }
}