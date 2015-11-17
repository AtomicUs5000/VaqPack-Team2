/*-----------------------------------------------------------------------------*
 * VP_GUIBuilder.java
 * - Constructs the GUI and maintains references to key components
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1600
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class VP_GUIBuilder {
    private final VP_GUIController controller;
    private final VP_Header header;
    private final VP_Tree leftTree;
    private final VP_Center center;
    private final VP_Footer footer;

    /*------------------------------------------------------------------------*
     * VP_GUIBuilder()
     * - Constructor. Initialiazes the header, leftTree, and center of the GUI.
     * No Parameters.
     *------------------------------------------------------------------------*/
    protected VP_GUIBuilder(VP_GUIController controller) {
        this.controller = controller;
        header = new VP_Header();
        leftTree = new VP_Tree();
        center = new VP_Center();
        footer = new VP_Footer();
    }

    /*------------------------------------------------------------------------*
     * createShell()
     * - Creates an empty BorderPane and its children to be built after the
     *   stage is showing and while the dtatabase is being checked.
     * - No paramters.
     * - Returns the empty GUI BorderPane.
     *------------------------------------------------------------------------*/
    protected BorderPane createShell() {
        BorderPane guiShell = new BorderPane();
        guiShell.setTop(header);
        guiShell.setLeft(leftTree);
        guiShell.setCenter(center);
        guiShell.setBottom(footer);
        return guiShell;
    }

    /*------------------------------------------------------------------------*
     * buildTop()
     * - Builds the header. Called in task, to build in the background
     * - Constrcusts the menubar for the header.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void buildTop() {
        Menu    homeMenu = new Menu("Home"),
                optionsMenu = new Menu("Options"),
                helpMenu = new Menu("Help");
        MenuItem userLogin = new MenuItem("Login"),
                userLogout = new MenuItem("Logout"),
                exitVP = new MenuItem("Exit"),
                toggleTree = new MenuItem("Toggle Tree View"),
                toggleFull = new MenuItem("Toggle Full Screen"),
                gettingStarted = new MenuItem("Getting Started with VaqPack"),
                aboutHelp = new MenuItem("About");
        homeMenu.getItems().addAll(
                userLogin,
                userLogout,
                new SeparatorMenuItem(),
                exitVP);
        //userLogin.setOnAction(...);
        //userLogout.setOnAction(...);
        //exitVP.setOnAction(...);
        //gettingStarted.setOnAction(...);
        //aboutHelp.setOnAction(...);
        //fullScreen.setOnAction(...fullScreenToggle());
        optionsMenu.getItems().addAll(toggleTree, toggleFull);
        helpMenu.getItems().addAll(gettingStarted, aboutHelp);
        
        header.getMenuBar().getMenus().addAll(homeMenu, optionsMenu, helpMenu);
        header.getHeaderBar().setPrefHeight(50);
        header.getHeaderBar().setAlignment(Pos.CENTER_LEFT);
        header.getHeaderBar().setFillHeight(true);
        header.getHeaderBar().setSpacing(20);
        header.getHeaderBar().getChildren().addAll(header.getHeaderLogo(), header.getHeaderCaption());
        header.getHeaderLogo().setPrefSize(200, 50);
        header.getHeaderCaption().setText("Graduate-to-Professional Aid Pack");
    }

    /*------------------------------------------------------------------------*
     * buildLeft
     * - Builds the left side. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildLeft() {
           Label testLabel = new Label("Left");
        leftTree.getChildren().addAll(testLabel);
        leftTree.setPrefWidth(200);
        leftTree.setPadding(new Insets(10, 10, 10, 10));
        TreeItem<String> coverLetterRoot = new TreeItem("Cover Letter");//Root01Beginning
        coverLetterRoot.setExpanded(true); //Expands the root and leaves
        TreeItem<String> header1 = new TreeItem("Header");//Beginning of Header Leaf
        //heading.setExpanded(true);
        TreeItem<String> name1 = new TreeItem("Name");//Child
        /* Input header or Name*/
        TreeItem<String> address1 = new TreeItem("Address");//Child   
        /* Input address*/
        TreeItem<String> phail1 = new TreeItem("Phone/E-mail");//Child
        TreeItem<String> phone1 = new TreeItem("Primary Phone");//Sub-child
        /* Input multiple phones # */
        TreeItem<String> mail1 = new TreeItem("Primary E-mail");//Sub-child
        /* Input Primary email*/
        header1.getChildren().addAll(name1,address1,phail1);
        phail1.getChildren().addAll(phone1, mail1);//Ending of Header Leaf
        TreeItem<String> inadd = new TreeItem("Inside Address");//Beginning of Addressing
        //inadd.setExpanded(true);
        TreeItem<String> contact = new TreeItem("Contact Name");//Child
        /* Input Contact Name*/
        TreeItem<String> company = new TreeItem("Company Name");//Child   
        /* Input Company Name*/
        TreeItem<String> street = new TreeItem("Address");//Child
        /* Input Company Address*/
        TreeItem<String> city = new TreeItem("City");//Sub-child
        TreeItem<String> state = new TreeItem("State");//Sub-child
        TreeItem<String> zip = new TreeItem("Zip Code");//Sub-child
        /* Input multiple phones # */
        inadd.getChildren().addAll(contact, company, street);
        street.getChildren().addAll(city, state, zip);
        //Ending of Addressing
        TreeItem<String> date = new TreeItem<String>("Date");
        /* Date Sent?*/
        TreeItem<String> salut = new TreeItem<String>("Salutation");
        /*Input Greeting and Salutation*/       
        TreeItem<String> body = new TreeItem<String>("Body");//Beginning of Body
        TreeItem<String> par = new TreeItem<String>("Paragraph"); //Multiple paragraphs allowed
        body.getChildren().add(par);//Ending of Body
        TreeItem<String> close = new TreeItem<String> ("Closing");
        TreeItem<String> sign = new TreeItem<String> ("Signature");        
        coverLetterRoot.getChildren().addAll(header1, inadd, date, salut, body, close, sign);//Root01Ending
        
        TreeItem<String> resumeRoot = new TreeItem("Resume"); //Root02Beginning
        resumeRoot.setExpanded(true);
        
        TreeItem<String> header2 = new TreeItem("Header");//Beginning of Header02 Leaf
        //header2.setExpanded(true);
        TreeItem<String> name2 = new TreeItem("Name");//Child
        /* Input header or Name*/
        TreeItem<String> address2 = new TreeItem("Mailing Address");//Child   
        /* Input address*/
        TreeItem<String> street2 = new TreeItem("Street");//Sub-child
        TreeItem<String> city2 = new TreeItem("City");//Sub-child
        TreeItem<String> state2 = new TreeItem("State");//Sub-child
        TreeItem<String> zip2 = new TreeItem("Zip Code");//Sub-child
        TreeItem<String> phail2 = new TreeItem("Phone/E-mail");//Child
        TreeItem<String> phone2 = new TreeItem("Primary Phone");//Sub-child
        /* Input multiple phones # */
        TreeItem<String> mail2 = new TreeItem("Primary E-mail");//Sub-child
        /* Input Primary email*/
        header2.getChildren().addAll(name2,address2,phail2);
        phail2.getChildren().addAll(phone2, mail2);
        address2.getChildren().addAll(street2, city2, state2, zip2);//Ending of Header02 Leaf
        
        TreeItem<String> objective = new TreeItem("Objective");//Beginning of Objective Leaf
        TreeItem<String> text = new TreeItem("Text");
        objective.getChildren().addAll(text);//Ending of Objective Leaf
        
        TreeItem<String> edu = new TreeItem("Education");//Beginning of Education leaf
        TreeItem<String> instit = new TreeItem("Institution");
        TreeItem<String> iname = new TreeItem("Name of Institution");
        TreeItem<String> degree = new TreeItem("Degree/Major");
        TreeItem<String> gpa = new TreeItem("GPA");
        TreeItem<String> local = new TreeItem("Location of Institute");//Child
        TreeItem<String> street3 = new TreeItem("Street");//Sub-child
        TreeItem<String> city3 = new TreeItem("City");//Sub-child
        TreeItem<String> state3 = new TreeItem("State");//Sub-child
        TreeItem<String> zip3 = new TreeItem("Zip Code");//Sub-child
        TreeItem<String> dura = new TreeItem("Durations");//Child
        TreeItem<String> start = new TreeItem("Start Date");//Sub-child
        TreeItem<String> end = new TreeItem("End Date");//Sub-child
        edu.getChildren().addAll(instit, iname, degree, gpa, local,dura);
        local.getChildren().addAll(street3, city3, state3, zip3);
        dura.getChildren().addAll(start, end);//End of Edution leaf
        //NOTE: Still need to work on being able to add to this leaf
        TreeItem<String> work = new TreeItem("Work Experience");//Beginning of Work Experience Leaf
        TreeItem<String> employ = new TreeItem("Employer");
        TreeItem<String> worklocal = new TreeItem ("Location");//Child
        TreeItem<String> street4 = new TreeItem("Street");//Sub-child
        TreeItem<String> city4 = new TreeItem("City");//Sub-child
        TreeItem<String> state4 = new TreeItem("State");//Sub-child
        TreeItem<String> zip4 = new TreeItem("Zip Code");//Sub-child
        TreeItem<String> pos = new TreeItem("Position");
        TreeItem<String> posTitle  = new TreeItem("Title");
        TreeItem<String> posDura = new TreeItem("Duration");
        TreeItem<String> posStart = new TreeItem("Start Date");
        TreeItem<String> posEnd = new TreeItem("End Date");
        pos.getChildren().addAll(posTitle,posDura);
        posDura.getChildren().addAll(posStart, posEnd);
        worklocal.getChildren().addAll(street4, city4, state4, zip4);
        work.getChildren().addAll(employ, worklocal,pos);//End of Work Experience Leaf
        
        TreeItem<String> award = new TreeItem("Awards and Organization");//Beginning of Awards and Organization
        TreeItem<String> prize = new TreeItem("Award Name");
        TreeItem<String> prizeDate = new TreeItem("Date Awarded");
        TreeItem<String> prizeInst = new TreeItem("Institute From");
        TreeItem<String> orgName = new TreeItem("Organization Name");
        TreeItem<String> orgDura = new TreeItem("Durration");
        TreeItem<String> orgStart = new TreeItem("Start Date");
        TreeItem<String> orgEnd = new TreeItem("End Date");       
        award.getChildren().addAll(prize, orgName);
        prize.getChildren().addAll(prizeDate,prizeInst);
        orgDura.getChildren().addAll(orgStart, orgEnd);//End of Awards and Organization
        //NOTE: Still need to work on being able to Add to this leaf
        TreeItem<String> comm = new TreeItem("Community");
        TreeItem<String> commName = new TreeItem("Community Group Name");
        TreeItem<String> commDura = new TreeItem ("Duration of Service");
        TreeItem<String> commStart = new TreeItem("Start Date");
        TreeItem<String> commEnd = new TreeItem ("End Date");
        comm.getChildren().addAll(commName, commDura);
        commDura.getChildren().addAll(commStart, commEnd);
        //NOTE: Still need to work on being able to Add to this leaf
        TreeItem<String> qual= new TreeItem("Qualifications and Highlights");
        TreeItem<String> qual1 = new TreeItem ("Qualification");
        TreeItem<String> high = new TreeItem("Highlights");
        qual.getChildren().addAll(qual1, high);
        //NOTE: Still need to work on being able to Add to this leaf
        TreeItem<String> lang = new TreeItem("Languages");
        TreeItem<String> prime = new TreeItem("Primary Language");
        TreeItem<String> second = new TreeItem("Secondary Language");
        lang.getChildren().addAll(prime, second);
        //NOTE: Still need to work on being able to Add to this leaf
        TreeItem<String> extra = new TreeItem("Additional Info");
        TreeItem<String> extra1 = new TreeItem("Research/Projects");
        extra.getChildren().addAll(extra1);
        //NOTE: Still need to work on being able to Add to this leaf
        TreeItem<String> ref = new TreeItem("References");//Beginning of the Reference Leaf
        TreeItem<String> refOps = new TreeItem("Reference options");
        TreeItem<String> refReq = new TreeItem("Request Reference");
        TreeItem<String> refList1 = new TreeItem("Reference List");
        TreeItem<String> refName = new TreeItem("Reference Name");
        TreeItem<String> refComp = new TreeItem("Reference Company");
        TreeItem<String> refPhone = new TreeItem("Reference Phone");
        TreeItem<String> refMail = new TreeItem("Mailing");
        TreeItem<String> refMail1 = new TreeItem("E-mail");
        TreeItem<String> refMail2 = new TreeItem("Address");
        TreeItem<String> street5 = new TreeItem("Street");//Sub-child
        TreeItem<String> city5 = new TreeItem("City");//Sub-child
        TreeItem<String> state5 = new TreeItem("State");//Sub-child
        TreeItem<String> zip5 = new TreeItem("Zip Code");//Sub-child
        ref.getChildren().addAll(refOps, refReq, refList1);
        refList1.getChildren().addAll(refName, refComp, refPhone, refMail);
        refMail2.getChildren().addAll(street5,city5,state5,zip5);//End of the Reference Leaf
        
        resumeRoot.getChildren().addAll(header2, objective, edu, work, award, comm, qual, lang, extra, ref);
        //end of Root 02
        TreeItem<String> bcardRoot = new TreeItem("Business Card");//Root03
        bcardRoot.setExpanded(true);
        TreeItem<String> bcName = new TreeItem("Name");
        TreeItem<String> bcProf = new TreeItem("Profession");
        TreeItem<String> bcComp = new TreeItem("Company");
        TreeItem<String> compName = new TreeItem("Company Name");
        TreeItem<String> compSlog = new TreeItem("Company Slogan");
        TreeItem<String> compWeb = new TreeItem("Web Address(URL)");
        TreeItem<String> bcmail = new TreeItem("Address");
        TreeItem<String> street6 = new TreeItem("Street");//Sub-child
        TreeItem<String> city6 = new TreeItem("City");//Sub-child
        TreeItem<String> state6 = new TreeItem("State");//Sub-child
        TreeItem<String> zip6 = new TreeItem("Zip Code");//Sub-child
        TreeItem<String> bcphone = new TreeItem("Contact");
        TreeItem<String> bcprimephone = new TreeItem("Primary Phone");
        TreeItem<String> bcsecondphone = new TreeItem("Secondary Phone");
        TreeItem<String> bcFax = new TreeItem("Fax number");
        TreeItem<String> bcEmail = new TreeItem("E-mail");
        bcComp.getChildren().addAll(compName, compSlog, compWeb);
        bcmail.getChildren().addAll(street6, city6, state6, zip6);
        bcardRoot.getChildren().addAll(bcName, bcProf,bcComp,bcmail); //End of Root 03
        
        TreeView<String> tree1 = new TreeView (coverLetterRoot);
        TreeView<String> tree2 = new TreeView (resumeRoot);
        TreeView<String> tree3 = new TreeView (bcardRoot);
        leftTree.getChildren().addAll(tree1, tree2, tree3);
    }


    /*------------------------------------------------------------------------*
     * buildCenter
     * - Builds the gui center. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildCenter() {
        Button btnEmail = new Button("Send the test email!");
        btnEmail.setOnAction((ActionEvent event) -> {
            String[] ccMail = {
                "shiro_aurion@yahoo.com",
                "atomicus@gmail.com",
                "shiro_aurion@yahoo.com",
                "mr.fernandob3@gmail.com",
                "erik.a.lopez01@utrgv.edu",
                "scorpioncarr1@gmail.com"
            };
            String msg =  "Test number 2. Trying to send to all recipients at "
                    + "the same time.\n\n"
                    + "This is a test email from the VaqPack software.\n"
                    + "This is the email that will include a randomly generated "
                    + "code that the new user must enter to verify the email "
                    + "address that they entered in when registering.\n"
                    + "If this is a success, I can finish the user login "
                    + "system and we can move forward to the wizard.";
            VP_Mail testEmail = new VP_Mail(controller, "william.dewald01@utrgv.edu", ccMail, "VaqPack Test Email", msg);
            testEmail.start();
        });
        center.getChildren().addAll(btnEmail);
        center.setPadding(new Insets(10, 10, 10, 10));
    }
    
    /*------------------------------------------------------------------------*
     * buildCenter
     * - Builds the gui center. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildBottom() {
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPrefHeight(40);
        footer.setFillHeight(true);
        footer.setSpacing(20);
        footer.setPadding(new Insets(0, 20, 0, 20));
        footer.getFooterCaption().setText("The University of Texas Rio Grande Valley");
        footer.getFooterLogo().setPrefSize(100, 20);
        footer.getChildren().addAll(footer.getFooterLogo(), footer.getFooterCaption());
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
