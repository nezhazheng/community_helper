### 接口约定

* 所有请求HTTP METHOD都是POST，交互数据格式为JSON

* 所有请求Http Content-Type=application/json

* 所有接口返回属性命名方式都采用驼峰标识

* 所有未标明Optional的参数都一定需要传递

* 用户登录后的所有请求都在url后面加上服务器端登录接口返回的token参数

* 接口权限验证失败返回http status 401

* 所有接口都需要传递如下数据

```json
{
	version;
    channel;			// 问后台channel号是多少
    platform;			// ios or android
    phonenum;
    imei;				// 手机唯一标识
    communityId;		// 小区ID
}
```

* URL中可添加如下可选参数

compress=1: 服务器端的response content会采用gzip方式进行压缩(开发中)

encrypt=1: 客户端request body是否加密(加密方式待定，开发中)

* 所有服务器端返回reponse模板如下

```json
{
    "status": "000",
    "message": "查询成功",
    "result": null		// 接口特有的返回数据都放在result中
}
```

#### Status状态码

SUCCESS("000", "成功"), 

ERROR("999", "系统错误"), 

USER_NOT_FOUND("001", "用户不存在"), 

PASSWORD_ERROR("002", "用户密码错误"), 

USER_ALREADY_EXISTS("003", "用户已经存在"),

WAIT_TO_AUTH("004", "实名认证审核中"),

ALREADY_AUTH("005", "实名认证已审核"),

NOT_HAVE_RECORD("999", "没有找到数据"), 

ALREADY_FEEDBACK("101","用户已经留言"), 

TOO_MANY_VALID_CODE("004", "验证码发送次数超出3次上限"), 

VALID_CODE_NOT_VALID("005", "验证码校验未通过"), 

SEND_SMS_ERROR("006", "发送短信失败");

### API

#### 软件升级

request

URL:/software/launch

```json
{
  "version": "3.12.0.1",
  "channel": "5",
  "platform": "ios"
}
```

response

```json
{
    "status": "000",
    "message": "查询成功",
    "result": {
        "upgrade": true,			// 是否需要提示升级, 当此参数为false时，									// updateSoftware为null
        "updateSoftware": {
            "id": 1,
            "platform": "ios",
            "version": "4.0.0",
            "channel": "5",
            "enableUpgrade": true,
            "upgradeDesc": "happy",	//升级描述
            "updateURL": "http://localhost/image/ii.png"
        },
        "launchImage": {
            "id": 1,
            "platform": "ios",
            "url": "http://localhost/image/i.png",
            "type": "LaunchImage"
        }
    }
}
```

#### 类别列表

request

URL:/category/{categoryId}

* 返回的数据中类别和商户都有可能包含,不返回子类别数据

categoryId: 父类别ID			Optional	Default:0(根级类别)

start: 分页开始位置，从1开始	Optional	Default:1

size: 获取多少项数据			Optional	Default:10

```json
{
  "start": 1,
  "size": 5
}
```

response

```json
{
    "status": "000",
    "message": "查询成功",
    "result": {
        "pageIndex": 1,
        "maxResult": 20,
        "totalResult": 4,
        "list": [
            {
                "id": 1,
                "categoryId": 0,
                "name": "维修商",
                "score": 0,
                "iconURL": "http://localhost/image/i.png",  // 类别对应图片
                "isCategory": true,			// 是否是类别
                "order": 1						// 后台会排好序，可以忽略
            },
            {
                "id": 1,
                "categoryId": 0,
                "name": "大李维修商",
                "score": 5,						// 平均评分
                "iconURL": null,
                "isCategory": false,
                "order": 1
            },
            {
                "id": 2,
                "categoryId": 0,
                "name": "物业服务商",
                "score": 0,
                "iconURL": null,
                "isCategory": true,
                "order": 2
            },
            {
                "id": 2,
                "categoryId": 0,
                "name": "小红物业服务商",
                "score": null,
                "iconURL": null,
                "isCategory": false,
                "order": 2
            }
        ],
        "currentPageNo": 1,
        "totalPage": 1
    }
}
```

#### 用户注册

request

URL:/user

```json
{
  "phonenum": "13311008877",
  "password": "123456",
  "realName": "hehehe",			// Optional
  "address":  "上地"				// Optional
}
```

response

```json
{
    "status": "000",				// USER_ALREADY_EXISTS is possible
    "message": "注册成功",
    "result": {
        "id": 2,
        "phonenum": "13311008877",
        "password": "123456",
        "realName": "hehehe",
        "address": "上地"
    }
}
```

