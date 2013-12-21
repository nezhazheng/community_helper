package com.communityhelper.software.api;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.communityhelper.page.Page;
import com.communityhelper.software.Image;
import com.communityhelper.software.Image.ImageType;


@RequestMapping("/software")
@Controller
public class SoftwareController {
    @Value("${imageServerURL}")
    private String imageServerURL;
    
    @Value("${imageServerDir}")
    private String imageServerDir;
    
    /**
     * 添加图片
     * @return
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public 
    @ResponseBody
    String addImage(@RequestParam("file") MultipartFile file
            , @RequestParam("platform") String platform
            , @RequestParam("imageType") ImageType imageType) {
        Image image = new Image();
        image.setPlatform(platform);
        image.setUrl(imageServerURL + file.getOriginalFilename());
        image.setType(imageType);
        image.persist();
        try {
            file.transferTo(new File(imageServerDir + file.getOriginalFilename()));
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public 
    @ResponseBody
    Page<Image> images(@RequestParam Integer page, 
            @RequestParam Integer start, 
            @RequestParam Integer limit){
        return Image.findAllImages(start, limit);
    }
}
