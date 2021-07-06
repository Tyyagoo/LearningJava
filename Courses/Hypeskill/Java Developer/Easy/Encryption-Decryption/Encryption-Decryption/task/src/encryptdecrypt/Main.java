package encryptdecrypt;

import java.util.*;
import java.io.*;

enum Mode { ENCODE, DECODE }
enum InputMode { ARGS, FILE }
enum OutputMode { SYSTEM, FILE }
enum EncryptMode { UNICODE, SHIFT }

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            EncryptContext encryptContext = EncryptFactory.getEncryptContext(args);
            encryptContext.invoke();
        } catch (IOException e) {
            System.out.println("Your filename is wrong. Please insert an valid filepath!");
        }
    }
}


class EncryptFactory {

    public static EncryptContext getEncryptContext(String[] args) {
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < args.length; i++){
            map.put(args[i], args[++i]);
        }

        EncryptMode encryptMode = (map.getOrDefault("-alg", "shift").equals("shift")) ? EncryptMode.SHIFT : EncryptMode.UNICODE;
        Mode mode = (map.getOrDefault("-mode", "enc").equals("enc")) ? Mode.ENCODE : Mode.DECODE;
        int key = Integer.parseInt(map.getOrDefault("-key", "0"));
        InputMode inputMode = (map.getOrDefault("-data", "").equals("")) ?
                map.getOrDefault("-in", "").equals("") ?
                        InputMode.ARGS : InputMode.FILE
                : InputMode.ARGS;
        OutputMode outputMode = (map.getOrDefault("-out", "").equals("")) ? OutputMode.SYSTEM : OutputMode.FILE;

        IOWrapper ioWrapper = new IOWrapper(inputMode, outputMode,
                (inputMode == InputMode.FILE) ? map.get("-in") : map.getOrDefault("-data", ""),
                map.get("-out"));

        return new EncryptContext(encryptMode, ioWrapper, mode, key);
    }
}


class IOWrapper {

    public final InputMode inputMode;
    public final OutputMode outputMode;


    private String argsInput;
    private String filepathInput;
    private String filepathOutput;

    IOWrapper(InputMode inputMode, OutputMode outputMode, String ...args) {
        this.inputMode = inputMode;
        this.outputMode = outputMode;

        int index = 0;
        if (inputMode == InputMode.FILE) {
            filepathInput = args[index++];
        } else {
            argsInput = args[index++];
        }

        if (outputMode == OutputMode.FILE) {
            filepathOutput = args[index];
        }
    }

    public String getInput() throws FileNotFoundException {
        if (inputMode == InputMode.FILE) {
            try (Scanner scanner = new Scanner(new File(filepathInput))) {
                return scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getClass().getName());
                return "";
            }
        } else {
            return argsInput;
        }
    }

    public void putOutput(String output) throws IOException {
        if (outputMode == OutputMode.FILE) {
            PrintWriter fileWriter = new PrintWriter(filepathOutput);
            fileWriter.print(output);
            fileWriter.close();
        } else {
            System.out.println(output);
        }
    }
}


class EncryptContext {

    private EncryptAlgorithm encryptAlgorithm;
    private Mode mode = Mode.ENCODE;
    private final int key;

    private IOWrapper ioWrapper;

    EncryptContext(EncryptMode encryptMode, IOWrapper ioWrapper, Mode mode, int key) {
        this.mode = mode;
        this.key = key;
        this.ioWrapper = ioWrapper;

        if (encryptMode == EncryptMode.UNICODE) {
            encryptAlgorithm = new UnicodeEncrypt();
        } else {
            encryptAlgorithm = new ShiftEncrypt();
        }
    }

    public void invoke() throws IOException {
        String outputString = encryptAlgorithm.execute(mode, ioWrapper.getInput(), key);
        ioWrapper.putOutput(outputString);
    }

    public void setEncryptAlgorithm(EncryptAlgorithm newAlg) {
        encryptAlgorithm = newAlg;
    }
}


interface EncryptAlgorithm { // strategy interface

    default String execute(Mode mode, String text, int key) {
        switch (mode) {
            case DECODE:
                return _decode(text, key);
            case ENCODE:
                return _encode(text, key);
            default:
                return "";
        }
    }
    String _encode(String text, int key);
    String _decode(String text, int key);
}


class UnicodeEncrypt implements EncryptAlgorithm {

    @Override
    public String _encode(String text, int key) {
        StringBuilder stringBuilder = new StringBuilder();

        for(char letter: text.toCharArray()){
            char newLetter = (char) ((int) letter + key);
            stringBuilder.append(newLetter);
        }

        return stringBuilder.toString();
    }

    @Override
    public String _decode(String text, int key) {
        StringBuilder stringBuilder = new StringBuilder();

        for(char letter: text.toCharArray()){
            char newLetter = (char) ((int) letter - key);
            stringBuilder.append(newLetter);
        }

        return stringBuilder.toString();
    }
}


class ShiftEncrypt implements EncryptAlgorithm {

    @Override
    public String _encode(String text, int key) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char letter: text.toCharArray()) {
            int asciiCode = letter;
            if (asciiCode >= 65 && asciiCode <= 90) {
                // process uppercase letters
                if (asciiCode + key > 90) {
                    stringBuilder.append((char) (64 + (asciiCode + key - 90)));
                } else {
                    stringBuilder.append((char) (asciiCode + key));
                }
                continue;
            }

            if (asciiCode >= 97 && asciiCode <= 122) {
                // process lowercase letters
                if (asciiCode + key > 122) {
                    stringBuilder.append((char) (96 + (asciiCode + key - 122)));
                } else {
                    stringBuilder.append((char) (asciiCode + key));
                }
                continue;
            }
            stringBuilder.append(letter);
        }

        return stringBuilder.toString();
    }

    @Override
    public String _decode(String text, int key) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char letter: text.toCharArray()) {
            int asciiCode = letter;
            if (asciiCode >= 65 && asciiCode <= 90) {
                // process uppercase letters
                if (asciiCode - key < 65) {
                    stringBuilder.append((char) (91 - (65 - (asciiCode - key))));
                } else {
                    stringBuilder.append((char) (asciiCode - key));
                }
                continue;
            }

            if (asciiCode >= 97 && asciiCode <= 122) {
                // process lowercase letters
                if (asciiCode - key < 97) {
                    stringBuilder.append((char) (123 - (97 - (asciiCode - key))));
                } else {
                    stringBuilder.append((char) (asciiCode - key));
                }
                continue;
            }
            stringBuilder.append(letter);
        }

        return stringBuilder.toString();
    }
}

