/*
	This is a subclass of the command class. It will implement some helper functions needed by the add/update, and erase commands.
*/
#include "bookCommand.h"
#include <string>

using namespace std;

/*
	Sets up the bookCommand class by taking in the istream and ostream objects and setting them to the in and out variables. 
*/
bookCommand::bookCommand(std::istream& in, std::ostream& out) : in(in), out(out){}

/*
	Displays the string that is passed in the parameter. 
*/
void bookCommand::display(std::string str) {
	out << str << endl;
}

/*
	Takes in a string which is output to the screen, prompting the user to input a string. That inputted string is then returned. 
*/
std::string bookCommand::prompt(std::string str) {
	out << str << endl;
	string input;
	getline(cin, input);
	return input;
}