
@NonCPS
def call(Map args) {
    def build = args.build
    def rooturl = args.rootUrl
    def name = args.name
    def buildUrl = args.buildUrl
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
    
    <TABLE>
      <TR><TD>
          ${sw -> 
                if(build.currentResult == 'SUCCESS') sw << """BUILD SUCCESS <IMG SRC="${rooturl}static/e59dfe28/images/32x32/blue.gif" />"""
                else if(build.currentResult == 'SUCCESS') sw << """BUILD FAILURE <IMG SRC="${rooturl}static/e59dfe28/images/32x32/red.gif" />"""
                else sw << """BUILD FINISHED <IMG SRC="${rooturl}static/e59dfe28/images/32x32/yellow.gif" />"""
           }
      </TD><TD valign="center"><B style="font-size: 200%;">BUILD ${build.currentResult}</B></TD></TR>
      <TR><TD>Build URL</TD><TD><A href="${buildUrl}">${buildUrl}</A></TD></TR>
      <TR><TD>Project:</TD><TD>${name}</TD></TR>
      <TR><TD>Date of build:</TD><TD>${new Date(build.startTimeInMillis)}</TD></TR>
      <TR><TD>Build duration:</TD><TD>${build.durationString}</TD></TR>
    </TABLE>
    <BR/>
    
    
    <TABLE width="100%">
        ${sw -> build.changeSets.collectMany{t -> t.getLogs()}.each { cs ->
            sw << """
              <TR><TD class="bg1" colspan="2"><B>Changes</B></TD></TR>
              <TR>
                <TD colspan="2" class="bg2">Revision <B>${cs.commitId ?: cs.revision ?: cs.changeNumber}</B> by
                  <B>${cs.author.displayName}: </B>
                  <B>(${cs.commentAnnotated})</B>
                </TD>
              </TR>
            """
        }}
    </TABLE>
    
  </BODY>
</j:jelly>
"""
    emailext body: body, recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test', to: 'josiah@sunshower.io'
}

