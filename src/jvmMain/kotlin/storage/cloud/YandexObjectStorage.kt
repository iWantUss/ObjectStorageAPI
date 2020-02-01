package storage.cloud

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.google.gson.Gson
import components.Component
import components.DefaultInfo
import storage.Storage
import java.io.InputStreamReader

class YandexObjectStorage(val selectBucket: String, val yandexObjectStorageConfig: YandexObjectStorageConfig) :
    Storage {

    private val s3: AmazonS3 by lazy {
        AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(yandexObjectStorageConfig))
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(
                    yandexObjectStorageConfig.endpoint, yandexObjectStorageConfig.region
                )
            ).build()
    }

    private fun listBuckets(): List<String> {
        return s3.listBuckets().map { it.name }
    }

    init {
        if (!listBuckets().contains(selectBucket)) {
            throw BucketNotFoundException(selectBucket)
        }
    }

    override fun pull(key: String): Component? {
        try {
            val obj = s3.getObject(selectBucket, key)
            return Component(
                obj.key,
                Gson().fromJson(InputStreamReader(obj.objectContent.delegateStream), DefaultInfo::class.java)
            )
        } catch (e : AmazonS3Exception){
            return null
        }
    }

    override fun push(component: Component) {
        s3.putObject(selectBucket, component.key, Gson().toJson(component.info))
    }

    override fun delete(component: Component) {
        s3.deleteObject(selectBucket, component.key)
    }
}

private class BucketNotFoundException(private val bucketName: String) : Exception("Bucket `$bucketName` is not found.")


data class YandexObjectStorageConfig(
    val SECRET_KEY: String,
    val KEY: String,
    val region: String = "ru-central1",
    val endpoint: String = "storage.yandexcloud.net"
) : AWSCredentials {

    override fun getAWSAccessKeyId() = KEY

    override fun getAWSSecretKey() = SECRET_KEY

}
