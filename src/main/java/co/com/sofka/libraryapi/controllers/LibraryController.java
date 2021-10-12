package co.com.sofka.libraryapi.controllers;

import co.com.sofka.libraryapi.dtos.ResourceDTO;
import co.com.sofka.libraryapi.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/resources")
public class LibraryController {
    private LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
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