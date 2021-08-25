<html>
    <head>
        <title>Latest</title>
        <meta charset="UTF-8"/>
        <link rel="stylesheet"
               href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
        <script>hljs.initHighlightingOnLoad();</script>
    </head>

    <body>
        <#list snippets as code>
        <div>
            <span class="load_date">${code.formattedDate}</span>
            <pre class="code_snippet">
                <code class="language-java">${code.code}</code>
            </pre>
        </div>
        </#list>
    </body>
</html>