package com.dbq.s3storage.service;

import com.dbq.s3storage.exc.GenericErrorResponse;
import com.dbq.s3storage.model.MimeTypes;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectTagsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.messages.Tags;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinIoService {
	
	 @Autowired
	 private MinioClient minioClient;
	 
	 @Value("${minio.url}")
	 private String url;
	 
	 public String  uploadFile(String bucketName,InputStream inputStream, String mimeType) {
		 	String objectName="";
	        try {
	            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
	            if (!found) {
	                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
	            }
	            objectName = UUID.randomUUID().toString() + MimeTypes.getDefaultExt(mimeType);
	            minioClient.putObject(
	                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
	                        inputStream, inputStream.available(), -1)
	                        .contentType(mimeType)
	                        .build());
	        } catch (Exception e) {
	        	throw GenericErrorResponse.builder()
                .message("Unable to upload file to S3")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
	        }
	        System.out.println("objectName>>>>"+objectName);
	        return objectName;
	    }
	 
	 public Map<String,Object>  downloadFile(String bucketName,String id) {
		 	Map<String,Object> map = new HashMap<String,Object>();
	        try {

	        	InputStream is = minioClient.getObject(
	        			   GetObjectArgs.builder()
	        			     .bucket(bucketName)
	        			     .object(id)
	        			     .build());
	        	if(is!=null)
	        	{
	        		StatObjectResponse objectStat =
		        		    minioClient.statObject(
		        		        StatObjectArgs.builder()
		        		            .bucket(bucketName)
		        		            .object(id)
		        		            .build());
	        		
	        		map.put("file-content", IOUtils.toByteArray(is));
	 	            map.put("mime-type", objectStat.contentType());
	 	            map.put("file-size", objectStat.size());
	 	            return map;
	        	}
	        	else
	        	{
	        		throw GenericErrorResponse.builder()
	                .message("Unable to download file from S3")
	                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	                .build();
	        	}
	           
	           
	        } catch (Exception e) {
	        	throw GenericErrorResponse.builder()
                .message("Unable to download file from S3")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
	        }
	    }

    public void deleteFile(String bucketName, String id) {
    	 try {
    	minioClient.removeObject(
    			RemoveObjectArgs.builder()
    			.bucket(bucketName)
    			.object(id)
    			.build());
    	
    	 } catch (Exception e) {
	        	throw GenericErrorResponse.builder()
             .message("Unable to remove file from S3")
             .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
             .build();
	        }
    }
    
    public void deleteBucket(String bucketName) {
   	 try {
   	minioClient.removeBucket(
   			RemoveBucketArgs.builder()
   			.bucket(bucketName)
   			.build());
   	
   	 } catch (Exception e) {
	        	throw GenericErrorResponse.builder()
            .message("Unable to delete bucket from S3")
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
	        }
   }
    
    public void createBucket(String bucketName) {
      	 try {
      		 boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
	            if (!found) {
	                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
	            }
      	
      	 } catch (Exception e) {
   	        	throw GenericErrorResponse.builder()
               .message("Unable to create bucket to S3")
               .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
               .build();
   	        }
      }


   
}