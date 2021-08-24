package platform.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import platform.model.CodeSnippet;
import platform.service.CodeSnippetService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller("webCodeController")
public class CodeController {

    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    CodeSnippetService codeSnippetService;

    public CodeController() {
        headers.setContentType(MediaType.TEXT_HTML);
    }

    @GetMapping(path = "/code")
    public ResponseEntity<String> getCodeSnippet() {
        Optional<CodeSnippet> codeSnippet = codeSnippetService.getCodeSnippetById(0);

        String dateSpan = codeSnippet.isEmpty() ? "" :
                String.format("<span id=\"load_date\">%s</span>", codeSnippet.get().getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        String codePre = codeSnippet.isEmpty() ? "" :
                String.format("<pre id=\"code_snippet\">%s</pre>", codeSnippet.get().getCode());

        String html = "<html>" +
                            "<head>" +
                            "    <title>Code</title>" +
                            "</head>" +
                            "<body>" +
                                dateSpan +
                                codePre +
                            "</body>" +
                    "</html>";

        return ResponseEntity.ok()
                .headers(headers)
                .body(html);
    }

    @GetMapping(path = "/code/new")
    public ResponseEntity<String> getSetupPage() {
        String html = "<html>" +
                "<head>" +
                "    <title>Create</title>" +
                "    <script>function send(){let e={code:document.getElementById(\"code_snippet\").value},t=JSON.stringify(e),n=new XMLHttpRequest;n.open(\"POST\",\"/api/code/new\",!1),n.setRequestHeader(\"Content-type\",\"application/json; charset=utf-8\"),n.send(t),200==n.status&&alert(\"Success!\")}</script>" +
                "    <style>#code_snippet{width:400px;height:400px}</style>" +
                "</head>" +
                "<body>" +
                    "<textarea id=\"code_snippet\"> // write your code here </textarea>" +
                    "<br><br>" +
                    "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>" +
                "</body>" +
                "</html>";
        return ResponseEntity.ok()
                .headers(headers)
                .body(html);
    }
}
