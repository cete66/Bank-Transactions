@tag
Feature: Check Transaction Status

  @tag1
  Scenario: RuleA
    Given A transaction that is not stored in our system
		When I check the status from any channel
		Then The system returns the status "INVALID"
	
	#@tag2
	#Scenario: RuleA
	Given A transaction that is stored in our system
	When I check the status from "CLIENT" channel
	And the transaction date is before today
	Then The system returns the status "SETTLED"
	And the amount substracting the fee
	
	#@tag9
	#Scenario: RuleI
	Given A transaction that is stored in our system
	When I check the status from "ATM" channel
	And the transaction date is before today
	Then The system returns the status "SETTLED"
	And the amount substracting the fee
	
	#@tag3
	#Scenario: RuleC
	Given A transaction that is stored in our system
	When I check the status from "INTERNAL" channel
	And the transaction date is before today
	Then The system returns the status "SETTLED"
	And the amount
	And the fee
	
	#@tag4
	#Scenario: RuleD
	Given A transaction that is stored in our system
	When I check the status from "CLIENT" channel
	And the transaction date is equals to today
	Then The system returns the status "PENDING"
	And the amount substracting the fee
	
	#@tag10
	#Scenario: RuleJ
	Given A transaction that is stored in our system
	When I check the status from "ATM" channel
	And the transaction date is equals to today
	Then The system returns the status "PENDING"
	And the amount substracting the fee
	
	#@tag5
	#Scenario: RuleE
	Given A transaction that is stored in our system
	When I check the status from "INTERNAL" channel
	And the transaction date is equals to today
	Then The system returns the status "PENDING"
	And the amount
	And the fee
	
	#@tag6
	#Scenario: RuleF
	Given A transaction that is stored in our system
	When I check the status from "CLIENT" channel
	And the transaction date is greater than today
	Then The system returns the status "FUTURE"
	And the amount substracting the fee
	
	#@tag7
	#Scenario: RuleG
	Given A transaction that is stored in our system
	When I check the status from "ATM" channel
	And the transaction date is greater than today
	Then The system returns the status "PENDING"
	And the amount substracting the fee
	
	#@tag8
	#Scenario: RuleH
	Given A transaction that is stored in our system
	When I check the status from "INTERNAL" channel
	And the transaction date is greater than today
	Then The system returns the status "FUTURE"
	And the amount
	And the fee