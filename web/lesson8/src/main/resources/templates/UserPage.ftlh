<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <#if displayedUser??>
        <div class="user-profile">
            <div class="user-info">
                <h2>${displayedUser.login}</h2>
                <ul>
                    <li>Id: ${displayedUser.id}</li>
                    <li>Login: ${displayedUser.login}</li>
                    <li>Creation time: ${displayedUser.creationTime}</li>
                </ul>
            </div>
            <img alt="User profile picture" src="<@spring.url '/img/user-profile-picture.jpg'/>">
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
