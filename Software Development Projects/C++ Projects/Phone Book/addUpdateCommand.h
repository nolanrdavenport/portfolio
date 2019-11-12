#pragma once
#include "bookCommand.h"
#include "phoneBook.h"

/*
	This command will add a new entry if the passed in name is not already in the phoneBook. Otherwise it will update and existing phoneBookEntry.
*/
class addUpdateCommand : public bookCommand {
public:
	addUpdateCommand(phoneBook &bookIn, std::istream &in, std::ostream &out);
	void execute();
private:
	phoneBook &book;
};