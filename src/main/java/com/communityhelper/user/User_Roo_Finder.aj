// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.user;

import com.communityhelper.user.User;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect User_Roo_Finder {
    
    public static TypedQuery<User> User.findUsersByMobileEquals(String mobile) {
        if (mobile == null || mobile.length() == 0) throw new IllegalArgumentException("The mobile argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.mobile = :mobile", User.class);
        q.setParameter("mobile", mobile);
        return q;
    }
    
}
