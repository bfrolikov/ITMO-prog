<#-- @ftlvariable name="post_id" type="java.lang.Long" -->

<#import "./common.ftlh" as c>

<@c.page>
    <#assign postIndex = c.findById(posts, post_id!)!/>
    <#if postIndex?has_content>
        <@c.postArticle posts[postIndex] false/>
    <#else>
        <p>No such post</p>
    </#if>
</@c.page>