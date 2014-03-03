## 这是什么？

给朋友的移动互联网创业产品做的后台，这个产品旨在为某小区提供类大众点评的功能。

## 为什么开源?  

产品无疾而终，由于代码写的漂亮，技术用的合理，所以发挥一下这个产品最后的光和热吧。

## 都有什么？

REST,Spring Roo,Spring Security,Spring MVC,MVC Unit Test,Redis和整洁的代码(前端菜鸟，community-mgr部分直接略过)。


### TODO
#### api feature

1. query category children 	DONE
2. query merchant detail	DONE
3. add merchant				DONE
4. software update api		DONE
5. feedback					DONE
6. add order to category	DONE
7. add complete user info api	DONE
8. add score avg and socre user count to merchant detail api.		DONE
9. modify password		DONE
10. sms related			DONE
11. audit merchant		DONE
12. user login and auth		DONE
13. add realname authorization when post merchant feedback	DONE
14. add software feedback api.	DONE
15. add device info to User model	DONE
16. add report merchant error api.	DONE
17. add user createtime realnameauth createtime.	DONE
18. add description to software update api.	DONE
19. find password api.		DONE
20. my merchant list api.	DONE
21. user store api.		DONE
22. keep consistent on page request param(start).	DONE
23. keep consistent on userAuthStatus response.		DONE
24. add real name auth info to my info api.		DONE
25. feedback order.		DONE

#### mgr feature
2. could add image id when create category.	DONE
3. made platform iamge type field to column box.
4. lazy load grid data.
5. could update category		DONE
6. add order to pageable api.		DONE
7. user list real name auth status doesn't appear.	DONE
8. could remove merchant on merchant list.	DONE
9. delete error report when delete merchant.	DONE
10. remove page from category list.		DONE

#### performance and security
1. add compress	
2. add encrypt
3. add caching to load user interface		working on it
4. password encrypt		DONE
5. add test for merchant feedback.

#### other
1. api doc			DONE
2. more merchant list test	working on
3. refactor api representation to keep consistency	DONE
4. create PageService		DONE
5. add log			DONE
6. run test first when deploy	DONE
7. add code quality tool
