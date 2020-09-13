## 学习记录接口
+ 功能说明：学习记录

### 历史记录

#### 2019-10-21 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/user/user-record-list
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/user/user-record-list
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service				|
|:--------------|:------------------------------|
|功能	     	| 学习记录						|
|接口名称		|/user/user-record-list			|
|请求方法		|POST					    	|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|
|nickName			|String	|是		  	|昵称或openId							|
|startTime			|String |是		  	|开始时间,yyyy-MM-dd						|
|endTime			|String	|是		  	|结束时间,yyyy-MM-dd						|
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
        "pageTotal": 13,
        "list": [
            {
                "id": 100118,
                "openId": "of3HB006agkRK-07REUEjDJjtkJw",
                "unionId": null,
                "nickName": "一本正经地胡说八道",
                "levelName": "四级英语",
                "levelId":1000005,
                "sex": 1,
                "headImgUrl": "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKGVWwZB0Ewdpfk1YxMlh6EmFEwhG0kDkiaS8picNa6lv3W335zDtOiagQLniaZQI48IXaM5L0er6caWg/132",
                "alreadyStudyDays": 3,
                "alreadyFinishDays": 0,
                "alreadyShareDays": 0,
                "alreadySendDays": 0,
                "pageNo": 0,
                "pageSize": 0,
                "beginDate":
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