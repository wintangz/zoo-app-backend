package net.wintang.zooapp.service;

import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class AzureStorageService {

    private final String accountName; // Load from properties or configuration
    private final String accountKey;  // Load from properties or configuration
    private final String containerName; // Specify the container name

    public AzureStorageService(@Value("${azure.storage.account-name}") String accountName,
                               @Value("${azure.storage.account-key}") String accountKey,
                               @Value("${azure.storage.container-name}") String containerName) {
        this.accountName = accountName;
        this.accountKey = accountKey;
        this.containerName = containerName;
    }

    public String uploadImageToBlobStorage(String imageName, byte[] imageData) {
        // Create a BlobServiceClient
        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s", accountName, accountKey);
        BlobServiceClientBuilder serviceClientBuilder = new BlobServiceClientBuilder().connectionString(connectionString);
        BlockBlobClient blobClient = serviceClientBuilder.buildClient().getBlobContainerClient(containerName).getBlobClient(imageName).getBlockBlobClient();

        // Upload the image data
        blobClient.upload(new ByteArrayInputStream(imageData), imageData.length, true);

        // Return the URL of the uploaded image
        return blobClient.getBlobUrl();
    }
}




