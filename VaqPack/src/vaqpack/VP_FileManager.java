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

import com.lowagie.text.DocumentException;
import java.io.File;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VP_FileManager {

    private String keyPass;
    private final String vector;
    private SecretKey key;
    private IvParameterSpec ivps;
    private DocumentBuilderFactory icFactory;
    private DocumentBuilder icBuilder;

    /*------------------------------------------------------------------------*
     * VP_FileManager()
     * - Constructor. Initialiazes the keyPass and vector used in encryption.
     * No Parameters.
     *------------------------------------------------------------------------*/
    protected VP_FileManager() {
        //-------- Initialization Start ----------\\
        keyPass = "localhostvaqPack3306";
        vector = "VAQPACK1";
        icFactory = null;
        icBuilder = null;
        //-------- Initialization End ------------\\
    }

    /*------------------------------------------------------------------------*
     * retrieveUrlPort()
     * - Retrieves the database url and port from the properties file.
     * - No parameters.
     * - Returns a string array of the database url and port.
     *------------------------------------------------------------------------*/
    protected String[] retrieveUrlPort() throws FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        String urlPort[] = new String[2];
        Properties prop = new Properties();
        InputStream input = new FileInputStream("mysqll.properties");
        //-------- Initialization End ------------\\

        prop.load(input);
        urlPort[0] = prop.getProperty("url");
        urlPort[1] = prop.getProperty("port");
        return urlPort;
    }

    /*------------------------------------------------------------------------*
     * storeUrlPort()
     * - Stores the database url and port in the properties file.
     * - Parameter loc is a string array of the database url and port.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void storeUrlPort(String[] loc) throws FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        Properties prop = new Properties();
        OutputStream output = new FileOutputStream("mysqll.properties");
        //-------- Initialization End ------------\\

        prop.setProperty("url", loc[0]);
        prop.setProperty("port", loc[1]);
        prop.store(output, null);
    }

    /*------------------------------------------------------------------------*
     * retrieveAdminEncrypted()
     * - Retrieves the encrypted database admin user and password from the
     *   properties file and calls decrypt().
     * - Parameter url is a string of the database url used as part of
     *   encryption method.
     * - Parameter port is a string of the database port used as part of
     *   encryption method.
     * - Returns a string array of decrypted database admin user credentials.
     *------------------------------------------------------------------------*/
    protected String[] retrieveAdminEncrypted(String url, String port) throws FileNotFoundException,
            IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        //-------- Initialization Start ----------\\
        keyPass = url + "vaqPack" + port;
        Properties prop = new Properties();
        InputStream input = new FileInputStream("mysqla.properties");
        String[] cred = new String[2];
        //-------- Initialization End ------------\\

        prop.load(input);
        cred[0] = prop.getProperty("user");
        cred[1] = prop.getProperty("pass");
        if (!(cred[0] == null || cred[1] == null)) {
            cred[0] = decrypt(cred[0]);
            cred[1] = decrypt(cred[1]);
        }
        return cred;
    }

    /*------------------------------------------------------------------------*
     * storeAdminUserPass()
     * - Stores the encrypted database admin user and password in the
     *   properties file.
     * - Parameter cred is a string array of the database admin user
     *   and password.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void storeAdminUserPass(String[] cred) throws FileNotFoundException,
            IOException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        //-------- Initialization Start ----------\\
        Properties prop = new Properties();
        OutputStream output = new FileOutputStream("mysqla.properties");
        //-------- Initialization End ------------\\

        prop.setProperty("user", encrypt(cred[0]));
        prop.setProperty("pass", encrypt(cred[1]));
        prop.store(output, null);
    }
    
    protected File generateBCardPDF(VP_User user) throws FileNotFoundException, 
            TransformerException, ParserConfigurationException, IOException, DocumentException {
        File bcpdf = new File("bcpdf.tmp"),
                bcxsl = new File("bcxsl.tmp"),
                bcxml = new File("bcxml.tmp"),
                bchtml = new File("bchtml.tmp");
        bcxsl.deleteOnExit();
        bcpdf.deleteOnExit();
        bcxml.deleteOnExit();
        bchtml.deleteOnExit();
        if (user.getBcard().hasCompletedBusinessCard() == true) {
            // write the xml file
            FileOutputStream outputStream = new FileOutputStream(bcxml);
            StreamResult xmlStream = new StreamResult(outputStream);
            icFactory = DocumentBuilderFactory.newInstance();
            icBuilder = icFactory.newDocumentBuilder();
            Document document = icBuilder.newDocument();
            Element root = document.createElement("businesscard");
            document.appendChild(root);
            Element name = document.createElement("name");
            root.appendChild(name);
            Element company = document.createElement("company");
            root.appendChild(company);
            Element address = document.createElement("address");
            root.appendChild(address);
            Element communication = document.createElement("communication");
            root.appendChild(communication);
            Element firstname = document.createElement("firstname");
            name.appendChild(firstname);
            firstname.appendChild(document.createTextNode(user.getFirstName().getValueSafe()));
            Element middlename = document.createElement("middlename");
            name.appendChild(middlename);
            middlename.appendChild(document.createTextNode(user.getMiddleName().getValueSafe()));
            Element lastname = document.createElement("lastname");
            name.appendChild(lastname);
            lastname.appendChild(document.createTextNode(user.getLastName().getValueSafe())); 
            Element profession = document.createElement("profession");
            company.appendChild(profession);
            profession.appendChild(document.createTextNode(user.getBcard().getProfession().getValueSafe()));
            Element companyname = document.createElement("companyname");
            company.appendChild(companyname);
            companyname.appendChild(document.createTextNode(user.getBcard().getCompanyName().getValueSafe()));
            Element slogan = document.createElement("slogan");
            company.appendChild(slogan);
            slogan.appendChild(document.createTextNode(user.getBcard().getCompanySlogan().getValueSafe()));
            Element line1 = document.createElement("line1");
            address.appendChild(line1);
            line1.appendChild(document.createTextNode(user.getAddress1().getValueSafe()));
            Element line2 = document.createElement("line2");
            address.appendChild(line2);
            line2.appendChild(document.createTextNode(user.getAddress2().getValueSafe()));
            Element city = document.createElement("city");
            address.appendChild(city);
            city.appendChild(document.createTextNode(user.getCity().getValueSafe()));
            Element state = document.createElement("state");
            address.appendChild(state);
            state.appendChild(document.createTextNode(user.getState().getValueSafe()));
            Element zip = document.createElement("zip");
            address.appendChild(zip);
            zip.appendChild(document.createTextNode(user.getZip().getValueSafe()));
            Element phone = document.createElement("phone");
            communication.appendChild(phone);
            phone.appendChild(document.createTextNode(user.getPhone().getValueSafe()));
            Element cell = document.createElement("cell");
            communication.appendChild(cell);
            cell.appendChild(document.createTextNode(user.getCell().getValueSafe()));
            Element email = document.createElement("email");
            communication.appendChild(email);
            email.appendChild(document.createTextNode(user.getDocEmail().getValueSafe()));
            Element web = document.createElement("web");
            communication.appendChild(web);
            web.appendChild(document.createTextNode(user.getBcard().getWebPage().getValueSafe()));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
            DOMSource source = new DOMSource(document);
            transformer.transform(source, xmlStream);
            outputStream.flush();
            outputStream.close();
            // write the xsl file
            outputStream = new FileOutputStream(bcxsl);
            byte[] xslBytes = user.getBcard().getXsl().getBytes();
            outputStream.write(xslBytes);
            outputStream.flush();
            outputStream.close();
            // convert to html
            VP_DataToHtml.convert(bcxml, bcxsl, bchtml);
            // convert to pdf
            VP_HtmlToPdf.convert(bchtml, bcpdf);
            if (bcxsl.exists()) {
                bcxsl.delete();
            }
            if (bcxml.exists()) {
                bcxml.delete();
            }
            if (bchtml.exists()) {
                bchtml.delete();
            }
        } else {
            bcpdf = null;
        }
        return bcpdf;
    }
    
    protected File generateCovLetPDF(VP_User user) throws FileNotFoundException, 
            TransformerException, ParserConfigurationException, IOException, DocumentException {
        File clpdf = new File("clpdf.tmp"),
                clxsl = new File("clxsl.tmp"),
                clxml = new File("clxml.tmp"),
                clhtml = new File("clhtml.tmp");
        clxsl.deleteOnExit();
        clpdf.deleteOnExit();
        clxml.deleteOnExit();
        clhtml.deleteOnExit();
        if (user.getCovlet().hasCompletedCoverLetter() == true) {
            // write the xml file
            FileOutputStream outputStream = new FileOutputStream(clxml);
            StreamResult xmlStream = new StreamResult(outputStream);
            icFactory = DocumentBuilderFactory.newInstance();
            icBuilder = icFactory.newDocumentBuilder();
            Document document = icBuilder.newDocument();
            Element root = document.createElement("coverletter");
            document.appendChild(root);
            Element heading = document.createElement("heading");
            root.appendChild(heading);
            Element date = document.createElement("date");
            root.appendChild(date);
            date.appendChild(document.createTextNode(user.getCovlet().getDate().getValueSafe()));
            Element adref = document.createElement("adreference");
            root.appendChild(adref);
            Element contactinfo = document.createElement("contactinformation");
            root.appendChild(contactinfo);
            Element salutation = document.createElement("salutation");
            root.appendChild(salutation);
            salutation.appendChild(document.createTextNode(user.getCovlet().getSalutation().getValueSafe()));
            Element body = document.createElement("body");
            root.appendChild(body);
            Element closing = document.createElement("closing");
            root.appendChild(closing);
            closing.appendChild(document.createTextNode(user.getCovlet().getClosing().getValueSafe()));
            Element signature = document.createElement("signature");
            root.appendChild(signature);
            Element name = document.createElement("name");
            heading.appendChild(name);
            Element address = document.createElement("address");
            heading.appendChild(address);
            Element communication = document.createElement("communication");
            heading.appendChild(communication);
            Element firstname = document.createElement("firstname");
            name.appendChild(firstname);
            firstname.appendChild(document.createTextNode(user.getFirstName().getValueSafe()));
            Element middlename = document.createElement("middlename");
            name.appendChild(middlename);
            middlename.appendChild(document.createTextNode(user.getMiddleName().getValueSafe()));
            Element lastname = document.createElement("lastname");
            name.appendChild(lastname);
            lastname.appendChild(document.createTextNode(user.getLastName().getValueSafe())); 
            Element line1 = document.createElement("line1");
            address.appendChild(line1);
            line1.appendChild(document.createTextNode(user.getAddress1().getValueSafe()));
            Element line2 = document.createElement("line2");
            address.appendChild(line2);
            line2.appendChild(document.createTextNode(user.getAddress2().getValueSafe()));
            Element city = document.createElement("city");
            address.appendChild(city);
            city.appendChild(document.createTextNode(user.getCity().getValueSafe()));
            Element state = document.createElement("state");
            address.appendChild(state);
            state.appendChild(document.createTextNode(user.getState().getValueSafe()));
            Element zip = document.createElement("zip");
            address.appendChild(zip);
            zip.appendChild(document.createTextNode(user.getZip().getValueSafe()));
            Element phone = document.createElement("phone");
            communication.appendChild(phone);
            phone.appendChild(document.createTextNode(user.getPhone().getValueSafe()));
            Element cell = document.createElement("cell");
            communication.appendChild(cell);
            cell.appendChild(document.createTextNode(user.getCell().getValueSafe()));
            Element email = document.createElement("email");
            communication.appendChild(email);
            email.appendChild(document.createTextNode(user.getDocEmail().getValueSafe()));
            Element adsource = document.createElement("source");
            adref.appendChild(adsource);
            adsource.appendChild(document.createTextNode(user.getCovlet().getAdSource().getValueSafe()));
            Element position = document.createElement("position");
            adref.appendChild(position);
            position.appendChild(document.createTextNode(user.getCovlet().getAdJobTitle().getValueSafe()));
            Element refnumber = document.createElement("refnumber");
            adref.appendChild(refnumber);
            refnumber.appendChild(document.createTextNode(user.getCovlet().getAdRefNumber().getValueSafe()));
            Element cname = document.createElement("name");
            contactinfo.appendChild(cname);
            Element company = document.createElement("company");
            contactinfo.appendChild(company);
            Element caddress = document.createElement("address");
            contactinfo.appendChild(caddress);
            Element cfirstname = document.createElement("firstname");
            cname.appendChild(cfirstname);
            cfirstname.appendChild(document.createTextNode(user.getCovlet().getContactFirstName().getValueSafe()));
            Element cmiddlename = document.createElement("middlename");
            cname.appendChild(cmiddlename);
            cmiddlename.appendChild(document.createTextNode(user.getCovlet().getContactMiddleName().getValueSafe()));
            Element clastname = document.createElement("lastname");
            cname.appendChild(clastname);
            clastname.appendChild(document.createTextNode(user.getCovlet().getContactLastName().getValueSafe()));
            Element ctitle = document.createElement("title");
            company.appendChild(ctitle);
            ctitle.appendChild(document.createTextNode(user.getCovlet().getContactTitle().getValueSafe()));
            Element companyname = document.createElement("companyname");
            company.appendChild(companyname);
            companyname.appendChild(document.createTextNode(user.getCovlet().getContactCompany().getValueSafe()));
            Element cline1 = document.createElement("line1");
            caddress.appendChild(cline1);
            cline1.appendChild(document.createTextNode(user.getCovlet().getContactAddress1().getValueSafe()));
            Element cline2 = document.createElement("line2");
            caddress.appendChild(cline2);
            cline2.appendChild(document.createTextNode(user.getCovlet().getContactAddress2().getValueSafe()));
            Element ccity = document.createElement("city");
            caddress.appendChild(ccity);
            ccity.appendChild(document.createTextNode(user.getCovlet().getContactCity().getValueSafe()));
            Element cstate = document.createElement("state");
            caddress.appendChild(cstate);
            cstate.appendChild(document.createTextNode(user.getCovlet().getContactState().getValueSafe()));
            Element czip = document.createElement("zip");
            caddress.appendChild(czip);
            czip.appendChild(document.createTextNode(user.getCovlet().getContactZip().getValueSafe()));
            for (int i = 0; i < 9; i++) {
                Element paragraph = document.createElement("paragraph" + (i + 1));
                body.appendChild(paragraph);
                paragraph.appendChild(document.createTextNode(user.getCovlet().getParagraphs().get(i).getValueSafe()));
            }
            Element sfirstname = document.createElement("firstname");
            signature.appendChild(sfirstname);
            sfirstname.appendChild(document.createTextNode(user.getFirstName().getValueSafe()));
            Element smiddlename = document.createElement("middlename");
            signature.appendChild(smiddlename);
            smiddlename.appendChild(document.createTextNode(user.getMiddleName().getValueSafe()));
            Element slastname = document.createElement("lastname");
            signature.appendChild(slastname);
            slastname.appendChild(document.createTextNode(user.getLastName().getValueSafe())); 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
            DOMSource source = new DOMSource(document);
            transformer.transform(source, xmlStream);
            outputStream.flush();
            outputStream.close();
            // write the xsl file
            outputStream = new FileOutputStream(clxsl);
            byte[] xslBytes = user.getCovlet().getXsl().getBytes();
            outputStream.write(xslBytes);
            outputStream.flush();
            outputStream.close();
            // convert to html
            VP_DataToHtml.convert(clxml, clxsl, clhtml);
            // convert to pdf
            VP_HtmlToPdf.convert(clhtml, clpdf);
            if (clxsl.exists()) {
                clxsl.delete();
            }
            if (clxml.exists()) {
                clxml.delete();
            }
            if (clhtml.exists()) {
                clhtml.delete();
            }
        } else {
            clpdf = null;
        }
        return clpdf;
    }

    /*------------------------------------------------------------------------*
     * encrypt()
     * - Encrypts a string.
     * - Parameter input is a string.
     * - Returns an encrypted string.
     *------------------------------------------------------------------------*/
    private String encrypt(String input) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        //-------- Initialization Start ----------\\
        key = new SecretKeySpec(Arrays.copyOf(keyPass.getBytes("utf-8"), 24), "DESede");
        ivps = new IvParameterSpec(vector.getBytes("utf-8"));
        Cipher encrypt = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        byte[] inputBytes,
                encryptedInputBytes;
        //-------- Initialization End ------------\\

        encrypt.init(Cipher.ENCRYPT_MODE, key, ivps);
        inputBytes = input.getBytes("utf-8");
        encryptedInputBytes = encrypt.doFinal(inputBytes);
        return Arrays.toString(encryptedInputBytes);
    }

    /*------------------------------------------------------------------------*
     * decrypt()
     * - Decrypts an encrypted string.
     * - Parameter input is an encrypted string.
     * - Returns a decrypted string.
     *------------------------------------------------------------------------*/
    private String decrypt(String input) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        key = new SecretKeySpec(Arrays.copyOf(keyPass.getBytes("utf-8"), 24), "DESede");
        ivps = new IvParameterSpec(vector.getBytes("utf-8"));
        Cipher decrypt = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        String[] byteValues = input.substring(1, input.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length],
                decryptedInputBytes;
        //-------- Initialization End ------------\\

        decrypt.init(Cipher.DECRYPT_MODE, key, ivps);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        decryptedInputBytes = decrypt.doFinal(bytes);
        return new String(decryptedInputBytes);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
