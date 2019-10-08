/*
		This class will represent an entry in the phone book. It contains the name, phone number and e-mail address.

		This class will contain three std::string values for name, phone number and e-mail address.
*/

#include "phoneBookEntry.h"
#include <iostream>

/*
	Constructs a phoneBookentry with no parameters. Everything is set to an empty string. 
*/
phoneBookEntry::phoneBookEntry() {
	this->currName = "";
	this->currNumber = "";
	this->currEmail = "";
}

/*
	Constructs a phoneBookEntry using a name and a number that were sent in as parameters. The email is set to an empty string. 
*/
phoneBookEntry::phoneBookEntry(const std::string& name, const std::string& number) {
	this->currName = name;
	this->currNumber = number;
	this->currEmail = "";
}

/*
	Constructs a phoneBookEntry using a name, a number and an email that were sent in as parameters.
*/
phoneBookEntry::phoneBookEntry(const std::string& name, const std::string& number, const std::string& email) {
	this->currName = name;
	this->currNumber = number;
	this->currEmail = email;
}

/*
	Copy constructor for phone book entries. 
*/
phoneBookEntry::phoneBookEntry(const phoneBookEntry& from) {
	this->currName = from.name();
	this->currNumber = from.phoneNumber();
	this->currEmail = from.email();
}

/*
	Returns the current name of this entry.
*/
std::string phoneBookEntry::name() const { 
	return currName;
}

/*
	Returns the current phone number of this entry.
*/
std::string phoneBookEntry::phoneNumber() const { 
	return currNumber;
}

/*
	returns the current email of this entry. 
*/
std::string phoneBookEntry::email() const { 
	return currEmail;
}

/*
	Sets the current phone number to the value of the parameter. 
*/
void phoneBookEntry::phoneNumber(const std::string& newNumber) { 
	this->currNumber = newNumber;
}

/*
	Sets the current email to the value of the parameter. 
*/
void phoneBookEntry::email(const std::string& newEmail) { 
	this->currEmail = newEmail;
}