*** Settings ***
Library           customlib.CustomJavaLib

*** Test Cases ***
Demo testcase for automation framework
	${session}		Given Login As				test		1
	${bankAccountSource}	When Get Detail Of Bank Account		${session}	13566			
	${bankAccountTarget}	And Create new Bank Account		${session}	${bankAccountSource}	
				Then Transfer Funds			${session}	${bankAccountSource}	${bankAccountTarget}	100	