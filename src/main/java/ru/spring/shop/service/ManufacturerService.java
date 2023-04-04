package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.dao.ManufacturerDao;
import ru.spring.shop.dao.ProductDao;
import ru.spring.shop.entity.Manufacturer;
import ru.spring.shop.entity.enums.Status;
import ru.spring.shop.web.mapper.ManufacturerMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerService {

    private final ManufacturerDao manufacturerDao;
    private final ManufacturerMapper manufacturerMapper;

    private final ProductDao productDao;

    @Transactional(readOnly = true)
    public List<ManufacturerDto> findAll() {
        return manufacturerDao.findAll().stream()
                .map(manufacturer -> manufacturerMapper.toManufacturerDto(manufacturer))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ManufacturerDto findByName(String name) {
        return manufacturerMapper.toManufacturerDto(manufacturerDao.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("There is no manufacturer with name " + name)));
    }

    @Transactional(readOnly = true)
    public ManufacturerDto findById(Long id) {
        return manufacturerMapper.toManufacturerDto(manufacturerDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no manufacturer with id " + id)));
    }

    public ManufacturerDto save(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerMapper.toManufacturer(manufacturerDto);
        if (manufacturer.getId() != null) {
            manufacturerDao.findById(manufacturer.getId()).ifPresent(m -> {
                manufacturer.setVersion(m.getVersion());
            });
        }
        return manufacturerMapper.toManufacturerDto(manufacturerDao.save(manufacturer));
    }

    public void deleteById(Long id) {
        Optional<Manufacturer> manufacturer = manufacturerDao.findById(id);
        manufacturer.ifPresent(m -> {
            m.setStatus(Status.DISABLE);
            manufacturerDao.save(m);
        });
    }

    public void delete(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerMapper.toManufacturer(manufacturerDto);
        if (manufacturer.getId() != null) {
            manufacturer.setStatus(Status.DISABLE);
            manufacturerDao.save(manufacturer);
        }
    }


}