<html>
    <head>
        <title>Create</title>
        <meta charset="UTF-8"/>
        <style>#code_snippet{width:400px;height:400px}</style>
        <script>function send(){let e={code:document.getElementById("code_snippet").value},t=JSON.stringify(e),n=new XMLHttpRequest;n.open("POST","/api/code/new",!1),n.setRequestHeader("Content-type","application/json; charset=utf-8"),n.send(t),200==n.status&&alert("Success!")}</script>
    </head>

    <body>
        <textarea id="code_snippet"> // write your code here </textarea><br>
        <button id="send_snippet" type="submit" onclick="send()">Submit</button>
    </body>
</html>