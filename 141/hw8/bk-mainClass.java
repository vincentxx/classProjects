//Name: VU Q TRAN
//ID:   48894667
//CS 141, HW 08

import java.util.* ;
import java.lang.Object.*;
import java.lang.Math.* ;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.StringBuffer;
import java.util.Hashtable;
import java.io.FileNotFoundException;


class Disk {
   static final int NUM_SECTORS = 1024;
   String name;
   StringBuffer sectors[];
   Disk (String name) {
      this.name = name;
      sectors = new StringBuffer[NUM_SECTORS];
   }

   void write(int sector, StringBuffer data) {
      sectors[sector] = data;   
   }
   StringBuffer read(int sector) {
       return sectors[sector];
   }

}

class DiskManager { //keep track disk sector and contains DirectoryManager to finding file sectors on Disk
   String name;
   Disk diskArray[];
   DirectoryManager dirManager;
   int[] nextFreeSector;
   DiskManager (String name) {
      this.name = name;
      diskArray = new Disk[2];
      nextFreeSector = new int[2];
      nextFreeSector[0] = 0;
      nextFreeSector[1] = 0;
      dirManager = new DirectoryManager(name);
   }
  void write (String fileName, StringBuffer content) { //UserThread object call this func to save data to fileName
  //Ask directory Manager to locate the file
  //Allocate the disk free sector to save file
  //Copy the content to the disk, updated the free sector to DiskManager
   FileInfo fileInfo = dirManager.lookup(fileName);
   if (fileInfo == null) {
       fileInfo = new FileInfo(1, nextFreeSector[1], 1);
       dirManager.enter(fileName,fileInfo);
       diskArray[1].write(nextFreeSector[1], content);
       nextFreeSector[1] +=1;
       //todo more here  
   }
   else {
      diskArray[fileInfo.diskNumber].write(fileInfo.startingSector,content);
      //error here
   }

  }
  void print (String fileName) {
    FileInfo fileInfo = dirManager.lookup(fileName);
    for (int i=0; i < fileInfo.fileLength; i++) {
       System.out.println("Disk 1: " + diskArray[1].sectors[fileInfo.startingSector + i]);
    }
  }

}

class DirectoryManager { //use HashTable storing mapping filename-disk sectors

  String name;
  Hashtable<String, FileInfo> Table ;
  DirectoryManager(String name) {
     this.name = name;
     Table = new Hashtable<String, FileInfo>();
  }
  void enter (String fileName, FileInfo fileinfo) {
    this.Table.put(fileName, fileinfo);
  }

  FileInfo lookup(String fileName) {
     return Table.get(fileName);
  }
}

class FileInfo {
   public int diskNumber;
   public int startingSector;
   public int fileLength;
   FileInfo(int diskNumber, int startingSector, int fileLength) {
      this.diskNumber = diskNumber;
      this.startingSector = startingSector;
      this.fileLength = fileLength;
   }
}

class UserThread extends Thread {
   String name ;
   StringBuffer buffer;
   String fileName; //input file contains the sequence commands for the thread to run
   int action; //1:saving, 2:print, 0:init
   File fin;
   Scanner scanFile;
   DiskManager diskMgr;
   UserThread(String name, DiskManager diskMgrPass) {
      this.name = name;
      this.diskMgr = diskMgrPass;
   }
   void execute(String fileName) throws FileNotFoundException { //exec the action with buffer data
      //open the file
      //for each line in file do
      //  if line[0] == .save then start to writing each line until .end
      //  if line[0] == .print then print     
      this.fin = new File(fileName);
      String line;
      String lineContent;
      scanFile = new Scanner(fin);
      while (scanFile.hasNextLine()) {
         line = scanFile.nextLine();
         System.out.println(line);
         String[] pattern = line.split(" ");
         System.out.println(Arrays.toString(pattern));

         if (pattern[0] == ".save") {
            //this.writeToDisk(); 
            lineContent = scanFile.nextLine();
            String[] content = lineContent.split(" ");
            while (content[0] != ".end") {
              StringBuffer message = new StringBuffer(lineContent);
              diskMgr.write(pattern[1], message); //diskMgr.write(fileName, StringBuffer content)
              lineContent = scanFile.nextLine();
              content = lineContent.split(" ");
            }    
         }
         else if (pattern[0] == ".print") {
             diskMgr.print(pattern[1]); 
         }
      }
   }
}

class Printer {
   
}

public class mainClass {
   public static int NUMBER_OF_USERS = 4;
   public static int NUMBER_OF_DISKS = 2;
   public static int NUMBER_OF_PRINTERS = 3;

   public static void main (String[] args) {
      DiskManager diskMgr = new DiskManager("diskManager");
      UserThread user1 = new UserThread("USER1", diskMgr);
      try {
        user1.execute("USER1");
      }
      catch (FileNotFoundException ex) {
         System.out.println("Error: " + ex);
      }
   }
}
