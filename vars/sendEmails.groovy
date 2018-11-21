
def call(Map args) {
    def body = '<j:jelly xmlns:j="jelly:core" xmlns:st="jelly:stapler" xmlns:d="jelly:define">\n' +
            '  \n' +
            '  <STYLE>\n' +
            '    BODY, TABLE, TD, TH, P {\n' +
            '    font-family:Verdana,Helvetica,sans serif;\n' +
            '    font-size:11px;\n' +
            '    color:black;\n' +
            '    }\n' +
            '    h1 { color:black; }\n' +
            '    h2 { color:black; }\n' +
            '    h3 { color:black; }\n' +
            '    TD.bg1 { color:white; background-color:#0000C0; font-size:120% }\n' +
            '    TD.bg2 { color:white; background-color:#4040FF; font-size:110% }\n' +
            '    TD.bg3 { color:white; background-color:#8080FF; }\n' +
            '    TD.test_passed { color:blue; }\n' +
            '    TD.test_failed { color:red; }\n' +
            '    TD.test_filtered { color:orange; }\n' +
            '    TD.output { font-family:Courier New;font-size:0%;}        \n' +
            '    TD.build_log { font-family:Courier New;}        \n' +
            '    TR.failed_row:hover {background-color:lightgray;}           \n' +
            '    TR.failed_row:hover + TR.output_row TD.output{font-size:100%;}                \n' +
            '  </STYLE>\n' +
            '  <BODY>\n' +
            '    <j:set var="spc" value="&amp;nbsp;&amp;nbsp;" />\n' +
            '    \n' +
            '    \n' +
            '    <!-- GENERAL INFO -->\n' +
            '    \n' +
            '    <TABLE>\n' +
            '      <TR><TD align="right">\n' +
            '          <j:choose>\n' +
            '            <j:when test="${build.result==\'SUCCESS\'}">\n' +
            '              <IMG SRC="${rooturl}static/e59dfe28/images/32x32/blue.gif" />\n' +
            '            </j:when>\n' +
            '            <j:when test="${build.result==\'FAILURE\'}">\n' +
            '              <IMG SRC="${rooturl}static/e59dfe28/images/32x32/red.gif" />\n' +
            '            </j:when>\n' +
            '            <j:otherwise>\n' +
            '              <IMG SRC="${rooturl}static/e59dfe28/images/32x32/yellow.gif" />\n' +
            '            </j:otherwise>\n' +
            '          </j:choose>\n' +
            '      </TD><TD valign="center"><B style="font-size: 200%;">BUILD ${build.result}</B></TD></TR>\n' +
            '      <TR><TD>Build URL</TD><TD><A href="${rooturl}${build.url}">${rooturl}${build.url}</A></TD></TR>\n' +
            '      <TR><TD>Project:</TD><TD>${project.name}</TD></TR>\n' +
            '      <TR><TD>Date of build:</TD><TD>${it.timestampString}</TD></TR>\n' +
            '      <TR><TD>Build duration:</TD><TD>${build.durationString}</TD></TR>\n' +
            '    </TABLE>\n' +
            '    <BR/>\n' +
            '    \n' +
            '    \n' +
            '    <!-- CHANGE SET -->\n' +
            '    \n' +
            '    <j:set var="changeSet" value="${build.changeSet}" />\n' +
            '    <j:if test="${changeSet!=null}">\n' +
            '      <j:set var="hadChanges" value="false" />\n' +
            '      <TABLE width="100%">\n' +
            '        <TR><TD class="bg1" colspan="2"><B>Changes</B></TD></TR>\n' +
            '        <j:forEach var="cs" items="${changeSet}" varStatus="loop">\n' +
            '          <j:set var="hadChanges" value="true" />\n' +
            '          <j:set var="aUser" value="${cs.hudsonUser}"/>\n' +
            '          <TR>\n' +
            '            <TD colspan="2" class="bg2">${spc}Revision <B>${cs.commitId?:cs.revision?:cs.changeNumber}</B> by\n' +
            '              <B>${aUser!=null?aUser.displayName:cs.author.displayName}: </B>\n' +
            '              <B>(${cs.msgAnnotated})</B>\n' +
            '            </TD>\n' +
            '          </TR>\n' +
            '          <j:forEach var="p" items="${cs.affectedFiles}">\n' +
            '            <TR>\n' +
            '              <TD width="10%">${spc}${p.editType.name}</TD>\n' +
            '              <TD>${p.path}</TD>\n' +
            '            </TR>\n' +
            '          </j:forEach>\n' +
            '        </j:forEach>\n' +
            '        <j:if test="${!hadChanges}">\n' +
            '          <TR><TD colspan="2">No Changes</TD></TR>\n' +
            '        </j:if>\n' +
            '      </TABLE>\n' +
            '      <BR/>\n' +
            '    </j:if>\n' +
            '    \n' +
            '    \n' +
            '    <!-- ARTIFACTS -->\n' +
            '    \n' +
            '    <j:set var="artifacts" value="${build.artifacts}" />\n' +
            '    <j:if test="${artifacts!=null and artifacts.size()&gt;0}">\n' +
            '      <TABLE width="100%">\n' +
            '        <TR><TD class="bg1"><B>Build Artifacts</B></TD></TR>\n' +
            '        <TR>\n' +
            '          <TD>\n' +
            '            <j:forEach var="f" items="${artifacts}">\n' +
            '              <li>\n' +
            '                <a href="${rooturl}${build.url}artifact/${f}">${f}</a>\n' +
            '              </li>\n' +
            '            </j:forEach>\n' +
            '          </TD>\n' +
            '        </TR>\n' +
            '      </TABLE>\n' +
            '      <BR/>  \n' +
            '    </j:if>\n' +
            '    \n' +
            '    <!-- Test reporting TEMPLATE (references to JUnit refer to the JUnit style XML Output produced by the MATLAB TestRunner) -->     \n' +
            '    <j:set var="resultList" value="${it.JUnitTestResult}" />\n' +
            '    <j:if test="${resultList.isEmpty()!=true}">\n' +
            '      <TABLE width="100%">\n' +
            '        <j:forEach var="testResult" items="${resultList}">\n' +
            '          <j:set var="failCount" value="${failCount + testResult.getFailCount()}"/>\n' +
            '          <j:set var="skipCount" value="${skipCount + testResult.getSkipCount()}"/>\n' +
            '          <j:set var="passCount" value="${passCount + testResult.getPassCount()}"/>\n' +
            '        </j:forEach>\n' +
            '        <TR><TD class="bg1" colspan="2"><B>MATLAB Test Suite Summary:</B> ${passCount+failCount+skipCount} Ran, ${failCount} Failed, ${passCount} Passed, ${skipCount} Filtered</TD></TR>\n' +
            '        \n' +
            '        <TR><TD class="bg1" colspan="2"><B>Failed Tests</B></TD></TR>\n' +
            '        <j:forEach var="testResult" items="${resultList}">\n' +
            '          <j:forEach var="packageResult" items="${testResult.getChildren()}">\n' +
            '            <j:forEach var="failed_test" items="${packageResult.getFailedTests()}">\n' +
            '              <TR class="failed_row" bgcolor="white"><TD class="test_failed" colspan="2"><B><li>Failed: ${failed_test.getFullName()} </li></B></TD></TR>\n' +
            '              <TR class="output_row"><TD class="output"><PRE>${failed_test.getErrorStackTrace()}</PRE></TD></TR>\n' +
            '            </j:forEach>\n' +
            '          </j:forEach> \n' +
            '        </j:forEach>\n' +
            '        \n' +
            '        <TR><TD class="bg1" colspan="2"><B>Filtered Tests</B></TD></TR>\n' +
            '        <j:forEach var="testResult" items="${resultList}">\n' +
            '          <j:forEach var="packageResult" items="${testResult.getChildren()}">\n' +
            '            <j:forEach var="filtered_test" items="${packageResult.getSkippedTests()}">\n' +
            '              <TR bgcolor="white"><TD class="test_filtered" colspan="2"><B><li>Filtered: ${filtered_test.getFullName()} </li></B></TD></TR>\n' +
            '            </j:forEach>  \n' +
            '          </j:forEach>\n' +
            '        </j:forEach>\n' +
            '      </TABLE>\t\n' +
            '      <BR/>\n' +
            '    </j:if>\n' +
            '\n' +
            '    <!-- CONSOLE OUTPUT -->\n' +
            '      <TABLE width="100%" cellpadding="0" cellspacing="0">\n' +
            '        <TR><TD class="bg1"><B>Build Log Tail (Full Log Attached)</B></TD></TR>\n' +
            '        <j:forEach var="line" items="${build.getLog(100)}"><TR><TD class="build_log">${line}</TD></TR></j:forEach>\n' +
            '      </TABLE>\n' +
            '      <BR/>\n' +
            '    \n' +
            '  </BODY>\n' +
            '</j:jelly>'
    emailext body: body, recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test', to: 'josiah@sunshower.io'
}

