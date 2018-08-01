/*
   Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This file is licensed under the Apache License, Version 2.0 (the "License").
   You may not use this file except in compliance with the License. A copy of
   the License is located at

    http://aws.amazon.com/apache2.0/

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
   CONDITIONS OF ANY KIND, either express or implied. See the License for the
   specific language governing permissions and limitations under the License.
*/
package aws.example.s3;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;

import java.util.List;

/**
 * List your Amazon S3 buckets.
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class ListBuckets
{
    public static void main(String[] args)
    {
    	
    	System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
    	
    	
    	

    	ClientConfiguration conf = new ClientConfiguration();
    	conf.setProxyHost("ADD_PROXY_URL_HERE");
    	conf.setProxyPort(8080);    	
    	conf.setProtocol(Protocol.HTTPS);
    	
    	// load the profile from credentials file

    	AWSCredentials credentials = new ProfileCredentialsProvider("default").getCredentials();
    	System.out.println( "credentials.getAWSAccessKeyId() " + credentials.getAWSAccessKeyId() + " credentials.getAWSSecretKey() " 
    	+ credentials.getAWSSecretKey()) ;

    	             

        // uncomment below line which choses the default profile set in credentials file
        //final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();    	       

        // comment below line to load default profile from credentials file
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        		.withCredentials(new ProfileCredentialsProvider("default"))
        		.withClientConfiguration(conf)
        		.withRegion("us-west-2")
        		.build();

        System.out.println(s3.getRegion());
        System.out.println(s3.getRegionName());
        
        CreateBucketRequest cbr = new CreateBucketRequest("my-dev-test"+System.currentTimeMillis());
    	
        System.out.println("cbr.getBucketName() :: " + cbr.getBucketName());
        
        System.out.println(s3.listBuckets());
        s3.createBucket(cbr);
        
        System.out.println(s3.getBucketLocation("my-dev-test-1"));
        
        
        String bucketLocation = s3.getBucketLocation(new GetBucketLocationRequest(cbr.getBucketName()));
        System.out.println("Bucket location: " + bucketLocation);
        
        
       // final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }
        
    	/*AmazonS3Client s3client = new AmazonS3Client(basicCredentials);
    	
    	System.out.println(" Ready to create Bucket ");
    	s3client.createBucket("my-dev-test-2");
    	System.out.println(" Bucket created " );
    	*/
    	        
      /*  List<Bucket> buckets = s3client.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }*/
        
        
        
        
    }
}
