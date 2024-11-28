package project.MoongChee.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class AwsS3Config {
    
    @Value("${aws.s3.accessKey:}")
    private String accessKey;

    @Value("${aws.s3.secretKey:}")
    private String secretKey;

    @Value("${aws.s3.region:}")
    private String region;

    @Value("${aws.s3.bucketName:}")
    private String bucketName;

    @PostConstruct
    public void logConfig() {
        System.out.println("S3 Configuration - AccessKey: " + accessKey + ", SecretKey: " + secretKey +
                ", Region: " + region + ", BucketName: " + bucketName);
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .serviceConfiguration(S3Configuration.builder()
                        .checksumValidationEnabled(true)
                        .build())
                .build();
    }
}
