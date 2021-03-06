/*-----------------------------------------------------------------------------*
 * VP_Resume.java
 * - Resume object
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 5300
 *-----------------------------------------------------------------------------*/
package vaqpack.user;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VP_Resume {

    private int themeId = -1,
            numbEducation = 1,
            numbExperience = 1,
            numbAchievements = 1,
            numbCommunity = 1,
            numbQualification = 1,
            numbHighlights = 1,
            numbLanguages = 1,
            numbSoftware = 1,
            numbReferences = 1,
            numbEducationStored = 1,
            numbExperienceStored = 1,
            numbAchievementsStored = 1,
            numbCommunityStored = 1,
            numbQualificationStored = 1,
            numbHighlightsStored = 1,
            numbLanguagesStored = 1,
            numbSoftwareStored = 1,
            numbReferencesStored = 1;
    private final VP_User owner;
    private boolean changes,
            completedObjective,
            completedEducation,
            completedWorkExperience,
            completedQualifications,
            completedHighlights,
            completedLanguages,
            completedSoftware,
            startedResume,
            completedResume;
    private final StringProperty objective;
    private String xsl,
            objectiveStored;
    private final ArrayList<ArrayList<StringProperty>> education,
            experience,
            achievements,
            community,
            references;
    private final ArrayList<StringProperty> qualifications,
            highlights,
            languages,
            software;
    private final ArrayList<ArrayList<String>> educationStored,
            experienceStored,
            achievementsStored,
            communityStored,
            referencesStored;
    private final ArrayList<String> qualificationsStored,
            highlightsStored,
            languagesStored,
            softwareStored;

    /*------------------------------------------------------------------------*
     * VP_Resume()
     * - Constructor.
     * Parameter owner is the logged in user who owns this.
     *------------------------------------------------------------------------*/
    protected VP_Resume(VP_User owner) {
        this.owner = owner;
        completedObjective = false;
        completedEducation = false;
        completedWorkExperience = false;
        completedHighlights = false;
        completedSoftware = false;
        startedResume = false;
        completedResume = false;
        objective = new SimpleStringProperty();
        education = new ArrayList();
        educationStored = new ArrayList();
        experience = new ArrayList();
        experienceStored = new ArrayList();
        achievements = new ArrayList();
        achievementsStored = new ArrayList();
        community = new ArrayList();
        communityStored = new ArrayList();
        qualifications = new ArrayList();
        qualificationsStored = new ArrayList();
        highlights = new ArrayList();
        highlightsStored = new ArrayList();
        languages = new ArrayList();
        languagesStored = new ArrayList();
        software = new ArrayList();
        softwareStored = new ArrayList();
        references = new ArrayList();
        referencesStored = new ArrayList();
        for (int i = 0; i < 9; i++) {
            educationStored.add(new ArrayList());
            referencesStored.add(new ArrayList());
            for (int ii = 0; ii < 6; ii++) {
                educationStored.get(i).add("");
                referencesStored.get(i).add("");
            }
            experienceStored.add(new ArrayList());
            for (int ii = 0; ii < 5; ii++) {
                experienceStored.get(i).add("");
            }
            achievementsStored.add(new ArrayList());
            communityStored.add(new ArrayList());
            for (int ii = 0; ii < 3; ii++) {
                achievementsStored.get(i).add("");
                communityStored.get(i).add("");
            }
            qualificationsStored.add("");
            highlightsStored.add("");
            languagesStored.add("");
            softwareStored.add("");
            education.add(new ArrayList());
            references.add(new ArrayList());
            for (int ii = 0; ii < 6; ii++) {
                education.get(i).add(new SimpleStringProperty());
                references.get(i).add(new SimpleStringProperty());
            }
            experience.add(new ArrayList());
            for (int ii = 0; ii < 5; ii++) {
                experience.get(i).add(new SimpleStringProperty());
            }
            achievements.add(new ArrayList());
            community.add(new ArrayList());
            for (int ii = 0; ii < 3; ii++) {
                achievements.get(i).add(new SimpleStringProperty());
                community.get(i).add(new SimpleStringProperty());
            }
            qualifications.add(new SimpleStringProperty());
            highlights.add(new SimpleStringProperty());
            languages.add(new SimpleStringProperty());
            software.add(new SimpleStringProperty());
        }
    }

    /*------------------------------------------------------------------------*
     * revert()
     * - Reverts bound properties to the last stored value
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    public void revert() {
        objective.setValue(objectiveStored);
        for (int i = 0; i < 9; i++) {
            for (int ii = 0; ii < 6; ii++) {
                education.get(i).get(ii).setValue(educationStored.get(i).get(ii));
                references.get(i).get(ii).setValue(referencesStored.get(i).get(ii));
            }
            for (int ii = 0; ii < 5; ii++) {
                experience.get(i).get(ii).setValue(experienceStored.get(i).get(ii));
            }
            for (int ii = 0; ii < 3; ii++) {
                achievements.get(i).get(ii).setValue(achievementsStored.get(i).get(ii));
                community.get(i).get(ii).setValue(communityStored.get(i).get(ii));
            }
            qualifications.get(i).setValue(qualificationsStored.get(i));
            highlights.get(i).setValue(highlightsStored.get(i));
            languages.get(i).setValue(languagesStored.get(i));
            software.get(i).setValue(softwareStored.get(i));
        }
        numbEducation = numbEducationStored;
        numbExperience = numbExperienceStored;
        numbAchievements = numbAchievementsStored;
        numbCommunity = numbCommunityStored;
        numbQualification = numbQualificationStored;
        numbHighlights = numbHighlightsStored;
        numbLanguages = numbLanguagesStored;
        numbSoftware = numbSoftwareStored;
        numbReferences = numbReferencesStored;
    }

    /*------------------------------------------------------------------------*
     * save()
     * - Stored values are set to the current bound properties.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    public void save() {
        changes = false;
        completedObjective = false;
        completedEducation = false;
        completedWorkExperience = false;
        completedQualifications = false;
        completedHighlights = false;
        completedLanguages = false;
        completedSoftware = false;
        completedResume = false;
        startedResume = false;

        if (numbEducation != numbEducationStored) {
            numbEducationStored = numbEducation;
            changes = true;
        }
        if (numbExperience != numbExperienceStored) {
            numbExperienceStored = numbExperience;
            changes = true;
        }
        if (numbAchievements != numbAchievementsStored) {
            numbAchievementsStored = numbAchievements;
            changes = true;
        }
        if (numbCommunity != numbCommunityStored) {
            numbCommunityStored = numbCommunity;
            changes = true;
        }
        if (numbQualification != numbQualificationStored) {
            numbQualificationStored = numbQualification;
            changes = true;
        }
        if (numbHighlights != numbHighlightsStored) {
            numbHighlightsStored = numbHighlights;
            changes = true;
        }
        if (numbLanguages != numbLanguagesStored) {
            numbLanguagesStored = numbLanguages;
            changes = true;
        }
        if (numbSoftware != numbSoftwareStored) {
            numbSoftwareStored = numbSoftware;
            changes = true;
        }
        if (numbReferences != numbReferencesStored) {
            numbReferencesStored = numbReferences;
            changes = true;
        }
        if ((objectiveStored != null && !objectiveStored.equals(objective.getValue()))
                || (objectiveStored == null && objective.getValue() != null)) {
            objectiveStored = objective.getValue();
            changes = true;
        }
        for (int i = 0; i < 9; i++) {
            for (int ii = 0; ii < 6; ii++) {
                if ((educationStored.get(i).get(ii) != null && !educationStored.get(i).get(ii).equals(education.get(i).get(ii).getValue()))
                        || (educationStored.get(i).get(ii) == null && education.get(i).get(ii).getValue() != null)) {
                    educationStored.get(i).set(ii, education.get(i).get(ii).getValue());
                    changes = true;
                }
                if ((referencesStored.get(i).get(ii) != null && !referencesStored.get(i).get(ii).equals(references.get(i).get(ii).getValue()))
                        || (referencesStored.get(i).get(ii) == null && references.get(i).get(ii).getValue() != null)) {
                    referencesStored.get(i).set(ii, references.get(i).get(ii).getValue());
                    changes = true;
                }
            }
            for (int ii = 0; ii < 5; ii++) {
                if ((experienceStored.get(i).get(ii) != null && !experienceStored.get(i).get(ii).equals(experience.get(i).get(ii).getValue()))
                        || (experienceStored.get(i).get(ii) == null && experience.get(i).get(ii).getValue() != null)) {
                    experienceStored.get(i).set(ii, experience.get(i).get(ii).getValue());
                    changes = true;
                }
            }
            for (int ii = 0; ii < 3; ii++) {
                if ((achievementsStored.get(i).get(ii) != null && !achievementsStored.get(i).get(ii).equals(achievements.get(i).get(ii).getValue()))
                        || (achievementsStored.get(i).get(ii) == null && achievements.get(i).get(ii).getValue() != null)) {
                    achievementsStored.get(i).set(ii, achievements.get(i).get(ii).getValue());
                    changes = true;
                }
                if ((communityStored.get(i).get(ii) != null && !communityStored.get(i).get(ii).equals(community.get(i).get(ii).getValue()))
                        || (communityStored.get(i).get(ii) == null && community.get(i).get(ii).getValue() != null)) {
                    communityStored.get(i).set(ii, community.get(i).get(ii).getValue());
                    changes = true;
                }
            }
            if ((qualificationsStored.get(i) != null && !qualificationsStored.get(i).equals(qualifications.get(i).getValue()))
                    || (qualificationsStored.get(i) == null && qualifications.get(i).getValue() != null)) {
                qualificationsStored.set(i, qualifications.get(i).getValue());
                changes = true;
            }
            if ((highlightsStored.get(i) != null && !highlightsStored.get(i).equals(highlights.get(i).getValue()))
                    || (highlightsStored.get(i) == null && highlights.get(i).getValue() != null)) {
                highlightsStored.set(i, highlights.get(i).getValue());
                changes = true;
            }
            if ((languagesStored.get(i) != null && !languagesStored.get(i).equals(languages.get(i).getValue()))
                    || (languagesStored.get(i) == null && languages.get(i).getValue() != null)) {
                languagesStored.set(i, languages.get(i).getValue());
                changes = true;
            }
            if ((softwareStored.get(i) != null && !softwareStored.get(i).equals(software.get(i).getValue()))
                    || (softwareStored.get(i) == null && software.get(i).getValue() != null)) {
                softwareStored.set(i, software.get(i).getValue());
                changes = true;
            }
        }

        // check for completeness
        if (objectiveStored != null && !objectiveStored.equals("")) {
            startedResume = true;
            completedObjective = true;
        }
        if (educationStored.get(0).get(0) != null && !educationStored.get(0).get(0).equals("")
                && educationStored.get(0).get(1) != null && !educationStored.get(0).get(1).equals("")
                && educationStored.get(0).get(2) != null && !educationStored.get(0).get(2).equals("")
                && educationStored.get(0).get(3) != null && !educationStored.get(0).get(3).equals("")
                && educationStored.get(0).get(4) != null && !educationStored.get(0).get(4).equals("")
                && educationStored.get(0).get(5) != null && !educationStored.get(0).get(5).equals("")) {
            startedResume = true;
            completedEducation = true;
        }
        if (experienceStored.get(0).get(0) != null && !experienceStored.get(0).get(0).equals("")
                && experienceStored.get(0).get(1) != null && !experienceStored.get(0).get(1).equals("")
                && experienceStored.get(0).get(2) != null && !experienceStored.get(0).get(2).equals("")
                && experienceStored.get(0).get(3) != null && !experienceStored.get(0).get(3).equals("")
                && experienceStored.get(0).get(4) != null && !experienceStored.get(0).get(4).equals("")) {
            startedResume = true;
            completedWorkExperience = true;
        }
        if (qualificationsStored.get(0) != null && !qualificationsStored.get(0).equals("")) {
            startedResume = true;
            completedQualifications = true;
        }
        if (highlightsStored.get(0) != null && !highlightsStored.get(0).equals("")) {
            startedResume = true;
            completedHighlights = true;
        }
        if (languagesStored.get(0) != null && !languagesStored.get(0).equals("")) {
            startedResume = true;
            completedLanguages = true;
        }
        if (softwareStored.get(0) != null && !softwareStored.get(0).equals("")) {
            startedResume = true;
            completedSoftware = true;
        }
        if (completedObjective && completedEducation && completedWorkExperience
                && completedQualifications && completedHighlights
                && completedLanguages && completedSoftware) {
            startedResume = true;
            completedResume = true;
        }
        if (completedResume) {
            generateXSL();
        }
    }

    /*------------------------------------------------------------------------*
     * clear()
     * - Clears all data in the resume for the next user
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void clear() {
        themeId = -1;
        xsl = null;
        changes = false;
        completedObjective = false;
        completedEducation = false;
        completedWorkExperience = false;
        completedHighlights = false;
        completedSoftware = false;
        completedResume = false;
        startedResume = false;
        numbEducation = 1;
        numbExperience = 1;
        numbAchievements = 1;
        numbCommunity = 1;
        numbQualification = 1;
        numbHighlights = 1;
        numbLanguages = 1;
        numbSoftware = 1;
        numbReferences = 1;
        numbEducationStored = 1;
        numbExperienceStored = 1;
        numbAchievementsStored = 1;
        numbCommunityStored = 1;
        numbQualificationStored = 1;
        numbHighlightsStored = 1;
        numbLanguagesStored = 1;
        numbSoftwareStored = 1;
        numbReferencesStored = 1;
        for (int i = 0; i < 9; i++) {
            for (int ii = 0; ii < 6; ii++) {
                educationStored.get(i).set(ii, "");
                referencesStored.get(i).set(ii, "");
            }
            for (int ii = 0; ii < 5; ii++) {
                experienceStored.get(i).set(ii, "");
            }
            for (int ii = 0; ii < 3; ii++) {
                achievementsStored.get(i).set(ii, "");
                communityStored.get(i).set(ii, "");
            }
            qualificationsStored.set(i, "");
            highlightsStored.set(i, "");
            languagesStored.set(i, "");
            softwareStored.set(i, "");
            for (int ii = 0; ii < 6; ii++) {
                education.get(i).get(ii).setValue("");
                references.get(i).get(ii).setValue("");
            }
            for (int ii = 0; ii < 5; ii++) {
                experience.get(i).get(ii).setValue("");
            }
            for (int ii = 0; ii < 3; ii++) {
                achievements.get(i).get(ii).setValue("");
                community.get(i).get(ii).setValue("");
            }
            qualifications.get(i).setValue("");
            highlights.get(i).setValue("");
            languages.get(i).setValue("");
            software.get(i).setValue("");
        }
    }

    /*------------------------------------------------------------------------*
     * generateXSL()
     * - Creates the xml stylesheet to be passed to html conversion.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    private void generateXSL() {
        String currentTheme = String.valueOf(themeId * -1);
        xsl = "<?xml version=\"1.0\"?>\n"
                + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" "
                + "version=\"1.0\">\n"
                + "<xsl:output method=\"xml\" indent=\"yes\"/>\n"
                + "<xsl:template match=\"/\">\n"
                + "<html>\n<head>\n<title>Resume -- <xsl:value-of select=\"resume/heading/name/lastname\"/></title>\n</head>\n<body";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_BODY_" + currentTheme);
        }
        xsl += ">"
                + "<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_NAME_" + currentTheme);
        }
        xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"resume/heading/name/firstname\"/> ";
        if (owner.getMiddleName().getValueSafe()!= null) {
            xsl += " <xsl:value-of select=\"resume/heading/name/middlename\"/> ";
        }
        xsl += " <xsl:value-of select=\"resume/heading/name/lastname\"/></span></div>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_ADDRESS1_" + currentTheme);
        }
        xsl += "><xsl:value-of select=\"resume/heading/address/line1\"/></div>\n";
        if (owner.getAddress2().getValueSafe() != null) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_ADDRESS2_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/heading/address/line2\"/></div>\n";
        }
        xsl += "<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_CITY_STATE_ZIP_" + currentTheme);
        }
        xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"resume/heading/address/city\"/>, "
                + "<xsl:value-of select=\"resume/heading/address/state\"/> "
                + "<xsl:value-of select=\"resume/heading/address/zip\"/></span></div>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_COMMUNICATION_" + currentTheme);
        }
        xsl += ">phone: <xsl:value-of select=\"resume/heading/communication/phone\"/>  ";
        if (owner.getCell().getValueSafe() != null) {
            xsl += "<span style=\"padding-left:8pt;\">cell: (956)876-5432</span>  ";
        }
        xsl += "<span style=\"padding-left:8pt;\"><xsl:value-of select=\"resume/heading/communication/email\"/>"
                + "</span></div>\n<br/>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">OBJECTIVE</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n<p";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
        }
        xsl += ">\n<xsl:value-of select=\"resume/objective\"/>\n</p>\n<br />\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">EDUCATION</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n";
        for (int i = 1; i <= numbEducation; i++) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_CONTAINERS_" + currentTheme);
            }
            xsl += ">\n<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/education/institution" + i + "/name\"/></p>\n<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/education/institution" + i + "/location\"/></p>\n<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/education/institution" + i + "/earned\"/></p>\n";
            if (education.get(i - 1).get(3).getValueSafe() != null) {
                xsl += "<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><span xml:space=\"preserve\">GPA: <xsl:value-of select=\"resume/education/institution" + i + "/gpa\"/></span></p>\n";
            }
            xsl += "<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"resume/education/institution" + i + "/start\"/> - "
                    + "<xsl:value-of select=\"resume/education/institution" + i + "/end\"/></span></p>\n</div>\n";
        }
        xsl += "<br/>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">EXPERIENCE</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n";
        for (int i = 1; i <= numbExperience; i++) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_CONTAINERS_" + currentTheme);
            }
            xsl += ">\n<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/experience/institution" + i + "/name\"/></p>\n<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/experience/institution" + i + "/location\"/></p>\n<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/experience/institution" + i + "/position\"/></p>\n<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"resume/experience/institution" + i + "/start\"/> - "
                    + "<xsl:value-of select=\"resume/experience/institution" + i + "/end\"/></span></p>\n</div>\n";
        }
        xsl += "<br/>\n";
        if (achievements.get(0).get(0).getValueSafe() != null) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
            }
            xsl += ">ACHIEVEMENTS</div>\n<hr";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
            }
            xsl += "/>\n";
            for (int i = 1; i <= numbAchievements; i++) {
                xsl += "<div";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_CONTAINERS_" + currentTheme);
                }
                xsl += ">\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/achievements/award" + i + "/name\"/></p>\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/achievements/award" + i + "/from\"/></p>\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/achievements/award" + i + "/date\"/></p>\n</div>\n";
            }
            xsl += "<br/>\n";
        }
        if (community.get(0).get(0).getValueSafe() != null) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
            }
            xsl += ">COMMUNITY</div>\n<hr";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
            }
            xsl += "/>\n";
            for (int i = 1; i <= numbCommunity; i++) {
                xsl += "<div";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_CONTAINERS_" + currentTheme);
                }
                xsl += ">\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/community/event" + i + "/name\"/></p>\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/community/event" + i + "/location\"/></p>\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/community/event" + i + "/date\"/></p>\n</div>\n";
            }
            xsl += "<br/>\n";
        }
        xsl += "<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">QUALIFICATIONS</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n<ul>\n";
        for (int i = 1; i <= numbQualification; i++) {
            xsl += "<li";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_LISTS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/qualifications/skill" + i + "\"/></li>\n";
        }
        xsl += "</ul>\n<br/>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">HIGHLIGHTS</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n<ul>\n";
        for (int i = 1; i <= numbHighlights; i++) {
            xsl += "<li";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_LISTS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/highlights/quality" + i + "\"/></li>\n";
        }
        xsl += "</ul>\n<br/>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">LANGUAGES</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n<ul>\n<li";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_LISTS_" + currentTheme);
        }
        xsl += ">Primary:\n<ul>\n<li";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_LISTS_" + currentTheme);
        }
        xsl +=  "><xsl:value-of select=\"resume/languages/lang1\"/></li>\n</ul>\n</li>\n";
        if (numbLanguages > 1) {
            xsl += "<li";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_LISTS_" + currentTheme);
            }
            xsl += ">Secondary:\n<ul>\n";
            for (int i = 2; i <= numbLanguages; i++) {
                xsl += "<li";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_LISTS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/languages/lang" + i + "\"/></li>\n";
            }
            xsl += "</ul>\n</li>\n";
        }
        xsl += "</ul>\n<br/>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">SOFTWARE</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n<ul>\n";
        for (int i = 1; i <= numbSoftware; i++) {
            xsl += "<li";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_LISTS_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"resume/software/product" + i + "\"/></li>\n";
        }
        xsl += "</ul>\n<br/>\n";
        xsl += "<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_TITLES_" + currentTheme);
        }
        xsl += ">REFERENCES</div>\n<hr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("RES_DIVIDERS_" + currentTheme);
        }
        xsl += "/>\n";
        if (references.get(0).get(0).getValueSafe() == null) {
            xsl += "<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
            }
            xsl += ">\nReferences are available upon request.\n</p>\n";
        } else {
            for (int i = 1; i <= numbReferences; i ++) {
                xsl += "<div";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_CONTAINERS_" + currentTheme);
                }
                xsl += "><p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"resume/references/ref" + i + "/firstname\"/> ";
                if (references.get(i - 1).get(1).getValueSafe() != null) {
                    xsl += "<xsl:value-of select=\"resume/references/ref" + i + "/middlename\"/> ";
                }
                xsl += "<xsl:value-of select=\"resume/references/ref" + i + "/lastname\"/></span></p>\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/references/ref" + i + "/company\"/></p>\n<p";
                if (themeId < 0) {
                    xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                }
                xsl += "><xsl:value-of select=\"resume/references/ref" + i + "/phone\"/></p>\n";
                if (references.get(i - 1).get(5).getValueSafe() != null) {
                    xsl += "<p";
                    if (themeId < 0) {
                        xsl += VP_Theme.Default.valueOf("RES_PARAGRAPHS_" + currentTheme);
                    }
                    xsl += "><xsl:value-of select=\"resume/references/ref" + i + "/email\"/></p>\n";
                }
                xsl += "</div>\n";
            }
        }
        xsl += "</body>\n</html>\n</xsl:template>\n</xsl:stylesheet>";
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    public boolean hasStartedResume() {
        return startedResume;
    }

    public boolean hasCompletedResume() {
        return completedResume;
    }

    public boolean hasCompletedObjective() {
        return completedObjective;
    }

    public boolean hasCompletedEducation() {
        return completedEducation;
    }

    public boolean hasCompletedWorkExperience() {
        return completedWorkExperience;
    }

    public boolean hasCompletedQualifications() {
        return completedQualifications;
    }

    public boolean hasCompletedHighlights() {
        return completedHighlights;
    }

    public boolean hasCompletedLanguages() {
        return completedLanguages;
    }

    public boolean hasCompletedSoftware() {
        return completedSoftware;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getNumbEducation() {
        return numbEducation;
    }

    public void setNumbEducation(int numbEducation) {
        this.numbEducation = numbEducation;
    }

    public int getNumbExperience() {
        return numbExperience;
    }

    public void setNumbExperience(int numbExperience) {
        this.numbExperience = numbExperience;
    }

    public int getNumbAchievements() {
        return numbAchievements;
    }

    public void setNumbAchievements(int numbAchievements) {
        this.numbAchievements = numbAchievements;
    }

    public int getNumbCommunity() {
        return numbCommunity;
    }

    public void setNumbCommunity(int numbCommunity) {
        this.numbCommunity = numbCommunity;
    }

    public int getNumbQualification() {
        return numbQualification;
    }

    public void setNumbQualification(int numbQualification) {
        this.numbQualification = numbQualification;
    }

    public int getNumbHighlights() {
        return numbHighlights;
    }

    public void setNumbHighlights(int numbHighlights) {
        this.numbHighlights = numbHighlights;
    }

    public int getNumbLanguages() {
        return numbLanguages;
    }

    public void setNumbLanguages(int numbLanguages) {
        this.numbLanguages = numbLanguages;
    }

    public int getNumbSoftware() {
        return numbSoftware;
    }

    public void setNumbSoftware(int numbSoftware) {
        this.numbSoftware = numbSoftware;
    }

    public int getNumbReferences() {
        return numbReferences;
    }

    public void setNumbReferences(int numbReferences) {
        this.numbReferences = numbReferences;
    }

    public StringProperty getObjective() {
        return objective;
    }

    public boolean hasChanges() {
        return changes;
    }

    public ArrayList<ArrayList<StringProperty>> getEducation() {
        return education;
    }

    public ArrayList<ArrayList<StringProperty>> getExperience() {
        return experience;
    }

    public ArrayList<ArrayList<StringProperty>> getAchievements() {
        return achievements;
    }

    public ArrayList<ArrayList<StringProperty>> getCommunity() {
        return community;
    }

    public ArrayList<ArrayList<StringProperty>> getReferences() {
        return references;
    }

    public ArrayList<StringProperty> getQualifications() {
        return qualifications;
    }

    public ArrayList<StringProperty> getHighlights() {
        return highlights;
    }

    public ArrayList<StringProperty> getLanguages() {
        return languages;
    }

    public ArrayList<StringProperty> getSoftware() {
        return software;
    }

    public String getXsl() {
        return xsl;
    }
}
