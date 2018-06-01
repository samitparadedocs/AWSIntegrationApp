package com.example.awsintegrationapp;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.model.*;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.util.ImmutableMapParameter;


/**
 * Encrypt and decrypt a simple message using AWS KMS
 */
public class KMSExample {
    private final AWSKMSClient kms;
    private  String keyId = "f93e7516-d166-4f3b-ad58-f3c3d9815377";
    private final Map<String, String> encryptionContext =
            ImmutableMapParameter.of("hello", "goodbye");

    public KMSExample(String keyId) {
       this.keyId=keyId;
        kms = getClient();
    }

    private AWSKMSClient getClient() {
        AWSKMSClient kms = new AWSKMSClient(new BasicAWSCredentials(
                "AKIAJEDLREWZB75GUMQA", "KgEpy1+eyBrTcfbDsKj0m1taMxocyagZjkbXErcw"));

        kms.setEndpoint("https://kms.us-east-1.amazonaws.com");

        return kms;
    }

    public byte[] encrypt(String msg) {
        ByteBuffer plaintext = ByteBuffer.wrap(msg.getBytes());

        EncryptRequest req =
                new EncryptRequest().withKeyId(keyId).withPlaintext(plaintext);
        req.setEncryptionContext(encryptionContext);
        ByteBuffer ciphertext = kms.encrypt(req).getCiphertextBlob();
        return ciphertext.array();
    }

    public String decrypt(byte[] cipherbytes) {
        ByteBuffer ciphertext = ByteBuffer.wrap(cipherbytes);
        DecryptRequest req =
                new DecryptRequest().withCiphertextBlob(ciphertext);
        req.setEncryptionContext(encryptionContext);
        ByteBuffer plaintext = kms.decrypt(req).getPlaintext();

        return new String(plaintext.array());
    }

  /*  public static void main(String[] args) {
        KMSExample kms = new KMSExample();
        System.out.println("Encrypted message is:");
        byte[] enc = kms.encrypt("hello");
        System.out.println(new String(enc));
        System.out.println("Decrypted message is:");
        System.out.println(kms.decrypt(enc));
    }*/
}