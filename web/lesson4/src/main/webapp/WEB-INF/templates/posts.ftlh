<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id?has_content && user_id?is_number>
        <#assign currentPosts = c.getPosts(user_id)>
        <#if currentPosts?has_content>
            <#list currentPosts as post>
                <@c.postArticle post true/>
            </#list>
        <#else>
            <p>No posts</p>
        </#if>
    <#else>
        <p>No posts</p>
    </#if>
</@c.page>