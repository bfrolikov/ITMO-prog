<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#-- @ftlvariable name="current_uri" type="java.lang.String" -->
<#macro menuItem item>
    <a href="${item.href}"
            <#if item.href?starts_with(current_uri)>
                class="active-link"
            </#if>
    >
        ${item.name}</a>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name":"Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <@menuItem item/>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <section>
                <div class="header">Post #${post.id}</div>
                <div class="body">
                    <@shortenedText post true/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro postArticle post shortened>
<#-- @ftlvariable name="post" type="ru.itmo.web.lesson4.model.Post" -->
<#-- @ftlvariable name="shortened" type="java.lang.Boolean" -->
    <article>
        <div class="title">${post.title}</div>
        <div class="information">By ${users[findBy(users,"id",post.userId)].handle}</div>
        <div class="body">
            <p>
                <@shortenedText post shortened/>
            </p>
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#macro userlink user nameOnly>
<#-- @ftlvariable name="user" type="ru.itmo.web.lesson4.model.User" -->
<#-- @ftlvariable name="nameOnly" type="java.lang.Boolean" -->
    <a href="/user?handle=${user.handle}"
            <#if !nameOnly>
                class="user-link-${user.color?lower_case}"
            </#if>
    >${user.name}</a>
</#macro>

<#macro shortenedText post shortened>
<#-- @ftlvariable name="post" type="ru.itmo.web.lesson4.model.Post" -->
<#-- @ftlvariable name="shortened" type="java.lang.Boolean" -->
    <#if !shortened || post.text?length lte 250>
        ${post.text}
    <#else>
        ${post.text[0..249]}&hellip;
    </#if>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item?index/>
        </#if>
    </#list>
</#function>

<#function getPosts userId>
    <#return posts?filter(p -> p.userId == userId)>
</#function>

<#function findById items id>
    <#if id?has_content && id?is_number>
        <#return findBy(items, "id", id)/>
    <#else>
        <#return ""/>
    </#if>
</#function>