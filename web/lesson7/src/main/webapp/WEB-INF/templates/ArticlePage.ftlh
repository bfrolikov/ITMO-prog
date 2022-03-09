<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#import "commons.ftlh" as c>
<@c.page>
    <div class="article form-box">
        <div class="header">New article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="newArticle"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title" value="${title!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text"> ${text!}</textarea>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>
    <script>
        $(function () {
            $(".article form").submit(function () {
                const $title = $(this).find("input[name='title']");
                const $text = $(this).find("textarea[name='text']");
                const $error = $(this).find(".error");
                ajax("newArticle", {title: $title.val(), text: $text.val()},
                    function (error) {
                        $error.text(error);
                    }
                );
                return false;
            });
        });
    </script>
</@c.page>