package com.communityhelper.software.api;

import static com.communityhelper.api.APIResponse.response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIRequest;
import com.communityhelper.api.APIResponse;
import com.communityhelper.software.Image;
import com.communityhelper.software.Image.ImageType;
import com.communityhelper.software.Software;
import com.communityhelper.software.api.representation.SoftwareLaunchDTO;

@RequestMapping("/software")
@Controller
public class SoftwareController {
    @RequestMapping(value = "/launch", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse launchInformation(@RequestBody APIRequest device) {
        Software currentSoftware = Software.findCurrentUpdateSoftware(device.getChannel(), device.getPlatform());
        Image launchImage = Image.findImageByTypeAndPlatform(ImageType.LaunchImage, device.getPlatform());
        
        SoftwareLaunchDTO dto = new SoftwareLaunchDTO();
        if (currentSoftware != null && currentSoftware.upgrade(device.getVersion())){
            dto.setUpgrade(true);
            dto.setUpdateSoftware(currentSoftware);
        }else {
            dto.setUpgrade(false);
        }
        dto.setLaunchImage(launchImage);
        
        return response().success("查询成功").result(dto);
    }
}
