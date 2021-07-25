package com.tyyagoo;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) throws IOException {
        findDirectoryWithMostFiles(args[0]);
    }

    public static void getDeepestFileName(String _path) throws IOException {
        int deepestIndex = 0;
        Path deepestPath = null;
        Path path = Path.of(_path);
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::exists)
                    .collect(Collectors.toList());
        }

        for (Path p: result) {
            if (p.getNameCount() > deepestIndex) {
                deepestIndex = p.getNameCount();
                deepestPath = p;
            }
        }
        System.out.println(deepestPath);
        System.out.println(deepestIndex);
    }

    public static void findEmptyDirectories(String rootPath) throws IOException {
        Path path = Path.of(rootPath);
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isDirectory).collect(Collectors.toList());
        }

        for (Path p: result) {
            File file = p.toFile();
            if (file.listFiles().length == 0) {
                System.out.println(file.getName());
            }
        }
    }

    public static void findDirectoryWithMostFiles(String rootPath) throws IOException {
        Path path = Path.of(rootPath);
        List<Path> directories;
        try (Stream<Path> walk = Files.walk(path)) {
            directories = walk.filter(Files::isDirectory).collect(Collectors.toList());
        }

        int numberOfFiles = 0;
        File directory = null;
        for (Path p: directories) {
            File dir = p.toFile();
            int c = 0;
            for (File f: dir.listFiles()) {
                if (f.isFile()) c++;
            }

            if (c > numberOfFiles) {
                numberOfFiles = c;
                directory = dir;
            }
        }

        System.out.println(directory.getName());
        System.out.println(numberOfFiles);
    }
    

}
