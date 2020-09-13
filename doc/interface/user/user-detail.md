## 学习记录查看详情接口
+ 功能说明：学习记录查看详情

### 历史记录

#### 2019-10-21 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/user/user-detail
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/user/user-detail
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service				|
|:--------------|:------------------------------|
|功能	     	| 学习记录查看详情				|
|接口名称		|/user/user-detail				|
|请求方法		|POST					    	|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|
|userId				|Integer|是		  	|userId									|
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
        "totalNum": 13,
        "list": [
            {
                "study": "未学习",
                "sendDate": "2019-03-14",
                "firstUseTime":"58"         //单位:秒
            },
            {
                "study": "已学习",
                "sendDate": "2019-03-13",
                "firstUseTime":"65"
            },
       
            {
                "study": "已补学",
                "sendDate": "2019-03-05",
                "firstUseTime":"72"
            },
        ],
        "user": {
            "alreadyShareDaysAll": 2,
            "unionId": "ou20T5gF0_qMgQ_-j5KpcBe5w0I0",
            "headImgUrl": "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLzIqQ1ltSFpY6icnsYo3eoicP63lV3csAcOocAvQBHHGIt25wr86MSLzKwFWGic57pEfhUOicxs0mcqw/132",
            "subscribe": 1,
            "sendCourseDays": 29,
            "openId": "of3HB0-g34_xdNfc6p6x2J7O6o_I",
            "nickName": "C++",
            "sex": 2,
            "alreadyStudyDaysAll": 12,
            "id": 100122,
            "alreadyFinishAll": 1
        }
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