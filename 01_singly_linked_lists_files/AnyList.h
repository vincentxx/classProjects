//CS250
//second commits
#ifndef ANYLIST_H
#define ANYLIST_H

#include<iostream>
#include <string>		//Need to include for NULL			
using namespace std;

class Node
{
public:
	Node() : data(0), ptrToNext(nullptr) {}
	Node(int theData, Node *newPtrToNext) 
		: data(theData), ptrToNext(newPtrToNext){}
	Node* getPtrToNext() const { return ptrToNext; }
	int getData( ) const { return data; }
    void setData(int theData) { data = theData; }
	void setPtrToNext(Node *newPtrToNext) 
		{ ptrToNext = newPtrToNext; }
	~Node(){}
private:
    int data;		
    Node *ptrToNext;	//pointer that points to next node
};


class AnyList
{
public:
	AnyList();	
		//default constructor

	void insertFront(int);	
		//insertFront - Inserts a node to the front of the list.
		//@param int - Value stored in the node that is inserted.

	void deleteNode(int);	
		//deleteNode - Deletes a node from the list.
		//@param int - Value stored in the node to be deleted.

	void print() const;
		//print const - Prints all values in the list.

	void destroyList();
		//destroyList - Destroys all nodes in the list.

	~AnyList();
		//destructor

private:
	Node *ptrToFirst; //pointer to point to the first node in the list
	int count;	      //keeps track of number of nodes in the list
};

#endif
