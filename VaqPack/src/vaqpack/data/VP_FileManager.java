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
 * FILE ID 3200
 *-----------------------------------------------------------------------------*/
package vaqpack.data;

import vaqpack.user.VP_User;
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
import vaqpack.user.VP_CoverLetter;
import vaqpack.user.VP_Resume;

public class VP_FileManager {

    private final String vector;
    private final VP_DataManager dataM;
    private String keyPass;
    private SecretKey key;
    private IvParameterSpec ivps;
    private DocumentBuilderFactory icFactory;
    private DocumentBuilder icBuilder;

    /*------------------------------------------------------------------------*
     * VP_FileManager()
     * - Constructor. Initialiazes the keyPass and vector used in encryption.
     * No Parameters.
     *------------------------------------------------------------------------*/
    protected VP_FileManager(VP_DataManager dataM) {
        //-------- Initialization Start ----------\\
        this.dataM = dataM;
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
    
    protected File[] generateBCardHTMLandPDF() throws FileNotFoundException, 
            TransformerException, ParserConfigurationException, IOException, DocumentException {
        VP_User user = dataM.getController().getCurrentUser();
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
        } else {
            bchtml = null;
            bcpdf = null;
        }
        return new File[]{bchtml, bcpdf};
    }
    
    protected File[] generateCovLetHTMLandPDF() throws FileNotFoundException, 
            TransformerException, ParserConfigurationException, IOException, DocumentException {
        VP_User user = dataM.getController().getCurrentUser();
        VP_CoverLetter covlet = user.getCovlet();
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
            date.appendChild(document.createTextNode(covlet.getDate().getValueSafe()));
            Element adref = document.createElement("adreference");
            root.appendChild(adref);
            Element contactinfo = document.createElement("contactinformation");
            root.appendChild(contactinfo);
            Element salutation = document.createElement("salutation");
            root.appendChild(salutation);
            salutation.appendChild(document.createTextNode(covlet.getSalutation().getValueSafe()));
            Element body = document.createElement("body");
            root.appendChild(body);
            Element closing = document.createElement("closing");
            root.appendChild(closing);
            closing.appendChild(document.createTextNode(covlet.getClosing().getValueSafe()));
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
            adsource.appendChild(document.createTextNode(covlet.getAdSource().getValueSafe()));
            Element position = document.createElement("position");
            adref.appendChild(position);
            position.appendChild(document.createTextNode(covlet.getAdJobTitle().getValueSafe()));
            Element refnumber = document.createElement("refnumber");
            adref.appendChild(refnumber);
            refnumber.appendChild(document.createTextNode(covlet.getAdRefNumber().getValueSafe()));
            Element cname = document.createElement("name");
            contactinfo.appendChild(cname);
            Element company = document.createElement("company");
            contactinfo.appendChild(company);
            Element caddress = document.createElement("address");
            contactinfo.appendChild(caddress);
            Element cfirstname = document.createElement("firstname");
            cname.appendChild(cfirstname);
            cfirstname.appendChild(document.createTextNode(covlet.getContactFirstName().getValueSafe()));
            Element cmiddlename = document.createElement("middlename");
            cname.appendChild(cmiddlename);
            cmiddlename.appendChild(document.createTextNode(covlet.getContactMiddleName().getValueSafe()));
            Element clastname = document.createElement("lastname");
            cname.appendChild(clastname);
            clastname.appendChild(document.createTextNode(covlet.getContactLastName().getValueSafe()));
            Element ctitle = document.createElement("title");
            company.appendChild(ctitle);
            ctitle.appendChild(document.createTextNode(covlet.getContactTitle().getValueSafe()));
            Element companyname = document.createElement("companyname");
            company.appendChild(companyname);
            companyname.appendChild(document.createTextNode(covlet.getContactCompany().getValueSafe()));
            Element cline1 = document.createElement("line1");
            caddress.appendChild(cline1);
            cline1.appendChild(document.createTextNode(covlet.getContactAddress1().getValueSafe()));
            Element cline2 = document.createElement("line2");
            caddress.appendChild(cline2);
            cline2.appendChild(document.createTextNode(covlet.getContactAddress2().getValueSafe()));
            Element ccity = document.createElement("city");
            caddress.appendChild(ccity);
            ccity.appendChild(document.createTextNode(covlet.getContactCity().getValueSafe()));
            Element cstate = document.createElement("state");
            caddress.appendChild(cstate);
            cstate.appendChild(document.createTextNode(covlet.getContactState().getValueSafe()));
            Element czip = document.createElement("zip");
            caddress.appendChild(czip);
            czip.appendChild(document.createTextNode(covlet.getContactZip().getValueSafe()));
            for (int i = 1; i <= 9; i++) {
                Element paragraph = document.createElement("paragraph" + i);
                body.appendChild(paragraph);
                paragraph.appendChild(document.createTextNode(covlet.getParagraphs().get(i - 1).getValueSafe()));
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
            byte[] xslBytes = covlet.getXsl().getBytes();
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
        } else {
            clhtml = null;
            clpdf = null;
        }
        return new File[]{clhtml, clpdf};
    }
    
