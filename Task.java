package WhichShouldIDo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Task {

    private Scanner _reader;
    private List<String> _taskList;
    private List<String> _choices;
    private int _minMin;
    private int _maxMin;
    private String _savePath;

    Task() {
        _reader = new Scanner(System.in);
        _taskList = new ArrayList<>();
        _choices = new ArrayList<>();
        _minMin = 0;
        _maxMin = 90;
        load();
        createChoices();
    }

    private void exit() {
        _reader.close();
    }

    private void makeSave() {
        System.out.println("What text document should we save to and load from?");
        System.out.println("Input path or type X to skip. ");
        String prompt = _reader.nextLine();
        while (prompt.equals("\n")) {
            prompt = _reader.nextLine();
        }
        if (!prompt.equals("X")) {
            _savePath = prompt;
        }
        load();
    }

    private void save() {
        try {
            File file = new File(_savePath);
            file.createNewFile();
            FileWriter write = new FileWriter(file);
            for (String task : _taskList) {
                write.write(task + "\n");
            }
            write.close();
        }
        catch(Exception e) {
            System.out.println("Could not make save.");
            makeSave();
        }
    }

    private void load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(_savePath));
            String task = br.readLine();
            while (task != null) {
                _taskList.add(task);
                task = br.readLine();
            }
        }
        catch(Exception e) {
            System.out.println("Could not make load.");
            makeSave();
        }
    }

    private void createChoices(){
        _choices.add("T: Make new task");
        _choices.add("D: Delete task");
        _choices.add("-: Set minimum task duration (minutes)");
        _choices.add("+: Set maximum task duration (minutes)");
        _choices.add("?: Which Should I Do?");
        _choices.add("X: Exit");
    }

    private void viewTasks() {
        System.out.println("Current Tasks: ");
        int i = 0;
        for (String item : _taskList) {
            System.out.println(i + ": " + item);
            i++;
        }
    }

    private void parseMenu() {
        String task = _reader.nextLine();
        switch(task) {
            case "T":
                makeTask();
                break;
            case "D":
                deleteTask();
                break;
            case "-":
                makeMin();
                break;
            case "+":
                makeMax();
                break;
            case "?":
                makeRandom();
                break;
            case "X":
                exit();
                break;
            case "\n":
                makeChoice();
                break;
            default:
                System.out.println("I don't understand. Please try again.");
                makeChoice();
                break;
        }
    }

    void makeChoice() {
        save();
        System.out.print("\n");
        viewTasks();
        System.out.print("\n");
        for (String choice : _choices) {
            System.out.println(choice);
        }
        System.out.print("What do you want to do? ");
        parseMenu();
    }

    private void makeTask() {
        System.out.print("Enter a Task: ");
        String task = _reader.nextLine();
        _taskList.add(task);
        System.out.print("Continue (Y/N)? ");
        String prompt = _reader.nextLine();
        while (prompt.equals("\n")) {
            prompt = _reader.nextLine();
        }
        if (prompt.equals("Y")) {
            makeTask();
        } else {
            makeChoice();
        }
    }

    private void deleteTask() {
        System.out.print("Enter Task Number to Remove: ");
        int rm = _reader.nextInt();
        if (_taskList.size() > rm) {
            _taskList.remove(rm);
            System.out.println("Removed task " + rm + ".");
        } else {
            System.out.println("Could not remove task.");
        }
        System.out.print("Continue (Y/N)? ");
        String prompt = _reader.nextLine();
        while (prompt.equals("\n")) {
            prompt = _reader.nextLine();
        }
        if (prompt.equals("Y")) {
            deleteTask();
        } else {
            makeChoice();
        }
    }

    private void makeMin() {
        System.out.print("Enter a minimum: ");
        int min = _reader.nextInt();
        if (min >= _maxMin) {
            System.out.println("Invalid minimum.");
        } else {
            System.out.println("Minimum set to " + _minMin + ".");
            _minMin = min;
        }
        System.out.print("Continue (Y/N)? ");
        String prompt = _reader.nextLine();
        while (prompt.equals("\n")) {
            prompt = _reader.nextLine();
        }
        if (prompt.equals("Y")) {
            makeMin();
        } else {
            makeChoice();
        }
    }

    private void makeMax() {
        System.out.print("Enter a maximum: ");
        int max = _reader.nextInt();
        if (max <= _minMin) {
            System.out.println("Invalid maximum.");
        } else {
            System.out.println("Maximum set to " + _maxMin + ".");
            _maxMin = max;
        }
        System.out.print("Continue (Y/N)? ");
        String prompt = _reader.nextLine();
        while (prompt.equals("\n")) {
            prompt = _reader.nextLine();
        }
        if (prompt.equals("Y")) {
            makeMax();
        } else {
            makeChoice();
        }
    }

    private void makeRandom() {
        Random r = new Random();
        int duration = r.nextInt((_maxMin - _minMin) + 1) + _minMin;
        String task;
        if (_taskList.size() == 0) {
            task = "Take it easy";
        } else {
            task = _taskList.get(r.nextInt(_taskList.size()));
        }
        System.out.println(task + " for " + duration + " minutes.");
        System.out.print("Continue (Y/N)? ");
        String prompt = _reader.nextLine();
        while (prompt.equals("\n")) {
            prompt = _reader.nextLine();
        }
        if (prompt.equals("Y")) {
            makeRandom();
        } else {
            makeChoice();
        }
    }

}
