def call(Map args) {
    emailext body: body, recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test', to: 'josiah@sunshower.io'
}

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
        '</j:jelly>\n' +
        '\n' +
        '           \n' +
        '\n' +
        'Get the MATLAB code Published with MATLAB® R2015b\n' +
        '\n' +
        'Category:  Continuous Integration\n' +
        ' \n' +
        '\n' +
        '\n' +
        '\n' +
        '< Format Sibling Rivalry  Open and extensible >\n' +
        'Related Content\n' +
        '\n' +
        '\n' +
        '\n' +
        '\n' +
        '\n' +
        '\n' +
        'You can follow any comments to this entry through the RSS 2.0 feed. You can leave a comment, or trackback from your own site.\n' +
        '\n' +
        '10 CommentsOldest to Newest\n' +
        'David Barry replied on November 18th, 2015 6:38 pm UTC : 1 of 10 Hi Andy, Thanks for another great Jenkins post. We\'ve just started using Jenkins and I don\'t know how we ever coped without it! Anyway, it would be good to see a post about how we can integrate the outputs from the MATLAB code coverage reports in to Jenkins.\n' +
        'Andy Campbell replied on November 18th, 2015 7:40 pm UTC : 2 of 10 Thanks David. Yes this is something we\'ve heard before and are looking into solutions. Currently the CodeCoveragePlugin is really useful for interactive MATLAB sessions and it doesn\'t fit as nicely into CI workflows.\n' +
        'Han Geerligs replied on November 24th, 2015 7:02 am UTC : 3 of 10 Hi Andy, Thanks for the post! Right now we\'re using TAP file output. You have any clues for transforming your Jelly script for handling TAP files? --Han\n' +
        'Andy Campbell replied on November 24th, 2015 4:47 pm UTC : 4 of 10 Hi Han, Yes I have some clues. Basically, you need the jelly script to connect with the actions that you get with the TAP Plugin. The email-ext plugin gives you some niceties when dealing with JUnit because that is what it knows. But I found you can still connect to the TAP information using the following simple script. From there you can take a look at the relevant JavaDoc and/or source code for both the jenkins TAPPlugin and tap4j itself. This should get you started:\n' +
        '<j:jelly xmlns:j="jelly:core" xmlns:st="jelly:stapler" xmlns:d="jelly:define">\n' +
        '  \n' +
        '  <BODY>\n' +
        '    <!-- Test reporting TEMPLATE for TAP -->     \n' +
        '    <j:set var="tapBuildAction" value="${it.getAction(\'org.tap4j.plugin.TapBuildAction\')}" />\n' +
        '    <j:set var="tapResult" value="${tapBuildAction.getResult()}" />\n' +
        '    <j:set var="tapTestSetMapList" value="${tapResult.getTestSets()}" /> \n' +
        '      <TABLE width="100%">\n' +
        '        <TR><TD><B>TAP Results</B></TD></TR>\n' +
        '        <j:forEach var="tapTestSetMap" items="${tapTestSetMapList}">\n' +
        '            <j:set var="tapTestSet" value="${tapTestSetMap.getTestSet()}" /> \n' +
        '            <j:set var="tapTestResults" value="${tapTestSet.getTestResults()}" /> \n' +
        '            <j:forEach var="tapTestResult" items="${tapTestResults}">\n' +
        '                <TR><TD colspan="2"><B><li>TAP Test Name: ${tapTestResult.getDescription()} </li></B></TD></TR>\n' +
        '            </j:forEach>\n' +
        '        </j:forEach>\n' +
        '      </TABLE>\t\n' +
        '      <BR/>\n' +
        '    \n' +
        '  </BODY>\n' +
        '</j:jelly>\n' +
        'That is just a simple "hello world" script showing how to connect your jelly script to the TAP objects. Once you have that you can add nice formatting and you can decide what information that is accessible from these objects you\'d like to show and how you would like to show it. Here is some relevant links to source and javadoc to help you navigate these. Let me know how you fare! https://github.com/jenkinsci/tap-plugin/tree/master/src/main/java/org/tap4j/plugin http://tap4j.sourceforge.net/apidocs/index.html\n' +
        'Marcel van der Linden replied on December 1st, 2015 1:34 pm UTC : 5 of 10 Hi Andy, Based on your example. I\'ve update the JUnit email template to a TAP template. Thanks for the starting point!\n' +
        '<j:jelly xmlns:j="jelly:core" xmlns:st="jelly:stapler" xmlns:d="jelly:define">\n' +
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
        '    <j:set var="artifacts" value="${build.artifacts}" />\n' +
        '    <j:if test="${artifacts!=null and artifacts.size()&gt;0}">\n' +
        '      <TABLE width="100%">\n' +
        '        <TR><TD class="bg1"><B>Build Artifacts</B></TD></TR>\n' +
        '\t\t<j:forEach var="f" items="${artifacts}">\n' +
        '        <TR>\n' +
        '          <TD>\n' +
        '                <a href="${rooturl}${build.url}artifact/${f}">${f}</a>\n' +
        '          </TD>\n' +
        '        </TR>\n' +
        '\t\t</j:forEach>\n' +
        '      </TABLE>\n' +
        '      <BR/>  \n' +
        '    </j:if>\n' +
        '\n' +
        '\n' +
        '\t<!-- TAP summary -->\n' +
        '\t\n' +
        '    <j:set var="tapBuildAction" value="${it.getAction(\'org.tap4j.plugin.TapBuildAction\')}" />\n' +
        '    <j:set var="tapResult" value="${tapBuildAction.getResult()}" />\n' +
        '    <j:set var="tapTestSetMapList" value="${tapResult.getTestSets()}" /> \n' +
        '\t<j:set var="failCount" value="${tapResult.getFailed()}"/>\n' +
        '\t<j:set var="skipCount" value="${tapResult.getSkipped()}"/>\n' +
        '\t<j:set var="passCount" value="${tapResult.getPassed()}"/>\n' +
        '\t<TABLE width="100%">\n' +
        '\t\t<TR><TD class="bg1" colspan="2"><B>MATLAB Test Suite Summary</B></TD></TR>\n' +
        '\t\t<TR>\n' +
        '\t\t\t<TD width="80px">Ran</TD>\n' +
        '\t\t\t<TD>${passCount+failCount+skipCount}</TD>\n' +
        '\t\t</TR>\n' +
        '\t\t<TR>\n' +
        '\t\t\t<TD>Failed</TD>\n' +
        '\t\t\t<TD>${failCount}</TD>\n' +
        '\t\t</TR>\n' +
        '\t\t<TR>\n' +
        '\t\t\t<TD>Skipped</TD>\n' +
        '\t\t\t<TD>${skipCount}</TD>\n' +
        '\t\t</TR>\n' +
        '\t\t<TR>\n' +
        '\t\t\t<TD>Passed</TD>\n' +
        '\t\t\t<TD>${passCount}</TD>\n' +
        '\t\t</TR>\n' +
        '\t</TABLE>\n' +
        '\t<BR/>\n' +
        '\t\n' +
        '\t<!-- Test reporting TEMPLATE for TAP -->  \n' +
        '\t\n' +
        '\t<j:forEach var="tapTestSetMap" items="${tapTestSetMapList}">\n' +
        '\t\t<j:set var="tapTestSet" value="${tapTestSetMap.getTestSet()}" /> \n' +
        '\t\t<j:set var="tapTestResults" value="${tapTestSet.getTestResults()}" /> \n' +
        '\t\t<TABLE width="100%">\n' +
        '\t\t<TR><td class="bg1" colspan="2"><B>${tapTestSetMap.getFileName()}</B></td></TR>\n' +
        '\t\t<j:forEach var="tapTestResult" items="${tapTestResults}">\n' +
        '\t\t\t<j:choose>\n' +
        '\t\t\t   <j:when test="${tapTestResult.getStatus() == \'not ok\'}"><j:set var="testStatusClass" value="test_failed"/></j:when>\n' +
        '\t\t\t   <j:when test="${tapTestResult.getDirective().getDirectiveValue() == \'SKIP\'}"><j:set var="testStatusClass" value="test_filtered"/></j:when>\n' +
        '\t\t\t   <j:when test="${tapTestResult.getStatus() == \'ok\'}"><j:set var="testStatusClass" value="test_passed"/></j:when>\n' +
        '\t\t\t   <j:otherwise><j:set var="testStatusClass" value=""/></j:otherwise>\n' +
        '\t\t\t</j:choose>\n' +
        '\t\t\t<TR>\n' +
        '\t\t\t\t<TD class="${testStatusClass}">${tapTestResult.getTestNumber()}</TD>\n' +
        '\t\t\t\t<!-- Substring() removes the trailing \'- \' of the description -->\n' +
        '\t\t\t\t<TD class="${testStatusClass}">${tapTestResult.getDescription().substring(2)}</TD>\n' +
        '\t\t\t</TR>\n' +
        '\t\t</j:forEach>\n' +
        '\t\t</TABLE>\t\n' +
        '\t\t<BR/>\t\n' +
        '\t</j:forEach>\n' +
        '      \n' +
        '\t  \n' +
        '\t  <!-- CONSOLE OUTPUT -->\n' +
        '      <TABLE width="100%" cellpadding="0" cellspacing="0">\n' +
        '        <TR><TD class="bg1"><B>Build Log Tail (Full Log Attached)</B></TD></TR>\n' +
        '        <j:forEach var="line" items="${build.getLog(100)}"><TR><TD class="build_log">${line}</TD></TR></j:forEach>\n' +
        '      </TABLE>\n' +
        '      <BR/>\n' +
        '    \n' +
        '  </BODY>\n' +
        '</j:jelly>'

