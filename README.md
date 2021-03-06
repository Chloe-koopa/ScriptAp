# ScriptAp

### 类别
Bukkit插件，AttributePlus附属插件，依赖CustomNPCs模组。

### 功能
允许自定义可以执行NPC脚本的AttributePlus属性。

### 使用方法：

* 使用`/defineattr 函数名 属性类型`来定义一个脚本属性，
* 属性类型为DAMAGE，INJURED，RUNTIME，NULL四选一

|类型名称|作用|
|-|-|
|DAMAGE|攻击时触发|
|INJURED|被攻击时触发|
|RUNTIME|属性更新时触发|
|NULL|无效果，但是会被正常计算|

详情请看：https://ersha.gitbook.io/attributeplus/kai-fa-wen-dang-1/zhu-ce-xin-shu-xing-baseattribute

* 在NPC的forge脚本里，钩子函数名为`ap_函数名`。

* 例如你想定义一个`啊，真棒`，类型为`INJURED`的属性，
那么在控制台输入`/defineattr 啊，真棒 INJURED`，
随后在**forge脚本**内建立一个名为`ap_啊，真棒`的钩子函数，
该函数的参数为本插件中的`AttributeEvent`。

* 如果钩子函数名不符合JavaScript语法（本例中为`ap_C++真棒`），那么使用
```javascript
this["ap_C++真棒"] = function(e) {...}
```
来添加钩子函数
