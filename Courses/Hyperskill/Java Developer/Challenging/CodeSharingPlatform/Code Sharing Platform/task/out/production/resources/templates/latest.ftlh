<html>
    <head>
        <title>Latest</title>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.2.0/build/styles/base16/darcula.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.2.0/build/highlight.min.js"></script>
        <script>hljs.highlightAll();</script>
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