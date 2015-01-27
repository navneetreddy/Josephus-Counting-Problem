///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  JosephusMain.java
// File:             CircularLinkedList.java
// Semester:         CS367 Fall 2013
//
// Author:           Navneet Reddy
// CS Login:         navneet
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jason Tiedt
// CS Login:         jtiedt
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          N/A
//////////////////////////// 80 columns wide //////////////////////////////////


/**
 * Class that implements the Circular List ADT
 *
 * <p>Bugs: No known bugs
 *
 * @author Jason Tiedt, Navneet Reddy
 */
public class CircularLinkedList<E> implements CircularListADT<E> {

	private ListNode<E> curr; // holds current position
	private static int size;  // holds the size of the list

	/**
	 * Creates the circular linked list and initializes size to zero
	 */
	public CircularLinkedList() {
		curr = new ListNode<E>(null); //initialize curr to an empty node
		size = 0; // initialize size to reflect the list is empty
	}

	/**
	 * Adds the given item to the current position in list.
	 *
	 * @param item the item to add
	 */
	@Override
	public void add(E item) {
		ListNode<E> newNode = new ListNode<E>(item); 
		
		//checks if the list is empty first because it would require different
		//links, otherwise continues as normal
		if(isEmpty())
		{
			newNode.setNext(newNode);
			newNode.setPrevious(newNode);
			curr = newNode;
		}
		else
		{
			newNode.setNext(curr);
			curr.getPrevious().setNext(newNode);
			newNode.setPrevious(curr.getPrevious());
			curr.setPrevious(newNode);
			curr = newNode;
		}
		size++;
	}

	/**
	 * Removes the item at current position.
	 *
	 * @return the element that was removed from the list.
	 * @throws ElementNotFoundException if the list is empty
	 */
	@Override
	public E remove() throws ElementNotFoundException {
		//checks if we are trying to remove from an empty list
		if(isEmpty())
			throw new ElementNotFoundException();
		
		//saves the data from the node to be removed
		E removedNode = curr.getData();
		
		//sets the links from before and after the node to skip it in
		//the sequence
		curr.getPrevious().setNext(curr.getNext());
		curr.getNext().setPrevious(curr.getPrevious());
		
		//sets curr to be the next node, 'shifting' the nodes down
		curr = curr.getNext();
		
		size--;
		
		return removedNode;
	}

	/**
	 * Returns the item at an offset from the current position.
	 *
	 * @param offset the offset of the item to return from the current position
	 * @return the item at the given position
	 * @throws ElementNotFoundException if the list is empty
	 */
	@Override
	public E get(int offset) throws ElementNotFoundException {
		//checks if we are trying to traverse an empty list
		if (isEmpty())
			throw new ElementNotFoundException();

		//make a new dummy node to traverse the list without
		//losing curr's position
		ListNode<E> node = curr;
		
		//loop that traverses the list to get the node to be returned
		if (offset > 0)
		{
			for (int i = 0; i < offset; i ++)
				node = node.getNext();
		}
		else if (offset < 0)
		{
			for (int i = 0; i > offset; i--)
				node = node.getPrevious();
		}

		return node.getData();
	}

	/**
	 * Determines if this List is empty.
	 *
	 * @return true if the List is empty; false otherwise
	 */
	@Override
	public boolean isEmpty() {
		if (size == 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns the number of items in this List.
	 *
	 * @return the number of items in this List
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Offsets the current position by the specified value. Has no effect if
	 * the list is empty.
	 *
	 * @param offset the offset by which the current position to be adjusted
	 */
	@Override
	public void setCurrentPosition(int offset) {
		//checks if we are trying to traverse an empty list by an
		//unnecessarily large amount
		if (!isEmpty())
			offset = offset % size();
		
		//traverses the list, using curr, either backwards or forwards
		if (offset > 0)
		{
			for (int i = 0; i < offset; i++)
				curr = curr.getNext();
		}
		else if (offset < 0)
		{
			for (int i = 0; i > offset; i--)
				curr = curr.getPrevious();
		}
	}

	/**
	 * Returns the contents of this List as a formatted String.
	 * 
	 * @param offset the offset from where to start printing the list
	 * @return the contents of the list as a String
	 */
	public String print(int offset) {
		//checks if we are trying to traverse the list by an
		//unnecessarily large amount
		if (!isEmpty())
			offset = offset % size();
		
		//creates a node to traverse the list without destroying curr
		ListNode<E> checker = curr;
		String names = "";
		
		//check if we are moving forward or backwards
		if (offset > 0)
		{
			for (int i = 0; i < offset; i++)
				checker = checker.getNext();
		}
		else if (offset < 0)
		{
			for (int i = offset; i < 0; i++)
				checker = checker.getPrevious();
		}
		
		//combines the strings in each node's data to return as one String
		for (int i = 0; i < size(); i++)
		{
			if (i != 0) 
			{
				names += checker.getNext().getData().toString() + "\n";
				checker = checker.getNext();
			}
			else 
				names += checker.getData().toString() + "\n";
		}
		
		return names;
	}
}
