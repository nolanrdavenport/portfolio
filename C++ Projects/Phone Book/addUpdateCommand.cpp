/*
	This command will add a new entry if the passed in name is not already in the phoneBook. Otherwise it will update and existing phoneBookEntry.
*/
#include "addUpdateCommand.h"

using namespace std;

/*
	Constructs an add/update command and automatically sends the bookCommand constructor the istream and ostream, and sets book to the bookIn parameter. 
*/
addUpdateCommand::addUpdateCommand(phoneBook& bookIn, std::istream& in, std::ostream& out) : bookCommand(in, out), book(bookIn){}

/*
	Executes the add/update command. Prompts the user to enter the name, number and email of the entry. Either adds a new entry if no entry exists with the inputted name, 
	or updates an existing entry if there exists an entry with the inputted name. 
*/
void addUpdateCommand::execute() {
	string newName = prompt("Enter name to add/update");
	string newNumber = prompt("Enter phone number");
	string newEmail = prompt("Enter e-mail address");

	bool hasEntry = book.find(newName);

	if (hasEntry) {
		display("Updating phone book entry for " + newName);
	}
	else {
		display("Adding phone book entry for " + newName);
	}

	book.insert(newName, newNumber, newEmail);
}