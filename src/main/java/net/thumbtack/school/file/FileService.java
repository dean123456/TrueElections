package net.thumbtack.school.file;

import com.google.gson.Gson;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.ColoredRectangle;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

import java.io.*;
import java.util.Scanner;

public class FileService {

    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        FileService.writeByteArrayToBinaryFile(new File(fileName), array);
    }

    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return FileService.readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        byte[] array = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(array);
        }
        return array;
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        byte[] byteArray;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(array);
            byteArray = baos.toByteArray();
        }

        int count = (byteArray.length + 1) / 2;

        byte[] result = new byte[count];
        try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray)) {
            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) bais.read();
                bais.skip(1);
            }
        }
        return result;
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        byte[] array = new byte[(int) file.length()];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(array);
        }
        return array;
    }

    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(rect.getTopLeft().getX());
            dos.writeInt(rect.getTopLeft().getY());
            dos.writeInt(rect.getBottomRight().getX());
            dos.writeInt(rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            return new Rectangle(dis.readInt(), dis.readInt(), dis.readInt(), dis.readInt());
        }
    }

    public static void writeColoredRectangleToBinaryFile(File file, ColoredRectangle rect) throws IOException {
        writeRectangleToBinaryFile(file, rect);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file, true))) {
            dos.writeUTF(rect.getColor().toString());
        }
    }

    public static ColoredRectangle readColoredRectangleFromBinaryFile(File file) throws IOException, ColorException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            return new ColoredRectangle(dis.readInt(), dis.readInt(), dis.readInt(), dis.readInt(), dis.readUTF());
        }
    }

    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] rects) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            for (Rectangle r : rects) {
                dos.writeInt(r.getTopLeft().getX());
                dos.writeInt(r.getTopLeft().getY());
                dos.writeInt(r.getBottomRight().getX());
                dos.writeInt(r.getBottomRight().getY());
            }
        }
    }

    public static Rectangle[] readRectangleArrayFromBinaryFileReverse(File file) throws IOException {
        Rectangle[] rects = new Rectangle[(int) (file.length() / 16)];
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            for (int i = 0; i < rects.length; i++) {
                raf.seek(file.length() - 16 * (i + 1));
                rects[i] = new Rectangle(raf.readInt(), raf.readInt(), raf.readInt(), raf.readInt());
            }
        }
        return rects;
    }

    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        try (PrintStream ps = new PrintStream(file)) {
            ps.printf("%s %s %s %s", rect.getTopLeft().getX(), rect.getTopLeft().getY(), rect.getBottomRight().getX(), rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException {
        try (Scanner s = new Scanner(new FileReader(file))) {
            return new Rectangle(Integer.parseInt(s.next()), Integer.parseInt(s.next()),
                    Integer.parseInt(s.next()), Integer.parseInt(s.next()));
        }
    }

    public static void writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException {
        try (PrintStream ps = new PrintStream(file)) {
            ps.println(rect.getTopLeft().getX());
            ps.println(rect.getTopLeft().getY());
            ps.println(rect.getBottomRight().getX());
            ps.print(rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromTextFileFourLines(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return new Rectangle(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()),
                    Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
        }
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (PrintStream ps = new PrintStream(file, "UTF-8")) {
            ps.printf("%s %s %s", trainee.getFirstName(), trainee.getLastName(), trainee.getRating());
        }
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, NumberFormatException, TrainingException {
        try (Scanner s = new Scanner(new FileReader(file))) {
            return new Trainee(s.next(), s.next(), Integer.parseInt(s.next()));
        }
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException, NumberFormatException, TrainingException {
        try (PrintStream ps = new PrintStream(file, "UTF-8")) {
            ps.println(trainee.getFirstName());
            ps.println(trainee.getLastName());
            ps.print(trainee.getRating());
        }
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException, NumberFormatException, TrainingException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return new Trainee(br.readLine(), br.readLine(), Integer.parseInt(br.readLine()));
        }
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(trainee);
        }
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws ClassNotFoundException, IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Trainee) ois.readObject();
        }
    }

    public static String serializeTraineeToJsonString(Trainee trainee) {
        return new Gson().toJson(trainee);
    }

    public static Trainee deserializeTraineeFromJsonString(String json) {
        return new Gson().fromJson(json, Trainee.class);
    }

    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            new Gson().toJson(trainee, bw);
        }
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return new Gson().fromJson(br, Trainee.class);
        }
    }

}
