
def call(Map args) {
    def build = args.build
    def rooturl = args.rootUrl
    def body = """
<j:jelly xmlns:j="jelly:core" xmlns:st="jelly:stapler" xmlns:d="jelly:define">
  
  <STYLE>
    BODY, TABLE, TD, TH, P {
    font-family:Verdana,Helvetica,sans serif;
    font-size:11px;
    color:black;
    }
    h1 { color:black; }
    h2 { color:black; }
    h3 { color:black; }
    TD.bg1 { color:white; background-color:#0000C0; font-size:120% }
    TD.bg2 { color:white; background-color:#4040FF; font-size:110% }
    TD.bg3 { color:white; background-color:#8080FF; }
    TD.test_passed { color:blue; }
    TD.test_failed { color:red; }
    TD.test_filtered { color:orange; }
    TD.output { font-family:Courier New;font-size:0%;}        
    TD.build_log { font-family:Courier New;}        
    TR.failed_row:hover {background-color:lightgray;}           
    TR.failed_row:hover + TR.output_row TD.output{font-size:100%;}                
  </STYLE>
  <BODY>
    <j:set var="spc" value="&amp;nbsp;&amp;nbsp;" />
    
    
    <!-- GENERAL INFO -->
    
    <TABLE>
      <TR><TD align="right">
          <j:choose>
            <j:when test="${build.result == 'SUCCESS'}">
              <IMG SRC="${rooturl}static/e59dfe28/images/32x32/blue.gif" />
            </j:when>
            <j:when test="${build.result == 'FAILURE'}">
              <IMG SRC="${rooturl}static/e59dfe28/images/32x32/red.gif" />
            </j:when>
            <j:otherwise>
              <IMG SRC="${rooturl}static/e59dfe28/images/32x32/yellow.gif" />
            </j:otherwise>
          </j:choose>
      </TD><TD valign="center"><B style="font-size: 200%;">BUILD ${build.result}</B></TD></TR>
      <TR><TD>Build URL</TD><TD><A href="${buildUrl}">${buildUrl}</A></TD></TR>
      <TR><TD>Project:</TD><TD>${project.name}</TD></TR>
      <TR><TD>Date of build:</TD><TD>${it.timestampString}</TD></TR>
      <TR><TD>Build duration:</TD><TD>${build.durationString}</TD></TR>
    </TABLE>
    <BR/>
    
    
    <!-- CHANGE SET -->
    
    <j:set var="changeSet" value="${build.changeSet}" />
    <j:if test="${changeSet != null}">
      <j:set var="hadChanges" value="false" />
      <TABLE width="100%">
        <TR><TD class="bg1" colspan="2"><B>Changes</B></TD></TR>
        <j:forEach var="cs" items="${changeSet}" varStatus="loop">
          <j:set var="hadChanges" value="true" />
          <j:set var="aUser" value="${cs.hudsonUser}"/>
          <TR>
            <TD colspan="2" class="bg2">${spc}Revision <B>${cs.commitId ?: cs.revision ?: cs.changeNumber}</B> by
              <B>${aUser != null ? aUser.displayName : cs.author.displayName}: </B>
              <B>(${cs.msgAnnotated})</B>
            </TD>
          </TR>
          <j:forEach var="p" items="${cs.affectedFiles}">
            <TR>
              <TD width="10%">${spc}${p.editType.name}</TD>
              <TD>${p.path}</TD>
            </TR>
          </j:forEach>
        </j:forEach>
        <j:if test="${!hadChanges}">
          <TR><TD colspan="2">No Changes</TD></TR>
        </j:if>
      </TABLE>
      <BR/>
    </j:if>
    
    
    <!-- ARTIFACTS -->
    
    <j:set var="artifacts" value="${build.artifacts}" />
    <j:if test="${
        artifacts != null
        and artifacts.size() & gt; 0
    }">
      <TABLE width="100%">
        <TR><TD class="bg1"><B>Build Artifacts</B></TD></TR>
        <TR>
          <TD>
            <j:forEach var="f" items="${artifacts}">
              <li>
                <a href="${rooturl}${build.url}artifact/${f}">${f}</a>
              </li>
            </j:forEach>
          </TD>
        </TR>
      </TABLE>
      <BR/>  
    </j:if>
    
    <!-- Test reporting TEMPLATE (references to JUnit refer to the JUnit style XML Output produced by the MATLAB TestRunner) -->     
    <j:set var="resultList" value="${it.JUnitTestResult}" />
    <j:if test="${resultList.isEmpty() != true}">
      <TABLE width="100%">
        <j:forEach var="testResult" items="${resultList}">
          <j:set var="failCount" value="${failCount + testResult.getFailCount()}"/>
          <j:set var="skipCount" value="${skipCount + testResult.getSkipCount()}"/>
          <j:set var="passCount" value="${passCount + testResult.getPassCount()}"/>
        </j:forEach>
        <TR><TD class="bg1" colspan="2"><B>MATLAB Test Suite Summary:</B> ${passCount + failCount + skipCount} Ran, ${
        failCount
    } Failed, ${passCount} Passed, ${skipCount} Filtered</TD></TR>
        
        <TR><TD class="bg1" colspan="2"><B>Failed Tests</B></TD></TR>
        <j:forEach var="testResult" items="${resultList}">
          <j:forEach var="packageResult" items="${testResult.getChildren()}">
            <j:forEach var="failed_test" items="${packageResult.getFailedTests()}">
              <TR class="failed_row" bgcolor="white"><TD class="test_failed" colspan="2"><B><li>Failed: ${
        failed_test.getFullName()
    } </li></B></TD></TR>
              <TR class="output_row"><TD class="output"><PRE>${failed_test.getErrorStackTrace()}</PRE></TD></TR>
            </j:forEach>
          </j:forEach> 
        </j:forEach>
        
        <TR><TD class="bg1" colspan="2"><B>Filtered Tests</B></TD></TR>
        <j:forEach var="testResult" items="${resultList}">
          <j:forEach var="packageResult" items="${testResult.getChildren()}">
            <j:forEach var="filtered_test" items="${packageResult.getSkippedTests()}">
              <TR bgcolor="white"><TD class="test_filtered" colspan="2"><B><li>Filtered: ${filtered_test.getFullName()} </li></B></TD></TR>
            </j:forEach>  
          </j:forEach>
        </j:forEach>
      </TABLE>\t
      <BR/>
    </j:if>

    <!-- CONSOLE OUTPUT -->
      <TABLE width="100%" cellpadding="0" cellspacing="0">
        <TR><TD class="bg1"><B>Build Log Tail (Full Log Attached)</B></TD></TR>
        <j:forEach var="line" items="${build.getLog(100)}"><TR><TD class="build_log">${line}</TD></TR></j:forEach>
      </TABLE>
      <BR/>
    
  </BODY>
</j:jelly>
"""
    emailext body: body, recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test', to: 'josiah@sunshower.io'
}

