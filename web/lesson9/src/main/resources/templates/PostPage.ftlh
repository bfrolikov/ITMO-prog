<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->

<@c.page>
    <@c.post post=post link=false/>
    <#if user??>
        <div class="form comment-form">
            <div class="header">Write comment</div>
            <div class="body">
                <form method="post" action="<@spring.url '/post/${post.id}/comments'/>">
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text"></textarea>
                        </div>
                        <@c.error "commentForm.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write comment">
                    </div>
                </form>
            </div>
        </div>
    </#if>
    <div class="comments">
        <h2>Comments</h2>
        <ul>
            <#list post.comments as comment>
                <li><span class="username">${comment.user.login}:</span> ${comment.text}</li>
            </#list>
        </ul>
    </div>
</@c.page>
