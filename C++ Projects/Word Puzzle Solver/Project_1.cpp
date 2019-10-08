/*
	The purpose of this application is to solve a word search puzzle when given the words. The word search puzzle file and extention and the words file and extention
	will be inputted in that order when the application is run.

	The application will first display the word search puzzle found in the word search puzzle file, and then, for each word found in the words file, it will search through
	the puzzle until an instance of the word is found. Once an instance is found, it will acknowledge that it was found, save the (row, col) location of the start of the word
	and save the direction that the word is facing relative to the first letter.

	Lastly, the application will display the location and direction of every word that was found once. If a word was found more than once, it will only display how many times
	that word was found. If a word was not found in the puzzle, it will display that the word was not found.
*/

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <cstring>
#include <algorithm>
#include <cctype>

using namespace std;

//This enum represents the direction that a word is facing relative to the first letter in that word. 
enum direction {
	DOWN,
	LEFT_DOWN,
	RIGHT_LEFT,
	LEFT_UP,
	UP,
	RIGHT_UP,
	LEFT_RIGHT,
	RIGHT_DOWN,
};

const int MAX = 50;

//This struct holds the word search puzzle, aswell as the version, number of rows and number of columns. 
struct wordGame {
	int version;
	int numberRows;
	int numberColumns;
	char puzzle[MAX][MAX];
};

//This struct holds the information of each word found in the words file. This includes its string value, whether it was found, location, direction and count of words found. 
struct wordFind {
	string word = "[NO WORD]";
	bool found = false;
	int row = 0;
	int column = 0;
	direction where = DOWN;
	int foundCount = 0;
};

bool readPuzzle(wordGame& game, string inputFileName);
void displayPuzzle(wordGame& game);
bool fillWordVector(vector<wordFind>& words, string wordsFileName);
void findWord(wordGame& game, wordFind& theFind);
void foundWord(int row, int col, wordFind& theFind, direction where);
string toUpperCase(string a);

int main() {
	//Gets file names and stores them into their string variables.
	string inputFileName;
	string wordsFileName;
	cin >> inputFileName >> wordsFileName;

	//Creates a wordGame variable and stores the puzzle from the file.
	wordGame game;
	if (!readPuzzle(game, inputFileName)) {
		cout << "The puzzle file \"" << inputFileName << "\" could not be opened or is invalid" << endl;
		exit(0);
	}
	else {
		cout << "The puzzle from file \"" << inputFileName << "\"" << endl;

	}

	//Displays the puzzle.
	displayPuzzle(game);

	//Creates a words vector and stores the words from the file.
	vector<wordFind> words;
	if (!fillWordVector(words, wordsFileName)) {
		cout << "The puzzle file \"" << wordsFileName << "\" could not be opened or is invalid" << endl;
		exit(0);
	}

	//Calls findWord for every word in the words vector.
	for (unsigned int i = 0; i < words.size(); i++) {
		findWord(game, words.at(i));
	}

	//Prints the information for every word listed in the words file.
	for (unsigned int i = 0; i < words.size(); i++) {
		if (words.at(i).found == false) {
			cout << "The word " << words.at(i).word << " was not found";
		}
		else if (words.at(i).foundCount > 1) {
			cout << "The word " << words.at(i).word << " was found " << words.at(i).foundCount << " times";
		}
		else {
			cout << "The word " << words.at(i).word << " was found at (" << words.at(i).row + 1 << ", " << words.at(i).column + 1 << ") - ";
			switch (words.at(i).where) {
			case 0:
				cout << "down";
				break;
			case 1:
				cout << "left/down";
				break;
			case 2:
				cout << "left";
				break;
			case 3:
				cout << "left/up";
				break;
			case 4:
				cout << "up";
				break;
			case 5:
				cout << "right/up";
				break;
			case 6:
				cout << "right";
				break;
			case 7:
				cout << "right/down";
				break;
			}
		}
		cout << endl;
	}

	return 0;
}

/*
	This function reads the word puzzle from the word puzzle file. The word puzzle file and extention were saved into the inputFileName string.
	This file name string is used to open an input file stream which is used to read the information in the word search file.

	The rows and columns are gathered from the file and used to save the word puzzle letters into the 2 dimentional puzzle array member of the wordGame
	struct that is passed as reference when the function is called.

	The game version is set to 1 and this function returns true if there are no problems reading from the file. If there are problems and the file didn't
	open, then this function will return false.
*/
bool readPuzzle(wordGame& game, string inputFileName) {
	ifstream file;
	file.open(inputFileName);
	game.version = 2;
	if (file.is_open()) {
		file >> game.numberRows >> game.numberColumns;
		if (game.numberRows < 1 || game.numberRows > MAX || game.numberColumns < 1 || game.numberColumns > MAX) {
			return false;
		}
		else {
			for (int r = 0; r < game.numberRows; r++) {
				string line;
				if (!file.eof()) {
					file >> line;
				}
				else if(game.numberRows - 1 > r){
					return false;
				}
				for (int c = 0; c < game.numberColumns; c++) {
					try {
						game.puzzle[r][c] = toupper(line.at(c));
					}
					catch (exception e) {
						return false;
					}
				}
			}
			file.close();
			return true;
		}
	}
	else {
		file.close();
		return false;
	}
}

