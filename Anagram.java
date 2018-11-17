import java.io.*;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;

public class Anagram
{
	private DictInterface D;
	private String[]storage;//set up 5 storage points, and store them based on the level
	private int storageIndex;
	private String[]storage2;
	private int storageIndex2;
	private String[]storage3;
	private int storageIndex3;
	private String[]storage4;
	private int storageIndex4;
	private String[]storage5;
	private int storageIndex5;

	public Anagram(String option)throws IOException{
		Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
		String st;
		StringBuilder sb;
			if(option.equals("orig")){
				D=new MyDictionary();
			}
			else if(option.equals("dlb")){
				D=new DLB();
			}
			else{
				System.out.println("error input, System forced end");
				System.exit(0);
			}
		while (fileScan.hasNext())
		{
			st = fileScan.nextLine();
			D.add(st);
		}
		storage=new String[15];
		storageIndex=0;
		storage2=new String[40];
		storageIndex2=0;
		storage3=new String[50];
		storageIndex3=0;
		storage4=new String[70];
		storageIndex4=0;
		storage5=new String[70];
		storageIndex5=0;
	}
	
	public void resultWordsInList(ArrayList<String> myList,String outputFileN){//implementing the recursive function here to search string builder char by chars and get the permutation of words	
		int level=0;
		for(int i=0;i<myList.size();i++){
			long  startTime=System.nanoTime();
			String newSt1 = new String(/*newStList[i]*/myList.get(i));
			StringBuilder myStringB=new StringBuilder();
			String newSt=null;
			String prevWords=null;
			if(newSt1.contains(" ")){
				newSt=newSt1.replaceAll("\\s+","");
			}
			else{
				newSt=newSt1;
			}
			resultWordsInWords(newSt,myStringB,level,prevWords,outputFileN,newSt);
			long  endTime=System.nanoTime();
			long  usedTime=endTime-startTime;
			System.out.println("time used for "+newSt+" is "+usedTime);
		}
	}

	public void writeFile(String prevWords, StringBuilder myWord,String outputFileN,int level,String originalSt ){
		try{
			PrintWriter writer = new PrintWriter(new FileWriter(outputFileN, true)/*outputFileN, "UTF-8"*/);
				if(level==0){
					writer.println(originalSt+" the one word solution is "+myWord);
				}
				else if(level==1){
					writer.println();					
					writer.println(originalSt+" the two words solution is "+prevWords+" and "+myWord);
				}
				else if(level==2){
					writer.println();
					writer.println(originalSt+" the three words solution is "+prevWords+" and "+myWord);
				}
				else if(level==3){
					writer.println();					
					writer.println(originalSt+" the four words solution is "+prevWords+" and "+myWord);	
				}
				else if(level==4){
					writer.println();					
					writer.println(originalSt+" the five words solution is "+prevWords+" and "+myWord);
				}
			writer.close();
		} catch (IOException e) {
			System.out.println("print not successful due to some I/O errors");
		}
	}
	
