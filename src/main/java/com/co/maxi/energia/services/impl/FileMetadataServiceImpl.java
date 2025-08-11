package com.co.maxi.energia.services.impl;

import com.co.maxi.energia.Repository.FileMetadataRepository;
import com.co.maxi.energia.entity.FileMetadata;
import com.co.maxi.energia.services.FileMetadataService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import com.co.maxi.energia.utils.CompressImages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class FileMetadataServiceImpl implements FileMetadataService {

    private final FileMetadataRepository fileRepository;

    private final S3Client s3Client;

    private final Region region;

    private final CompressImages compressImages;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public FileMetadata uploadFile(MultipartFile file) throws IOException {

        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        byte[] fileBytes;

        if (file.getContentType() != null) {
            fileBytes = compressImages.compressImage(file);
        } else {
            fileBytes = file.getBytes(); // no es imagen, dejar como está
        }

        try(InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, fileBytes.length));

            String url = String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region.toString(), key);

            FileMetadata archivo = FileMetadata.builder()
                .originalFileName(file.getOriginalFilename())
                .url(url)
                .build();

            return fileRepository.save(archivo);
        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void deleteArchivo(Long id) {
        Optional<FileMetadata> archivo = fileRepository.findById(id);

        if (archivo.isPresent()) {
            String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/",
                    bucketName, region.toString());

            String key = archivo.get().getUrl().replace(bucketUrlPrefix, "");

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteRequest);

            fileRepository.deleteById(id);
        }

    }

    @Override
    public FileMetadata updateArchivo(Long id, MultipartFile file) throws IOException {
        FileMetadata archivoExistente = fileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Archivo no encontrado con ID: " + id));

        // Extraer key del archivo anterior
        String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/",
            bucketName, region.toString());
        String oldKey = archivoExistente.getUrl().replace(bucketUrlPrefix, "");

        // Borrar archivo antiguo de S3
        s3Client.deleteObject(DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(oldKey)
            .build());

        // Subir nuevo archivo
        String newKey = UUID.randomUUID() + "_" + file.getOriginalFilename();
        byte[] fileBytes;

        if (file.getContentType() != null) {
            fileBytes = compressImages.compressImage(file);
        } else {
            fileBytes = file.getBytes(); // no es imagen, dejar como está
        }

        try(InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(newKey)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, fileBytes.length));

        } catch (IOException e) {
            throw new RuntimeException("Error al actualizar archivo en S3", e);
        }

        // Actualizar entidad en base de datos
        String nuevaUrl = String.format("https://%s.s3.%s.amazonaws.com/%s",
            bucketName, region.toString(), newKey);

        archivoExistente.setOriginalFileName(file.getOriginalFilename());
        archivoExistente.setUrl(nuevaUrl);

        return fileRepository.save(archivoExistente);
    }
}
