## 奖学金申请记录下载接口
+ 功能说明：奖学金申请记录下载

### 历史记录

#### 2019-12-06 
- 新增

**公共参数:**
+ 测试接口的地址为 http://squirrel-admin-api-test.ivykid.com/scholarship/apply-for-record-excel
+ 线上接口的地址为 https://squirrel-cms-api.ivykid.com/scholarship/apply-for-record-excel
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service									|
|:--------------|:--------------------------------------------------|
|功能	     	| 奖学金申请记录下载									|
|接口名称		|/scholarship/apply-for-record-excel				|
|请求方法		|GET					    						|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    							|
|:------------------|:------|:----------|:----------------------------------------------|
|nickName	   		|String |	否	  	|昵称			  								|
|openId		   		|String |	否	  	|openId 		  								|
|levelId	   		|Integer|	否	  	|频道id	      	  								|
|status			   	|Integer|	否		|奖学金申请状态: 3,已申请,审核中; 4,审核通过,发放成功; 5,审核拒绝;  	|
|refundStatus		|Integer|	否		|奖学金退款状态: 0,待退款; 1,已退款; 2,退款失败;  	|
|beginAtStartTime	|String	|	否 		|购买开始日期	       如:"2019-07-09 12:30:12"			|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code":0,
    "success":true,
    "denied":false,
    "message":""
}
```

**返回值描述**  

```
content:错误描述
```