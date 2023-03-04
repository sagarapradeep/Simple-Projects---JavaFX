package lk.ijse.dep10.app.util;

import javafx.scene.control.ComboBox;
import lk.ijse.dep10.app.enums.CourseStatus;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private CourseStatus courseStatus;
    private ComboBox<String> cmbDepartment;
    private ArrayList<String> selectedModules;

    public Course() {
    }

    public Course(CourseStatus courseStatus, ComboBox<String> cmbDepartment,
                  ArrayList<String> selectedModules) {
        this.courseStatus = courseStatus;
        this.cmbDepartment = cmbDepartment;
        this.selectedModules = selectedModules;
    }


    /*Getters*/
    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public ComboBox<String> getCmbDepartment() {
        return cmbDepartment;
    }

    public ArrayList<String> getSelectedModules() {
        return selectedModules;
    }



    /*Setters*/

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setCmbDepartment(ComboBox<String> cmbDepartment) {
        this.cmbDepartment = cmbDepartment;
    }

    public void setSelectedModules(ArrayList<String> selectedModules) {
        this.selectedModules = selectedModules;
    }
}