#### 用户登录

request

URL:/user/authenticate

```json
{
  "phonenum": "13311008877",
  "password": "123456"
}
```
response

```json
{
    "status": "000",			// USER_NOT_FOUND PASSWORD_ERROR is possible
    "message": "用户登录成功",
    "result": {
        "id": 1,
        "phonenum": "admin",
        "password": "111111",
        "realName": null,
        "address": null,
        "token": "xxx",
        "id": 1				// User ID
    }
}
```

#### 商户详情

request

URL:/merchant/{merchantId}

* 请求中的分页是反馈列表的分页

```json
{
  "platform": "android",
  "communityId": 1,
  "userId": 3,			// current user id
  "start": 0,
  "size": 2
}
```

response

```json
{
    "status": "000",
    "message": "查询成功",
    "result": {
        "merchant": {			//商户详情
            "id": 1,
            "categoryId": 0,
            "name": "大李维修商",
            "contactPhoneNumber": "11111222",
            "contactAddress": "海上海花园",
            "score": 5,					// 评分
            "scoreUserCount": 1			// 评分人数
            "status": "VALID",			// VALID:审核,NOT_VALID:未审核
            "order": 3,
            "collected": true			// true 代表已收藏 false代表未收藏
        },
        "feedbackList": {		//商户反馈列表
            "pageIndex": 0,
            "maxResult": 4,
            "totalResult": 1,
            "list": [
                {
                    "id": {
                        "userId": 1,
                        "merchantId": 1
                    },
                    "message": "感觉非常好",
                    "phonenum": "1329999999",	    // 用户手机号
                    "createDate": 1385345646000,  // 留言时间，根据需要进行格式化
                    "score": 5
                }
            ],
            "currentPageNo": 1,
            "totalPage": 1
        }
    }
}
```

#### 用户留言

request

URL:/merchant/{merchantId}/feedback

```json
{
  "message": "感觉非常好",
  "userId": 1,
  "score": 5			// 星星评分，可以选择1-5分
}
```

response

```json
{
    "status": "100",		// ALREADY_FEEDBACK 代表已经留言过了
    "message": "留言成功",
    "result": null
}
```

#### 完善用户信息

request

* 完善用户信息和修改密码等可以使用此接口，按需传参数即可

URL:/user/{id}/update

```json
{
  "address": "更新地址了",
  "realName": "呵呵呵呵",
  "password": "123456"
}
```

response

```json
{
    "status": "000",
    "message": "完善成功",
    "result": null
}
```

#### 修改密码

request

URL:/user/{id}/modifypassowrd

```json
{
  "oldPassword]": "111111",
  "password": "123456"
}
```

response

```json
{
    "status": "000",
    "message": "修改密码成功",
    "result": {
        "version": null,
        "channel": null,
        "platform": null,
        "phonenum": null,
        "imei": null,
        "communityId": null,
        "id": null,
        "password": "123456",
        "oldPassword": "111111",
        "realName": null,
        "address": null,
        "token": "MTM4NjM0MjQ2ODM2NTo1MDk0ZTU1NTY4OjEzMjk5OTk5OTk9OGM5MzkyMTRlNGM5N2NhOWNhMDRmYmE5ZWFmOWNlYTBjNTllOGY2NzllMWY2ZTI1MWFhZjg3MGUxMGQyZDE5NDUzOGYwZTc1ODQ4NTdiNWQ6MDRjNjI0ZTAyYjNlZTNiMjYzODE3MjgyZjhlZDUxYzAwOWY4ZDg5NGFiMjMwNDBkZTA0ZTQ5OTVmOWI2ZWNlYzM2NWQ1MjY2OGE1NjljNWQwYzVlMzhlYjZjZDAxNDdkOTExNTA3NGY3OTRlZTE4YjZjMTU0NDAyNDYyYzA1NDc="	// 使用新token 进行后续请求
    }
}
```


#### 实名认证

request

URL:/user/{id}/realnameauth

```json
{
  "address": "更新地址了",
  "realName": "呵呵呵呵"
}
```

response

```json
{
    "status": "000",		// WAIT_TO_AUTH & ALREADY_AUTH is possible.
    "message": "实名认证成功",
    "result": null
}
```

#### 标准类别查询

request

URL:/category/standard

response

```json
{
    "status": "000",
    "message": "查询成功",
    "result": [
        {
            "id": 1,
            "name": "维修"
        },
        {
            "id": 2,
            "name": "保洁"
        }
    ]
}
```

#### 添加商户

request

URL:/merchant

