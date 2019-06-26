package com.icefire.api.information.application.security;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import static java.lang.System.out;

public class KeyGenerator {

    final static String PATH = "src/main/resources/keys/";

    public static byte[] keyPairGenerator(String username) {
        byte[] publicKeyBytes = new byte[0];
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            Key pub = kp.getPublic();
            Key pvt = kp.getPrivate();

            //safe private
            savePrivateKeyToFile(pvt, username);

            publicKeyBytes = pub.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return publicKeyBytes;
    }

    public static byte[] getPrivateKey(String username) {
        byte[] privateKeyBytes = new byte[0];
        try {
            /* Read all bytes from the private key file */
            Path path = Paths.get(PATH + username + "_" + ".key");
            byte[] bytes = Files.readAllBytes(path);

            /* Generate private key. */
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey pvt = kf.generatePrivate(ks);

            privateKeyBytes = pvt.getEncoded();
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return privateKeyBytes;

    }

    private static void savePrivateKeyToFile(Key privateKey, String username) {
        try {
            PrintStream out = new java.io.PrintStream(PATH + username + "_" + ".key");
            out.write(privateKey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

}
