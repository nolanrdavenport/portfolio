#pragma once
#include "bookCommand.h"
#include "phoneBook.h"

/*
	Command that prints the phoneBook when the command is executed by the menu.
*/
class printCommand : public bookCommand {
public:
	printCommand(phoneBook& bookIn, std::istream& in, std::ostream& out);
	void execute();
private:
	phoneBook& book;
};