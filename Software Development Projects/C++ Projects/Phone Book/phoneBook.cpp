/*
	This is the phoneBook and contains phoneBookEntry values. You can add, update, and remove entries from the phoneBook and you can print the contents of the phoneBook.
*/
#include "phoneBook.h"
#include <iostream>
#include <vector>
phoneBook::phoneBook() {}

using namespace std;

/*
	Adds a new entry to the entries vector by sending in a phoneBookEntry object reference as the parameter. 
*/
void phoneBook::insert(const phoneBookEntry& entry) {
	int sameName = containsName(entry.name());
	if (sameName >= 0) {
		entries.at(sameName) = entry;
	}
	else {
		entries.push_back(entry);
	}
}

/*
	Adds a new entry to the entries vector by creating a new phoneBookEntry object using the name, number and email string references sent in as paramters. 
*/
void phoneBook::insert(const std::string& name, const std::string& number, const std::string& email) {
	phoneBookEntry tempEntry(name, number, email);
	int sameName = containsName(tempEntry.name());
	if (sameName >= 0) {
		entries.at(sameName) = tempEntry;
	}
	else {
		entries.push_back(tempEntry);
	}
}

/*
	Adds a new entry to the entries vector by creating a new phoneBookEntry object using the name and number string references sent in as paramters. 
*/
void phoneBook::insert(const std::string& name, const std::string& number) {
	phoneBookEntry tempEntry(name, number);
	int sameName = containsName(tempEntry.name());
	if (sameName >= 0) {
		entries.at(sameName) = tempEntry;
	}
	else {
		entries.push_back(tempEntry);
	}
}

/*
	Erases an entry based on its name from the entries vector. 
	Returns true if an entry was erased, and false if an entry was not erased. 
*/
bool phoneBook::erase(std::string name) { 
	for (int i = 0; i < entries.size(); i++) {
		if (entries.at(i).name() == name) {
			entries.erase(entries.begin() + i);
			return true;
		}
	}

	return false;
}

/*
	Finds whether or not there exists an entry with a name that matches the one sent in as a parameter. 
	Returns true if there does exist an entry with a name that matches the one sent in as a parameter, and false if not. 
*/
bool phoneBook::find(std::string name) {
	for (int i = 0; i < entries.size(); i++) {
		if (entries.at(i).name() == name) {
			return true;
		}
	}

	for (iterator it = phoneBook::begin(); it != phoneBook::end(); it++) {

	}
	return false;
}

/*
	Prints the contents of the entries vector in a structured manner. Uses cout as the output stream. 
*/
void phoneBook::print() const {
	cout << "Name:                          Phone Number:   E-Mail:" << endl;
	for (phoneBookEntry entry : entries) {
		string name = "                               ";
		for (int i = 0; i < entry.name().size(); i++) {
			name[i] = entry.name()[i];
		}
		string number = "                ";
		for (int i = 0; i < entry.phoneNumber().size(); i++) {
			number[i] = entry.phoneNumber()[i];
		}
		string email = entry.email();

		cout << name << number << email << endl;
	}
}

/*
	Prints the contents of the entries vector in a structured manner. Uses ostream parameter as the output stream. 
*/
void phoneBook::print(std::ostream& out) const {
	out << "Name:                          Phone Number:   E-Mail:" << endl;
	for (phoneBookEntry entry : entries) {
		string name = "                               ";
		for (int i = 0; i < entry.name().size(); i++) {
			name[i] = entry.name()[i];
		}
		string number = "                ";
		for (int i = 0; i < entry.phoneNumber().size(); i++) {
			number[i] = entry.phoneNumber()[i];
		}
		string email = entry.email();

		out << name << number << email << endl;
	}
}

/*
	Prints the entries vector for debug purposes. 
*/
void phoneBook::debug(std::ostream& out) const {
	for (phoneBookEntry entry : entries) {
		out << entry.name() << " | " << entry.phoneNumber() << " | " << entry.email() << endl;
	}
}

/*
	Returns the size of the entries vector. 
*/
std::size_t phoneBook::size() const { 
	return entries.size();
}

/*
	Returns the begin iterator from the entries vector.
*/
phoneBook::iterator phoneBook::begin() {
	return entries.begin();
}

/*
	Returns the end iterator from the entries vector.
*/
phoneBook::iterator phoneBook::end() {
	return entries.end();
}

/*
	Returns the index of the entry that has the same name as the parameter. Returns -1 if there is no name that is equal to the parameter. 
*/
int phoneBook::containsName(string nameIn) {
	for (int i = 0; i < entries.size(); i++) {
		if (entries.at(i).name() == nameIn) {
			return i;
		}
	}

	return -1;
}