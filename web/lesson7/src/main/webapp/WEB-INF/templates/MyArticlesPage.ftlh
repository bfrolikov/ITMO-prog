<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>
<@c.page>
    <form class="my-articles" method="post" action="">
        <div class="datatable">
            <div class="caption">My articles</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Show/Hide</th>
                </tr>
                </thead>
                <tbody>
                <#list articles as article>
                    <tr>
                        <td>${article.id}</td>
                        <td>${article.title}</td>
                        <td>
                            <button class="visibility-button" data-Id="${article.id}"
                                    data-Hidden="${article.hidden?string}">
                                <#if article.hidden>
                                    Show
                                <#else>
                                    Hide
                                </#if>
                            </button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </form>
    <script>
        $(function () {
            $(".my-articles").submit(function () {
                return false;
            });
            $(".my-articles .visibility-button").click(function () {
                const $btn = $(this);
                const id = $btn.attr("data-Id");
                let hidden = $btn.attr("data-Hidden") === "true";
                ajax("updateHidden", {id, hidden: !hidden},
                    function (error) {
                        notify(error, "error");
                    },
                    function (response) {
                        $btn.attr("data-Hidden", response.hidden ? "true" : "false");
                        $btn.text(response.hidden ? "Show" : "Hide");
                    }
                );
            });
        })
    </script>
</@c.page>