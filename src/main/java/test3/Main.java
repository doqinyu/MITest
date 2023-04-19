package test3;

public class Main {

    public static void main(String[] args) {
        int fileSize = 10;
        for (int i = 0; i< fileSize; i++) {
            File file = new File();
            file.readFromFile();
            file.start();
        }

        newFile newFile = new newFile();
        Thread mergeThread = new Thread(newFile);
        mergeThread.start();
    }
}
