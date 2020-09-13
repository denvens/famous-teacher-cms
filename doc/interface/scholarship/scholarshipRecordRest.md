## 查询搜索奖学金申请记录接口
+ 功能说明：查询搜索奖学金申请记录

### 历史记录

#### 2019-08-25 
- 新增

**公共参数:**
+ 测试接口的地址为 http://squirrel-admin-api-test.ivykid.com/scholarship/apply-for-record
+ 线上接口的地址为 https://squirrel-cms-api.ivykid.com/scholarship/apply-for-record
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service									|
|:--------------|:--------------------------------------------------|
|功能	     	| 奖学金规则列表页									|
|接口名称		|/scholarship/apply-for-record						|
|请求方法		|POST					    						|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    							|
|:------------------|:------|:----------|:----------------------------------------------|
|nickName	   		|String |	否	  	|昵称			  								|
|openId		   		|String |	否	  	|openId 		  								|
|levelId	   		|Integer|	否	  	|频道id	      	  								|
|status			   	|Integer|	否		|奖学金申请状态: 3,已申请,审核中; 4,审核通过,发放成功; 5,审核拒绝;  	|
|refundStatus		|Integer|	否		|奖学金退款状态: 0,待退款; 1,已退款; 2,退款失败;  	|
|beginAtStartTime	|String	|	否 		|购买开始日期	       如:"2019-07-09"					|
|pageNo		   		|Integer|	否 		|页码		        							|
|pageSize	   		|Integer|	否		|页面容量         									|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code":0,
    "data":{
        "total":1,
        "list":[
            {
                "nickName":"Ssss**",										// 昵称
                "openid":"o1uQGv9lQHdHcZrmBln90qkbiQnE",					// 昵称
                "levelName":"Level 1魔法森林",								// level名称
                "status":3,													//奖学金申请状态: 3,审核中; 4,审核通过,发放成功; 5,审核拒绝; 
                "refundStatus":0,											//奖学金退款状态: 0,待退款; 1,已退款; 2,退款失败; 
                "beginClassTime":"2019-08-18",								// 开课批次
                "learnDay":8,												// 学习天数
                "makeUpLearnDay":15,										// 补学天数
                "createdAt":"2019-08-24 13:43:45",							// 申请日期						
                "updatedAt":"2019-08-24 13:43:47",							// 通过时间
                "amount":150,												// 金额
                "bigbayTranctionId":7										// 订单号 
            }
        ]
    },
    "success":true,
    "denied":false,
    "message":""
}
```

**返回值描述**  

```
content:错误描述
```