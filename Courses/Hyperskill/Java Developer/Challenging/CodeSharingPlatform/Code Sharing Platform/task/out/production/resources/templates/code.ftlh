<html>
    <head>
        <title>Code</title>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.2.0/build/styles/base16/darcula.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.2.0/build/highlight.min.js"></script>
        <script>hljs.highlightAll();</script>
    </head>

    <body>
        <span id="load_date">${code.formattedDate}</span>
        <pre id="code_snippet">
            <code class="language-java">${code.code}</code>
        </pre>
    </body>
</html>