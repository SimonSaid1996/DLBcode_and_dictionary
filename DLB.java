// CS 1501 Summer 2017
// Test program for DictInterface.  Use this program to see how the DictInterface
// works and also to test your DLB implementation of the DictInterface (for Part B
// of Assignment 1).  See the posted output file for the correct output.
// As written the program uses MyDictionary, so it will work and show you the
// output using the MyDictionary class.  To test your DBL class substitute a 
// DLB object as indicated below.  Note that since MyDictionary and DLB should
// both implement DictInterface no other changes are necessary for the program
// to work.

//read through the slide about multiway tries the structure on the slide is a different one as yourss
import java.io.*;
import java.util.*;
public class DLB implements DictInterface
{
	
	 private boolean DLBisEmpty;//check if it is the end of the file
     private Node firstNode;//the file node
	 private int stringIndex;//the index of a chart in the string 	
	 private boolean shouldBreak;
	 private boolean addToNextNode;
	 private boolean NotAddingNextLev;
	 private char prevChar;
	public DLB(){
		stringIndex=0;
		DLBisEmpty=true;
		shouldBreak=false;
		NotAddingNextLev=false;
		addToNextNode=false;//not adding a string to the next node position
		firstNode=null;
		prevChar=' ';
	}
	
	public boolean curEqualChar(Node Cur, char curChar){
		if(Cur.value==curChar)
			return true;
		return false;
	}
	
	public Node moveRight(Node Cur,char curChar){//i have negalected the fact that two chars can be the same.......
		NotAddingNextLev=false;//default reset
		//System.out.println(" char is "+curChar+" CURCHAR IS "+Cur.value+" stringIndex is "+stringIndex);
		if(!curEqualChar(Cur,curChar)){
			if(Cur.next!=null){//not the same char, move to the next char in the same level
				Cur=Cur.next;
				//System.out.println("horizontal change into "+Cur.value);
			}
			else{//this one should go to cur.next and break
				shouldBreak=true;
					if(!NotAddingNextLev){
						addToNextNode=true;
					}
			}
		}
		else{
			Cur=moveDown(Cur,curChar);
		}
		//NotAddingNextLev=true;//reset
	return Cur;
	}
	
	public Node moveDown(Node Cur,char curChar){
			if(Cur.nextLev!=null){
				Cur=Cur.nextLev;
				NotAddingNextLev=true;//special cases, the previous char matches but the later chars might not match	
				//System.out.println("stringindex increased cur is "+Cur.value);
			}
			else{//nothing here, need to add the new line,
			//logically speaking, the error is here, but how??
				/*Node temp=new Node(null,null,' ');
				Cur.nextLev=temp;
				Cur=temp;*/
				shouldBreak=true;
				//System.out.println("prepare to break and curchar is "+curChar);
			}
			stringIndex++;
	return Cur;
	}
	
