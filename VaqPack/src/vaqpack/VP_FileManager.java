/*-----------------------------------------------------------------------------*
* VP_FileManager.java
* - Manages anything related to files, such as saving or reading the html files
*   and saving pdf files
* Authors:
* - Team-02
* -- William Dewald (Project Manager)
* -- Fernando Bazan
* -- Nathanael Carr
* -- Erik Lopez
* -- Raul Saavedra
* FILE ID 1300
*-----------------------------------------------------------------------------*/
package vaqpack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class VP_FileManager {
    private String keyPass = "localhostvaqPack3306";
    private final String vector = "VAQPACK1";
    private SecretKey key;
    private IvParameterSpec ivps;
    
    protected String[] retrieveUrlPort() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
	InputStream input = new FileInputStream("mysqll.properties");
        prop.load(input);
        String url = prop.getProperty("url");
        String port = prop.getProperty("port");
        return new String[]{url, port};
    }
    
    protected void storeUrlPort(String[] loc) throws FileNotFoundException, IOException  {
        Properties prop = new Properties();
	OutputStream output = new FileOutputStream("mysqll.properties");
        prop.setProperty("url", loc[0]);
        prop.setProperty("port", loc[1]);
        prop.store(output, null);
    }
    
    protected void storeAdminUserPass(String[] cred) throws FileNotFoundException, 
            IOException, NoSuchAlgorithmException, 
            NoSuchPaddingException, InvalidKeyException, 
            InvalidAlgorithmParameterException, UnsupportedEncodingException, 
            IllegalBlockSizeException, BadPaddingException  {
        Properties prop = new Properties();
	OutputStream output = new FileOutputStream("mysqla.properties");
        prop.setProperty("user", encrypt(cred[0]));
        prop.setProperty("pass", encrypt(cred[1]));
        prop.store(output, null);
    }
    
    private String encrypt(String input) throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException {
        key = new SecretKeySpec(Arrays.copyOf(keyPass.getBytes("utf-8"), 24), "DESede");
        ivps = new IvParameterSpec(vector.getBytes("utf-8"));
        Cipher encrypt = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        encrypt.init(Cipher.ENCRYPT_MODE, key, ivps);
        byte[] inputBytes = input.getBytes("utf-8");
        byte[] encryptedInputBytes = encrypt.doFinal(inputBytes);
        return Arrays.toString(encryptedInputBytes);
    }
    
    private String decrypt(String input) throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException {
        key = new SecretKeySpec(Arrays.copyOf(keyPass.getBytes("utf-8"), 24), "DESede");
        ivps = new IvParameterSpec(vector.getBytes("utf-8"));
        Cipher decrypt = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decrypt.init(Cipher.DECRYPT_MODE, key, ivps);
        String[] byteValues = input.substring(1, input.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];
        for (int i = 0; i < bytes.length; i++)
           bytes[i] = Byte.parseByte(byteValues[i].trim());
        byte[] decryptedInputBytes = decrypt.doFinal(bytes);
        return new String(decryptedInputBytes);
    }
    
    protected String[] retrieveAdminEncrypted(String url, String port) throws FileNotFoundException,
            IOException,
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        keyPass = url + "vaqPack" + port;
        Properties prop = new Properties();
	InputStream input = new FileInputStream("mysqla.properties");
        prop.load(input);
        String userEncrypted = prop.getProperty("user"),
                passEncrypted = prop.getProperty("pass"),
                user = "",
                pass = "";
        if (!(userEncrypted == null || passEncrypted == null)) {
            user = decrypt(userEncrypted);
            pass = decrypt(passEncrypted);
        }
        return new String[]{user, pass};
    }
}
