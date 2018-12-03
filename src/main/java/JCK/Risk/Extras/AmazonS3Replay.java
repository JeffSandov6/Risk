package JCK.Risk.Extras;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class AmazonS3Replay {
	
	private AmazonS3 s3Client;
	private String gameBucketName = "jck-allgamereplays-bucket";
	private String gameId, gameFilePath;
	private FileWriter fw;
	private BufferedWriter bw;
	
	
	public AmazonS3Replay() {
		createClientConnection();
		createGameId();
		createLoggerFile();
		System.out.println("success");
	}
	
	
//	public void createBucket(String bucketName) {
//		s3Client.createBucket(bucketName);
//	}
	
	public void addGameToS3Bucket() throws IOException {
		bw.close();
		File gameFile = new File(gameFilePath);
		PutObjectRequest putObjRequest = new PutObjectRequest(
				gameBucketName,
				getGameId(), 
				new File(gameFilePath))
				.withCannedAcl(CannedAccessControlList.PublicRead);
		
		s3Client.putObject(putObjRequest);
		gameFile.delete();
	}
	
	public String retrieveGameToReplay() throws IOException {
		
		S3Object s3Obj = s3Client.getObject(
				gameBucketName,
				getGameId());
		
		S3ObjectInputStream inputStream = s3Obj.getObjectContent();
		FileUtils.copyInputStreamToFile(
				inputStream,
				new File(gameFilePath));
		
		return gameFilePath;
		
	}
	
	private void createLoggerFile() {
		
		try {
			fw = new FileWriter(getGameId(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		gameFilePath = System.getProperty("user.dir") + "/" + getGameId();
		bw = new BufferedWriter(fw);
	}
	
	public void appendToLogger(String gameAction) {
		try {
			bw.write(gameAction);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createGameId() {
		gameId = "game" + RandomStringUtils.
				random(10, true, true) + ".txt";
		
	}
	
	private String getGameId() {
		return this.gameId;
	}
	
	private void createClientConnection() {
		Properties keys = grabApiProps();
		
		AWSCredentials credentials = new BasicAWSCredentials(
				keys.getProperty("s3Access"),  //access key id
				keys.getProperty("s3SecretKey"));  //secret access key
				
		
		s3Client = AmazonS3ClientBuilder
				.standard()
				.withCredentials(
						new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_WEST_1)
				.build();
				
	}
	
	private Properties grabApiProps() {
	    Properties sysProp = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream("secret_JCK_.prop");
			sysProp.load(in);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  
	  return sysProp;
		
		
	}

}
