package com.co.maxi.energia.services;

import com.co.maxi.energia.entity.FileMetadata;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileMetadataService {

    FileMetadata uploadFile(MultipartFile file) throws IOException;

    void deleteArchivo(Long id);

    FileMetadata updateArchivo(Long id, MultipartFile file) throws IOException;
}
