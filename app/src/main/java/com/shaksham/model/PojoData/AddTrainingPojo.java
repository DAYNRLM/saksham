package com.shaksham.model.PojoData;
import java.io.Serializable;
import java.util.List;

public class AddTrainingPojo implements Serializable {

    public static AddTrainingPojo addTrainingPojo = null;

    public static synchronized void getInstance() {
        if (addTrainingPojo == null) {
            addTrainingPojo = new AddTrainingPojo();
        }
    }

    private String gpName;
    private String villageName;
    private String blockCode;
    private String gpCode;
    private String villageCode;
    private String blockName;
    private String triningId;
    private String subTotal;
    private String otherParticipant;
    private String selectedShgCount;
    private String totalMembers;
    private String gpsLoation;
    private byte[] image;

    public String getGpsLoation() {
        return gpsLoation;
    }

    public void setGpsLoation(String gpsLoation) {
        this.gpsLoation = gpsLoation;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(String totalMembers) {
        this.totalMembers = totalMembers;
    }

    private String trainingCreatedDate;
    private List<SelectedModulesList> selectedModulesList;
    private List<SelectedShgList> selectedShgList;


    public String getTriningId() {
        return triningId;
    }

    public void setTriningId(String triningId) {
        this.triningId = triningId;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getOtherParticipant() {
        return otherParticipant;
    }

    public void setOtherParticipant(String otherParticipant) {
        this.otherParticipant = otherParticipant;
    }

    public String getSelectedShgCount() {
        return selectedShgCount;
    }

    public void setSelectedShgCount(String selectedShgCount) {
        this.selectedShgCount = selectedShgCount;
    }

    public String getTrainingCreatedDate() {
        return trainingCreatedDate;
    }

    public void setTrainingCreatedDate(String trainingCreatedDate) {
        this.trainingCreatedDate = trainingCreatedDate;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getGpCode() {
        return gpCode;
    }

    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public List<SelectedModulesList> getSelectedModulesList() {
        return selectedModulesList;
    }

    public void setSelectedModulesList(List<SelectedModulesList> selectedModulesList) {
        this.selectedModulesList = selectedModulesList;
    }

    public List<SelectedShgList> getSelectedShgList() {
        return selectedShgList;
    }

    public void setSelectedShgList(List<SelectedShgList> selectedShgList) {
        this.selectedShgList = selectedShgList;
    }


    public static class SelectedModulesList {

        private String moduleName;
        private String modulecode;
        private String position;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public String getModulecode() {
            return modulecode;
        }

        public void setModulecode(String modulecode) {
            this.modulecode = modulecode;
        }
    }

    public static class SelectedShgList {
        private String shgName;
        private String shgCode;

        private List<ShgMemberList> shgMemberLists;

        public String getShgName() {
            return shgName;
        }

        public void setShgName(String shgName) {
            this.shgName = shgName;
        }

        public String getShgCode() {
            return shgCode;
        }

        public void setShgCode(String shgCode) {
            this.shgCode = shgCode;
        }

        public List<ShgMemberList> getShgMemberLists() {
            return shgMemberLists;
        }

        public void setShgMemberLists(List<ShgMemberList> shgMemberLists) {
            this.shgMemberLists = shgMemberLists;
        }

        public static class ShgMemberList {
            private String memberName;
            private String memberCode;

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getMemberCode() {
                return memberCode;
            }

            public void setMemberCode(String memberCode) {
                this.memberCode = memberCode;
            }
        }
    }
}

