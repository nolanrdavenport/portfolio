/*
	Command that prints the phoneBook when the command is executed by the menu.
*/
#include "printCommand.h"

/*
	Constructs a print command and automatically sends the bookCommand constructor the istream and ostream, and sets book to the bookIn parameter.
*/
printCommand::printCommand(phoneBook& bookIn, std::istream& in, std::ostream& out) : bookCommand(in, out), book(bookIn) {}

/*
	Executes the print command. Calls the print() method in the phoneBook class. 
*/
void printCommand::execute() {
	book.print();
}