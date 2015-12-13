package vaqpack.peripherals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VP_Previewer {

   public WebView buildPreview(int type, int theme) throws IOException
   {
      WebView preview = new WebView();
      WebEngine webEngine = preview.getEngine();
      if (type == 0) {
          if (theme == -1) {
              webEngine.load(resumeDefault1().toURI().toURL().toString());
          } else if (theme == -2) {
              webEngine.load(resumeDefault2().toURI().toURL().toString());
          } else if (theme == -3) {
              webEngine.load(resumeDefault3().toURI().toURL().toString());
          } else if (theme == -4) {
              webEngine.load(resumeDefault4().toURI().toURL().toString());
          } else if (theme == -5) {
              webEngine.load(resumeDefault5().toURI().toURL().toString());
          } else if (theme == -6) {
              webEngine.load(resumeDefault6().toURI().toURL().toString());
          }
          preview.setZoom(0.5);
      } else if (type == 1) {
          if (theme == -1) {
              webEngine.load(bcardDefault1().toURI().toURL().toString());
          } else if (theme == -2) {
              webEngine.load(bcardDefault2().toURI().toURL().toString());
          } else if (theme == -3) {
              webEngine.load(bcardDefault3().toURI().toURL().toString());
          } else if (theme == -4) {
              webEngine.load(bcardDefault4().toURI().toURL().toString());
          } else if (theme == -5) {
              webEngine.load(bcardDefault5().toURI().toURL().toString());
          } else if (theme == -6) {
              webEngine.load(bcardDefault6().toURI().toURL().toString());
          }
      } else {
          if (theme == -1) {
              webEngine.load(covletDefault1().toURI().toURL().toString());
          } else if (theme == -2) {
              webEngine.load(covletDefault2().toURI().toURL().toString());
          } else if (theme == -3) {
              webEngine.load(covletDefault3().toURI().toURL().toString());
          } else if (theme == -4) {
              webEngine.load(covletDefault4().toURI().toURL().toString());
          } else if (theme == -5) {
              webEngine.load(covletDefault5().toURI().toURL().toString());
          } else if (theme == -6) {
              webEngine.load(covletDefault6().toURI().toURL().toString());
          }
          preview.setZoom(0.5);
      }
      return preview;
   }
   
   private File resumeDefault1() throws IOException {
       File html = new File("theme1Res.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Resume -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"padding:20pt;\">\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 14pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">phone: (555)555-5555<span style=\"padding-left:8pt;\">cell: (956)876-5432</span>\n" +
            "<span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">OBJECTIVE</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines.</p>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">EDUCATION</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Name 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Location 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourdegree 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">August 2008 - December 2012</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Name 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Location 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourdegree 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">August 2013 - December 2015</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">EXPERIENCE</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Company 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Location 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourposition 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">February 2000 - March 2006</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Company 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Location 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourposition 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">May 2007 - November 2010</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">ACHIEVEMENTS</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Award One</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Someone</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">January 13, 2012</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Award Two</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Someone Else</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">May 22 2014</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">COMMUNITY</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Community Event 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Event Location 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">April 14, 2013</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Community Event 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Event Location 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">April 30, 2013</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">QUALIFICATIONS</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your First Qualification</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Second Qualification</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Third Qualification</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">HIGHLIGHTS</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Personal Quality 1</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Personal Quality 2</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Personal Quality 3</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">LANGUAGES</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Primary:\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Primary Language</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Secondary:\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your first secondary langauge</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your second secondary langauge</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">SOFTWARE</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Some Software</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Some Other Software</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Additional Software</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-shadow: 1px 1px 0px #F00; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">REFERENCES</div>\n" +
            "<hr style=\"margin-top: 3px\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName1 ReferenceMiddleName1 ReferenceLastName1</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">ReferenceCompany1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">(555)555-5557</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">refemail1@somewhere.com</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName2 ReferenceMiddleName2 ReferenceLastName2</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">ReferenceCompany2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">(555)555-5558</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">refemail2@somewhere.com</p>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File resumeDefault2() throws IOException {
       File html = new File("theme2Res.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Resume -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"margin:0; padding: 0; padding-bottom: 20pt; background: rgb(240, 240, 240);\">\n" +
            "<div style=\"margin-bottom: 2pt; text-align:center; color: rgb(60, 60, 60); font-family: 'Arial Black', Gadget, sans-serif; font-weight: bold; font-size: 15pt; display: block; padding: 2pt;background: rgb(240, 240, 240);\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">phone: (555)555-5555<span style=\"padding-left:8pt;\">cell: (956)876-5432</span>\n" +
            "<span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">OBJECTIVE</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines.</p>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">EDUCATION</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Institution Name 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Institution Location 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Yourdegree 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">August 2008 - December 2012</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Institution Name 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Institution Location 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Yourdegree 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">August 2013 - December 2015</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">EXPERIENCE</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Employer Company 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Employer Location 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Yourposition 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">February 2000 - March 2006</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Employer Company 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Employer Location 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Yourposition 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">May 2007 - November 2010</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">ACHIEVEMENTS</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Award One</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Someone</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">January 13, 2012</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Award Two</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Someone Else</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">May 22 2014</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">COMMUNITY</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Community Event 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Event Location 1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">April 14, 2013</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Community Event 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">Event Location 2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">April 30, 2013</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">QUALIFICATIONS</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your First Qualification</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your Second Qualification</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your Third Qualification</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">HIGHLIGHTS</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your Personal Quality 1</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your Personal Quality 2</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your Personal Quality 3</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">LANGUAGES</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Primary:\n" +
            "<ul>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your Primary Language</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Secondary:\n" +
            "<ul>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your first secondary langauge</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Your second secondary langauge</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">SOFTWARE</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Some Software</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Some Other Software</li>\n" +
            "<li style=\"padding:0 0 6px 0;margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 12pt;\">Additional Software</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Arial', Helvetica, sans-serif; font-weight: bold; font-size: 13pt; display: block; padding: 2pt; padding-left: 8pt; background: rgb(204, 204, 204);\">REFERENCES</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName1 ReferenceMiddleName1 ReferenceLastName1</span>\n" +
            "</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">ReferenceCompany1</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">(555)555-5557</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">refemail1@somewhere.com</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20pt; margin-bottom: 14pt;\">\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName2 ReferenceMiddleName2 ReferenceLastName2</span>\n" +
            "</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">ReferenceCompany2</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">(555)555-5558</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: 'Arial', Helvetica, sans-serif; font-size: 12pt;\">refemail2@somewhere.com</p>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File resumeDefault3() throws IOException {
       File html = new File("theme3Res.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Resume -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"color: rgb(51, 34, 19); margin:0; padding: 0; padding-bottom: 20pt; background-color: rgb(249, 242, 236);\">\n" +
            "<div style=\"background-color: rgb(255, 248, 242);margin-top: 40px; border: solid rgb(171, 105, 48); border-width: 7px 0 0 0; padding: 20px; text-align:center; font-family: 'Georgia', Times, serif; font-weight: bold; font-variant: small-caps; letter-spacing: 2px; font-size: 16pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"background-color: rgb(255, 248, 242);text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"background-color: rgb(255, 248, 242);text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"background-color: rgb(255, 248, 242);text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"padding: 8px 0 20px 0; background-color: rgb(255, 248, 242); border: solid rgb(171, 105, 48); border-width: 0 0 4px 0;text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">phone: (555)555-5555<span style=\"padding-left:8pt;\">cell: (956)876-5432</span>\n" +
            "<span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">OBJECTIVE</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines.</p>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">EDUCATION</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Institution Name 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Institution Location 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Yourdegree 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">August 2008 - December 2012</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Institution Name 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Institution Location 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Yourdegree 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">August 2013 - December 2015</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">EXPERIENCE</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Employer Company 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Employer Location 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Yourposition 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">February 2000 - March 2006</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Employer Company 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Employer Location 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Yourposition 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">May 2007 - November 2010</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">ACHIEVEMENTS</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Award One</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Someone</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">January 13, 2012</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Award Two</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Someone Else</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">May 22 2014</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">COMMUNITY</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Community Event 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Event Location 1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">April 14, 2013</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Community Event 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Event Location 2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">April 30, 2013</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">QUALIFICATIONS</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your First Qualification</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your Second Qualification</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your Third Qualification</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">HIGHLIGHTS</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your Personal Quality 1</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your Personal Quality 2</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your Personal Quality 3</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">LANGUAGES</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Primary:\n" +
            "<ul>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your Primary Language</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Secondary:\n" +
            "<ul>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your first secondary langauge</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Your second secondary langauge</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">SOFTWARE</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<ul>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Some Software</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Some Other Software</li>\n" +
            "<li style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt;\">Additional Software</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"margin: 14pt; padding-left:20px; border: solid rgb(171, 105, 48); border-width: 0 0 7px 0; font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 13pt; display: block;\">REFERENCES</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName1 ReferenceMiddleName1 ReferenceLastName1</span>\n" +
            "</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">ReferenceCompany1</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">(555)555-5557</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">refemail1@somewhere.com</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin: 14pt; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName2 ReferenceMiddleName2 ReferenceLastName2</span>\n" +
            "</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">ReferenceCompany2</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">(555)555-5558</p>\n" +
            "<p style=\"padding-left:20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">refemail2@somewhere.com</p>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File resumeDefault4() throws IOException {
       File html = new File("theme4Res.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Resume -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"color: rgb(80, 0, 0);padding:20px;background-color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0);margin: 0\">\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Helvetica', Times, serif; font-weight: bold; font-size: 14pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">phone: (555)555-5555<span style=\"padding-left:8pt;\">cell: (956)876-5432</span>\n" +
            "<span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">OBJECTIVE</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines.</p>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">EDUCATION</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Name 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Location 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourdegree 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">August 2008 - December 2012</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Name 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Institution Location 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourdegree 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">August 2013 - December 2015</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">EXPERIENCE</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Company 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Location 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourposition 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">February 2000 - March 2006</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Company 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Employer Location 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Yourposition 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">May 2007 - November 2010</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">ACHIEVEMENTS</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Award One</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Someone</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">January 13, 2012</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Award Two</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Someone Else</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">May 22 2014</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">COMMUNITY</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Community Event 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Event Location 1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">April 14, 2013</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Community Event 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Event Location 2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">April 30, 2013</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">QUALIFICATIONS</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your First Qualification</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Second Qualification</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Third Qualification</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">HIGHLIGHTS</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Personal Quality 1</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Personal Quality 2</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Personal Quality 3</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">LANGUAGES</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Primary:\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your Primary Language</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Secondary:\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your first secondary langauge</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Your second secondary langauge</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">SOFTWARE</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<ul>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Some Software</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Some Other Software</li>\n" +
            "<li style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">Additional Software</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"text-align:center; border: 1px solid rgb(140, 0, 0); font-family: 'Helvetica', Times, serif;background-color:#ff9999; font-weight: bold; font-size: 13pt; display: block;\">REFERENCES</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName1 ReferenceMiddleName1 ReferenceLastName1</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">ReferenceCompany1</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">(555)555-5557</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">refemail1@somewhere.com</p>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px;  margin-bottom: 14pt; border: 1px solid rgb(255, 250, 250);\">\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName2 ReferenceMiddleName2 ReferenceLastName2</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">ReferenceCompany2</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">(555)555-5558</p>\n" +
            "<p style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\">refemail2@somewhere.com</p>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File resumeDefault5() throws IOException {
       File html = new File("theme5Res.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Resume -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"padding:20px; line-height: 1.25em; font-family: 'Helvetica', Arial, sans-serif; font-size: 12pt;\">\n" +
            "<div style=\"background: rgb(0, 38, 73);line-height: 1.5em; text-align:center; font-weight: bold; font-size: 14pt; display: block; color: #fb8b00;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);color: rgb(255, 255, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);color: rgb(255, 255, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);color: rgb(255, 255, 255); line-height: 1.5em; text-align:center; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);line-height: 1.5em;text-align:center; display: block; color: #fb8b00; font-size: 10pt;\">phone: (555)555-5555<span style=\"padding-left:8pt;\">cell: (956)876-5432</span>\n" +
            "<span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">OBJECTIVE</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines.</p>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">EDUCATION</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Name 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Location 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourdegree 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">August 2008 - December 2012</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Name 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Location 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourdegree 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">August 2013 - December 2015</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">EXPERIENCE</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Company 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Location 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourposition 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">February 2000 - March 2006</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Company 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Location 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourposition 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">May 2007 - November 2010</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">ACHIEVEMENTS</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Award One</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Someone</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">January 13, 2012</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Award Two</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Someone Else</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">May 22 2014</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">COMMUNITY</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Community Event 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Event Location 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">April 14, 2013</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Community Event 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Event Location 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">April 30, 2013</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">QUALIFICATIONS</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Your First Qualification</li>\n" +
            "<li>Your Second Qualification</li>\n" +
            "<li>Your Third Qualification</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">HIGHLIGHTS</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Your Personal Quality 1</li>\n" +
            "<li>Your Personal Quality 2</li>\n" +
            "<li>Your Personal Quality 3</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">LANGUAGES</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Primary:\n" +
            "<ul>\n" +
            "<li>Your Primary Language</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "<li>Secondary:\n" +
            "<ul>\n" +
            "<li>Your first secondary langauge</li>\n" +
            "<li>Your second secondary langauge</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">SOFTWARE</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Some Software</li>\n" +
            "<li>Some Other Software</li>\n" +
            "<li>Additional Software</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\" font-weight: bold; font-size: 13pt; background-color: #0b203a; color: #fb8b00; display: block; padding: 8px 8px 2px;\">REFERENCES</div>\n" +
            "<hr style=\"margin-top: 2px; background-color: #007030; height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName1 ReferenceMiddleName1 ReferenceLastName1</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">ReferenceCompany1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">(555)555-5557</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">refemail1@somewhere.com</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName2 ReferenceMiddleName2 ReferenceLastName2</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">ReferenceCompany2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">(555)555-5558</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">refemail2@somewhere.com</p>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File resumeDefault6() throws IOException {
       File html = new File("theme6Res.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Resume -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"color: rgb(0, 0, 30); background: rgb(240, 240, 255);padding:20px; line-height: 1.25em; font-family: 'Helvetica', Arial, sans-serif; font-size: 12pt;\">\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; background: rgb(0, 38, 64);line-height: 1.5em; text-align:center; font-weight: bold; font-size: 14pt; display: block; color: rgb(174, 209, 232);\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; border-top: 0;background: rgb(0, 38, 64);color: rgb(235, 235, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; border-top: 0;background: rgb(0, 38, 64);color: rgb(235, 235, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; border-top: 0;background: rgb(0, 38, 64);color: rgb(235, 235, 255); line-height: 1.5em; text-align:center; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-top: 0;background: rgb(0, 38, 64);line-height: 1.5em;text-align:center; display: block; color: rgb(174, 209, 232); font-size: 10pt;\">phone: (555)555-5555<span style=\"padding-left:8pt;\">cell: (956)876-5432</span>\n" +
            "<span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">OBJECTIVE</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines. The is an example objective statement, written in just a few lines.</p>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">EDUCATION</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Name 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Location 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourdegree 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">August 2008 - December 2012</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Name 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Institution Location 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourdegree 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">GPA: 4.0</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">August 2013 - December 2015</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">EXPERIENCE</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Company 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Location 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourposition 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">February 2000 - March 2006</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Company 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Employer Location 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Yourposition 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">May 2007 - November 2010</span>\n" +
            "</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">ACHIEVEMENTS</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Award One</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Someone</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">January 13, 2012</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Award Two</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Someone Else</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">May 22 2014</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">COMMUNITY</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Community Event 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Event Location 1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">April 14, 2013</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Community Event 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">Event Location 2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">April 30, 2013</p>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">QUALIFICATIONS</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Your First Qualification</li>\n" +
            "<li>Your Second Qualification</li>\n" +
            "<li>Your Third Qualification</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">HIGHLIGHTS</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Your Personal Quality 1</li>\n" +
            "<li>Your Personal Quality 2</li>\n" +
            "<li>Your Personal Quality 3</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">LANGUAGES</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Primary:\n" +
            "<ul>\n" +
            "<li>Your Primary Language</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "<li>Secondary:\n" +
            "<ul>\n" +
            "<li>Your first secondary langauge</li>\n" +
            "<li>Your second secondary langauge</li>\n" +
            "</ul>\n" +
            "</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">SOFTWARE</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<ul>\n" +
            "<li>Some Software</li>\n" +
            "<li>Some Other Software</li>\n" +
            "<li>Additional Software</li>\n" +
            "</ul>\n" +
            "<br/>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232);text-align:center; font-weight: bold; font-size: 13pt; background: rgb(0, 38, 64); color: rgb(174, 209, 232); display: block; padding: 8px 8px 2px;\">REFERENCES</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName1 ReferenceMiddleName1 ReferenceLastName1</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">ReferenceCompany1</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">(555)555-5557</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">refemail1@somewhere.com</p>\n" +
            "</div>\n" +
            "<div style=\"margin-bottom: 14pt\">\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">\n" +
            "<span xml:space=\"preserve\">ReferenceFirstName2 ReferenceMiddleName2 ReferenceLastName2</span>\n" +
            "</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">ReferenceCompany2</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">(555)555-5558</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">refemail2@somewhere.com</p>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File bcardDefault1() throws IOException {
       File html = new File("theme1BC.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Business Card -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"border-collapse:collapse;margin:0;padding:0;width:3.5in;height:2in;text-align:center;vertical-align:middle;border:1px solid #000;\">\n" +
            "<table style=\"background: rgb(255, 255, 255); border-collapse:collapse;margin:0;padding:0;width:3.5in;height:2in;text-align:center;vertical-align:middle;\">\n" +
            "<tr>\n" +
            "<td style=\"padding:0; margin:0;\">\n" +
            "<span style=\"font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 12pt; display: block;\" xml:space=\"preserve\">Yourfirstname   Yourmiddlename Yourlastname</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;font-size: 10pt; display: block;\">YourProfession</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;font-size: 11pt; display: block;\">YourCompanyName</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;padding-left:20pt; font-style: italic; font-size: 10pt; display: block;\">\"Your Company Slogan\"</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"padding:0; margin:0;\">\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;padding-top:10pt; font-size: 10pt;display: block;\">Youraddressline1</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;font-size: 10pt; display: block;\">Youraddressline2</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;font-size: 10pt; display: block;\" xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"padding:0; margin:0;\">\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;\" xml:space=\"preserve\">(555)555-5555<span style=\"padding-left:8pt;\">cell: (555)555-5556</span>\n" +
            "</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;font-size: 11pt; display: block;\">youremail@somewhere.com</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;font-size: 9pt; display: block;\">http://www.domain.com</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File bcardDefault2() throws IOException {
       File html = new File("theme2BC.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Business Card -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"border-collapse:collapse;margin:0;padding:0;width:2in;height:3.5in;text-align:left;vertical-align:middle;border:1px solid #000;\">\n" +
            "<table style=\"background: rgb(240, 240, 240); border-collapse:collapse;width:2in;height:3.5in;vertical-align:middle;\">\n" +
            "<tr>\n" +
            "<td>\n" +
            "<span style=\"padding:4pt; font-family: Arial, Helvetica, sans-serif; font-size: 10pt; display: block;\">YourCompanyName</span>\n" +
            "<span style=\"text-align: right; padding:2pt; font-family: Arial, Helvetica, sans-serif; font-size: 8pt; display: block;\">\"Your Company Slogan\"</span>\n" +
            "<span style=\"padding:4pt; font-family: Arial, Helvetica, sans-serif; font-size: 8pt; display: block;\">http://www.domain.com</span>\n" +
            "<span style=\"margin-top:8pt; margin-bottom: 2pt; padding:4pt;font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 11pt; display: block; color: rgb(255, 255, 255); background: rgb(60, 60, 60);\" xml:space=\"preserve\">Yourfirstname   Yourmiddlename Yourlastname</span>\n" +
            "<span style=\"text-align: right; padding:4pt; font-family: Arial, Helvetica, sans-serif; font-size: 9pt; display: block;\">YourProfession</span>\n" +
            "<span style=\"padding:4pt;font-family: Arial, Helvetica, sans-serif; padding-top:10pt; font-size: 10pt;display: block;\">Youraddressline1</span>\n" +
            "<span style=\"padding:4pt;font-family: Arial, Helvetica, sans-serif; font-size: 9pt; display: block;\">Youraddressline2</span>\n" +
            "<span style=\"margin-bottom: 8pt; padding:4pt;font-family: Arial, Helvetica, sans-serif; font-size: 10pt; display: block;\" xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "<span style=\"padding:4pt;font-family: Arial, Helvetica, sans-serif; font-size: 9pt; display: block; color: black;\" xml:space=\"preserve\">(555)555-5555<span style=\"padding-left:8pt;\">cell: (555)555-5556</span>\n" +
            "</span>\n" +
            "<span style=\"padding:4pt;font-family: Arial, Helvetica, sans-serif; font-size: 9pt; display: block; color: black;\">youremail@somewhere.com</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File bcardDefault3() throws IOException {
       File html = new File("theme3BC.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Business Card -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"margin:0;padding:0;width:3.5in;height:2in;text-align:center;vertical-align:middle;border:1px solid #000;\">\n" +
            "<table style=\"background-color: rgb(249, 242, 236);width:3.5in;height:2in;text-align:center;vertical-align:middle;\">\n" +
            "<tr>\n" +
            "<td style=\"padding:2px; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family: 'Georgia', Times, serif; font-weight: bold; font-size: 10pt; font-variant: small-caps;display: block;\" xml:space=\"preserve\">Yourfirstname   Yourmiddlename Yourlastname</span>\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;font-size: 7pt; font-variant: small-caps; display: block;\">YourProfession</span>\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;font-size: 9pt; display: block;\">YourCompanyName</span>\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;padding-left:20px; font-style: italic; font-size: 7pt; display: block;\">\"Your Company Slogan\"</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"padding:2px; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif; font-size: 9pt;display: block;\">Youraddressline1</span>\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;font-size: 7pt; display: block;\">Youraddressline2</span>\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;font-size: 7pt; display: block;\" xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"padding:2px; border: 1px solid rgb(171, 105, 48);\">\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;font-size: 9pt;\" xml:space=\"preserve\">(555)555-5555<span style=\"padding-left:8pt;\">cell: (555)555-5556</span>\n" +
            "</span>\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;font-size: 7pt; display: block;\">youremail@somewhere.com</span>\n" +
            "<span style=\"padding: 2px; color: rgb(51, 34, 19); font-family:'Georgia', Times, serif;font-size: 7pt; display: block;\">http://www.domain.com</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File bcardDefault4() throws IOException {
       File html = new File("theme4BC.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Business Card -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"background-color: rgb(255, 0, 0); border-collapse:collapse;margin:0;padding:2px;;width:3.5in;height:2in;vertical-align:middle;border:1px solid #000;\">\n" +
            "<table style=\"background-color: rgb(80, 0, 0); border: 4px solid rgb(140, 0, 0); border-collapse:collapse;margin:0;padding:0;width:3.5in;height:2in;vertical-align:middle;\">\n" +
            "<tr>\n" +
            "<td style=\"text-align:left; padding-left: 6px; padding-right: 6px;\">\n" +
            "<span style=\"text-align:center; font-family: 'Times New Roman', Times, serif;color:white;font-weight: bold; font-size: 12pt; display: block;\" xml:space=\"preserve\">Yourfirstname   Yourmiddlename Yourlastname</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-left:80px;font-size: 9pt; display: block;\">YourProfession</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-left:120px;font-size: 10pt; display: block;\">YourCompanyName</span>\n" +
            "<span style=\"text-align: right; font-family:'Times New Roman', Times, serif; color:white;padding-right:40px; font-style: italic; font-size: 8pt; display: block;\">\"Your Company Slogan\"</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"text-align:left; padding-left: 6px; padding-right: 6px;\">\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-left:5;padding-top:10pt; font-size: 10pt;display: block;\">Youraddressline1</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-left:5;font-size: 9pt; display: block;\">Youraddressline2</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-left:5;font-size: 9pt; display: block;\" xml:space=\"preserve\">Yourcity, TX 98765-4321</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"text-align:left; padding-left: 6px; padding-right: 6px;\">\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-left:5;font-size: 10pt;\" xml:space=\"preserve\">(555)555-5555<span style=\"padding-left:8pt;\">cell: (555)555-5556</span>\n" +
            "</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-right:5;text-align:right;font-size: 10pt; display: block;\">youremail@somewhere.com</span>\n" +
            "<span style=\"font-family:'Times New Roman', Times, serif;color:white;padding-right:5;text-align:right;font-size: 8pt; display: block;\">http://www.domain.com</span>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File bcardDefault5() throws IOException {
       File html = new File("theme5BC.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Business Card -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"border-collapse:collapse;margin:0;padding:0;width:3.5in;height:2in;text-align:center;vertical-align:middle; background: rgb(0, 38, 73);\">\n" +
            "<table style=\"font-family: 'Helvetica', Arial, sans-serif; color: rgb(255, 255, 255);\">\n" +
            "<tr>\n" +
            "<td style=\"padding:8px; vertical-align: top; line-height: 1.25em; font-weight: bold; width: 40%;\">\n" +
            "<div style=\"margin-bottom: 8px; font-size: 13pt; line-height: 1.25em; color: rgb(249, 86, 2);\" xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</div>\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">YourProfession</div>\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">YourCompanyName</div>\n" +
            "<div style=\"font-size: 9pt; font-weight: normal;\">\"Your Company Slogan\"</div>\n" +
            "</td>\n" +
            "<td style=\"white-space:nowrap; padding:8px; vertical-align: top; line-height: 1.5em; font-size: 10pt; width: 60%; text-align: right;\">\n" +
            "<div>Youraddressline1</div>\n" +
            "<div>Youraddressline2</div>\n" +
            "<div xml:space=\"preserve\">Yourcity, TX 98765-4321</div>\n" +
            "<div>(555)555-5555</div>\n" +
            "<div>cell: (555)555-5556</div>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td colspan=\"2\" style=\"font-size: 10pt; width:3.5in; padding: 4px 8px; color: rgb(255, 255, 255); line-height:1.25em; background:rgb(0, 115, 61); border-top: solid 1px rgb(255, 255, 255);\">\n" +
            "<div>youremail@somewhere.com</div>\n" +
            "<div>http://www.domain.com</div>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File bcardDefault6() throws IOException {
       File html = new File("theme6BC.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Business Card -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"border-collapse:collapse;margin:0;padding:0;width:3.5in;height:2in;text-align:center;vertical-align:middle; background: rgb(0, 38, 64);\">\n" +
            "<table style=\"width:3.5in;height:2in; border: 1px solid rgb(0, 0, 100); font-family: 'Helvetica', Arial, sans-serif; color: rgb(235, 235, 255);\">\n" +
            "<tr>\n" +
            "<td style=\"padding:8px; vertical-align: top; line-height: 1.25em; font-weight: bold; width: 40%;\">\n" +
            "<div style=\"margin-bottom: 8px; font-size: 13pt; line-height: 1.25em; color: rgb(174, 209, 232);\" xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</div>\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">YourProfession</div>\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">YourCompanyName</div>\n" +
            "<div style=\"font-size: 9pt; font-weight: normal;\">\"Your Company Slogan\"</div>\n" +
            "</td>\n" +
            "<td style=\"white-space:nowrap; padding:8px; vertical-align: top; line-height: 1.5em; font-size: 10pt; width: 60%; text-align: right;\">\n" +
            "<div style=\"font-size: 9pt; font-weight: normal;\">Youraddressline1</div>\n" +
            "<div style=\"font-size: 9pt; font-weight: normal;\">Youraddressline2</div>\n" +
            "<div style=\"font-size: 9pt; font-weight: normal;\" xml:space=\"preserve\">Yourcity, TX 98765-4321</div>\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">(555)555-5555</div>\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">cell: (555)555-5556</div>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td colspan=\"2\" style=\"border-bottom: 1px solid rgb(0, 0, 100); background-color: rgb(77, 107, 117);font-size: 10pt; width:3.5in; padding: 4px 8px; color: rgb(235, 235, 255); line-height:1.25em; border-top: 1px solid rgb(235, 235, 255);\">\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">youremail@somewhere.com</div>\n" +
            "<div style=\"font-size: 10pt; font-weight: normal;\">http://www.domain.com</div>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File covletDefault1() throws IOException {
       File html = new File("theme1CL.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Cover Letter -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"padding:20pt;\">\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 14pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity,   TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">phone: (555)555-5555  <span style=\"padding-left:8pt;\">cell: (555)555-5556</span>  <span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</span>\n" +
            "</div>\n" +
            "<hr/>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Saturday, December 12, 2015</div>\n" +
            "<br/>\n" +
            "<br/>\n" +
            "<div style=\"float:right; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<div>Ad Source</div>\n" +
            "<div>Job Position</div>\n" +
            "<div>Ref# 123456789</div>\n" +
            "</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Employerfirstname  Employermiddlename  Employerlastname</span>\n" +
            "</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Employer Title</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Employer Compnay</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Employeraddressline1</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Employeraddressline2</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">EmployerCity,   TX 12345-6789</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Dr. Ms Soandso,</div>\n" +
            "<br/>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">This is paragraph1 of your cover letter. This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">This is paragraph2 of your cover letter. This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.</p>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Sincerely,</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File covletDefault2() throws IOException {
       File html = new File("theme2CL.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Cover Letter -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"margin:0; padding: 0; padding-bottom: 20pt; background: rgb(240, 240, 240);\">\n" +
            "<div style=\"margin-bottom: 2pt; text-align:center; color: rgb(60, 60, 60); font-family: 'Arial Black', Gadget, sans-serif; font-weight: bold; font-size: 15pt; display: block; padding: 2pt;background: rgb(240, 240, 240);\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity,   TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"padding: 2pt; text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">phone: (555)555-5555  <span style=\"padding-left:8pt;\">cell: (555)555-5556</span>  <span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</span>\n" +
            "</div>\n" +
            "<hr style=\"border: 4px solid rgb(60, 60, 60);\"/>\n" +
            "<br/>\n" +
            "<div style=\"padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">Saturday, December 12, 2015</div>\n" +
            "<br/>\n" +
            "<br/>\n" +
            "<div style=\"float:right;padding: 2pt; padding-right:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 12pt; display: block;\">\n" +
            "<div>Ad Source</div>\n" +
            "<div>Job Position</div>\n" +
            "<div>Ref# 123456789</div>\n" +
            "</div>\n" +
            "<div style=\"padding: 2pt; padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Employerfirstname  Employermiddlename  Employerlastname</span>\n" +
            "</div>\n" +
            "<div style=\"padding: 2pt; padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 12pt; display: block;\">Employer Title</div>\n" +
            "<div style=\"padding: 2pt; padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 12pt; display: block;\">Employer Compnay</div>\n" +
            "<div style=\"padding: 2pt; padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 12pt; display: block;\">Employeraddressline1</div>\n" +
            "<div style=\"padding: 2pt; padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 12pt; display: block;\">Employeraddressline2</div>\n" +
            "<div style=\"padding: 2pt; padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">EmployerCity,   TX 12345-6789</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">Dr. Ms Soandso,</div>\n" +
            "<br/>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">This is paragraph1 of your cover letter. This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.</p>\n" +
            "<p style=\"padding:20pt; text-indent:1cm; margin:0; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">This is paragraph2 of your cover letter. This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.</p>\n" +
            "<br/>\n" +
            "<div style=\"padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">Sincerely,</div>\n" +
            "<br/>\n" +
            "<div style=\"padding-left:20pt; font-family: Arial, Helvetica, sans-serif; font-size: 13pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File covletDefault3() throws IOException {
       File html = new File("theme3CL.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Cover Letter -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"color: rgb(51, 34, 19); margin:0; padding: 0; padding-bottom: 20pt; background-color: rgb(249, 242, 236);\">\n" +
            "<div style=\"background-color: rgb(255, 248, 242);margin-top: 40px; border: solid rgb(171, 105, 48); border-width: 7px 0 0 0; padding: 20px; text-align:center; font-family: 'Georgia', Times, serif; font-weight: bold; font-variant: small-caps; letter-spacing: 2px; font-size: 16pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"background-color: rgb(255, 248, 242);text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"background-color: rgb(255, 248, 242);text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"background-color: rgb(255, 248, 242);text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity,   TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"padding: 8px 0 20px 0; background-color: rgb(255, 248, 242); border: solid rgb(171, 105, 48); border-width: 0 0 4px 0;text-align:center; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">phone: (555)555-5555  <span style=\"padding-left:8pt;\">cell: (555)555-5556</span>  <span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</span>\n" +
            "</div>\n" +
            "<hr style=\"margin: 0; padding: 0; height: 0; border: 0;\"/>\n" +
            "<br/>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Saturday, December 12, 2015</div>\n" +
            "<br/>\n" +
            "<br/>\n" +
            "<div style=\"background-color: rgb(255, 248, 242); margin-right: 20px; vertical-align: top; padding:20px; border: 1px solid rgb(171, 105, 48); float:right; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<div>Ad Source</div>\n" +
            "<div>Job Position</div>\n" +
            "<div>Ref# 123456789</div>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Employerfirstname  Employermiddlename  Employerlastname</span>\n" +
            "</div>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 10pt; display: block;\">Employer Title</div>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 10pt; display: block;\">Employer Compnay</div>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 10pt; display: block;\">Employeraddressline1</div>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 10pt; display: block;\">Employeraddressline2</div>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 10pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">EmployerCity,   TX 12345-6789</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Dr. Ms Soandso,</div>\n" +
            "<br/>\n" +
            "<p style=\"padding: 0 20px 0 20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">This is paragraph1 of your cover letter. This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.</p>\n" +
            "<p style=\"padding: 0 20px 0 20px; text-indent:1cm; margin:0; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">This is paragraph2 of your cover letter. This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.</p>\n" +
            "<br/>\n" +
            "<div style=\"padding-left:20px; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">Sincerely,</div>\n" +
            "<br/>\n" +
            "<div style=\"margin-bottom: 40px; border: solid rgb(171, 105, 48); border-width: 0 0 12px 0; padding: 0 20px 20px 20px; font-family: 'Georgia', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File covletDefault4() throws IOException {
       File html = new File("theme4CL.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Cover Letter -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"color: rgb(80, 0, 0);padding:20px;background-color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0);margin: 0\">\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Helvetica', Times, serif; font-weight: bold; font-size: 14pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-bottom: 0; border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity,   TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"color: rgb(255, 245, 245);border: 4px solid rgb(140, 0, 0); border-top: 0; background-color: rgb(80, 0, 0); text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">phone: (555)555-5555  <span style=\"padding-left:8pt;\">cell: (555)555-5556</span>  <span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</span>\n" +
            "</div>\n" +
            "<hr style=\"border: 2px solid rgb(140, 0, 0);\"/>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Saturday, December 12, 2015</div>\n" +
            "<br/>\n" +
            "<br/>\n" +
            "<div style=\"float:right; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<div>Ad Source</div>\n" +
            "<div>Job Position</div>\n" +
            "<div>Ref# 123456789</div>\n" +
            "</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Employerfirstname  Employermiddlename  Employerlastname</span>\n" +
            "</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 11pt; display: block;\">Employer Title</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 11pt; display: block;\">Employer Compnay</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 11pt; display: block;\">Employeraddressline1</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 11pt; display: block;\">Employeraddressline2</div>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 11pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">EmployerCity,   TX 12345-6789</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\"padding-top:10px; border-top: 4px solid rgb(140, 0, 0); font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Dr. Ms Soandso,</div>\n" +
            "<br/>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">This is paragraph1 of your cover letter. This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">This is paragraph2 of your cover letter. This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.</p>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\">Sincerely,</div>\n" +
            "<br/>\n" +
            "<div style=\"font-family: 'Vladimir Script', Times, serif;text-indent:1cm; font-size: 24pt; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File covletDefault5() throws IOException {
       File html = new File("theme5CL.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Cover Letter -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"padding:20px; line-height: 1.25em; font-family: 'Helvetica', Arial, sans-serif; font-size: 12pt;\">\n" +
            "<div style=\"background: rgb(0, 38, 73);line-height: 1.5em; text-align:center; font-weight: bold; font-size: 14pt; display: block; color: #fb8b00;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);color: rgb(255, 255, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);color: rgb(255, 255, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);color: rgb(255, 255, 255); line-height: 1.5em; text-align:center; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity,   TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"background: rgb(0, 38, 73);line-height: 1.5em;text-align:center; display: block; color: #fb8b00; font-size: 10pt;\">\n" +
            "<span xml:space=\"preserve\">phone: (555)555-5555  <span style=\"padding-left:8pt;\">cell: (555)555-5556</span>  <span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</span>\n" +
            "</div>\n" +
            "<hr style=\"margin-top: 2px; background:rgb(0, 115, 61); height: 6px;\"/>\n" +
            "<br/>\n" +
            "<div style=\" display: block; font-size:10pt;font-weight:bold;\">Saturday, December 12, 2015</div>\n" +
            "<br/>\n" +
            "<br/>\n" +
            "<div style=\"float:right; display: block; font-size: 10pt;\">\n" +
            "<div>Ad Source</div>\n" +
            "<div>Job Position</div>\n" +
            "<div>Ref# 123456789</div>\n" +
            "</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">\n" +
            "<span xml:space=\"preserve\">Employerfirstname  Employermiddlename  Employerlastname</span>\n" +
            "</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employer Title</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employer Compnay</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employeraddressline1</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employeraddressline2</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">\n" +
            "<span xml:space=\"preserve\">EmployerCity,   TX 12345-6789</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\" display: block;\">Dr. Ms Soandso,</div>\n" +
            "<br/>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">This is paragraph1 of your cover letter. This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">This is paragraph2 of your cover letter. This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.</p>\n" +
            "<br/>\n" +
            "<div style=\" display: block;font-style:italic;\">Sincerely,</div>\n" +
            "<br/>\n" +
            "<div style=\" display: block; font-weight:bold;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
   
   private File covletDefault6() throws IOException {
       File html = new File("theme6CL.html");
       html.deleteOnExit();
       String htmlText = "<html>\n" +
            "<head>\n" +
            "<title>Cover Letter -- Yourlastname</title>\n" +
            "</head>\n" +
            "<body style=\"color: rgb(0, 0, 30); background: rgb(240, 240, 255);padding:20px; line-height: 1.25em; font-family: 'Helvetica', Arial, sans-serif; font-size: 12pt;\">\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; background: rgb(0, 38, 64);line-height: 1.5em; text-align:center; font-weight: bold; font-size: 14pt; display: block; color: rgb(174, 209, 232);\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; border-top: 0;background: rgb(0, 38, 64);color: rgb(235, 235, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline1</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; border-top: 0;background: rgb(0, 38, 64);color: rgb(235, 235, 255); line-height: 1.5em; text-align:center; display: block;\">Youraddressline2</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-bottom: 0; border-top: 0;background: rgb(0, 38, 64);color: rgb(235, 235, 255); line-height: 1.5em; text-align:center; display: block;\">\n" +
            "<span xml:space=\"preserve\">Yourcity,   TX 98765-4321</span>\n" +
            "</div>\n" +
            "<div style=\"border: 3px solid rgb(174, 209, 232); border-top: 0;background: rgb(0, 38, 64);line-height: 1.5em;text-align:center; display: block; color: rgb(174, 209, 232); font-size: 10pt;\">\n" +
            "<span xml:space=\"preserve\">phone: (555)555-5555  <span style=\"padding-left:8pt;\">cell: (555)555-5556</span>  <span style=\"padding-left:8pt;\">youremail@somewhere.com</span>\n" +
            "</span>\n" +
            "</div>\n" +
            "<hr style=\"border-bottom: 1px solid rgb(0, 0, 100); margin-top: 2px; background-color: rgb(77, 107, 117); height: 6px;\"/>\n" +
            "<br/>\n" +
            "<div style=\" display: block; font-size:10pt;font-weight:bold;\">Saturday, December 12, 2015</div>\n" +
            "<br/>\n" +
            "<br/>\n" +
            "<div style=\"float:right; display: block; font-size: 10pt;\">\n" +
            "<div>Ad Source</div>\n" +
            "<div>Job Position</div>\n" +
            "<div>Ref# 123456789</div>\n" +
            "</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">\n" +
            "<span xml:space=\"preserve\">Employerfirstname  Employermiddlename  Employerlastname</span>\n" +
            "</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employer Title</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employer Compnay</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employeraddressline1</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">Employeraddressline2</div>\n" +
            "<div style=\" display: block; font-size: 10pt;\">\n" +
            "<span xml:space=\"preserve\">EmployerCity,   TX 12345-6789</span>\n" +
            "</div>\n" +
            "<br/>\n" +
            "<div style=\" display: block;\">Dr. Ms Soandso,</div>\n" +
            "<br/>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">This is paragraph1 of your cover letter. This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.This is paragraph1 of your cover letter.</p>\n" +
            "<p style=\"text-indent:1cm; padding:0 0 6px 0; margin:0; display: block;\">This is paragraph2 of your cover letter. This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.This is paragraph2 of your cover letter.</p>\n" +
            "<br/>\n" +
            "<div style=\" display: block;font-style:italic;\">Sincerely,</div>\n" +
            "<br/>\n" +
            "<div style=\" display: block; font-weight:bold;\">\n" +
            "<span xml:space=\"preserve\">Yourfirstname  Yourmiddlename  Yourlastname</span>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
       try (BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(html))) {
           htmlWriter.write(htmlText);
           htmlWriter.newLine();
           htmlWriter.flush();
       }
       return html;
   }
}
