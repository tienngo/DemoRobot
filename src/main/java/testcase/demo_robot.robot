*** Settings ***
Library           customlib.CustomJavaLib

*** Test Cases ***
Demo testcase for automation framework
	${session}		Given Login As				test		1
	${bankAccountSource}	When Get Detail Of Bank Account		${session}	13566			
	${bankAccountTarget}	And Create new Bank Account		${session}	${bankAccountSource}	
	${transferMsg}		And Transfer Funds			${session}	${bankAccountSource}	${bankAccountTarget}	100
				Then Transfer Message Should Be correct	${transferMsg}	${bankAccountSource}	${bankAccountTarget}	100