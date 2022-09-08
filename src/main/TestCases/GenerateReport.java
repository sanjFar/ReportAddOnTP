package main.TestCases;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.tests.WebTest;
import io.testproject.java.sdk.v2.tests.helpers.WebTestHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.*;
import static org.apache.commons.io.FileUtils.writeStringToFile;



@Action(name = "Generate Report and send emails")
public class GenerateReport implements WebTest {

    @Parameter(defaultValue = "M", description = "please enter comma separated emails", direction = ParameterDirection.INPUT)
    String mailingList = "sanjay.sharma@getfareye.com";

    @Parameter(defaultValue = "M", description = "enter comma separated JobIDs ", direction = ParameterDirection.INPUT)
    String jobIDs="QnjlWbfyAk6vgMPbDgk6zQ";

    @Parameter(defaultValue = "M", description = "enter ProjectID", direction = ParameterDirection.INPUT)
    String projectID ="yjRsn-VsuUue7Atjd8ayJg";

    @Parameter(defaultValue = "M", description = "please enter your authorization token", direction = ParameterDirection.INPUT)
    String AuthToken = "6u7noyaUoTR28l0T3N68gDk2-PVsuxMoq5Fo7URQW_U1";

    StringBuffer resultJsonFinal3 = new StringBuffer();
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    Set<Report> set = new HashSet<>();
    int jobCount = 0;
    String resultJson="";
    String resultJsonFinal;
    String resultJsonFinal4;
    int totalCasesCount = 0;
    int passedCasesCount = 0;
    int skippedCasesCount =0;
    int failedCasesCount = 0;
    String jobName = "";
    String platform = "";
    public static String htmlText;
    public static String htmlText2;
    ObjectMapper m = new ObjectMapper();
    Response response;
    Report report;

