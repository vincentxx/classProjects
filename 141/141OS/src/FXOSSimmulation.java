/*  Name: VU QUOC TRAN
    Class: 141HW08 + 09 
    141OS GUI Simulation
 */
package fxossimmulation;

//import for back-end program
import java.util.*;
import java.lang.Object.*;
import java.lang.Math.*;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.lang.StringBuffer;
import java.util.Hashtable;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;
//Reference
//https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html
//https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
//https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html#MILLISECONDS
//https://docs.oracle.com/javase/7/docs/api/java/io/BufferedWriter.html
//https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/

//import for front-end GUI
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.*;
import javafx.geometry.Pos;
import javafx.geometry.*;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author vuqt1
 */
public class FXOSSimmulation extends Application {

    Button startButton = new Button("Start");
    GridPane grid = new GridPane();
    Scene scene = new Scene(grid, 1200, 550);
    Insets insets = new Insets(25, 25, 25, 25);
    int startCol = 1, startRow = 2;
    Text scenetitle = new Text("Welcome 141OS Simple Simulation");
    int textLength = 1000;
    Label user4Label = new Label("User4:");
    TextField user4TextField = new TextField();
    Label user1Label = new Label("User1:");
    TextField user1TextField = new TextField();
    Label user2Label = new Label("User2:");
    TextField user2TextField = new TextField();
    Label user3Label = new Label("User3:");
    TextField user3TextField = new TextField();
    Label disk0Label = new Label("Disk0:");
    TextField disk0TextField = new TextField();
    Label disk1Label = new Label("Disk1:");
    TextField disk1TextField = new TextField();
    Label printer0Label = new Label("Printer0:");
    TextField printer0TextField = new TextField();
    Label printer1Label = new Label("Printer1:");
    TextField printer1TextField = new TextField();
    Label printer2Label = new Label("Printer2:");
    TextField printer2TextField = new TextField();

