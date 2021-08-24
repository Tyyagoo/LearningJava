<html>
    <head>
        <title>Latest</title>
        <meta charset="UTF-8"/>
    </head>

    <body>
        <#list snippets as code>
        <div>
            <span class="load_date">${code.formattedDate}</span>
            <pre class="code_snippet">${code.code}</pre>
        </div>
        </#list>
    </body>
</html>