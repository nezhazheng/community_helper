package com.communityhelper.software;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "image")
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "platform")
    private String platform;
    @Column(name = "url")
    private String url;
    @Enumerated(EnumType.STRING)
    private ImageType type;
    
    public enum ImageType{
        LaunchImage,
        CategoryImage
    }

    public static Image findImageByTypeAndPlatform(ImageType launchimage,
            String platform) {
        try {
            Image image = entityManager().createQuery("select o from Image o where o.type = :type and o.platform = :platform"
                    , Image.class)
                    .setParameter("type", launchimage)
                    .setParameter("platform", platform)
                    .getSingleResult();
            return image;
        } catch (EmptyResultDataAccessException empty) {
            return null;
        }
    }
}
