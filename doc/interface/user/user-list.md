## 会员列表接口
+ 功能说明：会员列表

### 历史记录

#### 2019-10-14 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/user/purchase-record
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/user/purchase-record
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 会员列表					|
|接口名称		|/user/purchase-record		|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|
|nickName			|Integer|是		  	|昵称									|
|subscribe			|Integer|是		  	|0 未关注 1 已关注							|
|isVip				|Integer|是		  	|0 非会员 1 会员							|
|beginAt			|Integer|是		  	|2019-10-14,2019-11-12					|
|vipBeginTime		|String |是		  	|2019-10-01,2019-10-14					|
|vipEndTime			|String |是		  	|2019-10-01,2019-11-11					|
|pageNo				|Integer|是		  	|1，当前页								|
|pageSize			|Integer|是		  	|10，页面容量								|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code": 0,
    "data": {
        "pageTotal": 8,
        "list": [
             {
                "id": 241,
                "squirrelUserId": 0,
                "levelId": 1000012,
                "transacationId": 0,
                "createdAt": 1551781069000,
                "beginAt": "2019-02-25",
                "vipBeginTime": "2019-02-25",
                "vipEndTime": "2019-03-10",
                "effectiveDate": 0,
                "pageNo": 0,
                "pageSize": 0,
                "findParam": null,
                "nickName": "绝响",
                "openId": "of3HB03l42FmfNp1_kL2Qsfi59vA",
                "headImgUrl": "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIibCsia4B22kmGUAN7bNMgicNlxHvGxFNM1raC0SBQnfgbbDuibj3Aeqqe4wdM0ZdyD5eNF21lrnMQcg/132"
            },
            {
                "id": 266,
                "squirrelUserId": 0,
                "levelId": 1000013,
                "transacationId": 0,
                "createdAt": 1552013447000,
                "beginAt": "2019-03-04",
                "vipBeginTime": "2019-02-25",
                "vipEndTime": "2019-03-10",
                "effectiveDate": 0,
                "pageNo": 0,
                "pageSize": 0,
                "findParam": null,
                "nickName": "绝响",
                "headImgUrl": "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIibCsia4B22kmGUAN7bNMgicNlxHvGxFNM1raC0SBQnfgbbDuibj3Aeqqe4wdM0ZdyD5eNF21lrnMQcg/132"
            }
        ]
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