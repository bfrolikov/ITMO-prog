<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Submissions</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
            <#-- @ftlvariable name="u" type="ru.itmo.web.lesson4.model.User" -->
                <tr>
                    <td>${u.id}</td>
                    <td><@c.userlink u false></@></td>
                    <td>${u.handle}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
