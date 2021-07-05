package com.shaksham.model.PojoData;

import java.io.Serializable;
import java.util.List;

public class BaselineDonePojo implements Serializable {

    public static BaselineDonePojo baselineDonePojo=null;



    public static synchronized void getInstance() {
        if (baselineDonePojo == null)
           baselineDonePojo=new BaselineDonePojo();


    }
    List<Block> blocks;


    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

   public  static class Block
   {
       String blockName;
       String blockCode;
       List<Gp> gps;

       public List<Gp> getGps() {
           return gps;
       }

       public void setGps(List<Gp> gps) {
           this.gps = gps;
       }

       public String getBlockName() {
           return blockName;
       }

       public void setBlockName(String blockName) {
           this.blockName = blockName;
       }

       public String getBlockCode() {
           return blockCode;
       }

       public void setBlockCode(String blockCode) {
           this.blockCode = blockCode;
       }


public static class Gp
{
    String gpName;
    String gpCode;
    List<Village> villages;

    public List<Village> getVillages() {
        return villages;
    }

    public void setVillages(List<Village> villages) {
        this.villages = villages;
    }


    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }

    public String getGpCode() {
        return gpCode;
    }

    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }

    public  static class Village
    {
        String villageName;
        String villageCode;
        List<Shgs> shgs;

        public List<Shgs> getShgs() {
            return shgs;
        }

        public void setShgs(List<Shgs> shgs) {
            this.shgs = shgs;
        }

        public String getVillageName() {
            return villageName;
        }

        public void setVillageName(String villageName) {
            this.villageName = villageName;
        }

        public String getVillageCode() {
            return villageCode;
        }

        public void setVillageCode(String villageCode) {
            this.villageCode = villageCode;
        }

        public static  class Shgs
        {
            String shgName;
            String shgCode;

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


        }
    }

}


   }
}
