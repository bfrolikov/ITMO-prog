<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="symbol" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<#macro arrow index symbol>
    <#if index gte 0 && index lt users?size>
        <a href="/user?handle=${users[index].handle}" class="active-arrow">${symbol}</a>
    <#else>
        <span class="inactive-arrow">${symbol}</span>
    </#if>
</#macro>

<@c.page>
    <#if handle?has_content>
        <#assign uIndex = c.findBy(users, "handle", handle)!/>
    <#else>
        <#assign uIndex = c.findById(users, user_id!)!/>
    </#if>
    <#if uIndex?has_content>
        <#assign u = users[uIndex]/>
    <#-- @ftlvariable name="u" type="ru.itmo.web.lesson4.model.User" -->
        <div class="user-profile">
            <div class="user-info">
                <h2><@c.userlink u false/></h2>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <#assign postCount = c.getPosts(u.id)?size>
                    <li><a href="posts?user_id=${u.id}">Posts: ${postCount}</a></li>
                </ul>
            </div>
            <img alt="User profile picture" src="/img/user-profile-picture.jpg">
        </div>
        <div class="user-navigation">
            <#assign
            nextIndex = uIndex + 1
            prevIndex = uIndex - 1/>
            <@arrow prevIndex "←"/>
            <@arrow nextIndex "→"/>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
