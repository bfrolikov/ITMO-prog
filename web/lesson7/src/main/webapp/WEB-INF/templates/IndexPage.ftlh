<#import "commons.ftlh" as c>

<@c.page>
    <ul class="articles">
        <li class="no-data"></li>
        <template>
            <li>
                <article>
                    <div class="title"></div>
                    <div class="information">By <span class="author"></span>, <span class="creation-time"></span></div>
                    <div class="body"></div>
                    <div class="footer">
                        <div class="left">
                            <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                            <span class="positive-score">+173</span>
                            <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                        </div>
                        <div class="right">
                            <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                            <span class="creation-time"></span>
                            <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                            <a href="#">68</a>
                        </div>
                    </div>
                </article>
            </li>
        </template>
    </ul>

    <script>
        $(function () {
            const $articles = $(".articles");
            $articles.find(".no-data").text("Loading articles...");
            ajax("findAllVisibleOrderByCreationTime", {},
                function (error) {
                },
                function (response) {
                    $articles.find(".no-data").remove();
                    response.articles.forEach(function (article) {
                        const $tpl = $($articles.find("template").prop("content")).clone();
                        $tpl.find(".title").text(article.title);
                        $tpl.find(".body").text(article.text);
                        $tpl.find(".author").text(article.userLogin);
                        $tpl.find(".creation-time").text(article.creationTime.toString());
                        $articles.append($tpl);
                    });
                },
                "GET", "index"
            );
        });
    </script>
</@c.page>
