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
 * FILE ID 2300
 *-----------------------------------------------------------------------------*/
package vaqpack;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VP_Resume {
    private int
            themeId = -1,
            numbEducation = 1,
            numbExperience = 1,
            numbAchievements = 0,
            numbCommunity = 0,
            numbQualification = 1,
            numbHighlights = 1,
            numbLanguages = 1,
            numbSoftware = 1,
            numbReferences = 0,
            numbEducationStored = 1,
            numbExperienceStored = 1,
            numbAchievementsStored = 0,
            numbCommunityStored = 0,
            numbQualificationStored = 1,
            numbHighlightsStored = 1,
            numbLanguagesStored = 1,
            numbSoftwareStored = 1,
            numbReferencesStored = 0;
    private final VP_User owner;
    private boolean
            changes,
            completedObjective,
            completedEducation,
            completedWorkExperience,
            completedQualifications,
            completedHighlights,
            completedLanguages,
            completedSoftware,
            startedResume,
            completedResume;
    private final StringProperty
            objective;
    private String xsl,
            objectiveStored;
    private final ArrayList<StringProperty>
            education,
            experience,
            achievements,
            community,
            qualifications,
            highlights,
            languages,
            software,
            references;
    private final ArrayList<String>
            educationStored,
            experienceStored,
            achievementsStored,
            communityStored,
            qualificationsStored,
            highlightsStored,
            languagesStored,
            softwareStored,
            referencesStored;
    
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
        
        for (int i = 0; i < 9; i ++) {
            educationStored.add("");
            experienceStored.add("");
            achievementsStored.add("");
            communityStored.add("");
            qualificationsStored.add("");
            highlightsStored.add("");
            languagesStored.add("");
            softwareStored.add("");
            referencesStored.add("");
            
            education.add(new SimpleStringProperty());
            experience.add(new SimpleStringProperty());
            achievements.add(new SimpleStringProperty());
            community.add(new SimpleStringProperty());
            qualifications.add(new SimpleStringProperty());
            highlights.add(new SimpleStringProperty());
            languages.add(new SimpleStringProperty());
            software.add(new SimpleStringProperty());
            references.add(new SimpleStringProperty());
        }
    }
    
    /*------------------------------------------------------------------------*
     * revert()
     * - Reverts bound properties to the last stored value
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void revert() {
        objective.setValue(objectiveStored);
        for (int i = 0; i < 9; i++) {
            education.get(i).setValue(educationStored.get(i));
            experience.get(i).setValue(experienceStored.get(i));
            achievements.get(i).setValue(achievementsStored.get(i));
            community.get(i).setValue(communityStored.get(i));
            qualifications.get(i).setValue(qualificationsStored.get(i));
            highlights.get(i).setValue(highlightsStored.get(i));
            languages.get(i).setValue(languagesStored.get(i));
            software.get(i).setValue(softwareStored.get(i));
            references.get(i).setValue(referencesStored.get(i));
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
    protected void save() {
        changes = false;
        completedObjective = false;
        completedEducation = false;
        completedWorkExperience = false;
        completedHighlights = false;
        completedSoftware = false;
        completedResume = false;
        startedResume = false;
        
        
        
        generateXLS();
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
        numbAchievements = 0;
        numbCommunity = 0;
        numbQualification = 1;
        numbHighlights = 1;
        numbLanguages = 1;
        numbSoftware = 1;
        numbReferences = 0;
        numbEducationStored = 1;
        numbExperienceStored = 1;
        numbAchievementsStored = 0;
        numbCommunityStored = 0;
        numbQualificationStored = 1;
        numbHighlightsStored = 1;
        numbLanguagesStored = 1;
        numbSoftwareStored = 1;
        numbReferencesStored = 0;
        for (int i = 0; i < 9; i ++) {
            educationStored.set(i, "");
            experienceStored.set(i, "");
            achievementsStored.set(i, "");
            communityStored.set(i, "");
            qualificationsStored.set(i, "");
            highlightsStored.set(i, "");
            languagesStored.set(i, "");
            softwareStored.set(i, "");
            referencesStored.set(i, "");
            
            education.get(i).setValue("");
            experience.get(i).setValue("");
            achievements.get(i).setValue("");
            community.get(i).setValue("");
            qualifications.get(i).setValue("");
            highlights.get(i).setValue("");
            languages.get(i).setValue("");
            software.get(i).setValue("");
            references.get(i).setValue("");
        }
    }
    
    /*------------------------------------------------------------------------*
     * generateXLS()
     * - Creates the xml stylesheet to be passed to html conversion.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    private void generateXLS() {
        
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/

    protected boolean hasStartedResume() {
        return startedResume;
    }

    protected boolean hasCompletedResume() {
        return completedResume;
    }

    protected boolean hasCompletedObjective() {
        return completedObjective;
    }

    protected boolean hasCompletedEducation() {
        return completedEducation;
    }

    protected boolean hasCompletedWorkExperience() {
        return completedWorkExperience;
    }

    protected boolean hasCompletedQualifications() {
        return completedQualifications;
    }

    protected boolean hasCompletedHighlights() {
        return completedHighlights;
    }

    protected boolean hasCompletedLanguages() {
        return completedLanguages;
    }

    protected boolean hasCompletedSoftware() {
        return completedSoftware;
    }

    protected int getThemeId() {
        return themeId;
    }

    protected void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    protected int getNumbEducation() {
        return numbEducation;
    }

    protected void setNumbEducation(int numbEducation) {
        this.numbEducation = numbEducation;
    }

    protected int getNumbExperience() {
        return numbExperience;
    }

    protected void setNumbExperience(int numbExperience) {
        this.numbExperience = numbExperience;
    }

    protected int getNumbAchievements() {
        return numbAchievements;
    }

    protected void setNumbAchievements(int numbAchievements) {
        this.numbAchievements = numbAchievements;
    }

    protected int getNumbCommunity() {
        return numbCommunity;
    }

    protected void setNumbCommunity(int numbCommunity) {
        this.numbCommunity = numbCommunity;
    }

    protected int getNumbQualification() {
        return numbQualification;
    }

    protected void setNumbQualification(int numbQualification) {
        this.numbQualification = numbQualification;
    }

    protected int getNumbHighlights() {
        return numbHighlights;
    }

    protected void setNumbHighlights(int numbHighlights) {
        this.numbHighlights = numbHighlights;
    }

    protected int getNumbLanguages() {
        return numbLanguages;
    }

    protected void setNumbLanguages(int numbLanguages) {
        this.numbLanguages = numbLanguages;
    }

    protected int getNumbSoftware() {
        return numbSoftware;
    }

    protected void setNumbSoftware(int numbSoftware) {
        this.numbSoftware = numbSoftware;
    }

    protected int getNumbReferences() {
        return numbReferences;
    }

    protected void setNumbReferences(int numbReferences) {
        this.numbReferences = numbReferences;
    }

    protected StringProperty getObjective() {
        return objective;
    }

    protected boolean hasChanges() {
        return changes;
    }
}