    protected File[] generateResHTMLandPDF() throws FileNotFoundException, 
            TransformerException, ParserConfigurationException, IOException, DocumentException {
        VP_User user = dataM.getController().getCurrentUser();
        VP_Resume res = user.getResume();
        File respdf = new File("respdf.tmp"),
                resxsl = new File("resxsl.tmp"),
                resxml = new File("resxml.tmp"),
                reshtml = new File("reshtml.tmp");
        resxsl.deleteOnExit();
        respdf.deleteOnExit();
        resxml.deleteOnExit();
        reshtml.deleteOnExit();
        if (user.getResume().hasCompletedResume() == true) {
            // write the xml file
            FileOutputStream outputStream = new FileOutputStream(resxml);
            StreamResult xmlStream = new StreamResult(outputStream);
            icFactory = DocumentBuilderFactory.newInstance();
            icBuilder = icFactory.newDocumentBuilder();
            Document document = icBuilder.newDocument();
            Element root = document.createElement("resume");
            document.appendChild(root);
            Element heading = document.createElement("heading");
            root.appendChild(heading);
            Element objective = document.createElement("objective");
            objective.appendChild(document.createTextNode(res.getObjective().getValueSafe())); 
            root.appendChild(objective);
            Element education = document.createElement("education");
            root.appendChild(education);
            Element experience = document.createElement("experience");
            root.appendChild(experience);
            Element achievements = document.createElement("achievements");
            root.appendChild(achievements);
            Element community = document.createElement("community");
            root.appendChild(community);
            Element qualifications = document.createElement("qualifications");
            root.appendChild(qualifications);
            Element highlights = document.createElement("highlights");
            root.appendChild(highlights);
            Element languages = document.createElement("languages");
            root.appendChild(languages);
            Element software = document.createElement("software");
            root.appendChild(software);
            Element references = document.createElement("references");
            root.appendChild(references);
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
            for (int i = 1; i <= 9; i++) {
                int oneLess = i - 1;
                // education
                Element edinstitution = document.createElement("institution" + i);
                Element edname = document.createElement("name");
                edname.appendChild(document.createTextNode(res.getEducation().get(oneLess).get(0).getValueSafe()));
                Element edloc = document.createElement("location");
                edloc.appendChild(document.createTextNode(res.getEducation().get(oneLess).get(1).getValueSafe()));
                Element edearned = document.createElement("earned");
                edearned.appendChild(document.createTextNode(res.getEducation().get(oneLess).get(2).getValueSafe()));
                Element edgpa = document.createElement("gpa");
                edgpa.appendChild(document.createTextNode(res.getEducation().get(oneLess).get(3).getValueSafe()));
                Element edstart = document.createElement("start");
                edstart.appendChild(document.createTextNode(res.getEducation().get(oneLess).get(4).getValueSafe()));
                Element edend = document.createElement("end");
                edend.appendChild(document.createTextNode(res.getEducation().get(oneLess).get(5).getValueSafe()));
                edinstitution.appendChild(edname);
                edinstitution.appendChild(edloc);
                edinstitution.appendChild(edearned);
                edinstitution.appendChild(edgpa);
                edinstitution.appendChild(edstart);
                edinstitution.appendChild(edend);
                education.appendChild(edinstitution);
                // experience
                Element exinstitution = document.createElement("institution" + i);
                Element exname = document.createElement("name");
                exname.appendChild(document.createTextNode(res.getExperience().get(oneLess).get(0).getValueSafe()));
                Element exloc = document.createElement("location");
                exloc.appendChild(document.createTextNode(res.getExperience().get(oneLess).get(1).getValueSafe()));
                Element exposition = document.createElement("position");
                exposition.appendChild(document.createTextNode(res.getExperience().get(oneLess).get(2).getValueSafe()));
                Element exstart = document.createElement("start");
                exstart.appendChild(document.createTextNode(res.getExperience().get(oneLess).get(3).getValueSafe()));
                Element exend = document.createElement("end");
                exend.appendChild(document.createTextNode(res.getExperience().get(oneLess).get(4).getValueSafe()));
                exinstitution.appendChild(exname);
                exinstitution.appendChild(exloc);
                exinstitution.appendChild(exposition);
                exinstitution.appendChild(exstart);
                exinstitution.appendChild(exend);
                experience.appendChild(exinstitution);
                // achievements
                Element acaward = document.createElement("award" + i);
                Element acname = document.createElement("name");
                acname.appendChild(document.createTextNode(res.getAchievements().get(oneLess).get(0).getValueSafe()));
                Element acfrom = document.createElement("from");
                acfrom.appendChild(document.createTextNode(res.getAchievements().get(oneLess).get(1).getValueSafe()));
                Element acdate = document.createElement("date");
                acdate.appendChild(document.createTextNode(res.getAchievements().get(oneLess).get(2).getValueSafe()));
                acaward.appendChild(acname);
                acaward.appendChild(acfrom);
                acaward.appendChild(acdate);
                achievements.appendChild(acaward);
                // community
                Element comevent = document.createElement("event" + i);
                Element comname = document.createElement("name");
                comname.appendChild(document.createTextNode(res.getCommunity().get(oneLess).get(0).getValueSafe()));
                Element comloc = document.createElement("location");
                comloc.appendChild(document.createTextNode(res.getCommunity().get(oneLess).get(1).getValueSafe()));
                Element comdate = document.createElement("date");
                comdate.appendChild(document.createTextNode(res.getCommunity().get(oneLess).get(2).getValueSafe()));
                comevent.appendChild(comname);
                comevent.appendChild(comloc);
                comevent.appendChild(comdate);
                community.appendChild(comevent);
                // qualifications
                Element skill = document.createElement("skill" + i);
                skill.appendChild(document.createTextNode(res.getQualifications().get(oneLess).getValueSafe()));
                qualifications.appendChild(skill);
                // highlights
                Element quality = document.createElement("quality" + i);
                quality.appendChild(document.createTextNode(res.getHighlights().get(oneLess).getValueSafe()));
                highlights.appendChild(quality);
                // languagess
                Element lang = document.createElement("lang" + i);
                lang.appendChild(document.createTextNode(res.getLanguages().get(oneLess).getValueSafe()));
                languages.appendChild(lang);
                // software
                Element product = document.createElement("product" + i);
                product.appendChild(document.createTextNode(res.getSoftware().get(oneLess).getValueSafe()));
                software.appendChild(product);
                // references
                Element ref = document.createElement("ref" + i);
                Element reffirstname = document.createElement("firstname");
                reffirstname.appendChild(document.createTextNode(res.getReferences().get(oneLess).get(0).getValueSafe()));
                Element refmiddlename = document.createElement("middlename");
                refmiddlename.appendChild(document.createTextNode(res.getReferences().get(oneLess).get(1).getValueSafe()));
                Element reflastname = document.createElement("lastname");
                reflastname.appendChild(document.createTextNode(res.getReferences().get(oneLess).get(2).getValueSafe()));
                Element refcompany = document.createElement("company");
                refcompany.appendChild(document.createTextNode(res.getReferences().get(oneLess).get(3).getValueSafe()));
                Element refphone = document.createElement("phone");
                refphone.appendChild(document.createTextNode(res.getReferences().get(oneLess).get(4).getValueSafe()));
                Element refemail = document.createElement("email");
                refemail.appendChild(document.createTextNode(res.getReferences().get(oneLess).get(5).getValueSafe()));
                ref.appendChild(reffirstname);
                ref.appendChild(refmiddlename);
                ref.appendChild(reflastname);
                ref.appendChild(refcompany);
                ref.appendChild(refphone);
                ref.appendChild(refemail);
                references.appendChild(ref);
            }
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
            outputStream = new FileOutputStream(resxsl);
            byte[] xslBytes = user.getResume().getXsl().getBytes();
            outputStream.write(xslBytes);
            outputStream.flush();
            outputStream.close();
            // convert to html
            VP_DataToHtml.convert(resxml, resxsl, reshtml);
            // convert to pdf
            VP_HtmlToPdf.convert(reshtml, respdf);
            if (resxsl.exists()) {
                resxsl.delete();
            }
            if (resxml.exists()) {
                resxml.delete();
            }
        } else {
            reshtml = null;
            respdf = null;
        }
        return new File[]{reshtml, respdf};
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
