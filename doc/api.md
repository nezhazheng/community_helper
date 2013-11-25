
### 接口约定

* 所有请求HTTP METHOD都是POST，交互数据格式为JSON

* 所有请求Http Content-Type=application/json

* 所有接口返回属性命名方式都采用驼峰标识

* 所有未标明Optional的参数都一定需要传递

* 用户登录后的所有请求都在url后面加上服务器端登录接口返回的token参数

* 所有接口都需要传递如下数据

```json
{
	version;
    channel;
    platform;
    phonenum;
    imei;
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
    "result": null
}
```

#### Status状态码

USER_NOT_FOUND("001", "用户不存在"), 

PASSWORD_ERROR("002", "用户密码错误"), 

USER_ALREADY_EXISTS("003", "用户已经存在"),

NOT_HAVE_RECORD("999", "没有找到数据"), 

SUCCESS("000", "成功"), 

ALREADY_FEEDBACK("101","用户已经留言"),

ERROR("999", "系统错误");

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

URL:/category/{categoryId}?start=0&size=20

* 返回的数据中类别和商户都有可能包含

categoryId: 父类别ID			Optional	Default:0(根级类别)

start: 分页开始位置			Optional	Default:0

size: 父类别ID			Optional	Default:10


response

```json
{
  "status": "000",
  "message": "查询成功",
  "result": {
    "pageIndex": 0,
    "maxResult": 10,
    "totalResult": 4,
    "list": [
      {
        "id": 3,				// 类别模型
        "parentId": 1,
        "name": "餐饮"
      },
      {
        "id": 4,
        "parentId": 1,
        "name": "超市"
      },
      {
        "id": 3,				// 商户模型
        "categoryId": 1,
        "name": "小毛物业服务商",
        "contactPhoneNumber": null,
        "contactAddress": null,
        "score": null			评分
      },
      {
        "id": 4,
        "categoryId": 1,
        "name": "馄饨店",
        "contactPhoneNumber": null,
        "contactAddress": null,
        "score": null
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

response

```json
{
    "status": "000",
    "message": "查询成功",
    "result": {
        "id": 1,
        "categoryId": 0,
        "name": "大李维修商",
        "contactPhoneNumber": "11111222",
        "contactAddress": "海上海花园",
        "score": 5
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
