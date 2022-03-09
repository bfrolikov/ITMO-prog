<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.postArticle post true/>
    </#list>
</@c.page>