    //back-end
    @Override
    public void start(Stage primaryStage) {

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                startTask();
            }
        });

        //Setup the component display on the main window
        //Then will display it by realtime
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(insets);

        //text and labels
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Adding stuff on the grid
        grid.add(startButton, 0, 1);
        //User0 on the same row, 
        grid.add(user4Label, 0, startRow + 3);
        grid.add(user4TextField, 1, startRow + 3);
        user4TextField.setPrefWidth(textLength);
        user4TextField.clear();
        user4TextField.setText("Display User4 tracking activities .....");

        //User1 on the same row, 
        grid.add(user1Label, 0, startRow);
        grid.add(user1TextField, 1, startRow);
        user1TextField.setPrefWidth(textLength);
        user1TextField.clear();
        user1TextField.setText("Display User1 tracking activities .....");

        //User2 on the same row, 
        grid.add(user2Label, 0, startRow + 1);
        grid.add(user2TextField, 1, startRow + 1);
        user2TextField.setPrefWidth(textLength);
        user2TextField.clear();
        user2TextField.setText("Display User2 tracking activities .....");

        //User3 on the same row, 
        grid.add(user3Label, 0, startRow + 2);
        grid.add(user3TextField, 1, startRow + 2);
        user3TextField.setPrefWidth(textLength);
        user3TextField.clear();
        user3TextField.setText("Display User3 tracking activities .....");

        //disk0 on the same row, 
        grid.add(disk0Label, 0, startRow + 4);
        grid.add(disk0TextField, 1, startRow + 4);
        disk0TextField.setPrefWidth(textLength);
        disk0TextField.clear();
        disk0TextField.setText("Display Disk0 tracking activities .....");
        //User3 on the same row, 
        grid.add(disk1Label, 0, startRow + 5);
        grid.add(disk1TextField, 1, startRow + 5);
        disk1TextField.setPrefWidth(textLength);
        disk1TextField.clear();
        disk1TextField.setText("Display Disk1 tracking activities .....");

        //Printer0 on the same row, 
        grid.add(printer0Label, 0, startRow + 6);
        grid.add(printer0TextField, 1, startRow + 6);
        printer0TextField.setPrefWidth(textLength);
        printer0TextField.clear();
        printer0TextField.setText("Display Printer0 tracking activities .....");

        //Printer1 on the same row, 
        grid.add(printer1Label, 0, startRow + 7);
        grid.add(printer1TextField, 1, startRow + 7);
        printer1TextField.setPrefWidth(textLength);
        printer1TextField.clear();
        printer1TextField.setText("Display Printer1 tracking activities .....");

        //Printer2 on the same row, 
        grid.add(printer2Label, 0, startRow + 8);
        grid.add(printer2TextField, 1, startRow + 8);
        printer2TextField.setPrefWidth(textLength);
        printer2TextField.clear();
        printer2TextField.setText("Display Printer2 tracking activities .....");

        primaryStage.setScene(scene);
        primaryStage.setTitle("141OS Simple GUI ");
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void startTask() {
        // Create a Runnable
        Runnable task = new Runnable() {
            public void run() {
                runTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    /*HERE IS THE BACK-END */
    public void runTask() {
        Disk disk0 = new Disk("Disk0");
        Disk disk1 = new Disk("Disk1");
        Printer printer0 = new Printer("Printer0");
        Printer printer1 = new Printer("Printer1");
        Printer printer2 = new Printer("Printer2");
        PrinterManager printerMgr = new PrinterManager("PrinterManager0", printer0, printer1, printer2);
        ResourceManager resourceMgr = new ResourceManager("ResourceManager0", disk0, disk1, printerMgr);
        UserThread user1 = new UserThread("USER1", resourceMgr, "inputs/USER1"); //second USER1 is the input filename
        UserThread user2 = new UserThread("USER2", resourceMgr, "inputs/USER2");
        UserThread user3 = new UserThread("USER3", resourceMgr, "inputs/USER3");
        UserThread user4 = new UserThread("USER4", resourceMgr, "inputs/USER4");
        System.out.println("141OS Start Simulation !!");
        user1.start(); //start() call run() java threading //USER1 is the input filename, see hw desciption.
        user2.start();
        user3.start();
        user4.start();
    }

    /**
     * ***BACK END*******************************************
     */
    class Disk { //Simple Disk class, assume disk space is infinite(1024), do not handle free space & re-write

        static final int NUM_SECTORS = 1024;
        public String name;
        public boolean isBusy;
        StringBuffer sectors[];
        public int nextFreeSector;

        Disk(String name) {
            this.name = name;
            sectors = new StringBuffer[NUM_SECTORS];
            nextFreeSector = 0;
            isBusy = false;
        }

        public synchronized void write(Block block) {
            //todo print and sleep here
            this.isBusy = true;
            for (int i = 0; i < block.size; i++) {
                //todo sleep
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupt");
                }
                System.out.println(name + " writing sector " + nextFreeSector + " : " + block.contents[i]);
                //Display on GUI
                if (name.equals("Disk0")) {
                    disk0TextField.setText("writing sector number " + nextFreeSector + " : " + block.contents[i]);
                } else {
                    disk1TextField.setText("writing sector number " + nextFreeSector + " : " + block.contents[i]);
                }
                sectors[this.nextFreeSector] = block.contents[i];
                nextFreeSector += 1;
            }
            this.isBusy = false;
            if (name.equals("Disk0")) {
                disk0TextField.setText("done");
            } else {
                disk1TextField.setText("done");
            }
        }

        public synchronized StringBuffer read(int sector) {
            //todo print and sleep here
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupt");
            }
            //Display on GUI
            if (name.equals("Disk0")) {
                disk0TextField.setText("reading sector: " + nextFreeSector + " : " + sectors[sector]);
            } else {
                disk1TextField.setText("reading sector: " + nextFreeSector + " : " + sectors[sector]);
            }

            return sectors[sector];
        }

        public synchronized void done() {
            if (!this.isBusy) {
                if (name.equals("Disk0")) {
                    disk0TextField.setText("done");
                } else {
                    disk1TextField.setText("done");
                }
            }
        }

    }

    class DirectoryManager { //use HashTable storing mapping filename-disk sectors

        String name;
        Hashtable<String, FileInfo> Table;

        DirectoryManager(String name) {
            this.name = name;
            Table = new Hashtable<String, FileInfo>();
        }

        void enter(String fileName, FileInfo fileinfo) {
            this.Table.put(fileName, fileinfo);
        }

        FileInfo lookup(String fileName) {
            return Table.get(fileName);
        }
    }

    class FileInfo {

        Disk disk;
        public int startingSector;
        public int fileLength;
        public String fileName;

        FileInfo(Disk disk, int startingSector, int fileLength, String fileName) {
            this.disk = disk;
            this.startingSector = startingSector;
            this.fileLength = fileLength;
            this.fileName = fileName;

        }
    }

    class Block {

        public String fileName;
        public int size;
        public StringBuffer contents[];

        Block(String fileName) {
            this.fileName = fileName;
            size = 0;
            contents = new StringBuffer[1024];
        }

        void add(StringBuffer sector) {
            contents[size] = sector;
            size += 1;
        }
    }

    class ResourceManager {

        public String name;
        public Disk disk0;
        public Disk disk1;
        public DirectoryManager dirManager;
        public PrinterManager printerMgr;

        ResourceManager(String name, Disk disk0, Disk disk1, PrinterManager printerMgr) {
            this.disk0 = disk0; //reference only, not duplicate objects
            this.disk1 = disk1;
            dirManager = new DirectoryManager("DirectorManager0");
            this.printerMgr = printerMgr;
        }

        public synchronized Disk requestDisk(Block block) {
            //find the size of block
            //find which disk is free and available for the size
            //generate FileInfo to saving in Dir Manager
            //return that Disk number 
            while (true) {
                if (!disk0.isBusy) {
                    FileInfo tmpFileInfo = new FileInfo(disk0, disk0.nextFreeSector, block.size, block.fileName);
                    dirManager.enter(block.fileName, tmpFileInfo);
                    return disk0;
                } else if (!disk1.isBusy) {
                    FileInfo tmpFileInfo = new FileInfo(disk1, disk1.nextFreeSector, block.size, block.fileName);
                    dirManager.enter(block.fileName, tmpFileInfo);
                    return disk1;
                }
                //stuck here so wait
                try {
                    this.wait();
                } //wait() tells the calling thread to give up the monitor 
                //and go to sleep until some other thread enters the same monitor and calls notify( ).
                catch (InterruptedException ie) {
                    System.out.println("Interrupt Exception");
                }

            }
        }

        public synchronized void releaseDisk() {
            this.notify(); //notify() wakes up the first thread that called wait() on the same object.
        }

        public FileInfo requestFileInfo(String filename) {
            return dirManager.lookup(filename);
        }
    }

    class UserThread extends Thread {

        String name;
        StringBuffer buffer;
        String fileName; //input file contains the sequence commands for the thread to run
        File fin;
        Scanner scanFile;
        public ResourceManager resourceMgr;

        UserThread(String name, ResourceManager resourceMgr, String fileName) {
            this.name = name;
            this.resourceMgr = resourceMgr;
            this.fileName = fileName;
        }

        public void run() { //overwrite run() for multithreading execution
            //open the file
            //for each line in file do
            //  if line[0] == .save then start to writing each line until .end
            //  if line[0] == .print then print     
            this.fin = new File(this.fileName);
            String line;
            String lineContent;
            try {
                scanFile = new Scanner(fin);
            } catch (FileNotFoundException ex) {
                System.out.println("Error: " + ex);
            }

            while (scanFile.hasNextLine()) {
                line = scanFile.nextLine();
                // System.out.println(line);
                String[] pattern = line.split(" ");
                //System.out.println(Arrays.toString(pattern));
                if (pattern[0].equals(".save")) { //detect a write to file
                    System.out.println("UserThread " + name + " start writing to file " + pattern[1]);

                    //Display GUI
                    if (name.equals("USER1")) {
                        user1TextField.setText("writing to file " + pattern[1]);
                    } else if (name.equals("USER2")) {
                        user2TextField.setText("writing to file " + pattern[1]);
                    } else if (name.equals("USER3")) {
                        user3TextField.setText("writing to file " + pattern[1]);
                    } else if (name.equals("USER4")) {
                        user4TextField.setText("writing to file " + pattern[1]);
                    }

                    lineContent = scanFile.nextLine();
                    String[] content = lineContent.split(" ");

                    //Prepare for a file block 
                    Block block = new Block(pattern[1]);

                    while (!content[0].equals(".end")) {
                        StringBuffer message = new StringBuffer(lineContent);
                        block.add(message);
                        lineContent = scanFile.nextLine();
                        content = lineContent.split(" ");
                    }
                    //We have a block to write now, call Resource Manager to write it to disk
                    Disk disk = resourceMgr.requestDisk(block);
                    //got a disk number, then parallel writing to it
                    disk.write(block);
                    resourceMgr.releaseDisk();
                } else if (pattern[0].equals(".print")) {
                    System.out.println(name + " print " + pattern[1]);
                    //Display GUI
                    if (name.equals("USER1")) {
                        user1TextField.setText("asking printing job to print file " + pattern[1]);
                    } else if (name.equals("USER2")) {
                        user2TextField.setText("asking printing job to print file " + pattern[1]);
                    } else if (name.equals("USER3")) {
                        user3TextField.setText("asking printing job to print file " + pattern[1]);
                    } else if (name.equals("USER4")) {
                        user4TextField.setText("asking printing job to print file " + pattern[1]);
                    }

                    PrintJobThread printThread = new PrintJobThread(resourceMgr, pattern[1]);
                    printThread.start();
                }
            }
            //Display GUI
            if (name.equals("USER1")) {
                user1TextField.setText("User thread done");
            } else if (name.equals("USER2")) {
                user2TextField.setText("User thread done");
            } else if (name.equals("USER3")) {
                user3TextField.setText("User thread done");
            } else if (name.equals("USER4")) {
                user4TextField.setText("User thread done");
            }
        }
    }

    class PrintJobThread extends Thread {

        FileInfo fileInfo;   //fileinfo to print contains disk number, start, length, etc.
        ResourceManager resourceMgr;
        PrinterManager printerMgr;

        PrintJobThread(ResourceManager resourceMgr, String fileName) {
            this.resourceMgr = resourceMgr;
            this.fileInfo = resourceMgr.requestFileInfo(fileName);
            this.printerMgr = resourceMgr.printerMgr;
        }

        public void run() {
            System.out.println("Starting PrintJobThread...");
            Printer printer = printerMgr.requestPrinter();
            int startSector = fileInfo.startingSector;
            StringBuffer aLine;
            Block block = new Block("printingBlock");
            block.fileName = fileInfo.fileName;
            //read from disk, copying to the block
            for (int i = 0; i < fileInfo.fileLength; i++) {

                aLine = fileInfo.disk.read(startSector + i);
                block.add(aLine);
            }
            //send block to printer
            printer.print(block);
            printerMgr.releasePrinter(); //release printer for others thread
            //signal to the disk done
            fileInfo.disk.done();
        }

    }

    class PrinterManager {

        String name;
        Printer printer0;
        Printer printer1;
        Printer printer2;

        PrinterManager(String name, Printer printer0, Printer printer1, Printer printer2) {
            this.name = name;
            this.printer0 = printer0;
            this.printer1 = printer1;
            this.printer2 = printer2;

        }

        public synchronized Printer requestPrinter() {
            //loop check which one is free
            //if yes then assign it
            //otherwise wait()
            while (true) {
                if (!printer0.isBusy) {
                    return this.printer0;
                } else if (!printer1.isBusy) {
                    return this.printer1;
                } else if (!printer2.isBusy) {
                    return this.printer2;
                } else {
                    try {
                        this.wait();
                    } catch (InterruptedException ie) {
                        System.out.println("Printer Manager Interrupt Exception");
                    }
                }
            }
        }

        public synchronized void releasePrinter() {
            //release printer, signal other thread to work
            this.notify();
        }

    }

    class Printer { //HW9

        String name;
        public boolean isBusy;
        File fout;
        BufferedWriter writer;

        Printer(String name) {
            this.name = name;
            isBusy = false;
            String fileNameOut = "outputs/" + this.name;
            //fout = new File(fileNameOut);
            try {
                writer = new BufferedWriter(new FileWriter(fileNameOut));
            } catch (IOException ie) {
                System.out.println("BufferWriter Exception");
            }
            //Open file name PRINTERi and ready to write
            //
        }

        public synchronized void print(Block block) {
            isBusy = true;
            //open a file
            //write each sector to the file
            for (int i = 0; i < block.size; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2750);
                } catch (InterruptedException e) {
                    System.out.println("Printer interrupt");
                }
                try {
                    String message = new String(block.contents[i]);
                    writer.write(message, 0, message.length());
                    writer.newLine();
                    writer.flush();
                } catch (IOException io) {
                    System.out.println("IO Exception");
                }
                System.out.println(name + " printing:" + block.contents[i]);
                //Display on GUI
                if (name.equals("Printer0")) {
                    printer0TextField.setText("printing: " + block.contents[i]);
                } else if (name.equals("Printer1")) {
                    printer1TextField.setText("printing: " + block.contents[i]);
                } else if (name.equals("Printer2")) {
                    printer2TextField.setText("printing: " + block.contents[i]);
                }

            }
            isBusy = false;
            if (name.equals("Printer0")) {
                printer0TextField.setText("done");
            } else if (name.equals("Printer1")) {
                printer1TextField.setText("done");
            } else {
                printer2TextField.setText("done");
            }

        }
    }
    /**
     * *******END BACK-END CODE**************
     */

} //End the Application Main Class

