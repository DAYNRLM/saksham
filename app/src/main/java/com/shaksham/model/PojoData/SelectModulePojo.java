package com.shaksham.model.PojoData;

public class SelectModulePojo {
    private boolean isSelected;
    private String moduleName;
    private String moduleCode;
    private String position;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
               this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
