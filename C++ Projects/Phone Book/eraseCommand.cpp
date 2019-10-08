/*
	Removes the specified phoneBookEntry(if it exists).
*/
#include "eraseCommand.h"

using namespace std;

/*
	Constructs an erase command and automatically sends the bookCommand constructor the istream and ostream, and sets book to the bookIn parameter.
*/
eraseCommand::eraseCommand(phoneBook& bookIn, std::istream& in, std::ostream& out) : bookCommand(in, out), book(bookIn) {}

/*
	Executes the erase command. The user is prompted to enter a name to erase, and that name is erased if there exists an entry with that name. 
*/
void eraseCommand::execute() {
	string nameToErase = prompt("Enter name to erase");
	bool erased = book.erase(nameToErase);
	if (erased) {
		display("Phone book entry " + nameToErase + " was erased");
	}
	else {
		display("Phone book entry " + nameToErase + " was not erased");
	}
}