/*
	This function displays the puzzle found in the 2 dimentional puzzle member of the wordGame struct that is passed as reference when this function is
	called.
*/
void displayPuzzle(wordGame& game) {
	for (int r = 0; r < game.numberRows; r++) {
		for (int c = 0; c < game.numberColumns; c++) {
			cout << game.puzzle[r][c];
		}
		cout << endl;
	}
}

/*
	This function reads each word found in the words file and stores them into the words vector which is passed as reference when this function is called.
	The words file name and extention were saved into the wordsFileName string. This file name string is used to open an input file stream which is used to
	read the information in the words file.

	This function returns true if there are no problems reading from the file. If there are problems and the file didn't
	open, then this function will return false.
*/
bool fillWordVector(vector<wordFind>& words, string wordsFileName) {
	ifstream file;
	file.open(wordsFileName);

	if (file.is_open()) {
		while (!file.eof()) {
			wordFind tempWord;
			file >> tempWord.word;
			tempWord.word = toUpperCase(tempWord.word);

			if (tempWord.word != "[NO WORD]") {
				words.push_back(tempWord);
			}
		}
		file.close();
		return true;
	}
	else {
		file.close();
		return false;
	}

}

/*
	This function searches through the word search puzzle, which is the 2 dimentional puzzle array member of the wordGame struct which is passed as reference
	when this function was called. Then, when the word is found, it's information is stored into the wordFind struct which is passed as reference when this
	function is called.

	This function searches through every single (row, col) coordinate in the puzzle and checks the eight directions around the coordinate. Before checking for
	the word, it first checks if the amount of characters in that direction is greater than or equal to the word itself in order to avoid complications with going
	out of bounds.

	If a word is found, the foundWord function is called and passed in its wordFind struct reference, location and direction.
*/
void findWord(wordGame& game, wordFind& theFind) {
	int wordSize = theFind.word.size();

	for (int r = 0; r < game.numberRows; r++) {
		for (int c = 0; c < game.numberColumns; c++) {
			//DOWN
			if ((game.numberRows + 1) - r >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r + j][c];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, DOWN);
				}
			}

			//LEFT_DOWN
			if ((game.numberRows + 1) - r >= wordSize && c + 1 >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r + j][c - j];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, LEFT_DOWN);
				}
			}

			//RIGHT_LEFT
			if (c + 1 >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r][c - j];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, RIGHT_LEFT);
				}
			}

			//LEFT_UP
			if (r + 1 >= wordSize && c + 1 >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r - j][c - j];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, LEFT_UP);
				}
			}
			//UP
			if (r + 1 >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r - j][c];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, UP);
				}
			}
			//RIGHT_UP
			if (r + 1 >= wordSize && (game.numberColumns + 1) - c >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r - j][c + j];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, RIGHT_UP);
				}
			}

			//LEFT_RIGHT
			if ((game.numberColumns + 1) - c >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r][c + j];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, LEFT_RIGHT);
				}
			}
			//RIGHT_DOWN
			if ((game.numberRows + 1) - r >= wordSize && (game.numberColumns + 1) - c >= wordSize) {
				string tempWord = "";
				for (int j = 0; j < wordSize; j++) {
					tempWord += game.puzzle[r + j][c + j];
				}
				if (toUpperCase(tempWord) == toUpperCase(theFind.word)) {
					foundWord(r, c, theFind, RIGHT_DOWN);
				}
			}
		}
	}
}

/*
	This function uses a given location and direction and assigns this information to the wordFind struct which is passed in as reference when this function
	is called.

	It first checks if the word is previously not found, and if not, it assigns the given information into the wordFind struct which holds the word's information.

	If the word was previously found, then it will simply increment the foundCount member of the referenced wordFind struct.
*/
void foundWord(int row, int col, wordFind& theFind, direction where) {
	if (theFind.found == false) {
		theFind.row = row;
		theFind.column = col;
		theFind.found = true;
		theFind.where = where;
	}
	theFind.foundCount++;
}

/*
	This function takes in a string and returns that string in all Caps.
*/
string toUpperCase(string a) {
	for (unsigned int i = 0; i < a.length(); ++i) {
		a[i] = toupper(a[i]);
	}

	return a;
}