package com.communityhelper.software.api.representation;

import com.communityhelper.software.Image;
import com.communityhelper.software.Software;

public class SoftwareLaunchDTO {
    private boolean upgrade;
    private Software updateSoftware;
    private Image launchImage;
    public Software getUpdateSoftware() {
        return updateSoftware;
    }
    public void setUpdateSoftware(Software updateSoftware) {
        this.updateSoftware = updateSoftware;
    }
    public Image getLaunchImage() {
        return launchImage;
    }
    public void setLaunchImage(Image launchImage) {
        this.launchImage = launchImage;
    }
    public boolean isUpgrade() {
        return upgrade;
    }
    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }
}
