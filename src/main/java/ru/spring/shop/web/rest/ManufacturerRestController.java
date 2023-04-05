package ru.spring.shop.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.service.ManufacturerService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturer")
@Slf4j
public class ManufacturerRestController {

    private final ManufacturerService manufacturerService;

    @GetMapping("/all")
    public List<ManufacturerDto> getManufacturerList() {
//        log.error("EXCEPTION FOR RETRY");
//        throw new RuntimeException();
        return manufacturerService.findAll();
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "manufacturerId") Long id) {
        ManufacturerDto manufacturer = manufacturerService.findById(id);
        if (manufacturer.getId() != null) {
            ManufacturerDto manufacturerDto = ManufacturerDto.builder()
                    .id(manufacturer.getId())
                    .name(manufacturer.getName())
                    .status(manufacturer.getStatus())
                    .build();
            return new ResponseEntity<>(manufacturerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addManufacturer(@Validated @RequestBody ManufacturerDto manufacturerDto) {
        ManufacturerDto savedManufacturer = manufacturerService.save(manufacturerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/manufacturer/" + savedManufacturer.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "manufacturerId") Long id,
                                                @Validated @RequestBody ManufacturerDto manufacturerDto) {
        manufacturerDto.setId(id);
        ManufacturerDto savedManufacturer = manufacturerService.save(manufacturerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/manufacturer/" + savedManufacturer.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{manufacturerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "manufacturerId") Long id) {
        manufacturerService.deleteById(id);
    }
}