```json
{
  "name": "测试商户",
  "contactPhoneNumber": "123456789",
  "contactAddress": "呵呵呵呵",
  "desc": "测试描述",
  "standardCategoryId": 1				// 标准类别ID
}
```

response

```json
{
    "status": "000",
    "message": "添加商户成功",
    "result": null
}
```

#### 修改商户

request

URL:/merchant/{merchantId}/update

```json
{
  "name": "测试商户",
  "contactPhoneNumber": "123456789",
  "contactAddress": "呵呵呵呵",
  "desc": "测试描述",
  "standardCategoryId": 1
}
```

response

```json
{
    "status": "000",
    "message": "修改成功",
    "result": null
}
```

#### 商户报错

request

URL:/merchant/{merchantId}/reporterror

```json
{
  "platform": "android",
  "userId": 3,									// 报错用户ID
  "errorCategory": "ADDRESS_ERROR",			// ADDRESS_ERROR or PHONE_ERROR
  "communityId": 1
}
```

response

```json
{
    "status": "000",
    "message": "商户报错操作成功",
    "result": null
}
```

#### 我的信息

request

URL:/user/{userId}/my

response

```json
{
    "status": "000",
    "message": "我的信息查询成功",
    "result": {
        "user": {
            "id": 1,
            "phonenum": "admin",
            "password": "111111",
            "realName": "呵呵呵呵",
            "address": "更新地址了",
            "channel": null,
            "imei": null,
            "realNameAuth": "HAS_NOT_AUTH"
        },
        "merchants": [
            {
                "id": 3,
                "categoryId": 1,
                "userId": 1,
                "communityId": 1,
                "name": "测试商户",
                "contactPhoneNumber": "123456789",
                "contactAddress": "呵呵呵呵",
                "description": "测试描述",
                "score": 0,
                "scoreUserCount": 0,
                "status": "VALID",
                "order": 3,
                "collected": false
            },
            {
                "id": 4,
                "categoryId": 1,
                "userId": 1,
                "communityId": 1,
                "name": "测试用户认证商户",
                "contactPhoneNumber": "123456789",
                "contactAddress": "呵呵呵呵",
                "description": "测试用户认证商户描述",
                "score": 0,
                "scoreUserCount": 0,
                "status": "VALID",
                "order": 4,
                "collected": false
            }
        ]
    }
}```

#### 添加或取消收藏

* 添加取消都是使用此API，如果已添加则设置取消

request

URL:/merchant/{merchantId}/collection

```json
{
  "userId": 3,
  "communityId": 1
}
```

response

```json
{
    "status": "000",
    "message": "添加收藏成功",
    "result": null
}
```

#### 我的收藏

request

URL:/user/{userId}/merchantcollection

```json
{
  "platform": "android",
  "communityId": 1
}
```

response

```json
{
    "status": "000",
    "message": "我的收藏查询成功",
    "result": [
        {
            "id": 1,
            "categoryId": 0,
            "userId": null,
            "communityId": 1,
            "name": "大李维修商",
            "contactPhoneNumber": "11111222",
            "contactAddress": "海上海花园",
            "description": null,
            "score": 5,
            "scoreUserCount": 1,
            "status": "VALID",
            "order": 3,
            "collected": false
        }
    ]
}
```

#### 用户认证商户

request

URL:/user/{userId}/merchant/auth

```json
{
  "name": "测试商户",
  "contactPhoneNumber": "123456789",
  "contactAddress": "呵呵呵呵",
  "desc": "测试描述",
  "standardCategoryId": 1				// 标准类别ID
}
```

response

```json
{
    "status": "000",
    "message": "添加商户成功",
    "result": null
}
```

#### 我要反馈

request

URL:/software/user/{userId}/feedback

```json
{
  "platform": "ios",
  "version": "4.0.0",
  "channel": "5",
  "message": "测试反馈"	
}
```

response

```json
{
    "status": "000",
    "message": "反馈成功",
    "result": null
}
```

#### 发送验证码

request

URL:/sms/{phonenum}/sendvalidcode

response

```json
{
    "status": "000",
    "message": "验证码发送成功",
    "result": null
}
```

#### 校验验证码

request

URL:/sms/{phonenum}/valid/{validCodeStr}

* validCodeStr  验证码

response

```json
{
    "status": "000",
    "message": "验证通过",
    "result": null
}
```

#### 找回密码

request

URL:/sms/{phonenum}/findpassword

response

```json
{
    "status": "000",				// USER_NOT_FOUND is possible.
    "message": "找回密码成功",
    "result": null
}
```
