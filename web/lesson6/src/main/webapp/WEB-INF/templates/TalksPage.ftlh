<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talkViews" type="ru.itmo.wp.model.domain.TalkView[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box message-form-box">
        <div class="header">New message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendMessage"/>
                <div class="field">
                    <div class="name">
                        <label for="text">Message:</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text"></textarea>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="target">Send message to:</label>
                    </div>
                    <div class="value">
                        <select id="target" name="targetUserLogin">
                            <#list users as user>
                                <option>${user.login}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <@c.displayError/>
                <div class="button-field">
                    <input type="submit" value="Send Message">
                </div>
            </form>
        </div>
    </div>
    <div class="datatable">
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list talkViews as talk>
                <tr>
                    <td>${talk.id}</td>
                    <td>${talk.sourceUserLogin}</td>
                    <td>${talk.targetUserLogin}</td>
                    <td>${talk.text}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>