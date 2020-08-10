# ScriptAp

### 类别
Bukkit插件，AttributePlus附属插件，依赖CustomNPCs模组。

### 功能
允许自定义可以执行NPC脚本的AttributePlus属性。

### 使用方法：

使用`/defineattr 函数名 属性`类型来定义一个脚本属性，
在NPC的forge脚本里，钩子函数名为`ap_函数名`。

例如你想定义一个`啊，真棒`，类型为`INJURED`的属性，
那么在控制台输入`/defineattr 啊，真棒 INJURED`，
随后在**forge脚本**内建立一个名为`ap_啊，真棒`的钩子函数，
该函数的参数为本插件中的`AttributeEvent`。

如果钩子函数名不符合JavaScript语法（本例中为`C++真棒`），那么使用
```javascript
this["C++真棒"] = function(e) {...}
```
来添加钩子函数
