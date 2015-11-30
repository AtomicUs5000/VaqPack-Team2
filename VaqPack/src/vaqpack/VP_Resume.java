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
    private final ArrayList<ArrayList<StringProperty>>
            education,
            experience,
            achievements,
            community,
            references;
    private final ArrayList<StringProperty>
            qualifications,
            highlights,
            languages,
            software;
    private final ArrayList<ArrayList<String>>
            educationStored,
            experienceStored,
            achievementsStored,
            communityStored,
            referencesStored;
    private final ArrayList<String>
            qualificationsStored,
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
        for (int i = 0; i < 9; i ++) {
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
    protected void revert() {
        objective.setValue(objectiveStored);
        for (int i = 0; i < 9; i++) {
            for (int ii = 0; ii < 6; ii++) {
                education.get(i).get(ii).setValue(educationStored.get(i).get(ii));
                references.get(i).get(ii).setValue(educationStored.get(i).get(ii));
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
    protected void save() {
        changes = false;
        completedObjective = false;
        completedEducation = false;
        completedWorkExperience = false;
        completedHighlights = false;
        completedSoftware = false;
        completedResume = false;
        startedResume = false;
        
        if ((objectiveStored != null && !objectiveStored.equals(objective.getValue())) || 
                (objectiveStored == null && objective.getValue() != null) ) {
            objectiveStored = objective.getValue();
            changes = true;
        }
        
        // check for completeness
        if (objectiveStored != null && !objectiveStored.equals("")) {
            startedResume = true;
            completedObjective = true;
        }
        if (completedObjective && completedEducation && completedWorkExperience 
                && completedHighlights && completedSoftware) {
            startedResume = true;
            completedResume = true;
        }
        if (completedResume && changes) {
            generateXLS();
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
