<html>
    <head>
        <title>Code</title>
        <meta charset="UTF-8"/>
        <link rel="stylesheet"
               href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
        <script>hljs.initHighlightingOnLoad();</script>
    </head>

    <body>
        <span id="load_date">${code.formattedDate}</span><br>

        <#if code.viewsRestricted>
            <span id="views_restriction">${code.views}</span> more views allowed.<br>
        </#if>

        <#if code.timeRestricted>
            The code will be available for <span id="time_restriction">${code.time}</span> seconds<br>
        </#if>

        <pre id="code_snippet">
            <code class="language-java">${code.code}</code>
        </pre>
    </body>
</html>