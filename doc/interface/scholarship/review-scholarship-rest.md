## 审核奖学金接口
+ 功能说明：审核奖学金接口，审核通过发放退款发奖学金，审核拒绝标识；

### 历史记录

#### 2019-12-06 
- 新增

**公共参数:**
+ 测试接口的地址为 http://squirrel-admin-api-test.ivykid.com/scholarship/review-scholarship
+ 线上接口的地址为 https://squirrel-cms-api.ivykid.com/scholarship/review-scholarship
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:------------------------------------------|
|功能	     	| 审核奖学金									|
|接口名称		|/scholarship/review-scholarship			|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    							|
|:------------------|:------|:----------|:----------------------------------------------|
|ids				|String	|	是 		|重新发放奖学金的id集合，逗号分隔列：1,2,3 			|
|status				|Integer|	是 		| 4,审核通过,发放成功; 5,审核拒绝; 				|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
"code": 0,
"data": null,
"success": true,
"denied": false,
"message": ""
}
```

**返回值描述**  

```
content:错误描述
```