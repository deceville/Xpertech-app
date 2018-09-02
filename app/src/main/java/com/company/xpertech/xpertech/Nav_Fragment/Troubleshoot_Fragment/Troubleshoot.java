package com.company.xpertech.xpertech.Nav_Fragment.Troubleshoot_Fragment;

/**
 * Created by Skylar Gail on 8/18/2018.
 */

public class Troubleshoot {
    private String instruct;
    private String img;

    public Troubleshoot(String instruct, String img) {
        this.instruct = instruct;
        this.img = img;
    }

    public String getInstruct() {
        return instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