	public void printDLB(){
		System.out.println("currentline is "+firstNode.value);
		Node curNode=firstNode;
		while(curNode.next!=null){
			System.out.println("node is "+curNode.value);
			curNode=curNode.next;
		}
		System.out.println("node is "+curNode.value);
		while(curNode.nextLev!=null){
			System.out.println("next leve node is "+curNode.value);
			curNode=curNode.nextLev;
		}
		System.out.println("next leve node is "+curNode.value);
		//System.out.println("node is "+curNode.value);
	}
	public boolean add(String newSt){//adding from the end
	//System.out.println("adding "+newSt);
	boolean addSucceed=false;
		if(DLBisEmpty){
			Node myNode=new Node(null,null,newSt.charAt(newSt.length()-1));
			myNode.nextLev=firstNode;//adding the node in the end
			myNode.isEnd=true;
            firstNode=myNode;
			for(int i=newSt.length()-2;i>=0;i--){//to access every elements inside of the newSt
                Node NextLN=new Node(null,null,newSt.charAt(i));//to insert the new node
                NextLN.nextLev=firstNode;//adding the node in the end
                firstNode=NextLN;
				
             }
			//firstNode.isEnd=true;//the first node ends in the tail of the first string 
			DLBisEmpty=false;//the first node added	
			//System.out.println("already added "+firstNode.value);
		}
		else{
		Node curNode=firstNode;
			while(stringIndex<=newSt.length()-1){
				curNode=moveRight(curNode,newSt.charAt(stringIndex));
				if(shouldBreak){
					//System.out.println("break the loop");
					break;
				}			
			}	
		shouldBreak=false;//reset	
		int restLength=newSt.length()-1-stringIndex;
		int newStIndex=newSt.length()-1;
		//if(newSt.equals("yachting")||newSt.equals("yachts")){
			//System.out.println("currendt nod is "+curNode.value+" restlength is "+restLength+" newStIndex "+newStIndex+" current string is "+newSt);
		//}
			if(addToNextNode){
				//if(newSt.equals("yachting")){System.out.println("hello");}
				//System.out.println("newINDEX IS "+newStIndex+" and restlength is "+restLength);
				char addedChar=newSt.charAt(newStIndex);
				Node newStartNode=new Node(null,null,addedChar);
				newStartNode.isEnd=true;
				newStIndex--;
					while(newStIndex>=stringIndex){//adding up the list
						addedChar=newSt.charAt(newStIndex);
						Node addedNode=new Node(null,null,addedChar);
						addedNode.nextLev=newStartNode;
						newStartNode=addedNode;
						newStIndex--;
						//System.out.println("new st is "+newSt+" current node is "+addedChar+ " index is "+newStIndex+" currentNode is "+addedNode.value);
					}
				curNode.next=newStartNode;
				//printDLB();
				//System.out.println("curNode is "+curNode.value);
				//addToNextNode=false;//reset
				stringIndex=0;//reset
				addSucceed=true;
			}
			//else{System.out.println("newstindex is "+newStIndex+" restLength is "+restLength+" string is "+newSt+" currentNode is\n "+curNode.value+" string length is "+newSt.length());}
			else{//situation where we need to add the entire string to the next node
				//if(newSt.equals("yachts")){System.out.println("hello");}
				char addedChar=newSt.charAt(newStIndex);
				Node newStartNode=new Node(null,null,addedChar);
				newStartNode.isEnd=true;
				newStIndex--;
				//System.out.println("firs dded is "+addedChar+" the length is "+newStIndex);
				//newStIndex--;
					while(newStIndex>=stringIndex){//adding up the list
						//System.out.println("the length is "+newSt.length());	
						addedChar=newSt.charAt(newStIndex);
						Node addedNode=new Node(null,null,addedChar);
						addedNode.nextLev=newStartNode;
						newStartNode=addedNode;
						newStIndex--;
						//System.out.println("newStIndex "+newStIndex+" the other length is "+restLength+" string is "+newSt+" added  char is "+addedChar);
					}
					curNode.nextLev=newStartNode;//add the next node
					//printDLB();
					stringIndex=0;//reset
				//System.out.println(" currentNode is "+curNode.value+"new st is "+newSt+" current node is "+addedChar+ " index is "+newStIndex);
				//}
				addSucceed=true;
			}
			addToNextNode=false;//reset
		}
		return addSucceed;
		//System.out.println("the value is "+curNode.value);*/
	}//there are two adding situations, adding from the next node and add from the nextLev
	
	public boolean inSameRow(){
	    boolean isInTheSameRow=false;
		return isInTheSameRow;
	}
	
	public int searchPrefix(StringBuilder m,int length){//search(String userPass,pw_check myTree)throws IOException
		int status=0; //need to be able to return 0,1,2...
		Node curNode=firstNode;
		boolean matched=true;
			if(m.length()!=0){//if m is null, return 0 not found directly
				while(stringIndex<=m.length()-1){
					if(curNode.value==m.toString().charAt(m.length()-1)&&m.length()-1==stringIndex){
						break;
					}
					if(curNode.value!=m.toString().charAt(stringIndex)){
						if(curNode.next!=null){
							curNode=curNode.next;
							//System.out.println("go right");
							//System.out.println("changing the stringindex into "+stringIndex+" curnode is "+curNode.value);
						}
						else{
							//System.out.println("no hope");
							matched=false;
							/*if(m.toString().equals("spn")){
								System.out.println("no hope");
							}*/		
							break;
						}
					}
					else{
						if(curNode.nextLev!=null/*&&curNode.value!=m.toString().charAt(stringIndex)*/){//in case of null pointer
							curNode=curNode.nextLev;
						}
						stringIndex++;	
					}
				}
				
			if(matched){
				if(0==length&&curNode.isEnd){
					status=2;
				}
				else if((length>=0)&&curNode.nextLev!=null&&curNode.isEnd){
					status=3;
				}
				else if(curNode.nextLev!=null&&0<=length){
					status=1;
				}
				
			}	
				
			}
		stringIndex=0;//reset		
		return status;
	}
	
	public int searchPrefix(StringBuilder s, int start, int end){
		int result=0;
		return result;
	}
		
	private class Node{
       private Node nextLev;//the level goes depper
       private Node next;//to go to the next node
       private boolean isEnd=false;//to check if the words end
       private char value;//the char value of the word	   
       
       private Node(Node nextL,Node nextN,char currentVal){
       this.nextLev=nextL;
       this.next=nextN;
       this.value=currentVal;
       }
       
       
    }
}
