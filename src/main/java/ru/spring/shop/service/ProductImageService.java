package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.spring.shop.dao.ProductImageDao;
import ru.spring.shop.exeption.StorageException;
import ru.spring.shop.exeption.StorageFileNotFoundException;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductImageService {

//    private List<String> paths; //пути внутри storageLocation
    private static final String path = "products";
    @Value("${storage.location}")
    private String storagePath;
    private final ProductImageDao productImageDao;
    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(storagePath);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            log.error("Error while creating storage {}", rootLocation.toAbsolutePath());
            throw new StorageException(String.format("Error while creating storage %s", rootLocation.toAbsolutePath()));
        }
    }

    public List<String> save(MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            filenames.add(this.save(file, filename));
        }
        return filenames;
    }

    public String save(MultipartFile file) {
        String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        return this.save(file, filename);
    }

    public String save(MultipartFile file, String filename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException(String.format("File %s is empty", filename));
            }
            if (filename.contains("..")) {
                throw new StorageException("Symbol '..' do not permit");
            }
            Files.createDirectories(rootLocation.resolve(path));
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(rootLocation.resolve(path))) {
                for (Path child : dirStream) {
                    if (child.getFileName().toString().equals(filename)) {
                        throw new StorageException(String.format("File with name %s/%s already exists", rootLocation.resolve(path), filename));
                    }
                }
            } catch (IOException e) {
                throw new StorageException(String.format("Error while creating file %s", filename));
            }
        } catch (IOException e) {
            throw new StorageException("Error while creating storage");
        }
        try(InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(path).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException(String.format("Error while saving file %s", filename));
        }
        return filename;
    }

    public BufferedImage loadFileAsImageByImageId(Long id) throws IOException {
        String imageName = productImageDao.findById(id)
                .orElseThrow(() -> new StorageFileNotFoundException(String.format("File with id %s not found in directory %s", id, path)))
                        .getPath();
        Resource resource = loadAsResource(imageName);
        return ImageIO.read(resource.getFile());
    }

    public BufferedImage loadFileAsImage(Long id) throws IOException {
        String imageName = productImageDao.findIconImageNameByProductId(id);
        Resource resource = loadAsResource(imageName);
        return ImageIO.read(resource.getFile());
    }

    public Resource loadAsResource(String filename) {

        if (StringUtils.hasText(filename)) {
            try {
                Path file = rootLocation.resolve(path).resolve(filename);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new StorageFileNotFoundException(String.format("File %s not found in directory %s", filename, path));
                }
            } catch (MalformedURLException e) {
                throw new StorageFileNotFoundException(String.format("File %s not found in directory %s", filename, path), e);
            }
        } else {
            throw new StorageFileNotFoundException(String.format("Filename cannot be empty: %s", filename));
        }
    }

}
