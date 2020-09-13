## 查询购买记录信息接口
+ 功能说明：查询购买记录信息

### 历史记录

#### 2019-10-14 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/user/purchase-record-info
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/user/purchase-record-info
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service				|
|:--------------|:------------------------------|
|功能	     	| 查询购买记录信息				|
|接口名称		|/user/purchase-record-info		|
|请求方法		|POST					    	|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|
|openId				|String |是		  	|用户openId								|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code": 0,
    "data": {
        "id": 302,
        "squirrelUserId": 0,
        "levelId": 1000005,
        "transacationId": 0,
        "createdAt": 1552459783000,
        "beginAt": "2019-03-08",
        "vipBeginTime": "2019-03-08",
        "vipEndTime": "2019-03-30",
        "effectiveDate": 0,
        "pageNo": 0,
        "pageSize": 0,
        "findParam": null,
        "nickName": "C++",
        "headImgUrl": "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLzIqQ1ltSFpY6icnsYo3eoicP63lV3csAcOocAvQBHHGIt25wr86MSLzKwFWGic57pEfhUOicxs0mcqw/132",
        "openId": "of3HB0-g34_xdNfc6p6x2J7O6o_I",
        "userId":1111,
        "vipDays": null,
        "sendLessonCount": null,
        "alreadySendLessonDays":1
    },
    "success": true,
    "denied": false,
    "message": ""
}
```

**返回值描述**  

```
content:错误描述
```