package com.icefire.api.common.infrastructure.security;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.lang.System.out;

public class KeyGenerator {

    final static String PATH = "src/main/resources/keys/";

    public static byte[] keyPairGenerator(String username) {
        byte[] publicKeyBytes = new byte[0];
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            PublicKey pub = kp.getPublic(); // X.509 format
            PrivateKey pvt = kp.getPrivate(); // PKCS#8 format

            //safe private
            savePrivateKeyToFile(pvt, username);

            publicKeyBytes = pub.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return publicKeyBytes;
    }

    public static PrivateKey getPrivateKey(String username) {
        byte[] privateKeyBytes = new byte[0];
        try {
            /* Read all bytes from the private key file */
            Path path = Paths.get(PATH + username + "_" + ".key");
            byte[] bytes = Files.readAllBytes(path);

            /* Generate private key. */
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(ks);

        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void savePrivateKeyToFile(PrivateKey privateKey, String username) {
        try {
            PrintStream out = new PrintStream(PATH + username + "_" + ".key");
            out.write(privateKey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

}
