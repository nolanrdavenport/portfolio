#pragma once
#include "command.h"
#include <iostream>

/*
	This is a subclass of the command class. It will implement some helper functions needed by the add/update, and erase commands.
*/
class bookCommand : public command {
public:
	bookCommand(std::istream& in, std::ostream& out);
protected:
	std::istream& in;
	std::ostream& out;
	void display(std::string str);
	std::string prompt(std::string str);
};
