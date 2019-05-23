<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
</br>
<table>

    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>零花钱</td>
    </tr>
    <#if stus??>
        <#list stus as stu>
            <tr>
                <td>${stu_index+1}</td>
                <td <#if stu.name == '小明'>style="background:red;"</#if>>${stu.name}</td>
                <td>${stu.age}</td>
                <td>${stu.money}</td>
            </tr>
        </#list>
    </#if>

    遍历map
    <#list stuMap?keys as s>
        <tr>
            <td>${s_index+1}</td>
            <td>${stuMap[s].name}</td>
            <td>${stuMap[s].age}</td>
            <td>${stuMap[s].money}</td>
        </tr>
    </#list>
</table>

</body>
</html>