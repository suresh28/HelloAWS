package com.aws.sample.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.aws.sample.config.ConfigParam;
import com.aws.sample.exception.DownloadException;



@Controller
@RequestMapping
public class FileCreater {

	
	private static final Logger LOG = Logger.getLogger(FileCreater.class
			.getSimpleName());

	@RequestMapping(value = "/create/{bucketName}/{fileName}", method = RequestMethod.GET )
	public void doDownloadValidationLog(HttpServletResponse response,
			HttpServletRequest request, @PathVariable("bucketName") String bucketName,@PathVariable("fileName") String fileName, SessionStatus status)
			throws IOException,DownloadException {

		
		System.out.println("Ready to create object in s3: " + fileName);
		

		if (fileName == null) {
			LOG.error("Fis file cannot found");
			throw new DownloadException("Requested File name parameter is null");
		}
		
		if (bucketName == null) {
			LOG.error("Bucket file cannot found");
			throw new DownloadException("Bucket Name paramter is null");
		}

		
		ClientConfiguration conf = new ClientConfiguration();
    	conf.setProxyHost("ADD_PROXY_URL_HERE");
    	conf.setProxyPort(8080);    	
    	conf.setProtocol(Protocol.HTTPS);
    	
        
       // String bucket_name=ConfigParam.S3_BUCKET_NAME;
        String bucket_name=bucketName;
        
        fileName = fileName+"_"+System.currentTimeMillis()+".txt";
        
        System.out.format("Creating %s from S3 bucket %s...\n",  fileName,bucket_name);
        
        
        /*final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        		.withCredentials(new ProfileCredentialsProvider("default"))        		
        		.withRegion("us-west-2")
        		.build();
        */
        
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        
        try {
    		
    		if ( s3.doesBucketExistV2(bucket_name)){
    			
    			//PutObjectResult objResult=s3.putObject(bucket_name, fileName, new File(fileName));
    			
    			PutObjectResult objResult=s3.putObject(bucket_name, fileName, "Hello S3");
    			
    			System.out.println("objResult.getMetadata().getContentLength() :: " +objResult.getMetadata().getContentLength()); 
    			
    		} else
    		{
    			throw new DownloadException("Requested Bucket does not exist in S3");
    		}
    		
           
            
        } catch (AmazonServiceException e) {
            System.err.println("AWS Service exception occured ::" + e.getErrorMessage());
            //System.exit(1);
        } 
        
	        
		
		
		
		LOG.info(" Download Controller - process completed :: "  + fileName);

		// return K2View.DOWNLOAD_SUCCESS;

	}
	
}