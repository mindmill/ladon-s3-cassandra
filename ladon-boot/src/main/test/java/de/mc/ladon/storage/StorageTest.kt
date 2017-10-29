package de.mc.ladon.storage

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.S3ClientOptions
import com.amazonaws.services.s3.model.GetObjectMetadataRequest
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Ralf Ulrich
 * 17.03.17
 */
fun main(args: Array<String>) {

    val s3Client = getS3Client("iTK2YXGwMRzWrGyo8u9G", "l1qTIkMAoKC2R_oLHDfdEJ211hPRL0XAGOrz-toY")

    Files.list(Paths.get("/home/ralf/Bilder")).forEach {
        if (it.toFile().isFile) {
            println("put object " + it.toFile().name)
            s3Client.putObject("test", it.toFile().name, it.toFile())
          //  s3Client.deleteObject("test2", it.toFile().name)
        }
    }



    s3Client.listVersions("bilder", "System.Drawing.dll").versionSummaries.filter { !it.isDeleteMarker }.forEach {
        println(it.key + " - " + it.isDeleteMarker)
        val contentLength = s3Client.getObjectMetadata(GetObjectMetadataRequest("bilder", it.key, it.versionId)).contentLength
        println("length: $contentLength")
    }

}

fun getS3Client(accessKey: String, secretKey: String): AmazonS3Client {
    val credentials = BasicAWSCredentials(accessKey, secretKey)
    val newClient = AmazonS3Client(credentials,
            ClientConfiguration())
    newClient.setS3ClientOptions(S3ClientOptions().withPathStyleAccess(true))
    newClient.setEndpoint("http://localhost:8080/services/s3")
    newClient.signerRegionOverride = "S3SignerType"
    return newClient
}
