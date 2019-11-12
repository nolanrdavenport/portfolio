/* TODO: FIGURE THIS OUT
	We want to make use of iterators. The vector class already has support for iterators, so we are going to leverage that to implement out iterator. 
	The iterator support will allow us to use the range based for loop with our phoneBook class.
	The begin member function needs to call the vector's begin member function, and the end member function needs to call the vector's end member function.
	Note that the typedef statements are inside of the phoneBook class declaration. 
	If you make use of them outside of the class definition you will have to use the syntax phoneBook::iterator (as an example) to get access to them.
	This will be required if you implement begin or end in your phoneBook.cpp file:
		C:\Users\guzzo\Desktop\1st Semester Coursework\CS 2337\project 2\pics\iteratorIssue.png

*/
#pragma once
#include <string>
#include <vector>
#include "phoneBookEntry.h"

/*
	This is the phoneBook and contains phoneBookEntry values. You can add, update, and remove entries from the phoneBook and you can print the contents of the phoneBook.
*/
class phoneBook {
public:
	phoneBook();
	void insert(const phoneBookEntry& entry);
	void insert(const std::string& name, const std::string& number, const std::string& email);
	void insert(const std::string& name, const std::string& number);
	bool erase(std::string name);
	bool find(std::string name);
	void print() const;
	void print(std::ostream& out) const;
	void debug(std::ostream& out) const;
	std::size_t size() const;
	typedef std::vector<phoneBookEntry> phoneBookEntryList;
	typedef phoneBookEntryList::iterator iterator;
	typedef phoneBookEntryList::const_iterator const_iterator;
	iterator begin();
	iterator end();
	int containsName(std::string nameIn);
private:
	phoneBookEntryList entries;
};