    @Override
    public ExecutionResult execute(WebTestHelper helper) throws FailureException  {

        htmlText2 ="<html>"+
                "<head>"+
                "    <style>"+
                "table.customTable {"+
                "  width: 40%;"+
                "  background-color: #FFFFFF;"+
                "  border-collapse: collapse;"+
                "  border-width: 2px;"+
                "  border-color: #7EA8F8;"+
                "  border-style: solid;"+
                "  color: #000000;"+
                "}"+
                ""+
                "table.customTable td, table.customTable th {"+
                "  border-width: 2px;"+
                "  border-color: #7EA8F8;"+
                "  border-style: solid;"+
                "  padding: 5px;"+
                "}"+
                ""+
                "table.customTable thead {"+
                "  background-color: #7EA8F8;"+
                "}"+
                "</style>"+
                ""+
                "</head>"+
                ""+
                "<table class=\"customTable\">"+
                "    <thead>"+
                "    <tr>"+
                "        <th>Results</th>"+
                "        <th>TC Count</th>"+
                "    </tr>"+
                "    </thead>"+
                "    <tbody>"+
                "    <tr>"+
                "        <td style=\"background-color:#FFFFFF\">Total Test Cases</td>"+
                "        <td>#TOTAL#</td>"+
                "    </tr>"+
                "    <tr>"+
                "        <td style=\"background-color:#16a085\">Passed</td>"+
                "        <td>#PASSCOUNT#</td>"+
                "    </tr>"+
                "    <tr>"+
                "        <td style=\"background-color:#FF0000\">Failed</td>"+
                "        <td>#FAILCOUNT#</td>"+
                "    </tr>"+
                ""+
                "    <tr>"+
                "        <td style=\"background-color:#BEBEBE\">Skipped</td>"+
                "        <td>#SKIPCOUNT#</td>"+
                "    </tr>"+
                ""+
                "    </tbody>"+
                "</table>"+
                "</html>";
         htmlText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head>    <meta http-equiv=\"content-type\" content=\"application/xhtml+xml; charset=gbk\" />" +
                "<script type=\"text/javascript\" src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js\"></script>"+
                "<script type=\"text/javascript\">"+
                ""+
                "$(document).ready(function() {"+
                "      $(\"td\").css(\"background-color\", \"white\");"+
                "   });"+
                "   "+
                "   $(document).ready(function(){"+
                "    $(\"td:contains('Failed')\").css('background-color','red');"+
                "});"+
                ""+
                "$(document).ready(function(){"+
                "    $(\"td:contains('Passed')\").css('background-color','green');"+
                "});"+
                ""+
                "</script>"+
                ""+
                "</head>"+
                "<body>"+
                "<p style=\"text-align:center\"><span style=\"color:#16a085\"><strong>TEST REPORT</strong></span></p>"+
                ""+
                "<p style=\"margin-left:40px\"></p>"+
                ""+
                "<table id = \"dataTable\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:1142px\">"+
                "    <tbody>"+
                "    <tr>"+
                "        <td style=\"text-align:center; width:617px\">"+
                "            <h3><strong><span style=\"color:#000000\">Name</span></strong></h3>"+
                "        </td>"+
                "        <td style=\"text-align:center; width:200px\">"+
                "            <h3><strong><span style=\"color:#000000\">Execution Start</span></strong></h3>"+
                "        </td>"+
//                "        <td style=\"text-align:center; width:140px\">"+
//                "            <h3><strong><span style=\"color:#000000\">Execution End</span></strong></h3>"+
//                "        </td>"+
                "        <td style=\"text-align:center; width:129px\">"+
                "            <h3><strong><span style=\"color:#000000\">Duration</span></strong></h3>"+
                "        </td>"+
                "        <td class = \"resultData\" style= \"text-align:center; width:114px\">"+
                "            <h3><strong><span style=\"color:#000000\">Result</span></strong></h3>"+
                "        </td>"+
                "    </tr>"+
                "    #DATA#"+
                "    </tbody>"+
                "</table>"+
                ""+
                ""+
                ""+
                ""+
                "</body>"+
                "</html>";

        String json = "{\"name\":\"WB-15924: To verify that the pop up is appearing on deleting the mobile page\",\"executionStart\":\"2022-06-16T06:49:54.899Z\",\"executionEnd\":\"0001-01-01T00:00:00Z\",\"duration\":\"00:01:43.702\",\"result\":\"Passed\"}";

        RequestSpecification http_req = RestAssured.given().header("Authorization",AuthToken);
        String[] jobIDData = jobIDs.split(",");

        for(String job: jobIDData) {
            response = http_req.get("https://api.testproject.io/v2/projects/" + projectID.trim() + "/jobs/" + job.trim() + "/reports/latest");
            System.out.println("Response code " + response.getStatusCode());
            if(response.getStatusCode()==200){
            jobCount = jobCount + 1;
            totalCasesCount = totalCasesCount + response.jsonPath().getInt("passedTests") + response.jsonPath().getInt("failedTests") + response.jsonPath().getInt("skippedTests");
            passedCasesCount = passedCasesCount + response.jsonPath().getInt("passedTests");
            failedCasesCount = failedCasesCount + response.jsonPath().getInt("failedTests");
            skippedCasesCount = skippedCasesCount + response.jsonPath().getInt("skippedTests");

            try {
                for (int i = 0; i < response.jsonPath().getList("testResults.name").size(); i++) {
                    report = m.readValue(json, Report.class);
                    report.setName(response.jsonPath().getString("testResults[" + i + "].name"));
                    report.setExecutionStart(response.jsonPath().getString("testResults[" + i + "].executionStart"));
//                    report.setExecutionEnd(response.jsonPath().getString("testResults[" + i + "].executionEnd"));
                    report.setDuration(response.jsonPath().getString("testResults[" + i + "].duration"));
                    report.setResult(response.jsonPath().getString("testResults[" + i + "].result"));
                    set.add(report);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }}

        try {
            resultJson = ow.writeValueAsString(set)+",";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        resultJsonFinal = resultJson.replaceAll("\\[", "").replaceAll("\\]","");
        resultJsonFinal3.append("["+resultJsonFinal+"]");
        resultJsonFinal4 = resultJsonFinal3.deleteCharAt(resultJsonFinal3.length()-2).toString();
        System.out.println(resultJsonFinal4);
            System.out.println("Report Generator");
            JSONArray jsonArray = new JSONArray(resultJsonFinal4);
            JSONObject node;

            String colData = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                node = jsonArray.getJSONObject(i);
                String col = "<tr>";
                col += "<td>" + node.getString("name") + "</td>";
                col += "<td>" + node.getString("executionStart") + "</td>";
//                col += "<td>" + node.getString("executionEnd") + "</td>";
                col += "<td>" + node.getString("duration") + "</td>";
                col += "<td>" + node.getString("result") + "</td>";
                col += "</tr>";
                colData += col;
            }

            System.out.println("col data "+colData);
            htmlText = htmlText.replace("#DATA#", colData);
            htmlText2 = htmlText2.replace("#TOTAL#",String.valueOf(totalCasesCount));
            htmlText2 = htmlText2.replace("#PASSCOUNT#",String.valueOf(passedCasesCount));
            htmlText2 = htmlText2.replace("#FAILCOUNT#",String.valueOf(failedCasesCount));
            htmlText2 = htmlText2.replace("#SKIPCOUNT#",String.valueOf(skippedCasesCount));

        try {
            this.getClass().getResource("src/main/reporter/htmlrepo.html");
            this.getClass().getResource("src/main/reporter/ResultData.html");
            writeStringToFile(new File("src/main/reporter/htmlrepo.html"), htmlText);
            writeStringToFile(new File("src/main/reporter/ResultData.html"),htmlText2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Total count of test cases = "+totalCasesCount);
            SendEmail em = new SendEmail();
            em.composeMail("TestProject Results","src/main/reporter/ResultData.html",mailingList);
        return ExecutionResult.PASSED;
    }

}