	//need to remove the duplicate and alphabetical order, think abt it 
	public void resultWordsInWords(String newSt, StringBuilder myWord,int level,String prevWords,String outputFileN,String originalSt){//i also heard some one talk about the permutation, think about it later
		
		if(newSt.length() == 0)    {
			int secondAns=D.searchPrefix(myWord,0);
			if(secondAns==2||secondAns==3){
				boolean duplicate=false;
					if(level==0){
						for(int i=0;i<storageIndex;i++){
							if(storage[i].equals(myWord.toString())){
								duplicate=true;
							}
						}	
						if(!duplicate){
							writeFile( prevWords,  myWord, outputFileN, level,originalSt);
							storage[storageIndex]=myWord.toString();
							storageIndex++;
						}	
					}
					if(level==1){
						for(int i=0;i<storageIndex2;i++){
							if(storage2[i].equals(myWord.toString())){
								duplicate=true;
							}
						}	
						if(!duplicate){
							writeFile( prevWords,  myWord, outputFileN, level,originalSt);
							storage2[storageIndex2]=myWord.toString();
							storageIndex2++;
						}	
					}
					if(level==2){
						for(int i=0;i<storageIndex3;i++){
							if(storage3[i].equals(myWord.toString())){
								duplicate=true;
							}
						}	
						if(!duplicate){
							writeFile( prevWords,  myWord, outputFileN, level,originalSt);
							storage3[storageIndex3]=myWord.toString();
							storageIndex3++;
						}	
					}
					if(level==3){
						for(int i=0;i<storageIndex4;i++){
							if(storage4[i].equals(myWord.toString())){
								duplicate=true;
							}
						}	
						if(!duplicate){
							writeFile( prevWords,  myWord, outputFileN, level,originalSt);
							storage4[storageIndex4]=myWord.toString();
							storageIndex4++;
						}						}
					if(level==4){
						for(int i=0;i<storageIndex5;i++){
							if(storage5[i].equals(myWord.toString())){
								duplicate=true;
							}
						}	
						if(!duplicate){
							writeFile( prevWords,  myWord, outputFileN, level,originalSt);
							storage5[storageIndex5]=myWord.toString();
							storageIndex5++;
						}	
					}
			}
        }
		int i=0;
        while( i < newSt.length()) {//i is the index to add the next				
			StringBuilder temp=new StringBuilder();
			int length=newSt.length();
			temp.append(myWord);
			temp.append(newSt.charAt(i));
			int ans=D.searchPrefix(myWord, length);
				if(ans==0){//no result, go back
						if(myWord.length()!=0){
							i=newSt.length();//in order to skip
						}
						else{//just start case, ignore this one and keep adding 
							resultWordsInWords(newSt.substring(0, i) + newSt.substring(i+1, newSt.length()), temp,level,prevWords,outputFileN,originalSt);
						}
				}
				if(ans==1){//a prefix, keep adding word
				    resultWordsInWords(newSt.substring(0, i) + newSt.substring(i+1, newSt.length()), temp,level,prevWords,outputFileN,originalSt);
				}
				if(ans==3){//a prefix and a word, treat it as a prefix right now
					level=prefixAndWordS(myWord,newSt,level,prevWords,outputFileN,originalSt);							
					resultWordsInWords(newSt.substring(0, i) + newSt.substring(i+1, newSt.length()), temp,level,prevWords,outputFileN,originalSt);
				}
				i++;
        }
	}
	
	public int prefixAndWordS(StringBuilder myWord,String newSt,int level,String prevWords,String outputFileN,String originalSt){//not pruning enough for the 3 and 4 part, still have too many overlapping words in the screen
		StringBuilder newStringB=new StringBuilder();
		level++;
		StringBuilder newBuilder=new StringBuilder();
		if(prevWords!=null){
			newBuilder.append(prevWords).append(" ").append(myWord.toString());//i think the error is here, why did you append myWord with myWord?
		}
		else{
			newBuilder.append(myWord.toString());
			 prevWords =myWord.toString();
		}
		
		resultWordsInWords(newSt, newStringB,level,newBuilder.toString(),outputFileN,originalSt);
		level--;
		return level;
	}
	
		public static void main(String [] args) throws IOException
		{
			if (args.length != 3){
				System.out.println("wrong length, should put 3 args");
			}
			else{
				Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"/*"test1.txt"*/));
				String st;
				String inputFileN=args[0];
		
				//System.out.println("input fileN "+inputFileN);
				try{
					String outputFileN=args[1];
					String option=args[2];
					Scanner fileScan1 = new Scanner(new FileInputStream(/*"test4.txt"*/inputFileN));//didn't have enough time to do the file out check,so if it is not txt, it might have some errors
					String myString;
					ArrayList<String> myList = new ArrayList<String>();
						while (fileScan1.hasNext())
						{
							myString = fileScan1.nextLine();
							myList.add(myString);
						}
					Anagram myAna=new Anagram(option);				   
					myAna.resultWordsInList(myList,outputFileN);
					
				}catch(FileNotFoundException e){
					System.out.println("assigned file not found");
				}
					
			}		
	}
}

