## 获取管理员信息接口
+ 功能说明：获取管理员信息

### 历史记录

#### 2019-10-18 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/admin/info
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/admin/info
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 获取管理员信息				|
|接口名称		|/admin/info				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|id					|Integer|是		  	|主键id	      	  						|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code":0,
    "data":{
        "id":1000065,
        "loginName":"hkx",
        "password":"84a9f4deccb1e2cadb45610ef16908b3",
        "userName":"hkx",
        "plain":"PqKiZ4",
        "roleName":"instructor,oper"
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