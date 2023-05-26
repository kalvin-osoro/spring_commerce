package com.ecomerce.guava.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class HelperUtility {

    public static String toBase64String(String value) {
        byte[] data = value.getBytes(StandardCharsets.ISO_8859_1);
        return Base64.getEncoder().encodeToString(data);
    }

    public static String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @SneakyThrows
//    public static String getSecurityCredentials(String initiatorPassword) {
//        String encryptedPassword;
//
//        try {
//            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//            byte[] input = initiatorPassword.getBytes();
//
//            Resource resource = new ClassPathResource("cert.cer");
//            InputStream inputStream = resource.getInputStream();
//
//            FileInputStream fileInputStream = new FileInputStream(resource.getFile());
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            X509Certificate certificate = (X509Certificate) cf.generateCertificate(fileInputStream);
//            PublicKey pk = certificate.getPublicKey();
//            cipher.init(Cipher.ENCRYPT_MODE, pk);
//
//            byte[] cipherText = cipher.doFinal(input);
//
//            //convert the resulting encrypted byte array into a string using base64 encoding
//            encryptedPassword = Base64.getEncoder().encode(cipherText).toString().trim();
//            return encryptedPassword;
//        } catch (NoSuchAlgorithmException | CertificateException | InvalidKeyException | NoSuchPaddingException |
//                IllegalBlockSizeException | BadPaddingException | NoSuchProviderException | FileNotFoundException e) {
//            log.error(String.format("Error generating security credentials -> %s", e.getLocalizedMessage()));
//            throw e;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    public static String getSecurityCredentials(String initiatorPassword) throws NoSuchAlgorithmException,
            CertificateException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException, IOException {
        String encryptedPassword;

        Security.addProvider(new BouncyCastleProvider());
        byte[] input = initiatorPassword.getBytes();

        try (InputStream inputStream = HelperUtility.class.getClassLoader().getResourceAsStream("cert.cer")) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(inputStream);
            PublicKey pk = certificate.getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, pk);

            byte[] cipherText = cipher.doFinal(input);

            // Convert the resulting encrypted byte array into a string using base64 encoding
            encryptedPassword = Base64.getEncoder().encodeToString(cipherText).trim();
            return encryptedPassword;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Certificate file not found", e);
        }
    }

    public static String getTransactionUniqueNumber() {
        RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0','z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();

        String transactionNumber = stringGenerator.generate(12).toUpperCase();
        log.info("=================Transaction Number:" + transactionNumber);

        return transactionNumber;

//        return stringGenerator.generate(12).toUpperCase();
    }

    public static String getStkPushPassword(String shortCode, String passKey, String timeStamp) {
        String concatenatedString = String.format("%s%s%s", shortCode,passKey,timeStamp);
        return toBase64String(concatenatedString);
    }
    public static String getTransactionTimestamp() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

}
