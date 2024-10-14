package com.mathvideos.api.domain.service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.cloud.StorageClient;
import com.mathvideos.api.domain.service.util.ResponseHandler;

@Service
public class FirebaseService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private final ResponseHandler responseHandler;

	public FirebaseService(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}
	
	public ResponseEntity<Object> uploadThumbnail(MultipartFile file) {
	    try {
	        String fileName = "thumbnails/" + LocalDate.now() + "-" + UUID.randomUUID().toString() + "." + file.getContentType().split("/")[1];

	        var bucket = StorageClient.getInstance().bucket();
	        var blob = bucket.create(fileName, file.getInputStream(), file.getContentType());

	        String publicUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
	                bucket.getName(),
	                URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8));

	        return this.responseHandler.created("Upload finalizado", publicUrl);
	    } catch (Exception ex) {
	        logger.error("{} - Falha ao armazenar thumbnail no bucket", new Date());
	        logger.error(ex.getMessage());

	        return this.responseHandler.internalServerError("Ocorreu uma falha no upload da thumbnail");
	    }
	}

	public ResponseEntity<Object> uploadVideo(MultipartFile file) {
		try {
			String fileName = "videos/"+ LocalDate.now() + "-"+ UUID.randomUUID().toString() + ".mp4";

			var bucket = StorageClient.getInstance().bucket();
			var blob = bucket.create(fileName, file.getInputStream(), file.getContentType());

			String publicUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", 
	                bucket.getName(), 
	                URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8));
			
			return this.responseHandler.created("Upload finalizado", publicUrl);
		} catch (Exception ex) {
			logger.error("{} - Falha ao armazenar vídeo no bucket", new Date());
			logger.error(ex.getMessage());
			
			return this.responseHandler.internalServerError("Ocorreu uma falha no upload do vídeo");
		}
	}
	
	public ResponseEntity<Object> deleteFile(String fileUrl) {
	    try {
	        String fileName = fileUrl.substring(fileUrl.indexOf("/o/") + 3, fileUrl.indexOf("?"));

	        String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.toString());

	        var bucket = StorageClient.getInstance().bucket();
	        var blob = bucket.get(decodedFileName);

	        if (blob != null) {
	            blob.delete();
	            return this.responseHandler.ok("Arquivo deletado com sucesso", null);
	        } else {
	            return this.responseHandler.notFound("Arquivo não encontrado");
	        }
	    } catch (Exception ex) {
	        logger.error("{} - Falha ao deletar arquivo no bucket", new Date());
	        logger.error(ex.getMessage());

	        return this.responseHandler.internalServerError("Ocorreu uma falha ao deletar o arquivo");
	    }
	}

}
