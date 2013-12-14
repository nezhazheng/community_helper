package com.communityhelper.software;

import java.util.List;

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

import com.communityhelper.api.Page;
import com.communityhelper.mgr.feedback.Feedback;

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

    public static Page<Image> findAllImages(Integer start, Integer limit) {
        List<Image> images = entityManager().createQuery("from Image o", Image.class)
        .setFirstResult(start)
        .setMaxResults(limit)
        .getResultList();
        Page<Image> page = new Page<Image>();
        page.setList(images);
        page.setTotalResult(Integer.parseInt((Image.countImages() + "")));
        return page;
    }
}
