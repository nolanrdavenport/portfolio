#pragma once
#include "bookCommand.h"
#include "phoneBook.h"

/*
	Removes the specified phoneBookEntry(if it exists).
*/
class eraseCommand : public bookCommand {
public:
	eraseCommand(phoneBook& bookIn, std::istream& in, std::ostream& out);
	void execute(); 
private:
	phoneBook &book;
};